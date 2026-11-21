# 013 — Widgets configuráveis no painel

**Status:** Implementada (2026-11-21) · testada no emulador · **Depende de:** 002, 010

## Objetivo
Transformar os cards da coluna direita do painel em **widgets trocáveis** (mídia, destinos, clima, veículo, relógio, notificações) e permitir escolher a grade.

## Decisões de UX (boas práticas)
- **O mapa continua fixo** como elemento principal, como no Android Auto. Os widgets ficam nos espaços ao lado.
- **Grades predefinidas**, em vez de redimensionamento livre (que distrai e dá erro de toque):
  - **2 cards** (padrão: mídia em cima, sugestões embaixo);
  - **1 card grande**;
  - **3 cards** (mídia + 2 pequenos).
- **Para trocar um widget:** toque longo no card → "Trocar widget" → catálogo com prévia. Também em Ajustes → Painel. A troca usa a mesma animação de "card crescendo" da spec 010.
- **Só com o carro parado** (mesma regra da spec 011).

## Widgets
| Widget | Fonte de dados |
|---|---|
| Mídia | spec 004/010 |
| Destinos e sugestões | spec 002/012 |
| **Clima** | Open-Meteo (gratuito, sem chave), pela posição do GPS, atualizado a cada 30 min, com cache offline |
| Veículo | spec 006: bateria, velocidade, faróis, ré |
| Relógio e data | sistema |
| Notificações / mensagens | spec 004 |

## Requisitos
- **R13.1** O painel DEVE ter os layouts **2 cards / 1 card / 3 cards**, escolhidos em Ajustes → Painel.
- **R13.2** Cada espaço DEVE poder receber qualquer widget do catálogo, sem repetir. A configuração é persistida.
- **R13.3** Toque longo num card do painel (com o carro parado) DEVE abrir "Trocar widget".
- **R13.4** O widget Clima DEVE mostrar temperatura atual, ícone da condição, mínima/máxima e cidade. SE estiver sem rede, ENTÃO mostra o último valor com o horário.
- **R13.5** A troca de layout ou de widget DEVE animar no padrão da spec 010.

## Critérios de aceite
- [ ] **A1** Trocar o card de sugestões pelo Clima mostra a temperatura atual da cidade.
- [ ] **A2** Mudar para "3 cards" reorganiza o painel com animação e persiste após reiniciar.
