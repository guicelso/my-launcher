# 04 — Guia de operação (runbook)

Tudo o que é preciso para retomar o trabalho numa nova sessão: ambiente, conexão com a multimídia, instalação, depuração e o estado atual do projeto.
Atualizado em **2026-11-21**.

---

## 1. Resumo rápido

| O quê | Como |
|---|---|
| Preparar o terminal | `cd ~/projetos/my-launcher && source tools/env.sh` |
| Conectar na multimídia | `adb connect 192.168.10.136:5555` (só por Wi-Fi; a USB dela é só host) |
| Instalar a versão nova | `tools/deploy.sh` (release otimizada + AOT + permissões + define como HOME) |
| Voltar ao launcher original | `tools/rollback.sh` |
| Ver logs | `tools/logs.sh` |
| Abrir o emulador | `tools/emulator.sh --deploy` |
| Instalar no emulador | `DEVICE=emulator-5554 tools/deploy.sh` |
| Testes de unidade | `./gradlew :app:testDebugUnitTest` |
| Branch de trabalho | `feat/launcher-v1` (ainda não integrada à `main`) |

---

## 2. Ambiente de desenvolvimento (notebook)

Tudo fica no espaço do usuário, sem `sudo`:

- **JDK 17**: `~/.local/jdk-17`
- **Android SDK**: `~/Android/Sdk`
  - `platform-tools` (adb), `cmdline-tools`, `platforms;android-35`, `build-tools;35.0.0`;
  - emulador e imagem `system-images;android-29;default;x86_64`.
- **jadx** (decompilador): `~/.local/jadx`

Para reinstalar do zero: `tools/setup-dev-env.sh`. Em cada terminal novo: `source tools/env.sh`, que exporta `JAVA_HOME`, `ANDROID_HOME`, `PATH` e `HU_IP=192.168.10.136`.

> **zsh:** não use variável chamada `path` em scripts, porque ela é ligada ao `PATH`. Para comandos com várias palavras, use uma função (`a() { adb -s X "$@"; }`), já que o zsh não quebra a variável em palavras.

---

## 3. Multimídia (TopWay TS18)

Detalhes técnicos em [`00-discovery.md`](00-discovery.md). O essencial:

| Item | Valor |
|---|---|
| Android | **10 (API 29)**. A tela "Sobre" mostra 11, mas é falso |
| SoC / GPU | Unisoc UIS8581A (sp9863a) / PowerVR GE8322 (fraca: cuidado com desempenho) |
| Tela | 1280×720, 160 dpi (1 dp = 1 px) |
| Rotação | Feita por software (`ro.sf.swrotation=90`). **Nunca** force `screenOrientation` no manifesto |
| Root | Não tem. SELinux permissivo, bootloader desbloqueado, chave de plataforma própria da TopWay |
| Launcher original | `com.dofun.variety/com.dofun.overseasvariety.Launcher` (nunca desinstalar) |
| IP | `192.168.10.136` na rede atual. Se mudar, veja em Configurações → Wi-Fi → rede → detalhes e ajuste `HU_IP` |

### 3.1 Conectar pelo adb
```sh
source tools/env.sh
adb connect $HU_IP:5555
adb devices          # deve mostrar "192.168.10.136:5555  device"
```
- **`device`**: pronto.
- **`unauthorized`**: a chave do notebook não está na multimídia. Veja a seção 3.3.
- **`Connection refused` / `No route to host`**: a multimídia está desligada, em outra rede, ou o adb não subiu. Veja a seção 3.2.

### 3.2 Se o adb não responder (porta 5555 fechada)
A porta fica gravada em `persist.adb.tcp.port=5555` e deve sobreviver ao reboot. Se não subir:

1. Na multimídia, abra o app **Activity Launcher** e procure `com.sprd.engineermode`. Abra a tela principal **EngineerMode**, não a "AdbShellCMDActivity", que dá erro de permissão.
2. Vá em **DEBUG&LOG → Adb shell**. Esse shell roda como uid `system` e **não aceita `;`, `>` nem pipes**: um comando por vez, apertando **Start**.
   ```
   setprop service.adb.tcp.port 5555
   settings put global adb_enabled 0
   settings put global adb_enabled 1
   setprop ctl.restart adbd
   ```
3. Do notebook: `adb connect $HU_IP:5555`.

Senhas de referência do firmware TopWay: opções do desenvolvedor = `7890` + hora atual em 24 h (ex.: 18h → `789018`). Ajustes de fábrica: tente `123456`, `8888` ou `3368`.

### 3.3 Se aparecer `unauthorized` (cadastrar a chave do notebook)
O firmware **não mostra** o pedido "Permitir depuração", então a chave é cadastrada manualmente:

