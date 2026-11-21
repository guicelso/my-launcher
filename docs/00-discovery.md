# 00 — Estudo do aparelho

Levantado em 2026-11-21 via adb (`tools/collect-device-info.sh`) e decompilação dos apps do fabricante (jadx).
Dados brutos em `discovery/device-20261121-134517/` e `discovery/decompiled/` (locais, fora do git).

## 1. Hardware e sistema

| Item | Valor | Fonte |
|---|---|---|
| Plataforma | TopWay **TS18** (`ro.tw.version=TS18.2.2_20241210`) | getprop / tela Sobre |
| SoC | Unisoc UIS8581A (`sp9863a`), 8× Cortex-A55 1,6 GHz | getprop |
| GPU | PowerVR GE8322 | SurfaceFlinger |
| RAM / armazenamento | 3,8 GB utilizável (~1,4 GB livre em uso normal) / 64 GB | `/proc/meminfo` |
| Tela | 1280×720, **160 dpi** (1 dp = 1 px) | `wm size/density` |
| Android | **10 (API 29)** — a tela "Sobre" diz 11, mas é falso | `ro.build.version.sdk=29` |
| Build | `user`, `release-keys`, `ro.debuggable=0` | getprop |
| Chave de plataforma | Própria da TopWay (`CN=John, O=TW`), **não** as chaves públicas da AOSP | cert do `framework-res.apk` |
| SELinux | **Permissive** em tempo de execução | `getenforce` |
| Bootloader | **Desbloqueado** (`verifiedbootstate=orange`), dm-verity ativo, `/system` somente leitura | getprop / mount |
| Partições | Não-A/B, partições dinâmicas (`super`), `boot` = `mmcblk0p29`, só legíveis por root | `/dev/block/by-name` |
| Root | Não há `su` | — |
| Carro | Renault Duster (perfil CAN "Simple Soft-Renault-DUSTER"), MCU `Ts18.1.1 … DACIA (FT072)` | Ajustes de fábrica |

## 2. Acesso adb

- **As portas USB são só host**, com ou sem a opção "USB HUB". O adb funciona **somente pela rede Wi-Fi** (`192.168.10.136:5555`).
- A chave "Depuração USB" é protegida por senha. O caminho que funcionou foi este:
  1. Abrir o **EngineerMode** da Unisoc pelo app *Activity Launcher*: `com.sprd.engineermode` → DEBUG&LOG → Adb shell.
     - Esse shell roda como uid **system** via `Runtime.exec`. Ele **não usa um shell de verdade**: não aceita `;`, `>` nem pipes.
  2. Executar `setprop service.adb.tcp.port 5555` e `settings put global adb_enabled 1`.
  3. Pelo adb, executar `setprop persist.adb.tcp.port 5555`. Com isso, a porta continua aberta após o reboot (já confirmado).
- **Pendência:** depois do reboot, o adb voltou como `unauthorized` e o pedido de autorização não apareceu. Provavelmente o firmware desliga `adb_enabled` no boot. A tentativa em aberto: no EngineerMode, `adb_enabled 0` → `1` → `setprop ctl.restart adbd`, e então aceitar com "Sempre permitir".
- O adbd do fabricante registra no logcat todo comando recebido (`Tianyuan Current sh name: …`).

## 3. Apps relevantes

| Pacote | Papel | Observação |
|---|---|---|
| `com.dofun.variety` | Launcher atual (`com.dofun.overseasvariety.Launcher`) | Roda como **uid comum** (10093) e é ofuscado (360 Jiagu). **Nunca desinstalar**: é o plano de volta. |
| `com.tw.service`, `com.tw.service.xt`, `com.tw.coreservice` | Serviços da TopWay (MCU, comandos, volume, fonte) | priv-app, uid system |
| `com.tw.carinfoservice` | Dados do CAN (farol, velocidade, tensão, freio de mão) | `/data/app` |
| `com.tw.reverse`, `com.tw.bt`, `com.tw.radio`, `com.tw.music` | Câmera de ré, telefone BT, rádio, música local | priv-app |
| `com.sprd.engineermode` | Modo Engenheiro da Unisoc | aberto via Activity Launcher |
| Waze, Google Maps, HERE WeGo, Spotify, YouTube Music, WhatsApp | Apps de terceiros já instalados | alvos de integração |

## 4. API da MCU / veículo

Detalhes e caminhos de evidência estão nos fontes decompilados (caminhos relativos a `discovery/decompiled/`).

