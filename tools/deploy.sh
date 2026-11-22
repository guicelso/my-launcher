#!/usr/bin/env bash
# Compila, instala e define o launcher como tela inicial (spec 001, R1.6).
# Uso: tools/deploy.sh [--debug] [--no-build]   (padrão: release otimizada com R8)
#   DEVICE=<serial> para outro aparelho (padrão: $HU_IP:5555; ex.: DEVICE=emulator-5554)
set -euo pipefail
cd "$(dirname "$0")/.."
source tools/env.sh

VARIANT=release
BUILD=1
for arg in "$@"; do
  case "$arg" in
    --debug) VARIANT=debug ;;
    --release) VARIANT=release ;;
    --no-build) BUILD=0 ;;
  esac
done

DEVICE="${DEVICE:-$HU_IP:5555}"
PKG=app.mylauncher
ADB=(adb -s "$DEVICE")

if [[ "$DEVICE" == *:* ]]; then adb connect "$DEVICE" >/dev/null; fi

if [ "$BUILD" = 1 ]; then
  ./gradlew ":app:assemble${VARIANT^}" -q --console=plain
fi
APK="app/build/outputs/apk/$VARIANT/app-$VARIANT.apk"

echo "==> Instalando $APK em $DEVICE"
"${ADB[@]}" install -r -t "$APK"

echo "==> Pré-compilando (AOT) para a interface rodar fluida"
"${ADB[@]}" shell cmd package compile -m speed -f $PKG >/dev/null || true

echo "==> Permissões"
"${ADB[@]}" shell pm grant $PKG android.permission.ACCESS_FINE_LOCATION || true
"${ADB[@]}" shell pm grant $PKG android.permission.ACCESS_COARSE_LOCATION || true
"${ADB[@]}" shell pm grant $PKG android.permission.READ_PHONE_STATE || true
# Spec 016: o launcher mantém /sdcard/.Tcfg/sleepKillPackage.cfg (apps que a TopWay não fecha na espera).
"${ADB[@]}" shell pm grant $PKG android.permission.READ_EXTERNAL_STORAGE || true
"${ADB[@]}" shell pm grant $PKG android.permission.WRITE_EXTERNAL_STORAGE || true
# Spec 016: energia Unisoc "não otimizar" (auto-início, início por outro app, limpeza na tela bloqueada);
# sem isso o sistema nega a conexão do listener de notificações no boot. O app também garante isso ao iniciar.
for t in 4 5 6; do "${ADB[@]}" shell service call power_ex 30 s16 $PKG i32 $t i32 2 >/dev/null 2>&1 || true; done
"${ADB[@]}" shell cmd notification allow_listener $PKG/$PKG.data.media.MediaListenerService || true

# Dispensa o aviso de "tela cheia" do Android (modo imersivo do launcher).
"${ADB[@]}" shell settings put secure immersive_mode_confirmations confirmed || true

echo "==> Definindo como tela inicial"
"${ADB[@]}" shell cmd package set-home-activity $PKG/.MainActivity
"${ADB[@]}" shell am start -W -a android.intent.action.MAIN -c android.intent.category.HOME | grep -E "Status|TotalTime|Activity" || true
echo "OK. Para voltar ao launcher original: tools/rollback.sh"
