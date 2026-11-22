# 017 — Pilhas de widgets (deslizar para trocar)

**Status:** Aprovada (2026-11-21) · em andamento · **Depende de:** 006, 011, 013, 016 · **Design:** página "Pilhas de widgets" do canvas (`design/wireframes/Stack*.dc.html`)

## Objetivo
Cada espaço da coluna direita do painel passa a ser uma **pilha**: vários widgets dividem o mesmo lugar e o motorista desliza o card para os lados para trocar entre eles. A ideia vem das pilhas de widgets da One UI (Samsung), adaptada ao carro.

## Referência: como a Samsung faz (pesquisa de 2026-11-21)
- **Criar:** toque longo num widget → *Criar pilha* → escolher outro widget. Os widgets passam a ocupar o mesmo espaço e assumem o tamanho da pilha.
- **Trocar:** deslizar para a esquerda ou a direita sobre a pilha.
- **Editar:** toque longo na pilha → *Editar pilha*. Os widgets aparecem lado a lado, cada um com um sinal de menos vermelho para remover e, no fim, um card **+** para adicionar. Na mesma tela fica a chave *Girar widgets automaticamente*.
- **Limite:** até 7 widgets por pilha (One UI 4.1, quando o recurso se chamava *Smart Widgets*).
- **Desfazer:** toque longo → *Remover tudo* apaga a pilha.
- **Giro automático:** mostrava "primeiro a informação mais relevante". O giro por tempo saiu na One UI 6.1.1.
- **Queixa conhecida:** o deslizar da pilha briga com o deslizar entre as telas iniciais.

