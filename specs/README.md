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
| [001](001-launcher-base.md) | Launcher base, deploy e recuperação | Implementada · validar no carro | — |
| [002](002-dashboard-rail.md) | Painel e barra lateral (noite/dia) | Implementada · validar no carro | 001 |
| [003](003-app-drawer.md) | Gaveta de apps e recentes | Implementada · validar no carro | 001 |
| [004](004-media.md) | Mídia (card + tela cheia) | Implementada · validar no carro | 002 |
| [005](005-own-map.md) | Mapa próprio (MapLibre) | Implementada · validar no carro | 002 |
| [006](006-vehicle-integration.md) | Integração com o veículo (TopWay) | Implementada · validar no carro | 001 |
| [007](007-settings.md) | Ajustes do launcher | Implementada · validar no carro | 002, 006 |
| [008](008-embedded-navigation.md) | Navegação embutida (Waze/Maps no painel) | Pesquisa (ferramentas dos spikes S6.1/S8.1 prontas) | 005, 006 |

A ordem sugerida é 001 → 002 → 003 → 004 → 006 → 005 → 007 → 008. A 006 vem antes do mapa porque o tema automático pelos faróis é central no visual.
