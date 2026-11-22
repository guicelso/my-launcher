# 008 — Navegação embutida (Waze/Maps no painel)

**Status:** Pesquisa concluída · prova de conceito proposta (aguardando aprovação) · **Depende de:** 005, 006 · **Design:** artboard "Início · navegação embutida"

## Objetivo
Rodar o app de navegação (Google Maps ou Waze) **dentro** do card de mapa do painel, como faz o Android Auto, e trocar o `OwnMapPane` pelo `EmbeddedAppPane`.

## O que a pesquisa mostrou (2026-11-22)

### Testado na multimídia
| Teste | Resultado |
|---|---|
| `pm list features` | Tem `activities_on_secondary_displays` e `picture_in_picture`. `ro.config.low_ram` vazio (não é aparelho de pouca RAM). |
| Maps em display secundário (`overlay_display_devices=800x600/160` + `am start --display 1`) | **Funciona.** O Maps 26.46 desenhou completo no display 1 (GPS, tema escuro, conta), com o Lumia ativo no display 0 ao mesmo tempo. O firmware respeita `--display`. |
| `allowEmbedded` no manifesto | Maps: só na `GhostActivity` do Android Auto, **não** na `MapsActivity`. Waze 5.27: nenhuma. Ambos `resizeableActivity=true`. |
| **Spike S8.1** (`TWUtil.write(0x9F1A, …)` a partir do Lumia) | Reprovado **por essa via**: a chamada não dá erro, mas o comando não roda, porque o executor root do firmware (`cmd_services`) fica desligado. **Porém a via existe** — ver "Root: como conseguimos" abaixo. |
| Apps instalados | Maps, Waze, HERE, **ZLink** (receptor CarPlay/Android Auto da zjinnova) e um *stub* do Android Auto. |

### O que o código do Android 10 permite (AOSP `android10-release`)
- **Criar o display:** qualquer app cria um `VirtualDisplay` com `PUBLIC | OWN_CONTENT_ONLY` sem permissão (`DisplayManagerService.createVirtualDisplay`).
- **Abrir outro app nele:** um app comum **não consegue**. `ActivityStackSupervisor.isCallerAllowedToLaunchOnDisplay` exige `INTERNAL_SYSTEM_WINDOW` ou `allowEmbedded` + `ACTIVITY_EMBEDDING`. O uid **shell** tem `INTERNAL_SYSTEM_WINDOW`, `ACTIVITY_EMBEDDING`, `INJECT_EVENTS` e `MANAGE_ACTIVITY_STACKS`, então um auxiliar rodando como shell consegue.
- **Limitação estrutural ("fugas"):** quando o Maps abre **outra tela própria** (permissão, ajustes, alguns fluxos de busca), quem pede é o uid do Maps. Como o display não pertence ao uid system e a activity não tem `allowEmbedded`, o sistema joga essa tela para o display principal. Só um display do uid system (root) evita isso no Android 10. Projetos parecidos contornam com um auxiliar que vigia as tarefas e as devolve ao display do card (`moveStackToDisplay`).
- **Ciclo de vida:** um `VirtualDisplay` sem `Surface` é tratado como desligado e põe o Maps para dormir. Se o processo dono morrer, o display é destruído. Trocar por uma `Surface` reserva em vez de `null` quando o card some.
- **Teclado:** aparece no display principal (não no card), a não ser com `force_desktop_mode_on_external_displays`.
- **Desempenho:** a GPU compõe o display virtual com uma passada GLES extra (a HWC não costuma tratar displays virtuais). O caminho estilo `ActivityView` (abaixo) evita essa passada.

### Root: como conseguimos (confirmado no carro, 2026-11-22)
O firmware tem uma **porta dos fundos de root do próprio fabricante** que é alcançável sem a gente ter privilégio nenhum. Condições que tornam isso possível: **SELinux permissivo** (negações são só registradas, não bloqueiam) e **bootloader destravado**.

**Mecanismo:**
1. Serviço do init `cmd_services` (`/system/bin/cmd_services`, `user root`, `group system`, `oneshot`, `disabled`). Em `/system/etc/init/cmd_services.rc` há `on property:persist.sys.cmdservice.enable=enable → start cmd_services`.
2. Ao setar `persist.sys.cmdservice.enable=enable`, o init sobe o `cmd_services` **como root**. A propriedade é `system_prop` (normalmente nem `shell` nem app escrevem), mas com SELinux permissivo o `shell` consegue — testado. (Falta testar a partir de um app; provável que também passe.)
3. O daemon escuta no socket Unix **abstrato `@cmd_skt`**. Como é abstrato (sem permissão de arquivo) e o SELinux é permissivo, **qualquer processo conecta**.
4. Protocolo (cliente de referência: `com.sprd.engineermode`, `CmdUtils.run` + `SocketUtils.sendCmd`): conectar no `cmd_skt` (namespace ABSTRACT), escrever `[origem][tid]<comando\n>`, ler a resposta. O daemon roda o comando com `popen` **como root** e devolve a saída.
5. O daemon é `oneshot` e sai quando fica ocioso. O padrão do EngineerMode é religar a cada uso: `enable` → `sendCmd` → `disable`. Como a propriedade é `persist.`, ela sobrevive ao reboot (o `on property` dispara no boot).