### 4.1 Canais acessíveis a um app comum (preferidos)

1. **`com.tw.carinfoservice/.CarService`**
   - Exportado, sem verificação de quem chama. Ação de bind: `com.tw.carinfoservice.CarService.Bind`.
   - Interface `com.tw.carinfoservice.CarServiceAidl` (`c/b/a/b.java`): tx 1 registra callback, 3 pede dados, 4 lê tensão, 5 lê farol.
   - Callback `com.tw.carinfoservice.CarServiceCallBack`: tx 1 tensão, 2 farol (bool), 11 freio de mão, 15 velocidade, 63 Bundle.
2. **`com.tw.service.xt/.CommandService`**
   - Exportado, sem permissão. Ação de bind: `com.tw.service.xt.CommandService.Bind`.
   - Interface `com.tw.service.xt.aidl.ITWCommandAidl`, com códigos de transação em `aidl/ITWCommandAidl.java:289-355`.
   - Callbacks: `onReverseStatus`, `onSleepStatus` (ACC), `onSystemVolume`, `onVolumeStatus`, status do BT e `onExtendedInterface(Bundle)`. A ação do Bundle é `Source_recieve`, `light_recieve` ou `car_info_recieve`.
3. **Broadcasts implícitos** (exigem receiver dinâmico):
   - Ignição: `com.unisound.intent.action.ACC_ON`, `ACC_OFF`, `DO_SLEEP`, `DO_WAKEUP`, `DO_SHUTDOWN`.
   - Música local: `com.tw.music.info` (`musicaArtist`, `musicAlbum`, `musicPath`), `com.tw.launcher.musicinfo` e `com.tw.launcher.music_progress_duration`.
4. **Propriedades e Settings:** `persist.tw.theme.mode` (`day`/`night`, legível por qualquer app), `Settings.Secure ui_night_mode` e `Settings.System revserse_status` (grafia original).

### 4.2 `android.tw.john.TWUtil` (biblioteca da TopWay no framework)

- **Uso:** `new TWUtil()` → `open(short[] ids)` → `start()` → `addHandler(tag, Handler)`.
  - Cada evento chega como `Message` com `what`=ID, mais `arg1`, `arg2` e `obj`.
  - `write(id, a1, a2, obj)` envia comandos; `a1=255` consulta o valor atual.
- **Ainda não sabemos se um app comum consegue chamar `open()`.** Todos os usos encontrados rodam como uid system. Isso precisa de teste no aparelho.

| ID | Sinal |
|---|---|
| `0x0204` | **Iluminação/faróis**: `arg1==1` = faróis ligados; `arg2` = brilho dia/noite |
| `0x0102` | Modo de brilho (0 auto, 1 dia, 2 noite) |
| `0x9F1C` | **Marcha à ré** |
| `0x0205` | Freio de mão |
| `0x0202` | Energia/ACC (1 standby, 4 sleep, 5 wake, 6 normal) |
| `0x0203` | Volume (bit31 = mudo) |
| `0x0201` | Teclas (1 vol+, 2 vol−, 16 home, 18 back, 19 próxima, 20 play). Teclas do volante: provavelmente aqui (hipótese) |
| `0x0301` | Fonte de áudio (1 rádio, 3 música, 8 BT, 9 vídeo) |
| `0x050B` | Velocidade (0,01 km/h) e RPM · `0x050C` tensão da bateria |
| `0x9F1A` | **Executa um comando de shell como root** (`com.dofun.carsetting/.../f/v.java:141-149`) |

> ⚠️ `0x9F1A` é, na prática, uma porta dos fundos de root deixada pelo fabricante. Se um app comum conseguir usá-la, ela pode substituir o Magisk na spec 008. Tratar como pesquisa e nunca expor por rede.

### 4.3 Portas seriais
- A MCU/CAN fala por UART. O `carinfoservice` abre `/dev/ttyS3`.
- Acesso direto pelo nosso app: não planejado. Os serviços acima já fazem esse papel.

## 5. Pontos em aberto

- [ ] Autorização do adb persistente após o reboot (§2).
- [ ] Testar o bind em `CarService` e `CommandService` a partir de um app comum (spec 006).
- [ ] Testar `TWUtil.open()` e `0x9F1A` a partir de um app comum (specs 006/008).
- [ ] Ver se a barra de sistema da TopWay some com o modo imersivo (spec 001).
- [ ] Ver se o rádio/BT da TopWay publicam MediaSession (o dump indica que não; spec 004).
