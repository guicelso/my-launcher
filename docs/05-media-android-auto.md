# 05 — Mídia e streaming no Android Auto: estudo técnico e de UX

Base do redesenho do player e da navegação de mídia do Lumia (spec 018). Pesquisa de 2026-11-22.
Wireframes: página **Mídia e streaming (estudo)** do canvas (https://claude.ai/artifact/SiHfRy4w698zHFTzvYiXv6#page-midia).

Este estudo cobre o player e a navegação de conteúdo de apps de música, podcast, audiolivro e rádio no Android Auto. O objetivo é reproduzir esses recursos e essa UX num launcher próprio (Android 10, 1280×720 paisagem, 160 dpi), sem ativos proprietários.

**Legenda usada em todo o texto**
- **[F]**: fato documentado, com o link da fonte ao lado.
- **[O]**: observação, inferência minha ou relato de imprensa/fórum. Precisa ser validado no carro ou no emulador.

**Sobre as fontes**
- A UI projetada do Android Auto é código fechado. As fontes mais precisas são três:
  - a documentação de desenvolvimento (`developer.android.com/training/cars/media`);
  - o Design for Driving;
  - a implementação de referência aberta do AAOS (Media Center no AOSP), que segue o mesmo modelo de interação e os mesmos contratos de API.
- Os números de pixel/dp vêm do Design for Driving ou do AOSP. Não existe especificação pública em pixels da UI do Android Auto.
- As páginas antigas em `developers.google.com/cars/design/android-auto/apps/media/...` retornam 404. O conteúdo hoje está em `developer.android.com/design/ui/cars/...` e em `docs.partner.android.com/drivingux/...`. Desde março de 2026, as atualizações do Design for Driving saem só em docs.partner.android.com.

---

## 0. Resumo executivo (o que mais importa para o redesenho)

1. **Divisão de papéis.** O app de mídia fornece dados: árvore de browse, metadados, estado, ações, ícones e cor de destaque. O host desenha tudo.
   - Quem "é o Android Auto" no carro é o seu launcher. Ele precisa interpretar os mesmos extras que o AA interpreta: content style, group title, completion, explicit, download, slot reservation, custom actions, erros com ação de resolução e custom browse actions.
   - Fontes: [create-media-apps](https://developer.android.com/design/ui/cars/guides/app-types/create-media-apps), [content-styles](https://developer.android.com/training/cars/media/create-media-browser/content-styles). [F]
2. **Estrutura de telas.**
   - Browse em abas: no máximo 4, só itens navegáveis.
   - Listas e grades com subtítulos de grupo, poucas camadas (até 3 níveis recomendados).
   - Acesso permanente ao "tocando agora", por FAB ou barra minimizada.
   - "Tocando agora": capa, título, subtítulo, indicadores, progresso e uma barra de controles com slots fixos; fila acessada pelo cabeçalho; overflow para ações extras.
   - Fontes: [content-hierarchy](https://developer.android.com/training/cars/media/create-media-browser/content-hierarchy), [plan-browsing-views](https://developer.android.com/design/ui/cars/guides/app-types/plan-browsing-views), [AAOS playing-media](https://developers.google.com/cars/design/automotive-os/apps/media/interaction-model/playing-media). [F]
3. **Regras de controles.**
   - Os espaços de Anterior/Próximo são reservados. Se o app não os suporta, ações personalizadas ocupam o espaço, a menos que o app peça reserva via `android.media.playback.ALWAYS_RESERVE_SPACE_FOR.ACTION_SKIP_TO_PREVIOUS/NEXT`.
   - As ações personalizadas aparecem na ordem em que o app as adicionou. O que não cabe vai para o overflow.
   - Fonte: [enable-playback](https://developer.android.com/training/cars/media/enable-playback). [F]
4. **Visual 2026.** O player do AA migrou para Material 3 Expressive, com fundo de capa desfocada, barra de progresso ondulada ("squiggly") e botões reorganizados. Segundo a imprensa, a implantação começou em jan/2026 e ficou ampla em mar–jul/2026.
   - O Google anunciou oficialmente o Material 3 Expressive no carro em 12/05/2026.
   - Fontes: [blog.google 2026](https://blog.google/products-and-platforms/platforms/android/android-in-cars-updates/) [F]; [9to5Google](https://9to5google.com/2026/07/02/android-auto-more-widely-rolling-out-updates-for-spotify-and-youtube-music-apps/) [O].
5. **Limite crítico para um launcher comum.** Muitos apps validam o pacote cliente em `onGetRoot` e recusam ou devolvem uma árvore vazia a clientes desconhecidos.
   - O controle de reprodução via MediaController do NotificationListener sempre funciona.
   - O browse via MediaBrowserService depende de cada app. É preciso projetar um modo degradado: player completo, sem browse.
   - Fontes: [content-hierarchy §package validation](https://developer.android.com/training/cars/media/create-media-browser/content-hierarchy) [F]; casos reais em issues do GitHub [O].

---

## 1. Mapa de telas e fluxos

### 1.1 Arquitetura geral do sistema (Coolwalk, 2023 → 2026)

| Elemento | Descrição | Fonte |
|---|---|---|
| Barra do sistema (nav bar / taskbar) | Sempre visível. Fica embaixo ou na lateral, conforme a tela; espelhada em carros com volante à direita; vertical em telas largas. Contém: botão de apps (vira "dashboard" quando o launcher já está aberto), Assistente, dock de apps recentes, contador de notificações, relógio, sinal e bateria. | [F] [nav-bar](https://docs.partner.android.com/drivingux/android-auto/product-experience/system-ui/nav-bar), [system UI](https://docs.partner.android.com/drivingux/android-auto/product-experience/system-ui/overview) |
| Dashboard em tela dividida | "Split screen é padrão em todos os tipos e tamanhos de tela" (anúncio de 2022, chegou ao estável em jan/2023). | [F] [blog.google 2022](https://blog.google/products/android/new-ways-to-stay-connected-and-entertained-in-your-car/) |
| Cards do dashboard | Card de mapa (o maior) mais coluna com locais frequentes e card de mídia. Com destino definido, ou após alguns minutos dirigindo, a lista de locais some e o card de mídia cresce. Tocar no card abre o app em tela cheia. | [O] [9to5Google review 2023](https://9to5google.com/2023/01/10/review-android-auto-dashboard/) |
| Card de mídia | Cores das controles tiradas da capa (Material You). Deslizar o card mostra playlists e álbuns sugeridos do serviço padrão, que tocam sem abrir o app. O botão play/pause muda de forma conforme o estado. | [O] [9to5Google 2023](https://9to5google.com/2023/01/10/review-android-auto-dashboard/) |
| Recomendações no card | O app indica 10 itens via `BrowserRoot.EXTRA_SUGGESTED`, exibidos no dashboard quando nada está tocando. Sem isso, o AA puxa do topo da árvore. | [F] [provide-recommendations](https://developer.android.com/design/ui/cars/guides/app-types/provide-recommendations) |
| Ícone de atribuição no card | Ícone monocromático do app, declarado em `<meta-data android:name="androidx.car.app.TintableAttributionIcon">`. | [F] [configure-manifest](https://developer.android.com/training/cars/media/configure-manifest) |
| Mini controles na taskbar | Enquanto outro app está aberto, a taskbar mostra música ou navegação. Esses widgets foram rebaixados e podem ser reativados por configuração. | [O] [9to5Google 2023](https://9to5google.com/2023/01/10/review-android-auto-dashboard/) |
| Redesenho de 2026 | Mapa de borda a borda. Barra de apps flutuante na lateral ou embaixo. Notificações, controles de música e widgets flutuam sobre o mapa. Fontes e papel de parede do celular. Material 3 Expressive. Vídeo só com o carro parado, virando só áudio ao andar. | [F] [blog.google 12/05/2026](https://blog.google/products-and-platforms/platforms/android/android-in-cars-updates/); detalhes em [9to5Google](https://9to5google.com/2026/05/12/android-auto-redesign-widgets-new-update/) [O] |

### 1.2 Seleção e troca de app de mídia

- **[F]** O launcher do AA é uma grade com rolagem vertical que inclui os apps de mídia compatíveis (ícone e nome). O usuário pode ocultar e reordenar apps nas configurações do AA no celular. Fonte: [app-launcher](https://docs.partner.android.com/drivingux/android-auto/product-experience/system-ui/app-launcher).
- **[F]** Um app aparece como app de mídia do carro quando cumpre dois requisitos:
  - declara um serviço com intent-filter `android.media.browse.MediaBrowserService` (ou `androidx.media3.session.MediaLibraryService`);
  - tem `<meta-data android:name="com.google.android.gms.car.application">` apontando para um XML com `<uses name="media"/>`.
  - Nome e ícone podem ser sobrescritos com `android:label` e `android:icon` do `<service>`.
  - Fontes: [configure-manifest](https://developer.android.com/training/cars/media/configure-manifest), [auto](https://developer.android.com/training/cars/media/auto), [serve-content](https://developer.android.com/media/media3/session/serve-content).
- **[F] Referência AAOS.**
  - O Media Center abre com a extra `android.car.intent.extra.MEDIA_COMPONENT`. Sem ela, mostra o app atual.
  - Apps de mídia não têm atividade LAUNCHER: o launcher varre os pacotes que implementam MediaBrowserService.
  - Há um seletor de fontes próprio, em grade de 3 colunas (`num_app_selector_columns=3`).
  - Fontes: [technical_details](https://source.android.com/docs/automotive/hmi/media/technical_details), [integers car-media-common](https://android.googlesource.com/platform/packages/apps/Car/libs/+/refs/heads/main/car-media-common/res/values/integers.xml).
- **[F]** Um app com várias sessões aparece várias vezes, porque "Android Auto e dispositivos Bluetooth com tela listam sessões, não apps". Fonte: [Media3 MediaSession](https://developer.android.com/reference/androidx/media3/session/MediaSession).
- **[O]** No AA, a troca de fonte acontece pelo launcher ou pela dock de recentes da taskbar. O card de mídia mostra o app que está tocando ou o último usado.
  - Teardowns de 2026 indicam cards de mídia com várias sessões recentes, navegáveis por swipe. É relato de segunda mão, ainda não confirmado.

### 1.3 Navegação de conteúdo (browse)

**Fluxo técnico [F]** ([lifecycle](https://developer.android.com/training/cars/media/create-media-browser/lifecycle))
1. O host faz bind no serviço.
2. `onGetRoot(clientPackage, uid, rootHints)` devolve o root, que não é exibido.
3. `onLoadChildren(root)` devolve o nível superior.
4. Tocar num item navegável chama `onLoadChildren(id)`.
5. Tocar num item tocável chama `playFromMediaId`.
6. Busca chama `onSearch`.

**Abas**
- **[F]** O root hint `BROWSER_ROOT_HINTS_KEY_ROOT_CHILDREN_LIMIT` normalmente vale 4: até 4 abas. `..._ROOT_CHILDREN_SUPPORTED_FLAGS` vale `FLAG_BROWSABLE`: só itens navegáveis viram aba. Itens a mais "podem ser omitidos ou ficar menos visíveis". Fonte: [content-hierarchy](https://developer.android.com/training/cars/media/create-media-browser/content-hierarchy).
- **[F]** Cada aba tem ícone monocromático (de preferência branco) e rótulo curto. O host pode exibir só o rótulo quando os dois não cabem. Fonte: [plan-navigation-tabs](https://developer.android.com/design/ui/cars/guides/app-types/plan-navigation-tabs).
- **[F] Referência AAOS:** `max_tabs=4`, largura máxima da aba 134dp, aba não selecionada com alpha 0,5. Fontes: [integers](https://android.googlesource.com/platform/packages/apps/Car/Media/+/refs/heads/main/res/values/integers.xml), [dimens](https://android.googlesource.com/platform/packages/apps/Car/Media/+/refs/heads/main/res/values/dimens.xml).

**Listas, grades e categorias [F]** ([content-styles](https://developer.android.com/training/cars/media/create-media-browser/content-styles))
- Lista é o padrão. Na lista, títulos e metadados têm prioridade. Na grade, a imagem tem prioridade.
- O estilo padrão vem nos extras do `BrowserRoot` e pode ser sobrescrito por item. Detalhes na seção 2.2.
- `CATEGORY_LIST` e `CATEGORY_GRID` aplicam margens em volta de ícones pequenos. Valem só para itens navegáveis e exigem ícones vetoriais tingíveis.

**Grupos**
- **[F]** O título de grupo vem de `DESCRIPTION_EXTRAS_KEY_CONTENT_STYLE_GROUP_TITLE`. Os itens do mesmo grupo precisam vir contíguos; caso contrário viram grupos separados com o mesmo nome. Fonte: [content-styles](https://developer.android.com/training/cars/media/create-media-browser/content-styles).
- **[F]** Subtítulo de grupo é obrigatório quando há subcategorias. Fonte: [plan-browsing-views](https://developer.android.com/design/ui/cars/guides/app-types/plan-browsing-views).

**Hierarquia e voltar**
- **[F]** Evitar mais de 3 níveis abaixo do topo. Fonte: [plan-browsing-views](https://developer.android.com/design/ui/cars/guides/app-types/plan-browsing-views).
- **[F]** Ao descer de nível, surge um cabeçalho com seta de voltar. O cabeçalho fica fixo e o conteúdo rola por baixo dele. Fonte: [AAOS browsing](https://developers.google.com/cars/design/automotive-os/apps/media/interaction-model/browsing).

**Sem paginação**
- **[F]** "Android Auto e AAOS não suportam paginação." Com Media3, não confiar em `page`/`pageSize`: o app entrega a lista inteira. Fonte: [content-hierarchy](https://developer.android.com/training/cars/media/create-media-browser/content-hierarchy).

**Acesso ao "tocando agora" durante o browse**
- **[F]** No AA, o player fica acessível nas telas de browse por um FAB. Fontes: [partner media-apps](https://docs.partner.android.com/drivingux/android-auto/apps/media-apps), [AA media-apps](https://developers.google.com/cars/design/android-auto/apps/media-apps).
- **[F]** No AAOS e nos apps de template, isso é uma barra minimizada ("minimized control panel"), que é requisito MFT-1. Fonte: [CAL media](https://developer.android.com/training/cars/apps/media).

**Indicadores nos itens**
- **[F]** "E" para explícito, seta para baixo para baixado, círculo cheio para novo, barra para parcialmente ouvido. Fonte: [partner browsing-views](https://docs.partner.android.com/drivingux/android-auto/apps/browsing-views).

**Rolagem rápida**
- **[O]** Desde 2021 o AA tem um botão "A–Z" na barra de rolagem para pular por letra em listas longas. Fonte: [9to5Google 2021](https://9to5google.com/2021/06/15/android-auto-letter-navigation-more/).

### 1.4 Busca

- **[F]** Todo app precisa suportar busca por voz: `onPlayFromSearch(query, extras)`.
  - Extras opcionais: `EXTRA_MEDIA_ARTIST`, `EXTRA_MEDIA_ALBUM`, `EXTRA_MEDIA_GENRE`, `EXTRA_MEDIA_PLAYLIST`, `EXTRA_MEDIA_TITLE`.
  - Query vazia significa "toque algo".
  - Busca lenta: o app passa para `STATE_CONNECTING`.
  - Fonte: [voice-actions](https://developer.android.com/training/cars/media/voice-actions).
- **[F]** Busca navegável: o app declara `BROWSER_SERVICE_EXTRAS_KEY_SEARCH_SUPPORTED` (`"android.media.browse.SEARCH_SUPPORTED"`) = true nos extras do root e implementa `onSearch`.
  - Lista vazia significa "sem resultados". `null` significa erro.
  - Resultados podem vir agrupados por title hints (álbuns, artistas, músicas).
  - Depois de uma busca por voz, o player mostra a opção **"Resultados da busca"**.
  - Fonte: [browsable-search](https://developer.android.com/training/cars/media/create-media-browser/browsable-search).
- **[F]** O teclado e o affordance de voz são do host. Fonte: [plan-browsing-views](https://developer.android.com/design/ui/cars/guides/app-types/plan-browsing-views).
- **[F] Teclado bloqueado com o carro em movimento:**
  - AAOS: busca por teclado só parado. Fonte: [source.android media](https://source.android.com/docs/automotive/hmi/media).
  - Template de busca: "durante a direção o usuário não acessa o teclado, mas pode usar fala-para-texto e ver resultados anteriores". Fonte: [search-template](https://developer.android.com/design/ui/cars/guides/templates/search-template).
  - Princípio visual: "esconder recursos só-parado, como a caixa de busca, enquanto dirige". Fonte: [visual-principles](https://developers.google.com/cars/design/design-foundations/visual-principles).

### 1.5 Tela "tocando agora" (playback view)

**Elementos no AA [F]** ([partner playback-view](https://docs.partner.android.com/drivingux/android-auto/apps/playback-view), versão anterior ao redesenho de 2026)
- botão voltar;
- metadados: capa, título, subtítulo opcional, indicador de explícito;
- tempo decorrido;
- botão de fila;
- controles com indicador visual de progresso.

**Controles primários [F]** (mesma fonte)
- Ficam num "action card" de até 9 ações.
- Ordem, da esquerda para a direita: Fila/Playlist, Anterior, Play/Pause, Próximo, função opcional à direita.
- Sem Anterior/Próximo, outros botões ocupam essas posições.
- Com mais de 5 ações, o botão da direita vira overflow.
- Até 4 ações secundárias (ex.: joinha para cima/baixo) sobem dos controles primários quando o usuário toca no overflow.

**Modelo AAOS (mais detalhado, mesmo contrato) [F]** ([playing-media](https://developers.google.com/cars/design/automotive-os/apps/media/interaction-model/playing-media))
- O player ocupa todo o espaço de conteúdo. Deslizar para baixo o minimiza.
- Cabeçalho com o affordance de fila, se o app tiver fila.
- Linha inferior com 5 posições:
  1. custom;
  2. Anterior ou custom;
  3. Play/Pause (centro);
  4. Próximo ou custom;
  5. overflow (se houver mais de 5) ou custom.
- Linha superior aparece só quando expandida: até 5 ações custom.

**Regras de documentação do app [F]** ([customize-playback-controls](https://developer.android.com/design/ui/cars/guides/app-types/customize-playback-controls))
- O host fornece Play/Pause, Next, Prev e Overflow.
- O app **pode** fornecer até 6 custom actions, ou até 8 se não usar Next/Prev.
- Ícones das custom actions **devem** ser vetoriais monocromáticos e trocar conforme o estado.

**Indicadores no player [F]** ([content-styles](https://developer.android.com/training/cars/media/create-media-browser/content-styles), [MetadataExtras](https://developer.android.com/reference/androidx/car/app/mediaextensions/MetadataExtras), [enable-playback](https://developer.android.com/training/cars/media/enable-playback))
- Explícito e baixado aparecem no player.
- Completion status e porcentagem **não** aparecem no player.
- Ícone de formato de áudio (ex.: espacial) via `KEY_CONTENT_FORMAT_TINTABLE_LARGE_ICON_URI`, numa área de ~15 caracteres do subtítulo no player. A versão `_SMALL_` ocupa ~2 caracteres.
- Subtítulo e descrição podem ser **links** para outro item do browse (`KEY_SUBTITLE_LINK_MEDIA_ID`, `KEY_DESCRIPTION_LINK_MEDIA_ID`), por exemplo do nome do artista para a página do artista.

**Visual 2026 [O]** ([9to5Google jul/2026](https://9to5google.com/2026/07/02/android-auto-more-widely-rolling-out-updates-for-spotify-and-youtube-music-apps/), [mar/2026](https://9to5google.com/2026/03/25/android-autos-squiggly-media-player-is-rolling-out-to-more-users/), [dez/2025](https://9to5google.com/2025/12/22/android-auto-update-hints-at-cast-support-material-music-player-redesign-gallery/))
- Capa desfocada atrás dos controles.
- Barra de progresso Material 3 Expressive ondulada.
- Botões redesenhados e reorganizados.
- No YouTube Music, em tela cheia, a capa fica à direita.
- Foi descrito como mudança "puramente estética".

### 1.6 Fila

- **[F] API.** `setQueue`, `setQueueTitle`, `ACTION_SKIP_TO_QUEUE_ITEM` com `onSkipToQueueItem`, e `PlaybackState.setActiveQueueItemId` para o ícone de "tocando agora". Fonte: [enable-playback](https://developer.android.com/training/cars/media/enable-playback).
- **[F] Design.**
  - **Deve** indicar o item atual e incluir itens já tocados.
  - **Pode** ter miniaturas.
  - O app decide se mostra ícone ou tempo decorrido no item atual.
  - Fonte: [customize-playback-controls](https://developer.android.com/design/ui/cars/guides/app-types/customize-playback-controls).
- **[F]** No AA, o item atual fica fixado no topo. Tocar num item troca a faixa. Voltar retorna ao player. Fonte: [partner queue](https://docs.partner.android.com/drivingux/android-auto/apps/queue).
- **[F] AAOS.** O mínimo por item é o título. Selecionar um item toca na hora. Fonte: [playing-media](https://developers.google.com/cars/design/automotive-os/apps/media/interaction-model/playing-media).
- **[F] Referência AAOS (layout).** A fila abre sobre o player, escondendo metadados e seekbar, sobre um scrim de alpha 0,6, com fade de 500ms. Fonte: [integers](https://android.googlesource.com/platform/packages/apps/Car/Media/+/refs/heads/main/res/values/integers.xml).

### 1.7 Ações personalizadas

**No player [F]** ([enable-playback](https://developer.android.com/training/cars/media/enable-playback))
- Vêm de `PlaybackState.getCustomActions()`, na ordem de inserção.
- Ocupam slots livres, ou seja, Anterior/Próximo não suportados e não reservados. O resto vai para o overflow.
- Não devem duplicar ações padrão.
- Ícone padronizado opcional em `EXTRAS_KEY_COMMAND_BUTTON_ICON_COMPAT`, um inteiro `CommandButton.ICON_*` (rádio, coração, embaralhar, repetir, velocidade etc.). Quando presente, o host usa o próprio ícone em vez do recurso do app.
- Estados com ícone diferente para cada estado, e versão "desligada" para indisponível.
- **[F]** GB-1: elementos desabilitados não podem funcionar. Fonte: [quality](https://developer.android.com/docs/quality-guidelines/car-app-quality?category=media).

**No browse ("custom browse actions", recurso de 2023+) [F]** ([custom-browse-actions](https://developer.android.com/training/cars/media/create-media-browser/custom-browse-actions), [MediaConstants](https://developer.android.com/reference/androidx/media/utils/MediaConstants))
- O host anuncia o limite por item no root hint `BROWSER_ROOT_HINTS_KEY_CUSTOM_BROWSER_ACTION_LIMIT`. Valor 0 ou menor significa sem suporte; o que passar do limite é truncado.
- O app publica a lista global no root (`..._CUSTOM_BROWSER_ACTION_ROOT_LIST`: id, rótulo, URI de ícone tingível, extras). Cada item referencia ids (`DESCRIPTION_EXTRAS_KEY_CUSTOM_BROWSER_ACTION_ID_LIST`).
- O que excede o espaço vai para um menu overflow.
- Ações no nó raiz de um browse aparecem numa toolbar secundária abaixo da principal.
- Execução: `MediaBrowser.sendCustomAction(action, extras com KEY_CUSTOM_BROWSER_ACTION_MEDIA_ITEM_ID)`. O resultado pode trazer:
  - `RESULT_MESSAGE`: mensagem ao usuário;
  - `RESULT_REFRESH_ITEM`: recarregar o item via `onLoadItem`;
  - `RESULT_SHOW_PLAYING_ITEM`: abrir o player;
  - `RESULT_BROWSE_NODE`: navegar para um nó.
- Há progresso intermediário (`sendProgressUpdate`), por exemplo "Baixando 25/50/75%".

**Media3 [F]**
- Apps Media3 declaram `CommandButton`s com slot (`SLOT_BACK`, `SLOT_FORWARD`, `SLOT_OVERFLOW`, central). Fonte: [control-playback](https://developer.android.com/media/media3/session/control-playback).
- Também podem declarar botões por item (`setCommandButtonsForMediaItems`), visíveis só para controllers que anunciam `setMaxCommandsForMediaItems(n)`. Padrão é 0. Fonte: [serve-content](https://developer.android.com/media/media3/session/serve-content).

### 1.8 Estados

| Estado | Comportamento | Fonte |
|---|---|---|
| Carregando imagens | O host mostra um loading de imagem enquanto o ContentProvider responde. | [F] [media-artwork](https://developer.android.com/training/cars/media/create-media-browser/media-artwork) |
| Carregando processo | AAOS: indicador de progresso só após 1000ms (`progress_indicator_delay`). | [F] [integers](https://android.googlesource.com/platform/packages/apps/Car/Media/+/refs/heads/main/res/values/integers.xml) |
| Buffering | `STATE_CONNECTING`/`STATE_BUFFERING`: o botão play vira spinner. Mesma regra do controle do sistema, que segundo a doc "alinha com Auto". | [F] [media controls](https://developer.android.com/media/implement/surfaces/mobile) |
| Sem conteúdo | `STATE_NONE` torna a UI de reprodução inacessível. Usar só quando não há nada para tocar. | [F] [enable-playback](https://developer.android.com/training/cars/media/enable-playback) |
| Estado inicial | Sem autoplay ao conectar. Estado inicial STOPPED/PAUSED/NONE/ERROR. O host retoma conforme o carro ou o usuário. Requisito MA-1. | [F] [enable-playback](https://developer.android.com/training/cars/media/enable-playback), [quality](https://developer.android.com/docs/quality-guidelines/car-app-quality?category=media) |
| Erro fatal | `STATE_ERROR` com `setErrorMessage(code, msg)`. Mensagem localizada e voltada ao usuário. Se for preciso usar o celular, a mensagem diz isso e pede para só olhar quando for seguro (VI-1). | [F] [errors](https://developer.android.com/training/cars/media/errors), [quality](https://developer.android.com/docs/quality-guidelines/car-app-quality?category=media) |
| Login necessário | `ERROR_CODE_AUTHENTICATION_EXPIRED`: o host mostra a mensagem e um botão de resolução, com texto em `"android.media.extras.ERROR_RESOLUTION_ACTION_LABEL"` e PendingIntent em `"android.media.extras.ERROR_RESOLUTION_ACTION_INTENT"`. Enquanto em erro, nenhuma outra operação é possível. No AA, login só parado. | [F] [AAOS technical_details](https://source.android.com/docs/automotive/hmi/media/technical_details), [MediaConstants](https://developer.android.com/reference/androidx/media/utils/MediaConstants), [sign-in flow](https://developer.android.com/design/ui/cars/guides/flows/create-sign-in-flow) |
| Erro não fatal | O estado continua normal (ex.: PLAYING) com código de erro, como `ERROR_CODE_END_OF_QUEUE`. É reportado uma única vez. | [F] [mediasession legacy](https://developer.android.com/media/legacy/mediasession) |
| Erro não fatal (Media3) | `sendError(SessionError)` copia código e mensagem no PlaybackState da plataforma **sem** mudar para STATE_ERROR. | [F] [control-playback](https://developer.android.com/media/media3/session/control-playback) |
| Sem conexão / região | Mesmo mecanismo de STATE_ERROR, com códigos como `ERROR_CODE_NOT_AVAILABLE_IN_REGION` e problemas de servidor. | [F] [errors](https://developer.android.com/training/cars/media/errors), [technical_details](https://source.android.com/docs/automotive/hmi/media/technical_details) |
| Anúncio | O AA mostra uma notificação quando os metadados mudam. Com `METADATA_KEY_IS_ADVERTISEMENT` (`"android.media.metadata.ADVERTISEMENT"`) = 1, a notificação é suprimida. | [F] [distraction-safeguards](https://developer.android.com/training/cars/media/distraction-safeguards) |
| Configurações | AAOS: atividade `APPLICATION_PREFERENCES`, desabilitada com restrições de direção. Via CAL: `BROWSER_SERVICE_EXTRAS_KEY_APPLICATION_PREFERENCES_USING_CAR_APP_LIBRARY_INTENT`. | [F] [technical_details](https://source.android.com/docs/automotive/hmi/media/technical_details), [MediaConstants](https://developer.android.com/reference/androidx/media/utils/MediaConstants) |

**[O]** A forma visual exata do erro no AA não está documentada publicamente: tela cheia, ícone do app, mensagem e botão. No AAOS de referência, a tela de erro tem mensagem com margem lateral de 112dp, botão com 32dp acima e 24dp abaixo, e no widget no máximo 3 linhas de mensagem. Fontes: [dimens](https://android.googlesource.com/platform/packages/apps/Car/Media/+/refs/heads/main/res/values/dimens.xml), [integers car-media-common](https://android.googlesource.com/platform/packages/apps/Car/libs/+/refs/heads/main/car-media-common/res/values/integers.xml).

### 1.9 Mini player e tela dividida

- **[F] Barra minimizada (AAOS).** Aparece ao sair do player com algo tocando. Mostra metadados e controles básicos e permite navegar enquanto toca. Fonte: [playing-media](https://developers.google.com/cars/design/automotive-os/apps/media/interaction-model/playing-media).
  - Na referência AOSP há barra de progresso linear na barra minimizada (`show_linear_progress_bar=true`), com trilha de 4dp. A área de controles no browse tem 192dp de altura. Fontes: [bools](https://android.googlesource.com/platform/packages/apps/Car/libs/+/refs/heads/main/car-media-common/res/values/bools.xml), [dimens](https://android.googlesource.com/platform/packages/apps/Car/Media/+/refs/heads/main/res/values/dimens.xml).
- **[F] Card de mídia (AAOS de referência).** Contém:
  - capa, título (com título padrão se vazio), subtítulo e descrição (com links);
  - logo de formato;
  - ícone e nome do app;
  - Play/Pause, Skip e custom actions;
  - fila opcional, que pode ser limitada pelas restrições de direção;
  - histórico de fontes tocadas anteriormente.
  - Com metadados nulos, esconde título, capa etc.
  - Fonte: [media-card](https://source.android.com/docs/automotive/hmi/media/media-card).
- **[O] AA 2026 (teardown de jul/2026, não lançado).** Card com capa menor e centralizada, fundo de capa muito desfocado, informação da faixa abaixo e botões extras abaixo dos controles (aleatório, repetir, curtir), variando por app. Fonte: [9to5Google](https://9to5google.com/2026/07/14/android-auto-media-player-redesign-preparing/).

### 1.10 Linha do tempo de novidades

| Data | Novidade | Status |
|---|---|---|
| mai/2022 → jan/2023 | Coolwalk: dashboard dividido para todos os tamanhos, card de mídia com Material You. | [F] blog 2022; [O] imprensa |
| 2023+ | Custom browse actions. | [F] doc |
| mai/2025 | Car App Library 1.8 alpha: `MediaPlaybackTemplate` e `SectionedItemTemplate` para AA. Apps de mídia com template em beta (só faixas de teste internas/fechadas). | [F] [Android Developers Blog](https://android-developers.googleblog.com/2025/05/android-for-cars-google-io-2025.html), [CAL media](https://developer.android.com/training/cars/apps/media) |
| CAL 1.9 (alpha) | Chips, barras de progresso, Condensed Items, Header interativo e expandido, Spotlight Sections, Banners e barra minimizada obrigatória (MFT-1). | [F] [CAL media](https://developer.android.com/training/cars/apps/media) |
| ago/2025 | Spotify e YouTube Music testados na UI de mídia com template, com novo "Now Playing" e seekbar M3E. | [O] [Android Authority](https://www.androidauthority.com/spotify-youtube-music-android-auto-templated-media-app-3591234/) (só snippet de busca) |
| jan → jul/2026 | Player "squiggly" M3E com fundo de capa desfocada. Puxado em jan por bug nos botões do volante, depois volta e se espalha. | [O] 9to5Google |
| 12/05/2026 | Anúncio oficial: Material 3 Expressive, mapa de borda a borda, widgets, "ajustes visuais" em YouTube Music e Spotify, Dolby Atmos. | [F] [blog.google](https://blog.google/products-and-platforms/platforms/android/android-in-cars-updates/) |

---

## 2. Inventário de recursos e regras de comportamento

### 2.1 Player e sessão (o que o AA mostra × de onde vem)

| Recurso | Fonte do dado | Regra | Fonte |
|---|---|---|---|
| Título e subtítulo | `METADATA_KEY_DISPLAY_TITLE` / `DISPLAY_SUBTITLE`, com fallback `TITLE` / `ARTIST` | No AAOS: título até 2 linhas, artista/subtítulo 1 linha. | [F] [content-styles](https://developer.android.com/training/cars/media/create-media-browser/content-styles); [integers](https://android.googlesource.com/platform/packages/apps/Car/libs/+/refs/heads/main/car-media-common/res/values/integers.xml) |
| Capa | `METADATA_KEY_DISPLAY_ICON_URI`, `ART_URI` ou `ALBUM_ART_URI` (URI local `content://` ou `android.resource://`) | Não usar bitmap: o limite de binder é 1MB e o AAOS não suporta bitmap. | [F] [media-artwork](https://developer.android.com/training/cars/media/create-media-browser/media-artwork) |
| Tamanho da arte | Root hint `"android.media.extras.MEDIA_ART_SIZE_HINT_PIXELS"` | O host informa o tamanho recomendado. AAOS: 256px (`media_items_bitmap_max_size_px`). | [F] [MediaConstants](https://developer.android.com/reference/androidx/media/utils/MediaConstants), [customization](https://source.android.com/docs/automotive/hmi/media/customization) |
| Play/Pause/Spinner | `PlaybackState.state` | Play em NONE/STOPPED/PAUSED/ERROR; spinner em CONNECTING/BUFFERING; pause no resto. | [F] [media controls](https://developer.android.com/media/implement/surfaces/mobile) |
| Anterior/Próximo | `ACTION_SKIP_TO_PREVIOUS/NEXT` em `getActions()` | Sem a ação: o slot vira custom, ou fica vazio se reservado. | [F] [enable-playback](https://developer.android.com/training/cars/media/enable-playback) |
| Reserva de slot | Extras da **sessão** (`MediaSession.setExtras`): `"android.media.playback.ALWAYS_RESERVE_SPACE_FOR.ACTION_SKIP_TO_PREVIOUS"` / `"...ACTION_SKIP_TO_NEXT"` = true | Deixa o espaço em branco mesmo sem a ação. A tabela do controle do sistema cita os extras do PlaybackState; ler os dois por segurança. | [F] [enable-playback](https://developer.android.com/training/cars/media/enable-playback), [media controls](https://developer.android.com/media/implement/surfaces/mobile) |
| Barra de progresso | `METADATA_KEY_DURATION` mais posição | Sem duração, a barra não mostra progresso. | [F] para o controle do sistema ([media controls](https://developer.android.com/media/implement/surfaces/mobile)); [O] AA igual (ao vivo/rádio sem barra) |
| Posição atual | `position + (agora − lastPositionUpdateTime) × playbackSpeed` (cálculo padrão do PlaybackState) | Extrapolar no cliente, sem polling. | [O] uso padrão da API |
| Seek (arrastar) | `ACTION_SEEK_TO` | Sem a ação, a barra é só indicativa. | [F] [media controls](https://developer.android.com/media/implement/surfaces/mobile) |
| Pular ±15/30 s | Não é ação padrão no AA: vem como custom action (ex.: `CommandButton.ICON_SKIP_FORWARD_15` com `SLOT_FORWARD`) | Ocupa o slot de Próximo/Anterior quando o app não suporta skip. | [F] [control-playback](https://developer.android.com/media/media3/session/control-playback) |
| Velocidade | `PlaybackState.playbackSpeed`; botão via custom action com ícone `ICON_PLAYBACK_SPEED_*` | "Se usar texto/números no ícone, ocupar o máximo da caixa". O AA não tem controle nativo de velocidade. | [F] [customize-playback-controls](https://developer.android.com/design/ui/cars/guides/app-types/customize-playback-controls); [O] ausência de controle nativo |
| Curtir/descurtir | Custom actions (joinha para cima/baixo) | Ações secundárias no overflow. | [F] [partner playback-view](https://docs.partner.android.com/drivingux/android-auto/apps/playback-view) |
| Aleatório/repetir | Custom actions com ícones `ICON_SHUFFLE_*` / `ICON_REPEAT_*`. Media3 também expõe comandos do Player. | O redesenho de 2026 mostra esses botões no card. | [F] [control-playback](https://developer.android.com/media/media3/session/control-playback); [O] [9to5Google](https://9to5google.com/2026/07/14/android-auto-media-player-redesign-preparing/) |
| Fila | `getQueue()`, `getQueueTitle()`, `activeQueueItemId` | Botão de fila só se houver fila. | [F] [enable-playback](https://developer.android.com/training/cars/media/enable-playback) |
| Explícito / baixado | `"android.media.IS_EXPLICIT"` = 1; `EXTRA_DOWNLOAD_STATUS` (`"android.media.extra.DOWNLOAD_STATUS"`: 0 não baixado, 1 baixando, 2 baixado) | Funciona nos metadados e nos itens. | [F] [content-styles](https://developer.android.com/training/cars/media/create-media-browser/content-styles), [MetadataExtras](https://developer.android.com/reference/androidx/car/app/mediaextensions/MetadataExtras) |
| Ícones de indicador genéricos | `KEY_TINTABLE_INDICATOR_ICON_URI_LIST` | Quando presente, ignora DOWNLOAD_STATUS e IS_EXPLICIT. Área de ~2 caracteres. | [F] [MetadataExtras](https://developer.android.com/reference/androidx/car/app/mediaextensions/MetadataExtras) |
| Formato de áudio | `KEY_CONTENT_FORMAT_TINTABLE_LARGE/SMALL_ICON_URI`; `KEY_IMMERSIVE_AUDIO` | Grande ≈ 15 caracteres; pequeno ≈ 2. | [F] idem |
| Links no player | `KEY_SUBTITLE_LINK_MEDIA_ID` / `KEY_DESCRIPTION_LINK_MEDIA_ID` | O app precisa implementar `onLoadItem`/`onGetItem`. | [F] idem |
| Não misturar com outros apps | `KEY_EXCLUDE_MEDIA_ITEM_FROM_MIXED_APP_LIST` | Não mostrar o item ao lado de conteúdo de outros apps (relevante para recomendações mistas). | [F] idem |
| Cor do app | `colorAccent` do tema em `<meta-data android:name="com.google.android.gms.car.application.theme">`, ou do tema da aplicação | Estiliza controles e elementos. No AAOS, barra de progresso e spinner usam a cor da fonte. | [F] [configure-manifest](https://developer.android.com/training/cars/media/configure-manifest); [colors AOSP](https://android.googlesource.com/platform/packages/apps/Car/Media/+/refs/heads/main/res/values/colors.xml) |
| Abrir o app | `getSessionActivity()` (PendingIntent) | — | [F] [media controls](https://developer.android.com/media/implement/surfaces/mobile) |
| Continuidade entre viagens | O app guarda o último item, o estado e a fila; o host pede a retomada. | Sessões duram a viagem. | [F] [enable-playback](https://developer.android.com/training/cars/media/enable-playback) |

### 2.2 Browse (extras dos MediaItems e do root)

| Chave (constante → string) | Valores | Onde | Fonte |
|---|---|---|---|
| `DESCRIPTION_EXTRAS_KEY_CONTENT_STYLE_BROWSABLE` → `"android.media.browse.CONTENT_STYLE_BROWSABLE_HINT"` | 1 LIST, 2 GRID, 3 CATEGORY_LIST, 4 CATEGORY_GRID | Root (global) ou item (vale para os filhos imediatos navegáveis) | [F] [MediaConstants](https://developer.android.com/reference/androidx/media/utils/MediaConstants) |
| `..._CONTENT_STYLE_PLAYABLE` → `"android.media.browse.CONTENT_STYLE_PLAYABLE_HINT"` | idem | idem, para os filhos tocáveis | [F] idem |
| `..._CONTENT_STYLE_SINGLE_ITEM` → `"android.media.browse.CONTENT_STYLE_SINGLE_ITEM_HINT"` | idem | O próprio item. Tem precedência sobre os outros. | [F] [content-styles](https://developer.android.com/training/cars/media/create-media-browser/content-styles) |
| `..._CONTENT_STYLE_GROUP_TITLE` → `"android.media.browse.CONTENT_STYLE_GROUP_TITLE_HINT"` | string (título do grupo) | Itens contíguos | [F] idem |
| `DESCRIPTION_EXTRAS_KEY_COMPLETION_STATUS` → `"android.media.extra.PLAYBACK_STATUS"` | 0 não tocado, 1 parcial, 2 completo | Só itens; não aparece no player | [F] idem |
| `DESCRIPTION_EXTRAS_KEY_COMPLETION_PERCENTAGE` → `"androidx.media.MediaItem.Extras.COMPLETION_PERCENTAGE"` | double de 0.0 a 1.0 | Só itens. Barra de progresso no item. | [F] idem |
| `PLAYBACK_STATE_EXTRAS_KEY_MEDIA_ID` → `"androidx.media.PlaybackStateCompat.Extras.KEY_MEDIA_ID"` | mediaId | PlaybackState: atualiza ao vivo a barra do item no browse, junto com `METADATA_KEY_MEDIA_ID` igual ao do item | [F] idem |
| `BROWSER_SERVICE_EXTRAS_KEY_SEARCH_SUPPORTED` → `"android.media.browse.SEARCH_SUPPORTED"` | boolean | Extras do root | [F] [browsable-search](https://developer.android.com/training/cars/media/create-media-browser/browsable-search) |
| `BROWSER_SERVICE_EXTRAS_KEY_FAVORITES_MEDIA_ITEM` → `"androidx.media.BrowserRoot.Extras.FAVORITES_MEDIA_ITEM"` | MediaItem navegável | Favoritos/presets (ex.: rádios fixadas) que o host pode exibir ou assinar | [F] [MediaConstants](https://developer.android.com/reference/androidx/media/utils/MediaConstants) |
| Root hints enviados pelo host | `KEY_ROOT_CHILDREN_LIMIT` (4), `KEY_ROOT_CHILDREN_SUPPORTED_FLAGS` (BROWSABLE), `MEDIA_ART_SIZE_HINT_PIXELS`, `CUSTOM_BROWSER_ACTION_LIMIT` | `onGetRoot` | [F] [content-hierarchy](https://developer.android.com/training/cars/media/create-media-browser/content-hierarchy) |
| `BrowserRoot.EXTRA_RECENT` / `EXTRA_SUGGESTED` / `EXTRA_OFFLINE` | boolean | EXTRA_RECENT serve à retomada (System UI); EXTRA_SUGGESTED, às recomendações do AA | [F] [media controls](https://developer.android.com/media/implement/surfaces/mobile), [provide-recommendations](https://developer.android.com/design/ui/cars/guides/app-types/provide-recommendations) |
| `TRANSPORT_CONTROLS_EXTRAS_KEY_SHUFFLE` | boolean | Extra de `playFromX` para tocar em modo aleatório | [F] [MediaConstants](https://developer.android.com/reference/androidx/media/utils/MediaConstants) |

---

## 3. Especificação visual levantada

### 3.1 Design for Driving (oficial, vale para AA e AAOS) [F]

| Item | Valor | Fonte |
|---|---|---|
| Alvo de toque mínimo | 76×76dp. Pode estreitar se a altura aumentar e mantiver a área. Sem sobreposição. Ícone centralizado no alvo. | [sizing](https://developers.google.com/cars/design/android-auto/design-system/sizing), [visual-principles](https://developers.google.com/cars/design/design-foundations/visual-principles) |
| Espaço entre alvos | ≥ 23dp, quando possível | [visual-principles](https://developers.google.com/cars/design/design-foundations/visual-principles) |
| Ícones | Primário 44dp, secundário 36dp, terciário 24dp | [sizing](https://developers.google.com/cars/design/android-auto/design-system/sizing) |
| Texto | Primário (ex.: título da música) 32dp; secundário (ex.: artista) 24dp | [visual-principles](https://developers.google.com/cars/design/design-foundations/visual-principles) |
| Comprimento de texto | Até 120 caracteres por item (alfabeto romano) | idem |
| Contraste | ≥ 4,5:1 para texto, ícones e imagens informativas. Basta um dos elementos redundantes cumprir. O foco rotativo também precisa cumprir. | idem |
| Polaridade | À noite é obrigatória a polaridade negativa (claro sobre escuro) | idem |
| Em movimento | Diferenciar recursos permitidos e bloqueados (ex.: esmaecer). Esconder recursos só-parado. | idem |
| Requisitos de qualidade aplicáveis | ST-1: sem texto rolando automaticamente (sem marquee). SA-1: sem elementos animados (vídeo/GIF). IU-1: uma única imagem estática de contexto no fundo da tela de consumo (ex.: capa). DR-1: botões respondem em ≤ 2 s. DR-2/DR-3: abrir e carregar em ≤ 10 s. VD-2: ícones brancos que o sistema coloriza. | [quality](https://developer.android.com/docs/quality-guidelines/car-app-quality?category=media) |
| Listas em direção | Texto secundário truncado em 2 linhas ao dirigir (mais quando parado). Informação importante no começo do texto. | [list-template](https://developer.android.com/design/ui/cars/guides/templates/list-template) |

### 3.2 Medidas da referência AAOS (AOSP Media Center, car-media-common, car-ui-lib) [F]

Fontes: [Car/Media dimens](https://android.googlesource.com/platform/packages/apps/Car/Media/+/refs/heads/main/res/values/dimens.xml), [Car/Media integers](https://android.googlesource.com/platform/packages/apps/Car/Media/+/refs/heads/main/res/values/integers.xml), [car-media-common dimens](https://android.googlesource.com/platform/packages/apps/Car/libs/+/refs/heads/main/car-media-common/res/values/dimens.xml), [car-media-common integers](https://android.googlesource.com/platform/packages/apps/Car/libs/+/refs/heads/main/car-media-common/res/values/integers.xml), [car-ui-lib dimens](https://android.googlesource.com/platform/packages/apps/Car/libs/+/refs/heads/main/car-ui-lib/car-ui-lib/src/main/res/values/dimens.xml).

| Elemento | Valor |
|---|---|
| Grade de espaçamento (car-ui) | 4, 8, 12, 16, 24, 32, 48, 64, 96dp (`car_ui_padding_0..8`); margem lateral `car_ui_margin` 112dp |
| Tipografia (car-ui) | body1 32sp, body2 28sp, body3 24sp; sub1 22sp, sub2 20sp, sub3 18sp |
| Toolbar / app bar | Linha de 96dp; ícone da app bar com alvo de 76dp e cantos de 8dp |
| Item de lista (browse e fila) | Altura 116dp; miniatura 76dp; texto recuado 148dp (com miniatura) ou 112dp (com ícone); ícone de categoria 44dp |
| Cabeçalho de grupo | 76dp de altura |
| Grade | 3 colunas (`num_browse_columns`); espaçamento 8dp; 24dp abaixo do item; título 16dp abaixo da arte; ícone de categoria 76dp |
| Indicadores (E, baixado) | 25dp |
| Player: capa | 226dp; 48dp entre a capa e os metadados; título 2 linhas, subtítulo 1 |
| Player: controles | Alvo 64×64dp (abaixo do mínimo de 76 do Design for Driving); espaçamento interno 16dp, externo 81dp, margem 46dp; ripple com inset de 32dp |
| Seekbar | Altura da área 76dp; trilha 4dp; thumb 16dp; margem lateral 112dp |
| Fundo do player | Capa reduzida a 300px, escala 0,5, desfoque de raio 25; scrim preto alpha 0,6 (escurecido 0,9); fundo alpha 0,78 |
| Fila | Fade de 500ms; borda esmaecida de 81dp (0,1 × altura da janela); fundo alpha 0,6; botão de fila com fundo de 54dp |
| Animações | Troca de capa com fade-in de 250ms; fechar o player com gesto vertical para baixo |
| Barra de controles no browse | 192dp; progresso linear de 4dp |
| Cores | Fundo `#000000`; ripple `#27FFFFFF`; ícone de botão `#FFFFFF`, desabilitado `#80FFFFFF`; trilha do progresso minimizado `#464A4D`; destaque do progresso = cor da fonte (accent do app); fundo do ícone da app bar `#66FFFFFF` |
| Cores de placeholder (sem capa) | `#669DF6`, `#667EF6`, `#CA71E6`, `#85B95B`, `#5BB974`, `#EE925C`, `#EE675C`, `#FCC933` |

Fonte das cores: [colors car-media-common](https://android.googlesource.com/platform/packages/apps/Car/libs/+/refs/heads/main/car-media-common/res/values/colors.xml), [car-ui colors](https://android.googlesource.com/platform/packages/apps/Car/libs/+/refs/heads/main/car-ui-lib/car-ui-lib/src/main/res/values/colors.xml).

### 3.3 Aplicação na sua tela (1280×720 a 160 dpi, logo 1dp = 1px) [O]

- A tela inteira tem 720dp de altura.
  - Uma toolbar de 96dp e uma barra de controles de 192dp deixam cerca de 430dp para conteúdo: 3 itens de lista de 116dp, ou 1 linha e meia de grade.
  - Para manter 4 linhas visíveis, considerar itens de 96–104dp, sem descer de 76dp de alvo.
- A margem lateral de 112dp do car-ui foi pensada para telas grandes. Em 1280dp de largura, 24–48dp parecem mais adequados.
- Para o player em paisagem, a proporção do Spotify/YouTube Music de 2026 serve de modelo:
  - capa grande à esquerda (ou à direita no YouTube Music), cerca de 280–360dp;
  - metadados e controles ao lado;
  - fundo desfocado (no Lumia já existe o desfoque na CPU em `ArtBackdrop.kt`);
  - progresso ondulado só como estilo, sem animação contínua que viole o espírito de SA-1.
- Usar 76dp como alvo dos botões principais. O play/pause pode ser maior (96dp), como faz o Material 3 Expressive.

---

## 4. Restrições de direção (UX restrictions)

| Restrição | Valor | Status / fonte |
|---|---|---|
| Teclado | Bloqueado em movimento; voz disponível | [F] [search-template](https://developer.android.com/design/ui/cars/guides/templates/search-template), [AAOS media](https://source.android.com/docs/automotive/hmi/media) |
| Login e configurações | Só parado | [F] [sign-in flow](https://developer.android.com/design/ui/cars/guides/flows/create-sign-in-flow), [technical_details](https://source.android.com/docs/automotive/hmi/media/technical_details) |
| Profundidade | ≤ 3 níveis recomendados (browse com MediaBrowserService). Pilha de até 5 telas (CAL). | [F] [plan-browsing-views](https://developer.android.com/design/ui/cars/guides/app-types/plan-browsing-views), [CAL media](https://developer.android.com/training/cars/apps/media) |
| Fluxos | Tarefas em ≤ 3 passos sempre que possível | [F] [media-apps](https://developer.android.com/design/ui/cars/guides/app-types/media-apps) |
| Itens (AAOS de referência) | `limit_content`: maxCumulativeItems 21 e maxDepth 3 em movimento. `limit_string_length`: 120 caracteres. Também `no_keyboard`, `no_filtering`, `no_video`, `no_setup`. | [F] [car_ux_restrictions_map.xml](https://android.googlesource.com/platform/packages/services/Car/+/refs/heads/main/service/res/xml/car_ux_restrictions_map.xml) (valores padrão AOSP; montadoras podem mudar) |
| Fila em movimento | O card do AAOS pode limitar os itens da fila por UXR | [F] [media-card](https://source.android.com/docs/automotive/hmi/media/media-card) |
| Itens de lista/grade (templates) | Dependem do veículo (ConstraintManager); grade nunca abaixo de 6 | [F] [grid-template](https://developer.android.com/design/ui/cars/guides/templates/grid-template) |
| Texto secundário | 2 linhas em movimento | [F] [list-template](https://developer.android.com/design/ui/cars/guides/templates/list-template) |
| Limite de rolagem no AA ("speed bump") | Mensagem "Browsing is locked due to safety reasons", ~2 s, ao rolar ou trocar listas dirigindo. Relatos de limite de ~5–6 toques. | [O] [comunidade Spotify](https://community.spotify.com/t5/Android/Android-Auto-Annoying-message-during-scrolling/td-p/2483873); relatos de fórum, sem número oficial |
| Truncamento de listas longas no AA | Relatos de ~100–400 itens por lista conforme a unidade | [O] [Roon](https://community.roonlabs.com/t/library-not-fully-listed-in-arc-on-android-auto-ref-ikq2ma/320833) (só snippet de busca) |
| Bug de rolagem em 2025 | Rolagem voltava ao topo em alguns apps | [O] [9to5Google](https://9to5google.com/2025/03/10/android-auto-scrolling-issues/) (só snippet) |

**[O] Recomendação:** o Lumia já tem painel de depuração e lê a velocidade e o freio de mão da TopWay (spec 006). Implementar um "estado de direção" (parado/andando, se houver sinal de velocidade ou freio de mão; senão um modo manual) que aplique:
- sem teclado;
- no máximo cerca de 21 itens acumulados e profundidade 3 (valores AOSP);
- texto secundário em 2 linhas.

---

## 5. O que um launcher comum consegue × o que exige o host do Android Auto

### 5.1 Caminhos de acesso

| Capacidade | Via MediaController do NotificationListener (`MediaSessionManager.getActiveSessions`) | Via MediaBrowser (bind no MediaBrowserService ou MediaLibraryService) | Só o host do AA ou limitação |
|---|---|---|---|
| Descobrir apps de mídia | Só os que têm sessão ativa | `queryIntentServices("android.media.browse.MediaBrowserService")` mais meta-data `com.google.android.gms.car.application` com `<uses name="media"/>` para filtrar "apps de carro" [O viável; manifest [F](https://developer.android.com/training/cars/media/auto)] | — |
| Metadados, estado, posição, velocidade | Sim | Sim (`getSessionToken()` depois de conectar, mesmo sem tocar) | — |
| Play/pause/skip/seek/fila | Sim (TransportControls; `skipToQueueItem`) | Sim | — |
| `setPlaybackSpeed` | Sim no Android 10 (API 29) [O] | idem | — |
| Custom actions do player | Sim (`PlaybackState.getCustomActions()`; ícone é um resId do pacote do app, carregar via `getResourcesForApplication`; `EXTRAS_KEY_COMMAND_BUTTON_ICON_COMPAT` permite usar ícone próprio) | idem | **Apps Media3:** o launcher vê só a configuração da sessão de plataforma compartilhada, igual à do "media notification controller" (notificação do sistema). O app pode dar ao AA botões diferentes (`isAutoCompanionController`) que o launcher nunca verá. [F] [MediaSession](https://developer.android.com/reference/androidx/media3/session/MediaSession) |
| Reserva de slots | Sim (`MediaController.getExtras()`) | idem | — |
| Aleatório/repetir nativos | Não existem na API de plataforma. Via `MediaControllerCompat` criado a partir do token, depois que a sessão fica "pronta" (extra binder). Repeat, shuffle, rating type e extras do PlaybackState não funcionam logo após criar o controller se o token não veio por MediaBrowserCompat. [F] [MediaControllerCompat](https://developer.android.com/reference/android/support/v4/media/session/MediaControllerCompat) | Melhor: token vindo do MediaBrowserCompat | — |
| Erro + ação de resolução | Sim (código, mensagem e extras `ERROR_RESOLUTION_ACTION_*`; `PendingIntent.send()` abre a tela do app) | idem | **[O]** A tela do app aparece na multimídia, não no celular. Avaliar se é aceitável parado. |
| Abrir o app | `getSessionActivity()` | — | — |
| Browse, abas, grupos, indicadores | Não | Sim, **se o app aceitar o pacote** em `onGetRoot`. O launcher pode enviar os mesmos root hints do AA (limite 4, flags BROWSABLE, tamanho de arte, limite de browse actions). | Muitos apps devolvem `null`, um root vazio ou outra árvore a clientes fora da lista. A doc manda permitir sistema, AA/AAOS e Assistente e negar desconhecidos. [F] [content-hierarchy](https://developer.android.com/training/cars/media/create-media-browser/content-hierarchy). Casos reais: Waze recebendo root vazio e launcher de carro "Gearslip" recusado [O] ([myspoti #177](https://github.com/kota-zo/myspoti/issues/177), [RFSentinel #9](https://github.com/CIS-C0/RFSentinel/issues/9), só snippets) |
| Busca navegável | Não | `MediaBrowser.search()` (API 26+) quando `SEARCH_SUPPORTED` | — |
| "Toque X" por voz | `TransportControls.playFromSearch(query, extras)`, com reconhecimento de fala próprio [O viável] | idem | A integração com Assistente/Gemini e o roteamento de intenções são do Google |
| Custom browse actions | Não | `MediaBrowser.sendCustomAction()` (API 26+) mais root hint de limite [F] [MediaConstants](https://developer.android.com/reference/androidx/media/utils/MediaConstants) | Os apps podem só publicar a lista se o hint vier positivo, e alguns podem checar o pacote [O] |
| Botões por item (Media3) | Não | Só com MediaBrowser Media3 e `setMaxCommandsForMediaItems(n)` [F] [serve-content](https://developer.android.com/media/media3/session/serve-content) | — |
| Imagens do browse | — | URIs `android.resource://` podem ser abertos por qualquer app via ContentResolver [O]. URIs `content://` dependem de o provider do app ser exportado ou conceder permissão [O, testar por app]. | — |
| Ícone de atribuição e cor do app | Ler `TintableAttributionIcon` e o tema `com.google.android.gms.car.application.theme` (colorAccent) por PackageManager e `createPackageContext` [O viável; chaves [F](https://developer.android.com/training/cars/media/configure-manifest)] | — | — |
| Retomada do último app/faixa | Se não há sessão ativa: conectar no MediaBrowser e chamar `play()` / `playFromMediaId` | `EXTRA_RECENT` é pensado para o System UI, que a doc manda validar por pacote e assinatura da plataforma. É provável que um launcher receba a árvore normal ou nada. [F] [media controls](https://developer.android.com/media/implement/surfaces/mobile) | — |
| Recomendações (`EXTRA_SUGGESTED`) | — | Pedir com o root hint; depende do app aceitar | — |
| Apps de mídia com template (CAL) | O controle via sessão continua | O MediaBrowserService continua obrigatório nesses apps, mas a UI rica deles é renderizada pelo host do Google ou da montadora [F] [CAL media](https://developer.android.com/training/cars/apps/media), [AOSP CAL host](https://source.android.com/docs/automotive/hmi/aosp_host/media-cal) | **[O]** Um launcher comum não consegue hospedar templates CAL. Para esses apps restam a sessão e o MediaBrowserService mínimo. |
| Notificação em troca de faixa / anúncios | Ler `METADATA_KEY_IS_ADVERTISEMENT` e não mostrar toast em anúncios | — | — |

### 5.2 Riscos e cuidados práticos [O]

1. **Fallback obrigatório.** Para cada app, tente MediaBrowser. Se `onConnectionFailed`, root vazio ou timeout (~5 s): modo "só player", com sessão do NotificationListener, sem abas nem busca. Guarde o resultado por pacote e versão.
2. **Não disparar autoplay.** Conectar ao MediaBrowserService inicia o processo do app. Os apps são proibidos de tocar sozinhos ao conectar (MA-1). O launcher também não deve chamar `play()` sem ação do usuário, exceto numa opção explícita de "retomar ao ligar", como a configuração "Retomar mídia automaticamente" do AA.
3. **Sessões múltiplas.** O AA lista sessões, não apps [F]. Deduplicar por pacote e priorizar a sessão em estado PLAYING.
4. **Testar no carro os apps mais usados** (Spotify, YouTube Music, Deezer, Amazon Music, Pocket Casts/podcasts, Audible, apps de rádio), em três frentes:
   - se aceitam o pacote `app.mylauncher`;
   - se as imagens `content://` abrem;
   - quais custom actions aparecem.
   - Registrar os resultados na seção "Resultados" da spec, conforme o processo do projeto.

---

## 6. Recomendações para a spec do redesenho [O]

- **Telas:**
  - (a) Seletor de fontes em grade, com ícone e rótulo, mais último usado e tocando agora.
  - (b) Browse com até 4 abas e listas/grades conforme os content style hints, com títulos de grupo, A–Z em listas longas e indicadores E / baixado / progresso / novo.
  - (c) Player em paisagem: capa, título até 2 linhas, subtítulo 1 linha (com link quando houver), indicadores, progresso só com duração, seek só com `ACTION_SEEK_TO`, e barra de 5 slots com reserva e custom actions na ordem do app, mais overflow de até 4 ações secundárias.
  - (d) Fila sobreposta com item atual destacado e itens anteriores.
  - (e) Mini player persistente no browse e no dashboard.
  - (f) Estados: erro com botão de resolução, login, sem conteúdo e buffering com spinner no play.
- **Card do dashboard:** capa com cores extraídas dela (Palette, compatível com Android 10), play/pause que muda de forma, swipe para recomendações quando houver MediaBrowser e `EXTRA_SUGGESTED`.
- **Restrições de direção:** sem marquee, sem animação contínua, sem teclado em movimento, 76dp mínimo, contraste ≥ 4,5:1 e tema escuro à noite.
- **Ativos:** ícones próprios mapeados de `CommandButton.ICON_*`, sem ativos do Google.

## 7. Lacunas (não confirmadas)

- Não existe especificação pública em pixels da UI projetada do AA, nem do redesenho de 2026. Os números da seção 3.2 são da referência AAOS.
- Também não há número oficial para o limite de itens nem para a "trava" de rolagem no AA.
- Há números conflitantes de custom actions:
  - página de design: até 6, ou 8 sem Anterior/Próximo;
  - página partner antiga: card de até 9 ações, com 4 secundárias;
  - AAOS: 5 na linha inferior e 5 na superior.
  - Trate como "o que couber, mais overflow", na ordem do app.
- O valor típico do root hint de limite de custom browse actions no AA não é documentado.
- Os artigos da Android Authority (403) e alguns posts de fórum e do GitHub foram vistos só por snippet de busca.

---

## 8. O Lumia hoje × o Android Auto

| Recurso do AA | Lumia hoje (specs 004, 010, 016) | Falta |
|---|---|---|
| Card de mídia no painel | Capa em vidro fosco, controles, abre o player dividido | Ações extras (aleatório/repetir/curtir) no card; sugestões quando nada toca (`EXTRA_SUGGESTED`) |
| Tocando agora | Capa, título, artista, progresso arrastável, aleatório, repetir, fila, cor da capa, troca de faixa animada | Barra de 5 posições com reserva de slots; ações personalizadas do app (`getCustomActions`) com overflow; indicadores (explícito, baixado, formato); spinner em buffering; barra só com duração; seek só com `ACTION_SEEK_TO`; velocidade e ±15/30 s em podcasts; links no subtítulo |
| Fila | Lista de títulos | Miniaturas, item atual fixado e destacado, itens já tocados, título da fila (`queueTitle`) |
| Navegação no app (browse) | Não existe (só a barra de fontes) | Conexão ao `MediaBrowserService`, abas (até 4), listas, grades e categorias pelos content style hints, títulos de grupo, indicadores de progresso/novo, até 3 níveis, mini player persistente |
| Busca | Não existe | `MediaBrowser.search` quando `SEARCH_SUPPORTED`; busca por voz com `playFromSearch`; teclado só parado |
| Seleção de app | Abas de fonte no player | Seletor em grade com os apps de mídia (`MediaBrowserService` + meta-data de carro) e as fontes da TopWay |
| Estados | "Nada tocando" e pedido de permissão | Login necessário com ação de resolução, erro, sem conexão, carregando, app que recusa a navegação (modo só player) |
| Restrições em movimento | Edição da gaveta e dos widgets só parado | Sem teclado, até ~21 itens e 3 níveis, texto secundário em 2 linhas, aviso de navegação limitada |
| Retomada | Retoma a última música ao ligar a chave (spec 016) | — (já equivale ao "Retomar mídia automaticamente") |

## 9. Decisões para o Lumia (propostas na spec 018)

- **Modo degradado é o padrão seguro:** todo app funciona como "só player" pela MediaSession; o browse é um extra quando o app aceitar o pacote `app.mylauncher`. Testar e registrar por app.
- **Visual:** layout do AA de 2026 sobre os tokens atuais (spec 015). Capa desfocada no fundo (já existe na CPU, `ArtBackdrop.kt`), progresso ondulado **estático** (sem animação contínua, por desempenho e pela regra SA-1), botão tocar/pausar em quadrado arredondado que muda de forma.
- **Ícones próprios** mapeados de `CommandButton.ICON_*` e dos ícones das custom actions tingidos de branco; nenhum ativo do Google; apps nos wireframes são fictícios.
- **Estado de direção** vem da velocidade/freio de mão da TopWay (spec 006), com simulação no painel de depuração.
