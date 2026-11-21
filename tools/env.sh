# Uso: source tools/env.sh
export JAVA_HOME="$HOME/.local/jdk-17"
export ANDROID_HOME="$HOME/Android/Sdk"
export ANDROID_SDK_ROOT="$ANDROID_HOME"
export PATH="$JAVA_HOME/bin:$ANDROID_HOME/platform-tools:$ANDROID_HOME/cmdline-tools/latest/bin:$HOME/.local/jadx/bin:$PATH"
# IP da multimídia na rede local (ajuste se mudar)
export HU_IP="${HU_IP:-192.168.10.136}"
