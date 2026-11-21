# Specs — processo

Cada funcionalidade nasce como spec, é aprovada e só então é implementada. Ela é considerada pronta quando o roteiro de teste passa **na multimídia**.

## Ciclo

1. **Rascunho:** a spec é escrita com objetivo, requisitos, critérios de aceite e roteiro de teste.
2. **Aprovada:** o dono do projeto revisa e muda o status.
3. **Em andamento:** a implementação segue as tarefas da spec. Mudanças de escopo voltam para a spec antes de virarem código.
4. **Validada:** o roteiro de teste passou no carro. Os resultados e as pendências são registrados na própria spec.

## Requisitos

Escritos no formato EARS, com IDs estáveis (`R<spec>.<n>`), por exemplo:
- *Ubíquo:* "O launcher DEVE …"
- *Evento:* "QUANDO <evento>, o launcher DEVE …"
- *Estado:* "ENQUANTO <estado>, o launcher DEVE …"
- *Indesejado:* "SE <falha>, ENTÃO o launcher DEVE …"

Cada critério de aceite referencia os requisitos que cobre.

## Índice

| Spec | Título | Status | Depende de |
|---|---|---|---|
| [001](001-launcher-base.md) | Launcher base, deploy e recuperação | Implementada · validada no carro (parcial) | — |
| [002](002-dashboard-rail.md) | Painel e barra lateral (noite/dia) | Implementada · validada no carro (parcial) | 001 |
| [003](003-app-drawer.md) | Gaveta de apps e recentes | Implementada · validar no carro | 001 |
| [004](004-media.md) | Mídia (card + tela cheia) | Implementada · validada no carro (parcial) | 002 |
| [005](005-own-map.md) | Mapa próprio (MapLibre) | Implementada · validada no carro (parcial) | 002 |
| [006](006-vehicle-integration.md) | Integração com o veículo (TopWay) | Implementada · validada no carro (parcial) | 001 |
| [007](007-settings.md) | Ajustes do launcher | Implementada · validar no carro | 002, 006 |
| [009](009-rail-interactions.md) | Interações da barra lateral (menu, fixar, sino) | Implementada · validada no carro | 002, 003, 007 |
| [010](010-workspace-split-player.md) | Área de trabalho, tela dividida, animações e player | Implementada · validada no carro (animações) | 002, 004, 005 |
| [011](011-drawer-management.md) | Gestão da gaveta (ordem, ocultar, desinstalar) e ícones (formato, pacotes) | Implementada · testada no emulador, validar no carro | 003, 009 |
| [012](012-destinations.md) | Destinos sem digitação (local atual, compartilhar, aprendizado) | Implementada · testada no emulador, validar no carro | 005, 006 |
| [013](013-dashboard-widgets.md) | Widgets configuráveis no painel (clima, veículo…) e grades | Implementada · testada no emulador, validar no carro | 002, 010 |
| [014](014-rail-orientation.md) | Barra vertical ou horizontal | Implementada · testada no emulador, validar no carro | 002, 010 |
| [015](015-android-auto-visual.md) | Visual 1:1 com o Android Auto, Central de notificações e lado do motorista | Implementada · validar no carro | 002, 003, 004, 013, 014 |
| [016](016-acc-wake.md) | Desligar e ligar a chave: voltar ao painel e retomar a música | Implementada · validação parcial no carro | 004, 006, 010 |
| [008](008-embedded-navigation.md) | Navegação embutida (Waze/Maps no painel) | Pesquisa (ferramentas dos spikes S6.1/S8.1 prontas) | 005, 006 |

A ordem sugerida é 001 → 002 → 003 → 004 → 006 → 005 → 007 → 008. A 006 vem antes do mapa porque o tema automático pelos faróis é central no visual.
