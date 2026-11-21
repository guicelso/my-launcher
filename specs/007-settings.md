# 007 — Ajustes do launcher

**Status:** Rascunho · **Depende de:** 002, 006 · **Design:** artboard "Ajustes do launcher"

## Objetivo
Concentrar as preferências do usuário e as ferramentas de diagnóstico e recuperação.

## Requisitos
- **R7.1** Os ajustes DEVEM ter duas colunas: seções à esquerda (Aparência, Barra lateral, Navegação, Mídia, Veículo, Sistema) e detalhe à direita, com itens de 76 dp.
- **R7.2** **Aparência:** tema (Automático / Dia / Noite) e tamanho do texto (Normal / Grande).
- **R7.3** **Barra lateral:** apps fixados (mesmo editor da spec 003).
- **R7.4** **Navegação:** app padrão (Waze / Google Maps / HERE, entre os instalados) e endereços Casa/Trabalho (busca ou coordenadas).
- **R7.5** **Mídia:** app de música padrão e acesso a notificações, com atalho para conceder se estiver faltando.
- **R7.6** **Veículo:** baixar o volume na ré (via foco de áudio transitório com "duck", sem escrever na MCU), mostrar a tensão da bateria e a tela de diagnóstico (R6.7).
- **R7.7** **Sistema:**
  - versão;
  - exportar logs para `/sdcard/Android/data/app.mylauncher/files/logs/` (pasta do app; dispensa permissão de armazenamento);
  - **"Voltar ao launcher original"**, que abre a escolha de tela inicial do sistema (`ACTION_HOME_SETTINGS`). Um app comum não pode definir o HOME de outro app; se essa tela não existir, abre o DoFun direto. O rollback completo é `tools/rollback.sh`.
- **R7.8** As preferências DEVEM persistir em DataStore e ser aplicadas sem reiniciar o app.

## Critérios de aceite
- [ ] **A1** (R7.2, R7.8) Trocar o tema para "Dia" aplica na hora e persiste após reiniciar a multimídia.
- [ ] **A2** (R7.4) Mudar o app padrão para Google Maps faz a busca do mapa abrir o Maps.
- [ ] **A3** (R7.7) "Voltar ao launcher original" leva ao DoFun e o HOME passa a abrir o DoFun.
- [ ] **A4** (R7.7) A exportação de logs gera um arquivo legível via `adb pull`.

## Resultados
_(preencher após a validação)_
