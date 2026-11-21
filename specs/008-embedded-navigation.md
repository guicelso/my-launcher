# 008 — Navegação embutida (Waze/Maps no painel)

**Status:** Pesquisa · **Depende de:** 005, 006 · **Design:** artboard "Início · navegação embutida"

## Objetivo
Rodar o app de navegação (Waze ou Google Maps) **dentro** do card de mapa do painel, como faz o Android Auto, e trocar o `OwnMapPane` pelo `EmbeddedAppPane`.

## Técnica
Um **processo auxiliar privilegiado** faz o trabalho que um app comum não pode fazer:
1. Cria um `VirtualDisplay` cuja `Surface` é a do `SurfaceView` do nosso card, recebida via Binder (sem cópia).
2. Abre o Waze nesse display (`ActivityOptions.setLaunchDisplayId`, ou `am start --display <id>`).
3. Repassa os toques do card para o display com `InputManager.injectInputEvent` + `setDisplayId`.
4. Ao trocar o card para tela cheia, redimensiona o display (`VirtualDisplay.resize`).

A referência de implementação é o **scrcpy** (Apache-2.0), cuja opção `--new-display` faz exatamente isso no Android 10 ou superior, rodando como uid `shell`.

## Como obter o processo privilegiado (spikes, em ordem de custo)

| Spike | Caminho | Risco | Persistência no boot |
|---|---|---|---|
| **S8.1** | `TWUtil.write(0x9F1A, 1, 0, "<cmd>")`, que executa um comando como root (`docs/00-discovery.md` §4.2). Se funcionar a partir de um app comum, iniciamos o auxiliar com `app_process` como root. | Baixo (não grava partições) | O launcher dispara a cada boot |
| **S8.2** | adb local: o launcher conecta em `127.0.0.1:5555` com a própria chave RSA (autorizada uma vez) e roda o auxiliar como uid `shell` | Baixo | Depende do adb persistir (pendência da spec 001) |
| **S8.3** | Root com **Magisk**: precisa do `boot.img` do firmware oficial TS18 e de um meio de gravação sem USB. S8.1 pode servir como esse meio (`dd` como root). Com isso, o launcher vira priv-app via módulo Magisk. | **Alto**: um boot mal gravado deixa a multimídia sem ligar. Exige firmware completo como plano de socorro e aprovação explícita. | Total |

## Requisitos (para quando a pesquisa concluir)
- **R8.1** O `EmbeddedAppPane` DEVE implementar `NavigationPane` e ser selecionável nos ajustes. SE o auxiliar não estiver disponível, ENTÃO o launcher DEVE cair automaticamente para o `OwnMapPane`.
- **R8.2** O app embutido DEVE receber toques e gestos (arrastar e pinça) com latência ≤ 50 ms.
- **R8.3** QUANDO o usuário abrir o mapa em tela cheia, o mesmo display DEVE ser redimensionado, sem reiniciar o app de navegação.
- **R8.4** O áudio das instruções do Waze DEVE continuar funcionando, com a mídia abaixando o volume durante as falas.
- **R8.5** O auxiliar DEVE aceitar comandos **apenas** do uid do launcher (verificação de `Binder.getCallingUid`), nunca pela rede.

## Critérios de aceite
- [ ] **A1** Spikes S8.1 e S8.2 executados, com resultados registrados abaixo.
- [ ] **A2** Prova de conceito: o Waze aparece dentro do card e responde a toques.
- [ ] **A3** (R8.1) Ao matar o auxiliar, o card volta ao mapa próprio em ≤ 2 s.
- [ ] **A4** (R8.4) Navegando com música tocando, a fala do Waze abaixa a música.

## Riscos
- O Waze/Maps pode se recusar a renderizar ou a tocar áudio em um display secundário. Isso é testado na prova de conceito.
- Lentidão: dois apps desenhando ao mesmo tempo na GPU PowerVR GE8322.
- **O S8.3 só é executado com o firmware completo em mãos e com aprovação explícita do dono.**

## Resultados
_(preencher após a validação)_
