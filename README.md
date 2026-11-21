# my-launcher

Launcher Android no estilo Android Auto para a multimídia **TopWay TS18** (Android 10) do carro.
Desenvolvimento orientado a especificações (spec-driven): nada é implementado sem uma spec aprovada em [`specs/`](specs/).

## Mapa do repositório

| Caminho | Conteúdo |
|---|---|
| [`docs/00-discovery.md`](docs/00-discovery.md) | Estudo do aparelho: hardware, sistema, acesso adb, apps da TopWay e API da MCU |
| [`docs/01-architecture.md`](docs/01-architecture.md) | Stack, módulos, abstrações (painel de navegação, fonte de dados do veículo), deploy e segurança |
| [`docs/02-design.md`](docs/02-design.md) | Linguagem visual, regras de design para direção e lista de telas |
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
