# 015 — Visual 1:1 com o Android Auto (Coolwalk)

**Status:** Implementada · validar no carro · **Depende de:** 002, 003, 004, 009, 013, 014 · **Design:** canvas reorganizado por seção do doc oficial (`design/wireframes/README.md`)

## Objetivo
Aplicar ao app o redesenho 1:1 com a interface oficial do Android Auto ([System UI](https://developers.google.com/cars/design/android-auto/product-experience/system-ui/overview)), inclusive nos widgets, sem perder nenhuma funcionalidade existente.

## Requisitos
- **R15.1** Os tokens de cor e forma DEVEM seguir os valores medidos nas imagens oficiais (`docs/02-design.md`): fundo `#151616`, cards `#252626` com raio de 24 dp, espaço de 12 dp, botão principal `#C5D4FF`, selo `#83EA8F`. O tema dia continua existindo (é nosso).
- **R15.2** A barra vertical DEVE ter, de cima para baixo: status (sinal, Wi-Fi, BT, relógio, tensão da bateria, clima opcional), selo de notificações e, embaixo, os apps (fixados + recentes), o comando de voz e o botão da grade de apps.
- **R15.3** O botão da grade DEVE virar o ícone do Painel em qualquer tela que não seja o painel (grade aberta, mapa, player, Ajustes, Central); tocar nele volta ao painel.
- **R15.4** A barra horizontal DEVE seguir a ordem do Android Auto: grade e voz | apps | selo e status. Com o motorista à direita, a ordem se inverte.
- **R15.5** Ajustes → Aparência DEVE ter **Lado do motorista: Esquerda / Direita**. À direita, a barra vertical vai para a direita da tela.
- **R15.6** O selo de notificações DEVE mostrar a contagem (pílula verde, "9+" acima de 9) e abrir a **Central de notificações**: lista do mais novo para o mais antigo, com Abrir, Dispensar, Limpar tudo e **Central do sistema** (mantém o acesso à cortina do Android que o sino abria antes). Sem notificações, aparece um sino discreto.
- **R15.7** A grade de apps DEVE mostrar **Sair** primeiro (volta ao launcher original), seguido de Ajustes e dos apps. Ajustes → Apps tem **Mostrar "Sair" primeiro** (padrão: ligado).
- **R15.8** Ajustes → Barra de navegação DEVE ter **Clima na barra** (padrão: desligado).
- **R15.9** Gaveta e Ajustes DEVEM abrir como um card grande ao lado da barra; menus e folhas usam a superfície elevada; controles do mapa usam o painel escuro `#171E24`.
- **R15.10** Tocar/pausar: círculo claro no card do painel; quadrado arredondado `#CFD6D3` no player.

## Sem regressão (verificar)
Fixar/desafixar/remover pelo toque longo; edição da gaveta só parado; busca na gaveta; 6 animações da gaveta; ícones e pacotes; player dividido e em tela cheia; fila; widgets (todos os 10) e troca por toque longo; destinos; barra horizontal; tema pelos faróis; painel de depuração no relógio.

## Critérios de aceite
- [ ] **A1** (R15.2, R15.3) No painel, o botão de baixo abre a grade; com a grade aberta ou no mapa em tela cheia, ele mostra o Painel e volta ao painel.
- [ ] **A2** (R15.6) Com notificações, o selo mostra o número; tocar abre a Central; "Dispensar" remove o item; "Central do sistema" abre a cortina do Android.
- [ ] **A3** (R15.5) Lado do motorista = Direita move a barra vertical para a direita e inverte a horizontal.
- [ ] **A4** (R15.7) "Sair" na grade abre o DoFun.
- [ ] **A5** Todos os itens de "Sem regressão" funcionam na multimídia.

## Resultados
_(preencher após a validação)_
