# 002 — Painel e barra lateral (noite/dia)

**Status:** Rascunho · **Depende de:** 001 · **Design:** artboards "Barra lateral", "Início · noite", "Início · dia"

## Objetivo
Construir a tela principal no layout do Android Auto: barra lateral, card do mapa (por enquanto um placeholder do `NavigationPane`), card de mídia e card de sugestões, com tema noite e dia.

## Requisitos
### Barra lateral
- **R2.1** A barra DEVE ter 104 dp de largura e conter, de cima para baixo:
  - relógio (24 dp) + ícones de status (sinal, Wi-Fi, BT);
  - botão **Painel**;
  - **3 apps recentes**;
  - botão **Apps**;
  - um espaço flexível;
  - **Notificações**;
  - **Voz**.
- **R2.2** Cada item interativo DEVE ter um alvo de toque de 76×76 dp. O item ativo DEVE ter fundo `surface2` (noite) / `#E8F0FE` (dia).
- **R2.3** Os recentes DEVEM vir da spec 003. Antes dela, ficam fixos: app de navegação padrão, app de música padrão e telefone (`com.tw.bt`).
- **R2.4** QUANDO o relógio mudar de minuto, a barra DEVE atualizar sem recompor a tela inteira.
- **R2.5** O botão **Voz** DEVE disparar `Intent.ACTION_VOICE_COMMAND` e, se não houver quem trate, `RecognizerIntent`. SE nada tratar, ENTÃO o botão DEVE ficar desabilitado.

### Painel
- **R2.6** O painel DEVE seguir a grade de `docs/02-design.md`: mapa em `minmax(0,1fr)` e coluna direita de 408 dp, com padding 16 e gap 16.
- **R2.7** O card de mapa DEVE renderizar `NavigationPane.Content(mode = CARD)`. Nesta spec, ele mostra um mapa estático com um botão "Abrir navegação".
- **R2.8** O card de mídia DEVE ser um placeholder com o layout final. Os dados chegam na spec 004.
- **R2.9** O card de sugestões DEVE listar até 3 itens de 104 dp: destinos salvos (Casa, Trabalho) e a última notificação de mensagem (spec 004/notificações). SE não houver itens, ENTÃO DEVE mostrar um atalho para os Ajustes.

### Tema
- **R2.10** O app DEVE ter tokens noite/dia (`docs/02-design.md`) aplicados via `MaterialTheme`.
- **R2.11** O tema DEVE vir do `ThemeResolver(preferência, VehicleState)`. Nesta spec, o `VehicleState` vem do `FakeVehicleSource`, que alterna por um botão de debug.
- **R2.12** A troca de tema DEVE ser animada em ≤ 300 ms, sem recriar a activity.

## Critérios de aceite
- [ ] **A1** (R2.1, R2.2, R2.6) Um print da multimídia confere com o artboard "Início · noite", com diferença máxima de 8 dp por elemento.
- [ ] **A2** (R2.11, R2.12) Alternar o fake de faróis troca o tema em menos de 300 ms, sem piscar a tela.
- [ ] **A3** (R2.3) Tocar nos recentes abre os apps corretos, e HOME volta ao painel.
- [ ] **A4** (R2.5) O botão Voz abre o assistente disponível ou aparece desabilitado.
- [ ] **A5** O uso de CPU fica abaixo de 5% com o painel parado (`top -n 1 | grep mylauncher`).

## Tarefas
1. Tokens, `MaterialTheme` e `ThemeResolver`, com testes de unidade.
2. Composables `Rail`, `DashboardScreen`, `MediaCard` (placeholder) e `SuggestionsCard`.
3. Criar o `FakeVehicleSource` e o painel de debug (ativado por toque longo no relógio).
4. Comparar com o protótipo e medir a CPU no carro.

## Resultados
**2026-11-21, no carro:**
- Os faróis trocam o tema automaticamente (A2 ✔).
- Tensão da bateria exibida.
- Os recentes na barra lateral se atualizam com o uso.