1. No notebook, sirva a chave pública na rede:
   ```sh
   mkdir -p /tmp/keyserve && cp ~/.android/adbkey.pub /tmp/keyserve/
   python3 -m http.server 8765 --bind <IP_DO_NOTEBOOK> --directory /tmp/keyserve
   ```
   Se a multimídia não conseguir abrir o link, libere o firewall: `sudo ufw allow from 192.168.10.0/24 to any port 8765 proto tcp`.
2. No navegador da multimídia, abra `http://<IP_DO_NOTEBOOK>:8765/adbkey.pub`. O arquivo vai para a pasta Download.
3. No EngineerMode → Adb shell, um comando por vez:
   ```
   cp /sdcard/Download/adbkey.pub /data/misc/adb/adb_keys
   chmod 640 /data/misc/adb/adb_keys
   setprop ctl.restart adbd
   ```
4. `adb connect $HU_IP:5555` deve mostrar `device`. Pare o servidor HTTP.

> Num **notebook novo**, a chave muda (`~/.android/adbkey.pub`): repita a seção 3.3. Para manter a chave atual, guarde `~/.android/adbkey*` em local seguro.

### 3.4 Instalar a versão nova
```sh
tools/deploy.sh            # compila a release, instala, pré-compila (AOT), dá permissões, define HOME
tools/deploy.sh --no-build # instala o APK já compilado
tools/deploy.sh --debug    # versão de depuração (bem mais lenta na multimídia; só para depurar)
```
O script concede:
- localização e `READ_PHONE_STATE` (sinal/4G);
- acesso às notificações (mídia e mensagens);
- dispensa do aviso de "tela cheia".

### 3.5 Voltar ao launcher original
`tools/rollback.sh`. Pela própria multimídia, também dá para usar Ajustes do Lumia → Sistema → **Voltar ao launcher original**.

### 3.6 Prints e logs
- `adb -s $HU_IP:5555 exec-out screencap -p > print.png`. **O print sai girado 90°**, porque a imagem é capturada antes da rotação do firmware. Para ver certo:
  ```sh
  ffmpeg -i print.png -vf transpose=1 print_ok.png
  ```
  Há um ffmpeg disponível via `pip install imageio-ffmpeg` numa venv.
- Logs ao vivo: `tools/logs.sh`.
- Logs exportados pelo app: Ajustes → Sistema → Exportar logs → `adb pull /sdcard/Android/data/app.mylauncher/files/logs/`.
- Fluidez: `adb shell dumpsys gfxinfo app.mylauncher reset`, use o app e depois `dumpsys gfxinfo app.mylauncher`.

---

## 4. Emulador

- **AVD `hu_ts18`:** Android 10, 1280×720, 160 dpi, mesma tela da multimídia. Serial: `emulator-5554`.
- **Abrir:** `tools/emulator.sh --deploy`. Use `--headless` para rodar sem janela.
- **Painel de depuração:** toque longo no relógio da barra.
  - Simular veículo: faróis (tema dia/noite), ré e velocidade.
  - Simular player de música: MediaSession de teste com capas, fila, busca e aleatório.
  - Simular clima: todas as condições, dia ou noite.
  - Forçar crash: testa a proteção contra loop de crash.
- **GPS:** pela janela do emulador, `…` → Location. O emulador sem janela não inicia o GPS simulado.
- **Rede simulada:** `adb emu network speed umts|lte|full` (muda 4G/3G) e `adb emu gsm signal-profile 0..4`.
- **Toques por adb:** `adb shell input tap X Y`; toque longo com `input swipe X Y X Y 900`; arrastar com `input motionevent DOWN/MOVE/UP`.
- **Compartilhar um local para testar:**
  ```sh
  adb shell am start -a android.intent.action.SEND -t text/plain \
    --es android.intent.extra.TEXT "'https://www.google.com/maps/place/X/@-23.58,-46.65,17z'" \
    -n app.mylauncher/.ShareActivity
  ```
- Os serviços da TopWay não existem no emulador. Ajustes → Veículo → Diagnóstico mostra "Indisponível", e isso é o esperado.

---

## 5. O que o Lumia Launcher faz hoje

