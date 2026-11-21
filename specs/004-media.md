# 004 — Mídia (card e tela cheia)

**Status:** Rascunho · **Depende de:** 002 · **Design:** artboards "Mídia" e card de mídia do painel

## Objetivo
Mostrar e controlar o que está tocando, venha de um app de terceiros (MediaSession) ou dos apps nativos da TopWay (rádio, música local, BT).

## Requisitos
- **R4.1** O app DEVE declarar um `NotificationListenerService`, usado para obter `MediaSessionManager.getActiveSessions()`.
- **R4.2** `tools/deploy.sh` DEVE conceder o acesso do listener via `cmd notification allow_listener app.mylauncher/.data.media.MediaListenerService`.
- **R4.3** O card e a tela DEVEM mostrar: app de origem, capa (ou placeholder), título, artista, progresso e os controles anterior / tocar-pausar / próxima.
- **R4.4** QUANDO houver várias sessões, o launcher DEVE mostrar a última que mudou para `PLAYING`.
- **R4.5** A tela cheia DEVE ter seletor de fonte (Spotify, YouTube Music, Rádio, Bluetooth, mais apps de mídia instalados), aleatório, repetir e fila (se a sessão expuser `queue`).
- **R4.6** Para fontes da TopWay sem MediaSession, o launcher DEVE usar:
  - `CommandService` (spec 006) para a fonte ativa e os comandos;
  - os broadcasts `com.tw.music.info` / `com.tw.launcher.musicinfo` para os metadados.
- **R4.7** SE nenhuma mídia estiver ativa, ENTÃO o card DEVE mostrar "Nada tocando" e um botão para abrir o app de música padrão.
- **R4.8** O progresso DEVE ser interpolado localmente a partir de `PlaybackState.position` e `lastPositionUpdateTime`, sem consultas periódicas à sessão.

## Critérios de aceite
- [ ] **A1** (R4.3, R4.4) Com o Spotify tocando, o card mostra título, artista e capa; os controles funcionam com até 500 ms de latência.
- [ ] **A2** (R4.4) Pausar o Spotify e tocar o YouTube Music faz o card trocar para o YouTube Music.
- [ ] **A3** (R4.6) O rádio da TopWay aparece no card com nome ou frequência da estação. SE isso não for possível, ENTÃO a limitação fica registrada.
- [ ] **A4** (R4.6) Uma música via BT do celular aparece no card.
- [ ] **A5** (R4.7) Sem nada tocando, o estado vazio aparece.

## Pesquisa aberta
- Ver se `com.tw.bt` e `com.tw.radio` publicam MediaSession: rodar `dumpsys media_session` com cada fonte ativa.
- Descobrir quais extras o `CommandService` envia em `Source_recieve`.

## Resultados
**2026-11-21, no carro:**
- YouTube Music aparece no card com capa e título.
- Tocar/pausar, avançar e voltar funcionam (A1 ✔).
