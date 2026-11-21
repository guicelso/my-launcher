# 010 — Área de trabalho, tela dividida, animações e player

**Status:** Aprovada (pedido do dono, 2026-11-21) · **Depende de:** 002, 004, 005

## Objetivo
Aproximar a fluidez do Android Auto:
- o mapa fica sempre vivo, e os painéis ao lado dele mudam de tamanho com animação;
- tocar num app de mídia abre o player **dividindo a tela com o mapa**;
- o player ganha visual e interações mais ricos.

## Conceito: área de trabalho
O mapa e um **painel lateral** ocupam a área à direita da barra. O mapa é criado **uma única vez** e nunca é recriado ao trocar de modo; só a largura do painel anima.

| Modo | Mapa | Painel lateral (largura) | Como se chega |
|---|---|---|---|
| **Painel** | card | mídia + sugestões (408 dp) | botão Painel, HOME |
| **Dividido: mídia** | card | player completo (≈ 600 dp) | tocar num app de mídia na barra, ou na capa do card de mídia |
| **Mapa** | tela cheia | — (0 dp) | botão expandir do mapa, app de navegação padrão |
| Mídia (tela cheia) | oculto (pausado) | — | botão expandir do player dividido |

Gaveta de apps e Ajustes abrem **por cima** da área de trabalho. O mapa fica pausado, mas não é destruído.

## Requisitos
### Área de trabalho e animações
- **R10.1** O `MapView` DEVE ser criado uma vez e reutilizado em todos os modos, sem recarregar ao trocar de modo nem ao voltar da gaveta ou dos ajustes.
- **R10.2** A troca de modo DEVE animar a largura do painel com spring, sem quique, em ~350 ms. O conteúdo do painel troca com fade + escala sutil (0,96 → 1).
- **R10.3** Telas sobrepostas (gaveta, ajustes, mídia em tela cheia) DEVEM entrar e sair com fade + escala sutil em ≤ 250 ms.
- **R10.4** Todo elemento tocável DEVE ter retorno de pressão (escala 0,96 em ≤ 100 ms) além do ripple.
- **R10.5** Enquanto o mapa estiver oculto, ele DEVE parar de renderizar (`onStop`), para não gastar GPU.

### Tela dividida com mídia
- **R10.6** Um app da barra é considerado **de mídia** se:
  - declara `MediaBrowserService`; ou
  - responde a `MEDIA_BUTTON`; ou
  - está entre as fontes conhecidas (spec 004).
- **R10.7** QUANDO o usuário tocar num app de mídia na barra:
  - SE ele já tiver sessão ativa, ENTÃO o launcher abre o modo **Dividido: mídia** com aquela sessão;
  - SENÃO o launcher envia `MEDIA_PLAY` ao app (broadcast `MEDIA_BUTTON` direcionado) e abre o modo dividido;
  - SE em 3 s não surgir sessão, ENTÃO o launcher abre o app normalmente.
- **R10.8** Tocar num app de navegação da barra DEVE continuar abrindo o app (a navegação embutida é a spec 008).
- **R10.9** Ajustes → Mídia DEVE ter a chave **Abrir apps de mídia em tela dividida** (padrão: ligado).

### Player
- **R10.10** O fundo do player DEVE ser tingido com a cor dominante da capa (Palette), escurecida para manter contraste ≥ 4,5:1, com transição de cor de 500 ms ao trocar de faixa.
- **R10.11** A troca de faixa DEVE animar capa e título (slide horizontal + fade, na direção da troca).
- **R10.12** A barra de progresso DEVE atualizar uma vez por segundo, sem animação contínua: ela forçava o redesenho da interface o tempo todo (medido no carro). Se a sessão permitir, DEVE aceitar arrastar para buscar, com alvo de toque de 76 dp de altura.
- **R10.13** O botão tocar/pausar DEVE trocar de ícone com animação (crossfade + escala).
- **R10.14** O player dividido DEVE mostrar: app de origem, capa (≥ 240 dp), título, artista, progresso, controles (aleatório, anterior, tocar/pausar, próxima, repetir) e os botões **Fila** e **Expandir**.

## Critérios de aceite
- [ ] **A1** (R10.1, R10.2) Painel → tocar no Spotify → o mapa encolhe com animação e o player aparece. O mapa não pisca nem recarrega os tiles.
- [ ] **A2** (R10.7) Com o Spotify parado, tocar no ícone dele na barra inicia a música sem abrir o app.
- [ ] **A3** (R10.10, R10.11) Ao trocar de faixa, capa, título e cor de fundo mudam com transição suave.
- [ ] **A4** (R10.12) Arrastar a barra de progresso muda a posição da música.
- [ ] **A5** (R10.5) Com a gaveta aberta, `dumpsys gfxinfo` não mostra quadros novos do mapa.
- [ ] **A6** Navegação geral sem travadas visíveis na multimídia (≤ 10% de quadros lentos ao trocar de modo).

## Resultados
_(preencher após a validação)_