Fontes: [Samsung: widgets em celulares Galaxy](https://www.samsung.com/us/support/answer/ANS10000945/), [Android Police: Smart Widgets](https://www.androidpolice.com/how-to-use-samsung-smart-widgets/), [9to5Google: Smart Widgets](https://9to5google.com/2022/03/25/create-smart-widgets-on-your-samsung-galaxy-smartphone-heres-how/), [Comunidade Samsung: direção do deslizar](https://us.community.samsung.com/t5/Galaxy-S25/Widget-stack-swiping/td-p/3131890), [Comunidade Samsung: giro automático](https://us.community.samsung.com/t5/Suggestions/Widget-Stack-Auto-Rotation/m-p/3079599).

## Decisões de UX para o carro
- **Toda pilha é um espaço da spec 013.** Um espaço com um widget só continua igual ao de hoje, sem pontos. Não existe "criar pilha": basta adicionar um segundo widget ao espaço.
- **Deslizar não briga com nada:** o painel não tem páginas e o mapa fica em outro card. A rolagem é **circular** (depois do último volta ao primeiro), para trocar sempre na mesma direção.
- **Indicador só quando mexe:** pontos de 6 dp na base do card (o ativo vira um traço de 18 dp), numa pílula escura translúcida para ler sobre qualquer widget. Eles **aparecem ao tocar ou arrastar** a pilha (e quando uma sugestão troca o widget) e somem 1,5 s depois. Em repouso o card fica limpo (decisão do dono do projeto).
- **Principal:** o primeiro widget da pilha é o principal e aparece quando a chave é ligada (spec 016). Durante o uso a pilha fica onde o motorista deixou.
- **Sugestões inteligentes por evento, nunca por tempo.** Um card que muda sozinho enquanto se dirige distrai, e foi isso que a Samsung tirou. A pilha só pula para um widget quando algo acontece (lista em R17.7), mostra um selo por 5 s e para de sugerir até o próximo evento se o motorista deslizar.
- **Deslizar vale sempre; editar só com o carro parado** (mesma regra da spec 011).
- **Um widget fica numa pilha só** (mantém a regra R13.2). Adicionar um widget que está na outra pilha muda ele de lugar.
- **Reordenar com setas**, não com arrastar: alvos de 64 dp são mais fáceis de acertar na multimídia do que arrastar um card.

## Requisitos
- **R17.1** Cada espaço do painel DEVE guardar uma lista ordenada de 1 a 7 widgets do catálogo da spec 013. A configuração é persistida. SE vier uma configuração antiga (um widget por espaço), ENTÃO ela vira uma pilha de um widget.
- **R17.2** QUANDO o motorista desliza horizontalmente sobre uma pilha com 2 ou mais widgets, o widget DEVE seguir o dedo e o vizinho entrar pela borda. Ao soltar depois de 20% da largura, ou com um gesto rápido, a pilha vai para o vizinho; senão volta. A rolagem é circular.
- **R17.3** QUANDO o motorista tocar ou arrastar uma pilha com 2 ou mais widgets, ou uma sugestão trocar o widget, o card DEVE mostrar os pontos indicadores (quantidade e posição). Eles DEVEM sumir 1,5 s depois do fim do toque ou da troca. Em repouso, os pontos NÃO aparecem.
- **R17.4** QUANDO a pilha estiver com o foco das teclas do volante, esquerda e direita DEVEM trocar o widget.
- **R17.5** QUANDO o motorista der um toque longo numa pilha com o carro parado, o launcher DEVE abrir o menu da pilha: *Editar pilha*, *Adicionar widget à pilha*, *Trocar este widget* (catálogo da spec 013) e *Remover <widget> da pilha*. SE o carro estiver andando, ENTÃO o toque longo mostra o aviso "Disponível só com o carro parado" (como na spec 011).
- **R17.6** A tela *Editar pilha* DEVE mostrar os widgets lado a lado, na ordem, com prévia, botão de remover, setas para mover e o card **+** enquanto houver menos de 7; a chave *Sugestões inteligentes* (padrão: ligada, vale para todas as pilhas); e *Desfazer pilha*, que deixa só o widget principal. A pilha nunca fica vazia.
- **R17.7** ENQUANTO *Sugestões inteligentes* estiver ligada, QUANDO acontecer um destes eventos, a pilha que tem o widget correspondente DEVE ir até ele e mostrar um selo por 5 s:
  - a música começa a tocar → Mídia;
  - a tensão da bateria fica abaixo de 11,8 V por 30 s (a queda da partida não conta) → Veículo;
  - chega uma notificação de mensagem → Notificações (ou Mensagens);
  - a chave é ligada → widget principal.
- **R17.8** SE o motorista deslizar uma pilha, ENTÃO ela NÃO DEVE receber sugestões até o próximo evento de outro tipo.
- **R17.9** A troca de widget DEVE manter 60 fps na multimídia, compondo só o widget visível e o vizinho durante o arrasto, e NÃO DEVE redimensionar o mapa (armadilha do `MapView`).
- **R17.10** A pilha DEVE anunciar para acessibilidade "<widget>, <n> de <total>" e o gesto de deslizar.

## Critérios de aceite
- [ ] **A1** (R17.1) Depois de atualizar o app, o painel mostra os mesmos widgets de antes, sem pontos.
- [ ] **A2** (R17.2, R17.3) Numa pilha Mídia + Clima + Veículo, deslizar para a esquerda três vezes passa por todos e volta para Mídia; os pontos aparecem ao tocar, acompanham a troca e somem logo depois.
- [ ] **A3** (R17.2) Arrastar menos de 20% e soltar devolve o mesmo widget.
- [ ] **A4** (R17.4) Com o foco na pilha, as teclas do volante trocam o widget.
- [ ] **A5** (R17.5, R17.6) Parado: toque longo → *Adicionar widget à pilha* → Relógio aparece no fim da pilha; reiniciar o launcher mantém a pilha.
- [ ] **A6** (R17.6) Mover Clima para a primeira posição faz ele aparecer ao ligar a chave.
- [ ] **A7** (R17.5) Andando, o toque longo mostra o aviso e não abre o menu.
- [ ] **A8** (R17.7, R17.8) Com o Clima na frente, dar play na música leva a pilha para Mídia com o selo; deslizar para o Clima e trocar de faixa não puxa de volta.
- [ ] **A9** (R17.9) Deslizar com o mapa navegando não engasga o mapa nem a troca.

## Roteiro de teste
1. Emulador (`tools/emulator.sh --deploy`): A1–A6 e A8, com o painel de depuração simulando velocidade e bateria.
2. Multimídia (`tools/deploy.sh`): repetir A2, A4, A7, A8 e A9 no carro, com navegação ativa.

## Tarefas
1. Modelo: `widgets: List<WidgetType>` vira `stacks: List<List<WidgetType>>` em `SettingsRepository`, com migração e testes de `Widgets` (adicionar, remover, mover, unicidade entre pilhas, limite de 7).
2. `WidgetStack` (Compose): `HorizontalPager` circular, pontos indicadores, teclas do volante e semântica de acessibilidade.
3. Menu de toque longo e tela *Editar pilha* (overlay dentro da janela, `PopupProperties(focusable = false)`), reaproveitando o catálogo da spec 013 para adicionar e trocar.
4. Sugestões inteligentes: eventos de mídia, veículo, notificações e ACC (spec 016) → índice da pilha, selo e supressão.
5. Ajustes → Painel: listar as pilhas e abrir *Editar pilha* por lá também.
6. Teste no emulador e depois no carro; registrar aqui em "Resultados".

## Resultados

### Emulador (2026-11-21)
- **A1** ✅ A configuração antiga virou pilhas de um widget, sem pontos.
- **A2** ✅ Deslizar troca e dá a volta (Clima → Mídia); os pontos aparecem ao tocar e somem 1,5 s depois.
- **A3** ✅ Arrasto de 12% volta; de 29% troca. O pager do Compose usava 50% por padrão; ajustado para 20%.
- **A4** ⏳ Teclas do volante: implementado, testar no carro.
- **A5** ✅ Adicionar pelo menu e pelo catálogo; a pilha continua igual depois de fechar o app à força.
- **A6** ✅ Mover pelas setas troca o principal. A volta ao principal com a chave ligada fica para o carro.
- **A7** ✅ A 20 km/h, o toque longo mostra "Disponível só com o carro parado" e não abre o menu.
- **A8** ✅ Música tocando puxa a pilha para Mídia com selo; depois de deslizar, tocar de novo não puxa (R17.8). Bateria a 11,4 V traz o Veículo depois de 30 s.
- **Correção:** com 2 widgets, o pager compunha o mesmo card dos dois lados. Com a mesma chave de elemento compartilhado, ele era desenhado fora da pilha, por cima do mapa. Agora só a página assentada usa o movimento compartilhado.
- Painel de depuração ganhou "Tocar/pausar" e "Bateria fraca/normal" para estes testes.

### Multimídia (2026-11-21)
- Release instalada. **A1** ✅ o painel abriu com os mesmos widgets de antes.
- **Atenção:** a barra mostrava 11,8 V com o carro parado, exatamente o limite. Se a tensão em repouso ficar logo abaixo disso, a sugestão "Bateria fraca" pode aparecer sem necessidade. Avaliar se o limite deve baixar (ex.: 11,5 V) ou valer só com o motor ligado.
- Pendentes no carro: A2–A9 com navegação ativa.
