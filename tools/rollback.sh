#!/usr/bin/env bash
# Restaura o launcher original da multimídia (DoFun) como tela inicial (spec 001, R1.6).
# Uso: tools/rollback.sh   (DEVICE=<serial> para outro aparelho)
set -euo pipefail
cd "$(dirname "$0")/.."
source tools/env.sh

DEVICE="${DEVICE:-$HU_IP:5555}"
ORIGINAL="com.dofun.variety/com.dofun.overseasvariety.Launcher"
if [[ "$DEVICE" == *:* ]]; then adb connect "$DEVICE" >/dev/null; fi

adb -s "$DEVICE" shell cmd package set-home-activity "$ORIGINAL"
adb -s "$DEVICE" shell am start -a android.intent.action.MAIN -c android.intent.category.HOME >/dev/null
echo "Launcher original restaurado: $ORIGINAL"
