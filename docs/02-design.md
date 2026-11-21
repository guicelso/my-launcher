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

Valores medidos nas imagens da documentação oficial do Android Auto (Coolwalk). O tema dia é nosso: o Android Auto só tem tema escuro.

| Token | Noite | Dia |
|---|---|---|
| background (barra e espaços) | `#151616` | `#E9EBEE` |
| card | `#252626` | `#FFFFFF` |
| card elevado (alertas, menus) | `#292A2A` | `#FFFFFF` |
| pill (botão secundário) | `#3C3E3D` | `#E8EAED` |
| onSurface | `#E3E3E3` | `#1F1F1F` |
| onSurfaceVariant | `#A8ABAE` | `#5F6368` |
| primary (botão principal) | `#C5D4FF` / texto `#0B2A66` | `#D3E3FD` / texto `#041E49` |
| accent | `#8AB4F8` | `#0B57D0` |
| selo de notificações | `#83EA8F` / texto `#0B3D17` | `#137333` / texto `#FFFFFF` |
| manobra (navegação) | `#005B2A` | `#0D652D` |
| painel do mapa (ETA, botões) | `#171E24` | `#FFFFFF` |
| ETA | `#FDD663` (em rota), `#81C995` (compacto) | `#B06000`, `#137333` |
| tocar (card de mídia) | `#AEEDFF` / ícone `#06343F` | igual |
| tocar/pausar (player) | `#CFD6D3` / ícone `#151616` | igual |
| alerta de navegação | `#243037` | — |
| atender / recusar | `#5BBF6B` / `#FF897C` | igual |

- **Forma:** cards com raio de 24 dp; botões em pílula; alvos circulares de 76 dp; ícones de app de 56 dp na barra e 92 dp na grade.
- **Fonte:** Roboto (400/500/700).
- **Tema:** `AUTO` segue os faróis (spec 006). Se não houver sinal do veículo, segue `persist.tw.theme.mode` e, por último, o horário.

## Telas

O canvas tem uma página por seção do doc oficial. A lista completa de artboards está em `design/wireframes/README.md`.

| Seção | Artboards principais | Spec |
|---|---|---|
| Visão geral | Painel (barra vertical, horizontal, dia) | 002, 013, 014 |
| Barra de navegação | Barra vertical, barra horizontal, horizontal invertida, menu de toque longo | 002, 009, 014 |
| Área de conteúdo | Minimizada (mensagem + mídia, só mídia), mapa expandido, tela dividida | 005, 008, 010 |
| Acesso rápido aos apps | Grade com "Sair" primeiro, modo de edição | 003, 011 |
| Notificações | Alertas de mensagem, chamada, navegação e lembrete; Central | 004 |
| Barra de status | Elementos de status e fundos | 002 |
| Métodos de entrada | Toque, foco e teclas do volante | 006 |
| Apps de música | Navegação (lista e grade), reprodução, ações secundárias, fila | 004, 010 |
| Apps de mensagens | Mensagem recebida, mensagens salvas, resposta por voz | 004 |
| Personalizações | Ajustes, painel com 3 cards, trocar widget, salvar destino | 007, 012, 013 |

## Layout do painel (1280 × 720)

```
┌─────┬────────────────────────────────┬───────────────┐
│ 96  │  Mapa (card, raio 24)           │ Mensagem      │
│barra│  manobra ↖        recentrar ↘  ├───────────────┤
│     │  ETA + ações ↙    zoom ↘       │ Mídia (capa   │
│     │                                │ em tela cheia)│
└─────┴────────────────────────────────┴───────────────┘
margem 12 · espaço 12 · coluna direita 408
Barra vertical: status no topo; dock (3 apps), voz e grade embaixo.
Barra horizontal (88): grade e voz | dock | selo e status.
```
