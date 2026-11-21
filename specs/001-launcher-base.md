# 001 — Launcher base, deploy e recuperação

**Status:** Rascunho · **Depende de:** — · **Design:** —

## Objetivo
Ter um app Compose mínimo que a multimídia aceite como tela inicial. Ele precisa instalar com um comando e voltar ao launcher original com outro, e só depois disso começamos a interface.

## Pré-requisitos
- [ ] adb autorizado e persistente após reboot (`docs/00-discovery.md` §2).
- [ ] AVD de referência: API 29, 1280×720, 160 dpi.

## Requisitos
- **R1.1** O app DEVE declarar uma activity com `MAIN` + `HOME` + `DEFAULT`, `launchMode=singleTask`, `screenOrientation=landscape` e `resizeableActivity=false`.
- **R1.2** O app DEVE ter `minSdk 29`, `targetSdk 29`, Kotlin e Compose (Material 3), seguindo a estrutura de pacotes de `docs/01-architecture.md`.
- **R1.3** ENQUANTO a activity estiver visível, o app DEVE ocultar as barras de sistema (modo imersivo, que reaparecem com um deslize).
- **R1.4** QUANDO o botão HOME (físico, da barra da TopWay ou do volante) for acionado, o launcher DEVE voltar ao painel sem recriar a activity.
- **R1.5** O app DEVE desenhar o primeiro quadro em ≤ 1,5 s a partir do boot frio do processo (medido com `am start -W`).
- **R1.6** `tools/deploy.sh` DEVE compilar, instalar e definir o launcher como HOME. `tools/rollback.sh` DEVE restaurar `com.dofun.variety` como HOME.
- **R1.7** SE o launcher travar 2 vezes em 60 s, ENTÃO ele DEVE abrir o launcher original e registrar o erro (proteção contra loop de crash).
- **R1.8** O botão Voltar na tela inicial NÃO DEVE fechar o launcher.

## Critérios de aceite
- [ ] **A1** (R1.6) Com `./tools/deploy.sh`, a multimídia passa a mostrar o novo launcher ao tocar em HOME.
- [ ] **A2** (R1.6) Com `./tools/rollback.sh`, o launcher DoFun volta como HOME.
- [ ] **A3** (R1.4, R1.8) HOME pelo volante e pela barra volta ao launcher, e Voltar não o fecha.
- [ ] **A4** (R1.3) O registro na spec documenta se a barra de sistema da TopWay some com o modo imersivo, com print `adb exec-out screencap`.
- [ ] **A5** Após desligar e ligar o carro, o novo launcher continua como HOME. Se não continuar, a causa e a correção ficam documentadas.
- [ ] **A6** (R1.5) `am start -W` mostra `TotalTime` ≤ 1500 ms.
- [ ] **A7** (R1.7) Um crash forçado duas vezes (botão de debug) leva ao launcher original.

## Tarefas
1. Criar o projeto Gradle (Kotlin DSL, version catalog) com `:app`, `MainActivity` e um tema vazio.
2. Manifest HOME, modo imersivo e tratamento de `onNewIntent` para o HOME.
3. Criar `tools/deploy.sh`, `tools/rollback.sh` e `tools/logs.sh` (logcat filtrado pelo pacote).
4. Implementar o `CrashGuard` (R1.7).
5. Executar o roteiro no carro e registrar os resultados abaixo.

## Roteiro de teste no carro
```sh
source tools/env.sh && adb connect $HU_IP:5555
./tools/deploy.sh
adb -s $HU_IP:5555 shell am start -W -n app.mylauncher/.MainActivity
adb -s $HU_IP:5555 exec-out screencap -p > discovery/shot-001.png
./tools/rollback.sh
```

## Fora de escopo
Qualquer UI além de um placeholder com o nome do app.

## Resultados
_(preencher após a validação)_