| Área | Funcionalidades | Spec |
|---|---|---|
| Base | HOME imersivo, Voltar nunca fecha, proteção contra loop de crash, deploy/rollback | 001 |
| Painel | Layout do Android Auto (mapa + cards), tema dia/noite pelos faróis (MCU), depois pelo tema do sistema TopWay, depois pelo horário | 002 |
| Barra | Relógio, 4G/3G com barras, Wi-Fi, BT, tensão da bateria; Painel, 3 apps (fixados + recentes), gaveta, sino, voz; toque longo = menu do app; vertical à esquerda **ou** horizontal embaixo | 002/009/014 |
| Gaveta | Páginas 6×3, busca sem acento; edição (só parado): fixar e arrastar para reordenar; ocultar/desinstalar; apps ocultos nos Ajustes; 6 animações de abertura (padrão: cresce do botão) | 003/009/011 |
| Ícones | Formato redondo / quadrado arredondado / original; pacotes de ícones ADW/Nova | 011 |
| Mídia | MediaSessions (Spotify, YT Music…) + TopWay (rádio, música, BT); card com capa em vidro fosco; player dividido com o mapa e em tela cheia (o card cresce no lugar); progresso arrastável; fila; botão para abrir o app | 004/010 |
| Mapa | MapLibre + OpenFreeMap (claro/escuro), GPS, busca e destinos abrem Waze/Maps/HERE; toque longo no mapa salva o ponto | 005/012 |
| Veículo | Faróis, ré, ACC, velocidade, bateria, freio de mão via `CarService`/`CommandService` da TopWay; abaixa o volume na ré; diagnóstico | 006 |
| Destinos | Casa, Trabalho e favoritos sem digitar: "Salvar local atual", toque longo no mapa, compartilhar do Maps/Waze/WhatsApp; aprendizado opcional de lugares frequentes (só local) | 012 |
| Widgets | Grade de 1/2/3 cards; toque longo (parado) troca o widget: Mídia, Sugestões, Clima agora, Clima detalhado, Clima por hora, Previsão da semana, Data/hora/clima, Relógio, Veículo, Notificações. Clima via Open-Meteo (sem chave) | 013 |
| Ajustes | Aparência, Painel, Barra lateral, Apps, Navegação, Mídia, Veículo (diagnóstico + spikes), Sistema (logs, recuperação) | 007 |

### Decisões de desempenho (medidas no carro)
- **Instalar sempre a release** (R8) e pré-compilar com `cmd package compile -m speed`. A versão de depuração é muito lenta nessa GPU.
- **Mapa:**
  - em **TextureView**, de **tamanho fixo** (a área de trabalho inteira), só recortado pelos cards: nunca é redimensionado durante as animações;
  - limitado a **30 fps**;
  - o SurfaceView foi testado e ficou menos fluido.
- Progresso da música atualiza **uma vez por segundo**, sem animação contínua: a animação mantinha a interface redesenhando sem parar.
- Desfoque da capa feito na CPU: `Modifier.blur` só existe a partir do Android 12.

---

## 6. Pendências e próximos passos

Para validar no carro:
- [ ] Diagnóstico (Ajustes → Veículo): ré, ACC, velocidade e freio de mão chegando da MCU (spec 006).
- [ ] Clima real com GPS e 4G na multimídia (spec 013).
- [ ] Spikes **S6.1** (TWUtil) e **S8.1** (root via `0x9F1A`) nos botões de Ajustes → Veículo (spec 008).
- [ ] Gaveta: reordenar e ocultar no carro; pacote de ícones real (instalar um pela Play Store).
- [ ] Compartilhar um local do Google Maps/Waze **instalados na multimídia** para o Lumia ("Salvar no Lumia").
- [ ] Deep link de favoritos do Waze (`waze://?favorite=home`); hoje usamos coordenadas ou endereço.

Para desenvolver:
- **Spec 008:** navegação embutida (Waze/Maps dentro do card), começando pelos spikes S8.1/S8.2.
- Integrar `feat/launcher-v1` à `main` quando o uso no carro estiver estável.
- Atualizar o protótipo em `design/wireframes/` com as telas novas (widgets, barra horizontal, player em vidro).

---

## 7. Mapa do código

```
app/src/main/java/app/mylauncher/
├── LauncherApp, AppContainer (DI manual), MainActivity (HOME), ShareActivity (compartilhar local)
├── core/        FileLog, CrashGuard, LogExporter, debug/DebugMediaPlayer
├── data/
│   ├── apps/          AppsRepository, IconRenderer (formatos), IconPacks (ADW/Nova)
│   ├── destinations/  SharedPlaceParser, PlacesLearner, DestinationRequests
│   ├── location/      LocationRepository (GPS)
│   ├── media/         MediaRepository, MediaListenerService, MessagesRepository, MediaApps
│   ├── prefs/         SettingsRepository (DataStore) + regras puras (Recents, Widgets, AppOrdering)
│   ├── status/        StatusRepository (relógio, Wi-Fi, BT, sinal/4G)
│   ├── vehicle/       VehicleState, Fake, ReverseDucker, topway/ (Protocol, BinderLink, TopWayVehicleSource, TopWayMediaBridge, TwUtilProbe)
│   └── weather/       WeatherRepository (Open-Meteo, simulação)
├── nav/         NavigationPane, OwnMapPane (MapLibre), NavIntents
└── ui/          LauncherRoot, Navigator, workspace/ (área de trabalho + elementos compartilhados),
                 rail/ (barra vertical e horizontal), dashboard/, drawer/ (gaveta, edição, animações),
                 media/ (card, player, vidro fosco), widgets/, destinations/, settings/, theme/, icons/, components/
```

Protocolo da TopWay (códigos de Binder): [`03-topway-protocol.md`](03-topway-protocol.md).