**Prova (2026-11-22):** liguei o `cmd_services`, conectei no `@cmd_skt` com um cliente mínimo (`LocalSocket`) e mandei `id > arquivo`. O arquivo saiu com dono **`root:system`, modo `0600`** (o `shell` nem lê). Confirma execução como root. Depois desliguei o daemon e limpei tudo — o aparelho ficou no estado original.

**O que isso muda na spec 008:** com root podemos iniciar o auxiliar **como root** (ou criar o display virtual pertencente ao uid system), o que **elimina as "fugas"** de telas do Maps para o display principal — a maior limitação do plano sem root. É, na prática, o melhor dos caminhos.

**Pendências de root a resolver antes de usar em produção:**
- Testar o `SystemProperties.set("persist.sys.cmdservice.enable", …)` **a partir do Lumia** (app, não `shell`). Se não passar (API oculta/greylist no API 29), alternativas: (a) ligar uma vez pelo `deploy.sh` via adb e manter o Lumia com uma conexão aberta no `@cmd_skt` para o daemon não sair; (b) usar o `TWUtil.write(0x9F1A, …)`, que agora deve funcionar com o daemon ligado.
- Decidir a política de segurança: o `@cmd_skt` é um root irrestrito acessível a **qualquer app** do aparelho enquanto estiver ligado. Manter ligado o mínimo necessário e, idealmente, religar só sob demanda (como o EngineerMode).
- **S8.3 (Magisk):** com esse root dá para `dd` o `boot` (ler/gravar), patch e flash sem USB. Continua **alto risco** e sob a regra do projeto: só com firmware completo em mãos e aprovação explícita.

### Alternativas avaliadas
| Alternativa | Veredito |
|---|---|
| **Car App Library** (ser o "host" do Maps/Waze) | Inviável: o host precisa ser privilegiado ou estar na lista de assinaturas do `HostValidator`, e o Maps no Android Auto nem usa essa biblioteca. |
| **Maps SDK / Navigation SDK** dentro do Lumia | Proibido pelos termos da Google Maps Platform: "No Use in Embedded Vehicle Systems". |
| **Janela livre (freeform)** sobre o card | Possível com reflexão (`setLaunchWindowingMode` é oculta), mas tem barra de título e o painel pode cobrir a janela ao ser tocado. Plano B. |
| **Picture-in-picture** | Sem controle de posição/tamanho por app comum e sem interação real com o mapa. Descartado. |
| **Receptor Android Auto** (ZLink ou Headunit Revived, AGPL) | É o Android Auto de verdade, mas depende do celular e não fica dentro do nosso painel sem o mesmo truque de display. Fica como opção separada. |
| **Root (`cmd_services`)** | **Disponível** (ver "Root: como conseguimos"). Permite auxiliar/display como uid system, acabando com as fugas. Preferir este ao Magisk. |
| **Root total (S8.3, Magisk)** | Só se quisermos virar priv-app de verdade. Alto risco; bloqueado pelas regras do projeto: só com firmware completo e aprovação explícita. |

## Técnica revisada
Um **auxiliar como uid shell** (`app_process`, mesmo modelo do servidor do scrcpy, Apache-2.0) faz só o que o launcher não pode fazer. O resto fica no launcher.

1. **O Lumia cria o display** (`PUBLIC | OWN_CONTENT_ONLY`), do tamanho do card.
   - *PoC:* a `Surface` vem de um `SurfaceView`/`TextureView` do card (simples, com uma passada GLES extra).
   - *Depois:* estilo `ActivityView` (Android 10): display sem `Surface` enxertado na árvore de camadas do card com `IWindowSession.reparentDisplayContent()`. O WindowManager cria uma janela-portal e **os toques chegam nativos**, sem injeção. Exige API oculta (liberar com `settings put global hidden_api_policy 1` pelo adb, ou com uma biblioteca de bypass).
2. **O auxiliar abre o Maps/Waze** no display com `ActivityOptions.setLaunchDisplayId`.
3. **Toques** (só na PoC com `Surface`): o card repassa os eventos ao auxiliar, que injeta com `InputEvent.setDisplayId` + `InputManager.injectInputEvent`.
4. **Tela cheia:** `VirtualDisplay.resize()` no mesmo display. Nunca mover a tarefa para o display 0, que reinicia a activity.
5. **Fugas:** o auxiliar escuta as tarefas (`TaskStackListener`) e devolve ao display do card as telas do app de navegação que caírem no display 0.
6. **Mapa próprio pausado** enquanto o app embutido estiver visível (uma só renderização pesada na GPU).

