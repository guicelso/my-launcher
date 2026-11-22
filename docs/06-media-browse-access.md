# 06 — Por que a navegação e a busca ficam indisponíveis, e como liberar

Estudo de 2026-11-22, feito na multimídia com Spotify 9.2.0.2053 e YouTube Music 9.46.55 depois da implementação da spec 018. Complementa o [`05-media-android-auto.md`](05-media-android-auto.md), seção 5.

## 1. Diagnóstico (medido no carro)

| App | O que o app respondeu ao Lumia | Log |
|---|---|---|
| YouTube Music | `onGetRoot` devolveu `null` | `MediaBrowserService: No root for client app.mylauncher` |
| Google Notícias | Recusa explícita por chamador | `Caller %s is not allowed to use the news media browser service` + `No root for client app.mylauncher` |
| Spotify | Conexão recusada (mesmo sintoma) | `Browse com.spotify.music indisponível: recusou ou não respondeu` |

**Não é defeito do Lumia.** O próprio app de mídia escolhe quem pode navegar no catálogo dele. A documentação oficial manda fazer exatamente isso ([content-hierarchy, "package validation"](https://developer.android.com/training/cars/media/create-media-browser/content-hierarchy)): aceitar o sistema, o Android Auto, o Android Automotive e o Assistente, e recusar pacotes desconhecidos. O Lumia é um pacote desconhecido para eles.

O app simulado do painel de depuração aceita o Lumia, e com ele a navegação, a busca e as ações funcionam por inteiro (testado no emulador). Isso confirma que o código do host está certo.

### Quem esses apps aceitam
As validações de pacote seguem estes caminhos (código de referência UAMP/`PackageValidator` e documentação):

| Chamador aceito | Como o app reconhece | O Lumia consegue ser? |
|---|---|---|
| O sistema (uid 1000) | uid | Não |
| Android Auto (`com.google.android.projection.gearhead`) e Assistente | pacote + assinatura do Google | Não (assinatura) |
| Media Center do Android Automotive / apps do fabricante | assinatura de **plataforma** do aparelho | Não: a chave da TopWay é própria (`CN=John, O=TW`, SHA-1 `409d305d…9cd0`), e não a de teste do AOSP |
| Quem tem `MEDIA_CONTENT_CONTROL` | permissão `signature\|privileged` | Só como app privilegiado em `/system` (ver opção E) |
| Ouvintes de notificação habilitados (`isTrustedForMediaControl`) | alguns apps com Media3 | O Lumia já é um, e mesmo assim o YouTube Music recusou |

### O que já funciona sem navegação
O controle pela sessão (a mesma que o Lumia lê pelo acesso às notificações) não passa por essa validação. As ações que cada app aceita pela sessão:

| Ação da sessão | Spotify | YouTube Music |
|---|---|---|
| Tocar/pausar, anterior/próxima, arrastar | ✔ | ✔ |
| Aleatório e repetir | ✔ | ✔ |
| **Tocar por busca** (`PLAY_FROM_SEARCH`, "toque X") | ✔ | ✔ |
| **Tocar por URI** (`PLAY_FROM_URI`) | ✔ | ✔ |
| Tocar por id (`PLAY_FROM_MEDIA_ID`) | ✗ | ✔ |
| Tocar um item da fila (`SKIP_TO_QUEUE_ITEM`) | ✗ | ✗ |
| Ações personalizadas (curtir, rádio, coleção…) | ✔ (4) | ✔ (4) |

Achado colateral: nenhum dos dois aceita "tocar item da fila". Hoje o toque num item da fila não faz nada; o Lumia deve desabilitar o toque quando a ação não existir (regra GB-1: botão desabilitado não pode parecer ativo).

### O que a multimídia oferece
- O **app Android Auto completo** está instalado (`gearhead` 18.0.664264, atualizado em `/data/app` sobre o stub de `/system/product/priv-app/AndroidAutoStub`), assim como o Assistente. São esses os chamadores que os apps aceitam.
- Root pela porta dos fundos do fabricante (`cmd_services`, spec 008).
- Partição de sistema montada **somente leitura** com **dm-verity `enforcing`** (`ro.boot.veritymode`). O bootloader está desbloqueado (`orange`).

## 2. Alternativas

| | Alternativa | Navegação | Busca | Apps | Esforço | Risco |
|---|---|---|---|---|---|---|
| **A** | Busca por voz e por texto que toca direto ("toque X") pela sessão | — | ✔ (sem lista de resultados) | Todos com `PLAY_FROM_SEARCH` | Baixo | Nenhum |
| **B** | "Navegar no app": a interface do próprio app dentro do card, com o display virtual da spec 008 | ✔ (interface de celular) | ✔ (a do app) | Todos | Médio (infra pronta) | Baixo; GPU, como no Maps embutido |
| **C** | Spotify Web API (OAuth PKCE, conta do usuário) para biblioteca, playlists e busca com capas, tocando por `spotify:` URI na sessão | ✔ no visual Android Auto do Lumia | ✔ com lista | Spotify | Médio-alto | Baixo; exige criar um app de desenvolvedor no Spotify (gratuito, modo pessoal) e internet |
| **D** | YouTube Data API v3 (OAuth) para playlists e busca, tocando por URL na sessão | Parcial (playlists, curtidas) | ✔ com lista | YouTube Music | Médio-alto | Cota diária (≈100 buscas/dia); antes, testar se o YouTube Music aceita `music.youtube.com/watch?v=` no `PLAY_FROM_URI` |
| **E** | Lumia como app privilegiado (`/system/priv-app` + `MEDIA_CONTENT_CONTROL`) | Talvez | Talvez | Só os que aceitam essa permissão; os do Google provavelmente não | Alto | **Alto**: exige gravar `/system` com dm-verity ativo, ou seja, desligar a verificação de boot (vbmeta). Regra do projeto: só com o firmware completo em mãos e sua aprovação |
| **F** | Assinar o Lumia com a chave de plataforma | — | — | — | — | **Impossível**: a chave privada é da TopWay |
| **G** | Fazer o Lumia se passar pelo Android Auto (processo root com o uid do `gearhead`) | ✔ árvores de carro completas | ✔ | Todos | Alto | **Contorna o controle de acesso dos apps.** Viola os termos de uso (risco de bloqueio da conta) e quebra a cada atualização. Não recomendo e não faço sem decisão explícita sua |
| **H** | Usar o Android Auto de verdade pelo celular (ZLink) | ✔ | ✔ | Todos | — | É o Android Auto, não o Lumia |

## 3. Recomendação

1. **Agora (A):** liberar a **Busca** em todos os apps, mesmo no modo só player. Teclado parado, voz sempre, buscas recentes, e "Tocar 'X' no Spotify" como resultado. É o que o Android Auto faz com a busca por voz e cabe na spec 018 (R18.11).
2. **Agora (B):** no modo só player, trocar o aviso "Navegação indisponível" por um botão **Navegar no app**, que abre a interface do app dentro do card, como o Maps. Recupera o catálogo completo de qualquer app sem violar nada.
3. **Depois (C), se você quiser:** integração com a Spotify Web API para ter a navegação e a busca com lista no visual dos wireframes. Precisa que você crie um app gratuito em developer.spotify.com e faça login uma vez com o carro parado.
4. **Não recomendo E nem G.** E arrisca a multimídia por um ganho incerto; G contorna a proteção dos apps.

## 4. Critérios para validar
- [ ] A: falar "tocar Duo Litoral" ou digitar parado → a música começa no Spotify e no YouTube Music.
- [ ] B: "Navegar no app" mostra o Spotify no card; tocar numa playlist toca e volta ao player.
- [ ] Fila: itens sem `SKIP_TO_QUEUE_ITEM` aparecem sem ação de toque.
