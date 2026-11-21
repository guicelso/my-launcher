# Lumia Launcher — instruções para o Claude

Launcher Android no estilo Android Auto para a multimídia **TopWay TS18 (Android 10)** do carro do usuário. Kotlin + Jetpack Compose, pacote `app.mylauncher`.

## Leia primeiro
- [`docs/04-runbook.md`](docs/04-runbook.md): como conectar no adb, instalar, recuperar acesso, emulador e estado atual. **Comece por aqui.**
- [`specs/README.md`](specs/README.md): processo spec-driven e índice de specs.
- [`docs/00-discovery.md`](docs/00-discovery.md), [`docs/01-architecture.md`](docs/01-architecture.md), [`docs/02-design.md`](docs/02-design.md), [`docs/03-topway-protocol.md`](docs/03-topway-protocol.md).

## Regras de trabalho
- **Idioma:** português do Brasil, nas respostas, nos textos do app e nos comentários.
- **Spec-driven:** funcionalidade nova vira (ou atualiza) uma spec em `specs/` antes do código. Resultados de teste no carro são registrados na seção "Resultados" da spec.
- **Commits:** Conventional Commits, pequenos e por assunto, na branch `feat/launcher-v1`. Sem push sem pedido.
- **Quando o usuário pedir várias coisas de uma vez:** implementar tudo, compilando e testando a cada etapa, e só no fim fazer o build, testar no emulador e instalar na multimídia.
- **Sempre testar no emulador** (`tools/emulator.sh --deploy`, painel de depuração com toque longo no relógio) antes de instalar no carro com `tools/deploy.sh`.
- **Instalar sempre a release** (`tools/deploy.sh`). A versão de depuração é lenta demais na GPU da multimídia.

## Armadilhas conhecidas
- O `adb screencap` da multimídia sai **girado 90°**. Gire o print antes de avaliar o layout e **nunca** fixe `screenOrientation` no manifesto.
- O adb só funciona por **Wi-Fi** (`192.168.10.136:5555`). Se aparecer `unauthorized` ou a porta estiver fechada, siga o runbook (EngineerMode + `adb_keys`).
- Mapa: TextureView de tamanho fixo e 30 fps. Não redimensionar o `MapView` durante animações.
- Popups e diálogos: use `PopupProperties(focusable = false)` ou overlays dentro da janela. Diálogos com foco mostram as barras do sistema.
- Android 10: sem `Modifier.blur` (o desfoque é na CPU, ver `ArtBackdrop.kt`) e sem APIs de 11+.
- Nada de ativos proprietários do Google (logos, Google Sans, ícones de apps): o visual segue o layout do Android Auto sobre o Material 3.
- Nunca desinstalar o launcher original (`com.dofun.variety`). Gravar o boot ou fazer root (spec 008, S8.3) só com o firmware completo em mãos e aprovação explícita do usuário.
