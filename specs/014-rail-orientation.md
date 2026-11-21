# 014 — Barra lateral vertical ou horizontal

**Status:** Rascunho · **Depende de:** 002, 010

## Objetivo
Permitir escolher entre a barra **vertical à esquerda** (padrão atual) e a **barra horizontal embaixo**, como a *taskbar* do Android Auto em telas mais baixas.

## Decisões de UX
- **Horizontal** (referência: Android Auto Coolwalk):
  - gaveta e voz à esquerda;
  - apps fixados e recentes no centro;
  - notificações, sinal e relógio à direita.
  - Altura de 96 dp, com alvos de toque de 76 dp.
- A troca é aplicada com animação. O painel recalcula as larguras e os cards mantêm as proporções.
- A posição vertical fica do lado do motorista (esquerda no Brasil). Uma opção futura pode colocar a barra à direita, para carros com volante à direita.

## Requisitos
- **R14.1** Ajustes → Aparência DEVE ter **Barra: Vertical (esquerda) / Horizontal (embaixo)**.
- **R14.2** Na horizontal, todos os itens da vertical DEVEM continuar presentes, com os mesmos comportamentos: toque longo, indicador de fixado e status 4G/Wi-Fi/BT/bateria.
- **R14.3** A área de trabalho (spec 010) DEVE se adaptar à nova área útil sem recriar o mapa.

## Critérios de aceite
- [ ] **A1** Trocar para horizontal reposiciona a barra embaixo, e o mapa e os cards se ajustam sem recarregar.