### Como iniciar o auxiliar
| Caminho | Situação |
|---|---|
| **adb do notebook** (`adb shell app_process …`) | Bom para a PoC. O processo sobrevive à espera da chave (ACC), porque o `TWService` só fecha pacotes; morre no boot a frio. |
| **S8.2 — adb local** (o Lumia conecta em `127.0.0.1:5555` com a própria chave RSA, por exemplo com a biblioteca `dadb`) | Caminho de produção. Exige cadastrar a chave do Lumia em `adb_keys` uma vez (mesmo método do runbook §3.3). Risco: o adbd às vezes não volta depois do boot a frio (runbook §3.2); nesse caso o card cai no mapa próprio (R8.1). |

## Requisitos
- **R8.1** O `EmbeddedAppPane` DEVE implementar `NavigationPane` e ser selecionável nos ajustes (Maps ou Waze). SE o auxiliar não estiver disponível, ENTÃO o launcher DEVE cair automaticamente para o `OwnMapPane`.
- **R8.2** O app embutido DEVE receber toques e gestos (arrastar e pinça) com latência ≤ 50 ms.
- **R8.3** QUANDO o usuário abrir o mapa em tela cheia, o mesmo display DEVE ser redimensionado, sem reiniciar o app de navegação.
- **R8.4** O áudio das instruções DEVE continuar funcionando, com a mídia abaixando o volume durante as falas.
- **R8.5** O auxiliar DEVE aceitar comandos **apenas** do uid do launcher (verificação de `Binder.getCallingUid`), nunca pela rede.
- **R8.6** QUANDO uma tela do app de navegação cair no display principal, o auxiliar DEVE devolvê-la ao display do card em ≤ 1 s.
- **R8.7** ENQUANTO o app embutido estiver visível, o `OwnMapPane` DEVE ficar pausado.

## Critérios de aceite
- [x] **A1** Spikes S8.1 e S8.2 avaliados (S8.1: **root confirmado** via `cmd_services`/`@cmd_skt`; S8.2 adb local é a alternativa de execução do auxiliar).
- [ ] **A2** Prova de conceito: o Maps aparece dentro do card e responde a toques (auxiliar iniciado pelo adb do notebook).
- [ ] **A3** (R8.1) Ao matar o auxiliar, o card volta ao mapa próprio em ≤ 2 s.
- [ ] **A4** (R8.4) Navegando com música tocando, a fala do Maps abaixa a música.
- [ ] **A5** (R8.3) Card → tela cheia → card sem o Maps reiniciar.
- [ ] **A6** `dumpsys gfxinfo` do Lumia com ≤ 10% de quadros lentos durante 60 s com o Maps navegando no card.

## Plano da prova de conceito (proposta)
1. Auxiliar mínimo em `helper/` (módulo Java puro, empacotado como dex), iniciado por `tools/nav-helper.sh` via adb do notebook. Binder entregue ao Lumia por broadcast.
2. `EmbeddedAppPane` com `SurfaceView` do tamanho fixo da área de trabalho (mesma regra do mapa atual: nunca redimensionar durante animação), display virtual e repasse de toques.
3. Medir no carro: fluidez, latência do toque, fugas (busca, permissões), áudio, ciclo da chave.
4. Se a PoC passar: trocar para o caminho `ActivityView` e implementar o S8.2.

## Riscos
- **Fugas** de telas do Maps/Waze para o display principal (limitação do Android 10 sem root; mitigada pelo R8.6).
- **GPU:** dois apps desenhando ao mesmo tempo na PowerVR GE8322 (mitigado pelo R8.7 e, depois, pelo caminho `ActivityView`).
- **adb depois do boot a frio:** se não voltar, o card fica no mapa próprio até reativar o adb.
- **O S8.3 só é executado com o firmware completo em mãos e com aprovação explícita do dono.**

## Fontes
- scrcpy `--new-display` (Android 10+): `server/.../video/NewDisplayCapture.java`, `Device.startApp`, PRs #5370 e #5838.
- AOSP `android10-release`: `DisplayManagerService.createVirtualDisplay`, `ActivityStackSupervisor.isCallerAllowedToLaunchOnDisplay`, `ActivityView.java`, `VirtualDisplayAdapter.java`, `packages/Shell/AndroidManifest.xml`.
- Projetos parecidos (unidade K706, Android 10): open-car-project PR #88, discussão #48 do Dashwheel.
- Termos da Google Maps Platform (§ "No Use in Embedded Vehicle Systems").

## Resultados
**2026-11-22, na multimídia (via adb):**
- S8.1: **root confirmado** via `cmd_services`/`@cmd_skt` — comando rodou como `root:system`. (A via direta do `TWUtil` 0x9F1A ainda depende do daemon estar ligado.)
- Maps renderiza normalmente em display secundário, com GPS e conta, ao lado do Lumia.
- Recursos de multi-display presentes no firmware.
- **Root confirmado** via `cmd_services`/`@cmd_skt` (comando rodou como `root:system`). Daemon religado e desligado ao fim; aparelho no estado original.
