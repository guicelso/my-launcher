# 016 — Desligar e ligar a chave (ACC): voltar ao painel e retomar a música

**Status:** Implementada · validação parcial no carro · **Depende de:** 004, 006, 010

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

## Configuração da TopWay que interfere
- **"Inicialização da aplicação"** (Configurações do carro da TopWay) grava `/data/tw/app_autostart_name` (pacote, activity, nome). Ao ligar a chave, o `TWService` abre esse app **em primeiro plano** 5 s depois do ACC_ON (mensagem 65290). Com o YouTube Music nele, a tela do app aparecia por cima do painel mesmo com a música já retomada em segundo plano pelo launcher.
- Como o launcher passou a retomar a música (R16.6), essa opção deve ficar em **nenhum app** (arquivo vazio). Na multimídia do carro ela foi esvaziada por adb em 2026-11-21; o conteúdo anterior está em `~/lumia-backup/app_autostart_name.bak`.

## Resultados
- **2026-11-21, multimídia (1º ciclo da chave):**
  - [x] **A1**: voltou direto ao painel. O log da TopWay mostra `mDiskillPackageList.contains(...)` poupando o YouTube Music, e o Lumia não aparece mais entre os apps fechados.
  - [x] **A2**: "ACC desligado; música tocando: true" → "ACC ligado; voltando ao painel e retomando a música" → "Retomando com.google.android.apps.youtube.music pela sessão ativa". A música voltou sozinha.
  - Pendência resolvida na fonte: a tela do YouTube Music vinha da "Inicialização da aplicação" da TopWay (ver acima), não do launcher. Opção esvaziada; revalidar no próximo ciclo.
- **2026-11-21, multimídia (2º ciclo, com a "Inicialização da aplicação" vazia):**
  - [x] **A1/A2** de novo: painel direto e música retomada pela sessão ativa 3 s depois do ACC_ON.
  - [x] A TopWay leu o arquivo vazio (`packageName = null`) e não abriu a tela do app: a música continuou em segundo plano.
  - [ ] **A3** e **A4**: a validar.
