# 01 — Arquitetura

## Decisões

| # | Decisão | Motivo |
|---|---|---|
| D1 | **Kotlin + Jetpack Compose** (Material 3), app nativo | O trabalho é quase todo integração com o sistema e com a TopWay. O processador é modesto e o launcher não pode travar. O Compose tem um modelo declarativo parecido com o do React. |
| D2 | `minSdk 29`, `targetSdk 29`, `compileSdk 35` | Único alvo: Android 10. Com target 29 não precisamos de `QUERY_ALL_PACKAGES` e evitamos restrições de background de versões mais novas. O app é instalado via adb, fora da Play Store. |
| D3 | **Painel de navegação trocável** (`NavigationPane`) | Começar com mapa próprio (spec 005) e evoluir para o app de navegação embutido (spec 008) sem mexer no layout. |
| D4 | **Fonte de dados do veículo abstrata** (`VehicleSource`) | A integração com a TopWay é incerta e muda por firmware. Uma implementação falsa permite desenvolver e testar a UI no emulador. |
| D5 | Sem root no caminho crítico | O root vira pesquisa da spec 008. O launcher base funciona como app comum. |
| D6 | Launcher original nunca é desinstalado | É o plano de volta. Há comando de rollback em todo deploy. |
| D7 | Visual: layout do Android Auto (Coolwalk) sobre Material 3 | Sem ativos proprietários do Google (logos, Google Sans, ícones). Ver `02-design.md`. |
| D8 | DI manual (`AppContainer`), sem Hilt | Projeto pequeno; menos tempo de build e de inicialização. |

## Pacote e módulos

- **applicationId:** `app.mylauncher` (provisório).
- **Gradle:** um módulo `:app` no início. Extrair `:vehicle-topway` quando a spec 006 crescer.

```
app/src/main/java/app/mylauncher/
├── MainActivity.kt            # HOME activity, singleTask, imersiva
├── AppContainer.kt            # DI manual
├── ui/
│   ├── theme/                 # tokens noite/dia, tipografia, ThemeResolver
│   ├── rail/                  # barra lateral
│   ├── dashboard/             # painel (mapa + mídia + sugestões)
│   ├── drawer/                # gaveta de apps
│   ├── media/                 # tela de mídia
│   ├── map/                   # mapa em tela cheia
│   └── settings/              # ajustes
├── data/
│   ├── apps/                  # PackageManager, recentes, fixados
│   ├── media/                 # NotificationListener + MediaSession, ponte TopWay
│   ├── location/              # LocationManager (GPS)
│   ├── vehicle/               # VehicleSource: TopWayVehicleSource, FakeVehicleSource
│   ├── notifications/         # mensagens para o card de sugestões
│   └── prefs/                 # DataStore
└── nav/
    ├── NavigationPane.kt      # interface
    ├── OwnMapPane.kt          # MapLibre (spec 005)
    └── EmbeddedAppPane.kt     # tela virtual (spec 008)
```

## Abstrações-chave

```kotlin
interface VehicleSource {
    val state: StateFlow<VehicleState>   // headlightsOn, reverse, acc, speedKmh, batteryV, handbrake
}

interface NavigationPane {
    @Composable fun Content(modifier: Modifier, mode: PaneMode) // CARD no painel | FULL em tela cheia
}

// Tema: preferência do usuário (AUTO/DIA/NOITE) + VehicleState.headlightsOn -> Dark/Light
object ThemeResolver
```

`MediaController` unifica duas origens:
1. **MediaSession** de apps de terceiros (Spotify, YT Music), lidas via `NotificationListenerService`.
2. **Ponte TopWay** para rádio, música local e BT, via `CommandService` e broadcasts `com.tw.*`.

## Fluxo de deploy e recuperação

```sh
source tools/env.sh
./gradlew :app:assembleDebug
adb -s $HU_IP:5555 install -r app/build/outputs/apk/debug/app-debug.apk
adb -s $HU_IP:5555 shell cmd package set-home-activity app.mylauncher/.MainActivity
# Rollback para o launcher original:
adb -s $HU_IP:5555 shell cmd package set-home-activity com.dofun.variety/com.dofun.overseasvariety.Launcher
```

A spec 001 cria `tools/deploy.sh` e `tools/rollback.sh` encapsulando esses comandos.

## Testes

| Nível | Ferramenta | O que cobre |
|---|---|---|
| Unidade (JVM) | JUnit + Turbine | `ThemeResolver`, mapeamento de MediaSession, parsing dos callbacks da TopWay |
| UI | Compose UI Test no emulador (1280×720, 160 dpi, API 29) | navegação entre telas, estados vazios |
| Aceite | Roteiro manual no carro, descrito em cada spec | Comportamento real com MCU, GPS e apps |

O emulador de referência é um AVD de API 29 em 1280×720 / 160 dpi, configurado com `FakeVehicleSource`.

## Riscos

| Risco | Mitigação |
|---|---|
| Firmware reseta o launcher padrão ou mata o app | Testar na spec 001; verificar `Hibernar software` nos ajustes de fábrica |
| Barra de sistema da TopWay não some | Layout tolera 1280×(720 − barra); decidir na spec 001 |
| Serviços da TopWay exigem chamador de sistema | `FakeVehicleSource` + tema pelo horário como alternativa |
| MapLibre pesado na GPU PowerVR | Medir FPS na spec 005; estilo simplificado e limite de zoom |
