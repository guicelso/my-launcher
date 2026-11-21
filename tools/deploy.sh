#!/usr/bin/env bash
# Compila, instala e define o launcher como tela inicial (spec 001, R1.6).
# Uso: tools/deploy.sh [--release] [--no-build]
#   DEVICE=<serial> para outro aparelho (padrão: $HU_IP:5555; ex.: DEVICE=emulator-5554)
set -euo pipefail
cd "$(dirname "$0")/.."
source tools/env.sh

VARIANT=debug
BUILD=1
for arg in "$@"; do
  case "$arg" in
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

echo "==> Permissões"
"${ADB[@]}" shell pm grant $PKG android.permission.ACCESS_FINE_LOCATION || true
"${ADB[@]}" shell pm grant $PKG android.permission.ACCESS_COARSE_LOCATION || true
"${ADB[@]}" shell cmd notification allow_listener $PKG/$PKG.data.media.MediaListenerService || true

# Dispensa o aviso de "tela cheia" do Android (modo imersivo do launcher).
"${ADB[@]}" shell settings put secure immersive_mode_confirmations confirmed || true

echo "==> Definindo como tela inicial"
"${ADB[@]}" shell cmd package set-home-activity $PKG/.MainActivity
"${ADB[@]}" shell am start -W -a android.intent.action.MAIN -c android.intent.category.HOME | grep -E "Status|TotalTime|Activity" || true
echo "OK. Para voltar ao launcher original: tools/rollback.sh"
