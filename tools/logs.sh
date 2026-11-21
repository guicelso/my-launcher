#!/usr/bin/env bash
# Acompanha o logcat do launcher (e crashes). Uso: tools/logs.sh [-d]  (-d = despeja e sai)
set -euo pipefail
cd "$(dirname "$0")/.."
source tools/env.sh

DEVICE="${DEVICE:-$HU_IP:5555}"
if [[ "$DEVICE" == *:* ]]; then adb connect "$DEVICE" >/dev/null; fi

PID=$(adb -s "$DEVICE" shell pidof app.mylauncher | tr -d '\r' || true)
if [ -n "$PID" ]; then
  adb -s "$DEVICE" logcat -v time "$@" --pid="$PID"
else
  echo "(launcher não está rodando; mostrando tags MyLauncher e AndroidRuntime)"
  adb -s "$DEVICE" logcat -v time "$@" -s MyLauncher:V AndroidRuntime:E
fi
