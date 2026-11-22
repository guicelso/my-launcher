# Wireframes (protótipo de alta fidelidade)

Fontes do canvas publicado em https://claude.ai/artifact/SiHfRy4w698zHFTzvYiXv6

O canvas segue a estrutura da documentação oficial do Android Auto
(*Product experience → System UI / Apps*): uma página por seção, com as telas
equivalentes às imagens de cada página e notas que separam o que vem do doc
(azul) do que é nosso (verde).

- `canvas.json` — índice do canvas (páginas, posição e título de cada artboard, notas)
- `*.dc.html` — uma tela por arquivo (formato Design Component: HTML + bloco `<script type="text/x-dc">`)

| Página | Artboards |
|---|---|
| Visão geral da interface do sistema | `Main` (props `theme`, `barra`, `painel`, `nav`), `HomeHorizontal`, `HomeDay` |
| Barra de navegação | `Rail` (vertical), `Taskbar` (horizontal, prop `lado`), `TaskbarRHD`, `RailMenu` |
| Área de conteúdo | `HomeMessage`, `HomeMusic`, `MapFull`, `MediaSplit`, `MapCard` (componente) |
| Acesso rápido aos apps | `AppDrawer` (props `editar`, `formato`), `AppDrawerEdit` |
| Notificações | `Hun` (prop `tipo`), `HunCall`, `HunNav`, `HunReminder`, `NotificationCenter` |
| Barra de status | `StatusBar` |
| Métodos de entrada | `InputMethods` |
| Apps de música | `MediaBrowse` (props `formato`, `tela`), `MediaBrowseGrid`, `Media`, `MediaActions`, `MediaQueue` |
| Apps de mensagens | `MessageHun`, `MessageCenter`, `VoiceReply` |
| Personalizações do launcher | `Settings`, `HomeWidgets`, `WidgetPicker`, `SaveDestination` |
| Pilhas de widgets | `StackHome` (props `theme`, `menu`), `StackMenu`, `StackEdit` (prop `tela`), `StackAdd`, `StackStates`, `StackCard` (componente: `widgets`, `start`, `peek`, `hint`, `focus`) |

Os arquivos são a referência visual das specs 002–017. Ao mudar o design, atualize aqui e no canvas.
