#!/usr/bin/env bash
# Abre o emulador com a mesma tela da multimídia (Android 10, 1280x720, 160 dpi).
# Uso: tools/emulator.sh [--headless] [--deploy]
#   --headless  sem janela (para automação)
#   --deploy    instala o launcher assim que o emulador terminar de ligar
set -euo pipefail
cd "$(dirname "$0")/.."
source tools/env.sh

AVD=hu_ts18
IMAGE="system-images;android-29;default;x86_64"
HEADLESS=0
DEPLOY=0
for arg in "$@"; do
  case "$arg" in
    --headless) HEADLESS=1 ;;
    --deploy) DEPLOY=1 ;;
  esac
done

if [ ! -x "$ANDROID_HOME/emulator/emulator" ] || [ ! -d "$ANDROID_HOME/system-images/android-29/default/x86_64" ]; then
  echo "==> Instalando emulador e imagem Android 10"
  yes | sdkmanager --sdk_root="$ANDROID_HOME" "emulator" "$IMAGE" >/dev/null
fi

if ! avdmanager list avd -c | grep -qx "$AVD"; then
  echo "==> Criando AVD $AVD"
  echo no | avdmanager create avd -n "$AVD" -k "$IMAGE" -d pixel_c --force >/dev/null
  CFG="$HOME/.android/avd/$AVD.avd/config.ini"
  sed -i -e 's/^hw.lcd.width=.*/hw.lcd.width=1280/' -e 's/^hw.lcd.height=.*/hw.lcd.height=720/' \
         -e 's/^hw.lcd.density=.*/hw.lcd.density=160/' -e 's/^hw.keyboard=.*/hw.keyboard=yes/' "$CFG"
fi

ARGS=(-avd "$AVD" -gpu swiftshader_indirect -no-boot-anim -port 5554)
[ "$HEADLESS" = 1 ] && ARGS+=(-no-window -no-audio)

echo "==> Iniciando emulador (serial emulator-5554)"
"$ANDROID_HOME/emulator/emulator" "${ARGS[@]}" >/dev/null 2>&1 &

adb -s emulator-5554 wait-for-device
until [ "$(adb -s emulator-5554 shell getprop sys.boot_completed 2>/dev/null | tr -d '\r')" = "1" ]; do sleep 2; done
# Posição de teste (São Paulo) para o mapa.
adb -s emulator-5554 emu geo fix -46.6333 -23.5505 >/dev/null || true
echo "Emulador pronto."

if [ "$DEPLOY" = 1 ]; then
  DEVICE=emulator-5554 tools/deploy.sh
fi
