#!/usr/bin/env bash
# Coleta informações da multimídia via adb (somente leitura — não altera nada no aparelho).
# Uso: tools/collect-device-info.sh [serial]
set -uo pipefail

ADB="${ADB:-$HOME/Android/Sdk/platform-tools/adb}"
[ -n "${1:-}" ] && ADB="$ADB -s $1"

OUT="$(dirname "$0")/../discovery/device-$(date +%Y%m%d-%H%M%S)"
mkdir -p "$OUT"
echo "Salvando em $OUT"

run() { # run <arquivo> <comando shell no device>
  echo "  -> $1"
  $ADB shell "$2" > "$OUT/$1" 2>&1
}

$ADB wait-for-device

# Identidade / sistema
run getprop.txt            'getprop'
run id.txt                 'id; which su; ls -l /system/bin/su /system/xbin/su 2>/dev/null'
run display.txt            'wm size; wm density; dumpsys display | grep -iE "mBaseDisplayInfo|DisplayDeviceInfo" | head -20'
run cpu-mem.txt            'cat /proc/cpuinfo; echo; cat /proc/meminfo; echo; df -h'
run gpu.txt                'dumpsys SurfaceFlinger | grep -iE "GLES|EGL" | head -10'

# Pacotes e launcher atual
run packages-all.txt       'pm list packages -f -U'
run packages-system.txt    'pm list packages -s'
run packages-3rdparty.txt  'pm list packages -3'
run home-resolve.txt       'cmd package resolve-activity --brief -a android.intent.action.MAIN -c android.intent.category.HOME; echo; cmd package query-activities --brief -a android.intent.action.MAIN -c android.intent.category.HOME'
run features.txt           'pm list features'

# Serviços e processos (procurar MCU / canbus / car)
run services.txt           'service list'
run processes.txt          'ps -A -o USER,PID,PPID,NAME'
run activity-top.txt       'dumpsys activity top | head -80; dumpsys activity activities | grep -E "mResumedActivity|mFocusedApp|topResumedActivity"'

# Mídia, áudio, notificações
run media-session.txt      'dumpsys media_session'
run audio.txt              'dumpsys audio | head -150'
run notification-listeners.txt 'settings get secure enabled_notification_listeners; cmd notification list 2>/dev/null | head -40'

# Entrada (botões de volante, knob, touch)
run input.txt              'dumpsys input | head -200'
run getevent.txt           'getevent -pl 2>/dev/null'

# Configurações (fabricantes costumam guardar chaves próprias aqui)
run settings-system.txt    'settings list system'
run settings-secure.txt    'settings list secure'
run settings-global.txt    'settings list global'

# Portas seriais / devices (MCU geralmente fala por UART)
run dev.txt                'ls -l /dev | grep -iE "tty|mcu|can|serial|i2c"'

# Multi-janela / recursos de sistema que impactam o design
run multiwindow.txt        'settings get global enable_freeform_support; settings get global force_resizable_activities; getprop | grep -iE "multiwindow|freeform"'

# Snapshot de log (últimos eventos — útil para achar broadcasts do MCU)
run logcat.txt             'logcat -d -v time | tail -3000'

echo "Pronto. Resumo:"
grep -E "ro.product.(model|manufacturer|brand|board)|ro.build.version.(release|sdk)|ro.board.platform|ro.hardware\]|ro.product.cpu.abilist\]" "$OUT/getprop.txt"
cat "$OUT/display.txt" | head -2
cat "$OUT/home-resolve.txt" | head -3
