# 003 — Gaveta de apps e recentes

**Status:** Rascunho · **Depende de:** 001 · **Design:** artboard "Gaveta de apps"

## Objetivo
Listar e abrir todos os apps instalados, manter a lista de recentes da barra lateral e permitir fixar apps.

## Requisitos
- **R3.1** A gaveta DEVE listar as activities com `MAIN`/`LAUNCHER`, em ordem alfabética (pt-BR), em uma grade de 6 colunas com ícone de 96 dp e rótulo de 18 dp.
- **R3.2** A gaveta DEVE excluir o próprio launcher e uma lista configurável de pacotes ocultos (padrão: `com.sprd.engineermode`, `de.szalkowski.activitylauncher`).
- **R3.3** QUANDO um pacote for instalado, removido ou atualizado, a lista DEVE se atualizar sozinha (`LauncherApps.Callback`).
- **R3.4** A busca DEVE filtrar por nome, sem diferenciar acentos nem maiúsculas.
- **R3.5** QUANDO um app for aberto pelo launcher, ele DEVE entrar em primeiro lugar nos **recentes** (máx. 3, persistidos em DataStore). Apps fixados têm prioridade sobre os recentes.
- **R3.6** "Editar barra" DEVE permitir fixar até 3 apps na barra lateral.
- **R3.7** A gaveta DEVE paginar horizontalmente, com indicador de página, em vez de rolagem infinita.
- **R3.8** A entrada "Ajustes do launcher" DEVE abrir a spec 007. A entrada "Launcher original" DEVE abrir `com.dofun.variety`.

## Critérios de aceite
- [ ] **A1** (R3.1, R3.2) Todos os apps esperados aparecem, e os ocultos não.
- [ ] **A2** (R3.3) Após `adb install` de um APK qualquer, o app aparece sem reiniciar o launcher.
- [ ] **A3** (R3.5) Abrir Spotify → Waze → Telefone deixa os recentes nessa ordem, mesmo depois de reiniciar a multimídia.
- [ ] **A4** (R3.4) A busca "musica" encontra "Música USB".
- [ ] **A5** A gaveta abre em ≤ 300 ms com cerca de 60 apps (ícones em cache).

## Tarefas
1. `AppsRepository` (LauncherApps + cache de ícones em memória).
2. `RecentsStore` (DataStore).
3. `AppDrawerScreen` com `HorizontalPager` e busca.
4. Editor de fixados.

## Resultados
_(preencher após a validação)_
