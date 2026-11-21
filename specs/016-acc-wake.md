# 016 — Desligar e ligar a chave (ACC): voltar ao painel e retomar a música

**Status:** Em andamento · **Depende de:** 004, 006, 010

## Contexto (medido no carro em 2026-11-21)
Ao tirar a chave, a multimídia não desliga: entra em espera (`TWService`: `+STANDBY2`/`+SLEEP`). Antes de dormir, a rotina `killPackages` da TopWay faz `forceStopPackage` em todos os apps instalados, exceto:
- uma lista fixa no código (DoFun, `com.android.launcher`, serviços `com.tw.*`, teclado atual…);
- os pacotes listados em **`/sdcard/.Tcfg/sleepKillPackage.cfg`** ("mDiskillPackageList"), relido a cada desligamento.

O Lumia não está em nenhuma das duas listas. Por isso ele era fechado; ao religar, a tela que sobrava no topo era a de apps recentes. O app de música também era fechado, e o card mostrava "Nada tocando".

## Requisitos
- **R16.1** O launcher DEVE garantir que o próprio pacote esteja em `sleepKillPackage.cfg` (só em aparelhos TopWay, onde `/sdcard/.Tcfg` existe), preservando as linhas que já estiverem lá.
- **R16.2** Com **Manter o app de música aberto com a chave desligada** ligado (padrão), o app de música padrão também DEVE estar na lista, para a sessão de mídia sobreviver à espera.
- **R16.3** QUANDO a chave for ligada (ACC desligado → ligado), o launcher DEVE voltar ao painel.
- **R16.4** O launcher DEVE guardar a última sessão de mídia (app, faixa, artista, capa, posição e se estava tocando). Sem sessão ativa, o card e o player DEVEM mostrá-la com o botão tocar, em vez de "Nada tocando".
- **R16.5** Tocar numa sessão guardada DEVE retomar a música sem abrir o app, nesta ordem:
  1. sessão ativa do app → `play()`;
  2. conexão ao `MediaBrowserService` do app com o pedido de retomada (`EXTRA_RECENT`) → `play()`;
  3. tecla `MEDIA_PLAY` direcionada ao app;
  4. se nada disso criar uma sessão em 5 s, abrir o app.
- **R16.6** Com **Retomar a música ao ligar o carro** ligado (padrão), QUANDO a chave for ligada, o launcher DEVE retomar a música se ela estava tocando ao desligar. A TopWay pausa a mídia ao entrar em espera; uma pausa até 15 s antes do ACC desligar conta como "estava tocando".
- **R16.7** Os dois ajustes ficam em Ajustes → Mídia.

## Critérios de aceite
- [ ] **A1** (R16.1, R16.3) Desligar e ligar a chave volta direto ao painel do Lumia, sem a tela de apps recentes.
- [ ] **A2** (R16.2, R16.6) Com o YouTube Music tocando, desligar e ligar a chave retoma a música sozinha.
- [ ] **A3** (R16.4, R16.5) Depois de um reboot, o card mostra a última faixa; tocar em ▶ retoma sem abrir o app (ou abre o app em último caso).
- [ ] **A4** Com a música pausada antes de desligar, ela continua pausada ao ligar.

## Resultados
_(preencher após a validação)_
