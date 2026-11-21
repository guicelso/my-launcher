#!/usr/bin/env bash
# Instala o ambiente de desenvolvimento no espaço do usuário (sem sudo).
#   JDK 17 (Temurin) -> ~/.local/jdk-17
#   Android SDK      -> ~/Android/Sdk (platform-tools, cmdline-tools, platform 35, build-tools 35)
#   jadx             -> ~/.local/jadx
# Depois: source tools/env.sh
set -euo pipefail

SDK="$HOME/Android/Sdk"
TMP="$(mktemp -d)"
trap 'rm -rf "$TMP"' EXIT

if [ ! -x "$HOME/.local/jdk-17/bin/java" ]; then
  echo "==> JDK 17"
  curl -fsSL -o "$TMP/jdk.tgz" "https://api.adoptium.net/v3/binary/latest/17/ga/linux/x64/jdk/hotspot/normal/eclipse"
  mkdir -p "$TMP/jdk" && tar -xzf "$TMP/jdk.tgz" -C "$TMP/jdk"
  rm -rf "$HOME/.local/jdk-17" && mkdir -p "$HOME/.local"
  mv "$TMP"/jdk/jdk-17* "$HOME/.local/jdk-17"
fi
export JAVA_HOME="$HOME/.local/jdk-17"
export PATH="$JAVA_HOME/bin:$PATH"

if [ ! -x "$SDK/cmdline-tools/latest/bin/sdkmanager" ]; then
  echo "==> Android cmdline-tools"
  curl -fsSL -o "$TMP/clt.zip" "https://dl.google.com/android/repository/commandlinetools-linux-11076708_latest.zip"
  mkdir -p "$SDK/cmdline-tools" && unzip -qo "$TMP/clt.zip" -d "$TMP/clt"
  rm -rf "$SDK/cmdline-tools/latest" && mv "$TMP/clt/cmdline-tools" "$SDK/cmdline-tools/latest"
fi

echo "==> SDK packages"
yes | "$SDK/cmdline-tools/latest/bin/sdkmanager" --sdk_root="$SDK" --licenses >/dev/null || true
"$SDK/cmdline-tools/latest/bin/sdkmanager" --sdk_root="$SDK" \
  "platform-tools" "platforms;android-35" "build-tools;35.0.0"

if [ ! -x "$HOME/.local/jadx/bin/jadx" ]; then
  echo "==> jadx"
  URL=$(curl -fsSL https://api.github.com/repos/skylot/jadx/releases/latest \
        | grep -oE '"browser_download_url": *"[^"]*jadx-[0-9.]+\.zip"' | head -1 | cut -d'"' -f4)
  curl -fsSL -o "$TMP/jadx.zip" "$URL"
  rm -rf "$HOME/.local/jadx" && mkdir -p "$HOME/.local/jadx" && unzip -qo "$TMP/jadx.zip" -d "$HOME/.local/jadx"
  chmod +x "$HOME/.local/jadx/bin/"*
fi

echo "==> Versões"
java -version 2>&1 | head -1
"$SDK/platform-tools/adb" version | head -1
"$HOME/.local/jadx/bin/jadx" --version
echo "OK"
