# 018 — Mídia e streaming no padrão do Android Auto

**Status:** Proposta (estudo concluído, aguardando aprovação) · **Depende de:** 004, 006, 010, 015, 016 · **Estudo:** [`docs/05-media-android-auto.md`](../docs/05-media-android-auto.md) · **Design:** página "Mídia e streaming (estudo)" do canvas

## Objetivo
Ter no Lumia os mesmos recursos e a mesma UX de mídia do Android Auto:
- navegar no catálogo do app (abas, listas, grades);
- buscar;
- tocando agora com as ações do app;
- fila;
- estados de erro e de login;
- mini player;
- restrições com o carro em movimento.

O Lumia faz o papel do host do Android Auto: o app de mídia fornece os dados e o Lumia desenha tudo.

## Requisitos

### Fontes e conexão
- **R18.1** O seletor de mídia DEVE listar os apps que declaram `MediaBrowserService`/`MediaLibraryService` (com prioridade aos que têm a meta-data de carro `<uses name="media"/>`), os apps com sessão ativa e as fontes da TopWay (rádio, Bluetooth, USB), em grade, com o app tocando agora em destaque.
- **R18.2** Ao abrir um app, o Lumia DEVE tentar conectar ao `MediaBrowserService` com os mesmos root hints do Android Auto (limite de 4 abas, só navegáveis, tamanho de arte, limite de browse actions). SE a conexão falhar, o root vier vazio ou passar de 5 s, ENTÃO o app DEVE abrir em **modo só player** (MediaSession), com o aviso "Navegação indisponível neste app" e o botão **Abrir o app**. O resultado fica guardado por pacote e versão.
- **R18.3** Conectar NÃO DEVE iniciar a reprodução: só uma ação do usuário ou a retomada ao ligar a chave (spec 016) tocam.
- **R18.4** Várias sessões do mesmo app DEVEM ser deduplicadas, com prioridade para a que estiver tocando.

### Navegação (browse)
- **R18.5** Os filhos navegáveis do root DEVEM virar abas (até 4, ícone monocromático + rótulo; só rótulo se não couber).
- **R18.6** Listas e grades DEVEM seguir os content style hints: `BROWSABLE`/`PLAYABLE` (lista, grade, lista de categorias, grade de categorias), `SINGLE_ITEM` e `GROUP_TITLE` (itens contíguos formam um grupo com cabeçalho).
- **R18.7** Os itens DEVEM mostrar os indicadores: explícito, baixado/baixando, novo (não tocado), barra de progresso (`COMPLETION_PERCENTAGE`, atualizada ao vivo pelo `KEY_MEDIA_ID` do PlaybackState) e concluído.
- **R18.8** Abaixo do topo, um cabeçalho fixo com voltar e o título do nó. Ações de browse personalizadas do nó (ex.: Aleatório, Baixar) aparecem como botões abaixo do cabeçalho; as dos itens, no toque longo, com overflow.
- **R18.9** Listas longas DEVEM ter rolagem por botões (teclas do volante) e salto A–Z.
- **R18.10** Com algo tocando, a navegação DEVE mostrar o **mini player** fixo embaixo (capa, título, artista, progresso de 4 dp na cor do app, anterior/tocar/próxima); tocar nele abre o tocando agora.

### Busca
- **R18.11** SE o app declarar `SEARCH_SUPPORTED`, ENTÃO a navegação DEVE ter **Buscar**: campo com teclado só com o carro parado; em movimento, só voz (`playFromSearch` + reconhecimento do sistema) e as buscas recentes. Resultados agrupados pelos títulos de grupo do app.

### Tocando agora
- **R18.12** Layout: capa grande (≥ 280 dp) à esquerda, título (até 2 linhas), subtítulo (1 linha, vira link quando houver `KEY_SUBTITLE_LINK_MEDIA_ID`), indicadores (explícito, baixado, formato de áudio), tempos e progresso; fundo com a capa desfocada (uma imagem estática).
- **R18.13** Barra de 5 posições: [ação 1] [anterior] [tocar/pausar] [próxima] [ação 2 ou mais]. Sem anterior/próxima, as ações personalizadas ocupam a posição, exceto se a sessão reservar o espaço (`ALWAYS_RESERVE_SPACE_FOR.ACTION_SKIP_TO_*`). As ações seguem a ordem do app. Com mais de 5, a última posição vira **Mais**, que abre até 4 ações secundárias numa linha acima.
- **R18.14** Ícones das ações: os do app tingidos de branco ou os nossos, mapeados de `CommandButton.ICON_*`. Ação indisponível fica esmaecida e não responde.
- **R18.15** A barra de progresso só aparece com duração; arrastar só com `ACTION_SEEK_TO`. Em buffering, o tocar/pausar vira um indicador de carregamento.
- **R18.16** **Fila** no cabeçalho, só se a sessão tiver fila; abre sobre o player com o título da fila, o item atual fixado e destacado, os itens já tocados esmaecidos e miniaturas.

### Estados
- **R18.17** `STATE_ERROR` DEVE mostrar a mensagem do app e, se houver `ERROR_RESOLUTION_ACTION_LABEL/INTENT`, o botão de resolução (ex.: **Entrar**), habilitado só com o carro parado. Erros não fatais aparecem uma vez, como aviso.
- **R18.18** Carregando: esqueleto da lista; o indicador de progresso só depois de 1 s. Sem conexão: mensagem e atalho para o conteúdo baixado, se o app tiver.

### Painel e tela dividida
- **R18.19** O card de mídia DEVE ganhar capa desfocada no fundo e, quando nada tocar, as sugestões do app (`EXTRA_SUGGESTED`, até 10), com deslizar para trocar e tocar sem abrir o app.
- **R18.20** O player dividido (spec 010) DEVE usar a mesma barra de 5 posições e as mesmas regras.

### Restrições em movimento
- **R18.21** Com o carro andando (velocidade da TopWay, spec 006; simulação no painel de depuração): sem teclado, login e ajustes; até 21 itens acumulados e 3 níveis por lista; texto secundário em 2 linhas; o resto fica atrás do aviso "Mais itens com o carro parado".
- **R18.22** Sem texto rolando sozinho e sem animação contínua; alvos ≥ 76 dp; contraste ≥ 4,5:1; tema escuro à noite.

## Critérios de aceite
- [ ] **A1** (R18.2) Para Spotify, YouTube Music, Deezer, um app de podcast e um de rádio: registrar se aceitam o browse do Lumia; os que recusam abrem em modo só player.
- [ ] **A2** (R18.5–R18.7) Num app que aceita: abas, grupos e indicadores iguais aos do Android Auto no celular.
- [ ] **A3** (R18.13) Num app de podcast: ±15/30 s ocupam as posições de anterior/próxima e a velocidade aparece como ação.
- [ ] **A4** (R18.16) A fila mostra miniaturas, o item atual destacado e tocar num item troca a faixa.
- [ ] **A5** (R18.17) Sem login no app: a mensagem e o botão **Entrar** aparecem; em movimento, o botão fica desabilitado.
- [ ] **A6** (R18.21) Com a velocidade simulada > 0: teclado bloqueado e lista cortada em 21 itens com o aviso.

## Resultados
_(preencher após a implementação)_
