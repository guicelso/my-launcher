# Lumia Launcher

Launcher Android (repositório `my-launcher`, pacote `app.mylauncher`) no estilo Android Auto para a multimídia **TopWay TS18** (Android 10) do carro.
Desenvolvimento orientado a especificações (spec-driven): nada é implementado sem uma spec aprovada em [`specs/`](specs/).

## Mapa do repositório

| Caminho | Conteúdo |
|---|---|
| [`docs/00-discovery.md`](docs/00-discovery.md) | Estudo do aparelho: hardware, sistema, acesso adb, apps da TopWay e API da MCU |
| [`docs/01-architecture.md`](docs/01-architecture.md) | Stack, módulos, abstrações (painel de navegação, fonte de dados do veículo), deploy e segurança |
| [`docs/02-design.md`](docs/02-design.md) | Linguagem visual, regras de design para direção e lista de telas |
| [`docs/03-topway-protocol.md`](docs/03-topway-protocol.md) | Protocolo Binder dos serviços da TopWay (MCU/CAN) |
| [`docs/04-runbook.md`](docs/04-runbook.md) | **Guia de operação:** adb, instalar, recuperar acesso, emulador, estado atual e próximos passos |
| [`CLAUDE.md`](CLAUDE.md) | Instruções e armadilhas para as próximas sessões do Claude Code |
| [`design/wireframes/`](design/wireframes/) | Fontes do protótipo de alta fidelidade |
| [`specs/`](specs/) | Processo spec-driven e uma spec por funcionalidade |
| [`tools/`](tools/) | Scripts: ambiente de desenvolvimento, coleta de dados do aparelho |
| `discovery/` | Dumps do aparelho e APKs decompilados (local, fora do controle de versão) |

## Protótipo

Wireframes de alta fidelidade (canvas interativo): https://claude.ai/artifact/SiHfRy4w698zHFTzvYiXv6

## Ambiente

```sh
tools/setup-dev-env.sh      # instala JDK 17, Android SDK e jadx em ~/ (sem sudo)
source tools/env.sh         # exporta JAVA_HOME, ANDROID_HOME, PATH e HU_IP
adb connect $HU_IP:5555     # multimídia via Wi-Fi (a USB dela é só host)
```

## Rodar

| Comando | O que faz |
|---|---|
| `tools/emulator.sh --deploy` | Abre o emulador com a tela da multimídia (Android 10, 1280×720, 160 dpi) e instala o launcher |
| `tools/deploy.sh` | Compila, instala na multimídia (`$HU_IP:5555`), concede permissões e define como tela inicial |
| `DEVICE=emulator-5554 tools/deploy.sh` | O mesmo, no emulador |
| `tools/rollback.sh` | Volta ao launcher original da multimídia (DoFun) |
| `tools/logs.sh` | Acompanha o logcat do launcher |
| `./gradlew :app:testDebugUnitTest` | Testes de unidade |

**No emulador:**
- Toque longo no relógio abre o painel de depuração. Ali dá para simular faróis (tema noite/dia), ré e velocidade.
- A posição do GPS se define pela janela do emulador: `…` → *Location*.
- Os serviços da TopWay não existem no emulador. O diagnóstico em Ajustes → Veículo mostra "Indisponível", como esperado.
