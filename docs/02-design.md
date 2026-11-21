# 02 — Design

**Protótipo interativo:** https://claude.ai/artifact/SiHfRy4w698zHFTzvYiXv6

## Princípio

Reproduzir o **layout e o comportamento** do Android Auto (Coolwalk, versão de barra lateral para telas 16:9) sobre o **Material Design 3**. O Material 3 é aberto e é a mesma base do Android Auto.

Não usamos ativos proprietários do Google: logos, a fonte Google Sans, ícones de apps e a marca do Assistente. No protótipo, os apps aparecem como círculos com a inicial; no app real, os ícones vêm do `PackageManager`.

## Regras de design para direção

Fonte: diretrizes públicas *Design for Driving* do Google. Na tela de 160 dpi, 1 dp = 1 px.

| Regra | Valor |
|---|---|
| Alvo de toque mínimo | **76 × 76 dp**, sem sobreposição, ≥ 23 dp entre alvos quando possível |
| Ícones | 44 dp (primário), 36 dp (secundário), 24 dp (terciário) |
| Texto | 32 dp (primário), 24 dp (secundário); mínimo 18 dp em metadados |
| Contraste | ≥ 4,5:1 para texto e ícones |
| Polaridade | Noite: claro sobre escuro. Dia: escuro sobre claro |
| Animações | ≤ 300 ms, nada piscando; sem rolagem infinita no painel |

## Tokens

| Token | Noite | Dia |
|---|---|---|
| background | `#000000` | `#E8EAED` |
| surface (cards) | `#1E1F22` | `#FFFFFF` |
| surface2 | `#2B2C30` | `#F1F3F4` |
| outline | `#3A3B40` | `#DADCE0` |
| onSurface | `#E8EAED` | `#202124` |
| onSurfaceVariant | `#A9ADB3` | `#5F6368` |
| primary | `#8AB4F8` | `#1A73E8` |
| onPrimary | `#0B1A33` | `#FFFFFF` |

- **Forma:** cards com raio de 28 dp; botões em pílula; alvos circulares de 76 dp.
- **Fonte:** Roboto (400/500/700).
- **Tema:** `AUTO` segue os faróis (spec 006). Se não houver sinal do veículo, segue `persist.tw.theme.mode` e, por último, o horário.

## Telas

| Tela | Artboard | Spec |
|---|---|---|
| Barra lateral: relógio + status, Painel, 3 recentes, Apps, Notificações, Voz | Barra lateral | 002 |
| Painel: mapa + card de mídia + card de sugestões | Início · noite / dia | 002 |
| Painel com navegação embutida | Início · navegação embutida | 008 |
| Mapa em tela cheia | Mapa em tela cheia | 005 |
| Mídia em tela cheia (fontes, capa, controles) | Mídia | 004 |
| Gaveta de apps | Gaveta de apps | 003 |
| Ajustes do launcher | Ajustes do launcher | 007 |

## Layout do painel (1280 × 720)

```
┌──────┬──────────────────────────────┬───────────────┐
│ 104  │  Mapa (card, raio 28)         │ Mídia  (344)  │
│ barra│  busca "Para onde?" (topo)    ├───────────────┤
│      │  velocidade ↙   recentrar ↘   │ Sugestões     │
│      │                               │ (3 × 104)     │
└──────┴──────────────────────────────┴───────────────┘
padding 16 · gap 16 · coluna direita 408
```
