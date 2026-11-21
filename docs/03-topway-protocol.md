# 03 — Protocolo dos serviços da TopWay

Extraído por engenharia reversa (jadx) dos apps do firmware `TS18.2.2_20241210`. Caminhos relativos a `discovery/decompiled/`.
Implementado em `app/src/main/java/app/mylauncher/data/vehicle/topway/`.

## Regras gerais
- Os dois serviços estão **exportados e não exigem permissão**. Nenhum dos dois verifica `getCallingUid`, e os apps rodam com `sharedUserId=android.uid.system`.
- Todas as chamadas usam flags 0 (nenhuma é oneway).
- **Na chamada:** `writeInterfaceToken(DESCRIPTOR)`, depois os argumentos. Na volta, `reply.readException()` e então o retorno, se houver.
- **Tipos no parcel:**
  - booleanos trafegam como int (0/1);
  - um Bundle é `writeInt(1)` + `writeToParcel`, ou `writeInt(0)` quando é null.
- **Nos callbacks** (nós implementamos):
  - `enforceInterface` → ler os argumentos → `reply.writeNoException()` → `return true`.
  - Se o callback lançar exceção, o carinfoservice remove o registro (`c/b/a/g/b.java:101-104`).

## `com.tw.carinfoservice.CarServiceAidl`
Bind: action `com.tw.carinfoservice.CarService.Bind`, pacote `com.tw.carinfoservice` (`AndroidManifest.xml:64-75`, `CarService.java:305-309`).

| Código | Método | Args → Retorno |
|---|---|---|
| 1 | registerCarServiceCallBack | binder |
| 2 | unRegisterCarServiceCallBack | binder |
| 3 | requestData | – (reenvia todo o estado pelos callbacks, `h/b.java:1180-1206`) |
| 4 | getElectricVoltage | → float (V; −1 inválido) |
| 5 | getHeadlight | → int (arg1 bruto da msg MCU 516; −1 inválido) |
| 11 | getGear_Information | → int (0=D 1=N 2=R 3=P 4=S) |
| 14 | handbrake | → int |
| 18 | getInstantaneous_Speed | → int[] {velocidade, 0} |
| 27 | getCarReverse | → int (**sempre −1**) |

## `com.tw.carinfoservice.CarServiceCallBack` (nós implementamos)
| Código | Callback | Args | Significado |
|---|---|---|---|
| 1 | onElectricVoltage | float, String | Bateria em V (MCU 1292 × 0,01) |
| **2** | **onHeadlight** | int | **Faróis**: MCU 516 arg1 == 1; enviado só quando muda |
| 11 | onHandbrake | int | Freio de mão |
| 15 | onInstantaneous_Speed | int | km/h (MCU 1291 arg1 / 100), só quando válido |
| 16 | onEngine_Speed | int | RPM |
| 24 | onCarReverse | int | Ré pelo **CAN** (depende do protocolo do carro); preferir o xt |
| 61 | onTurn_Signal | int | 0 nenhum, 1 direita, 2 esquerda, 3 alerta |
| 63 | onExtendedInterface | int flag (sempre 0) | Batimento: chega antes de cada callback |

Os demais códigos (1–66) carregam consumo, portas, pneus, temperaturas etc. A tabela completa está no `.aidl` decompilado.

## `com.tw.service.xt.aidl.ITWCommandAidl`
Bind: action `com.tw.service.xt.CommandService.Bind`, pacote `com.tw.service.xt` (processo `:remote`). Todos os métodos retornam void.

| Código | Método | Args |
|---|---|---|
| 1 / 2 | register / unRegisterTWCommandCallback | binder |
| 11 / 12 | radioNext / radioPre | – |
| 23 / 24 / 25 | btPlayMusic / btPre / btNext | – |
| 31 / 32 / 33 / 34 | musicNext / musicPre / musicPlay / musicPause | – |
| 46 / 47 | getVolume / getVolumeStatus | – (resposta via callback 1/2) |
| 52 | getReverseStatus | – (resposta via callback 6) |
| 55 / 56 / 57 / 58 | mediaNext / mediaPre / mediaPlay / mediaPause | – |
| 67 | extendedInterface | Bundle `{project:"system", action:"source_request"\|"light_request"}` |

## `com.tw.service.xt.aidl.ITWCommandCallbackAidl` (nós implementamos)
| Código | Callback | Args | Significado |
|---|---|---|---|
| 1 | onSystemVolume | int | Volume 0–30 |
| 2 | onVolumeStatus | int | 0 = mudo |
| **6** | **onReverseStatus** | int | **Ré** (MCU 0x9F1C), enviado quando muda |
| **7** | **onSleepStatus** | int | 1 = ACC_ON, 0 = ACC_OFF |
| 8 | onExtendedInterface | int flag + Bundle | Chave `action`: `Source_recieve` (`SourceValue`: 0 outro, 1 rádio, 3 música, 8 BT, 9 vídeo) ou `light_recieve` (`LightValue`, `LightMax`) |

## Broadcasts
- `com.unisound.intent.action.ACC_ON` / `ACC_OFF` / `DO_SLEEP` / `DO_WAKEUP` / `DO_SHUTDOWN`.
- `com.tw.music.info`: `musicaArtist` (grafia original), `musicAlbum`, `musicPath`.
- `com.tw.launcher.musicinfo`: `msg_Artist`, `msg_Albumart` (byte[]).
- `com.tw.launcher.music_progress_duration`: `msg_music_progress`, `msg_music_duration` (int).
- **Rádio:** nenhum canal expõe frequência ou nome da estação. Só seria possível com o `com.tw.radio` (não decompilado) ou com as mensagens da MCU.
