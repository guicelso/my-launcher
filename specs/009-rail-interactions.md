# 009 — Interações da barra lateral

**Status:** Aprovada (pedido do dono, 2026-11-21) · **Depende de:** 002, 003, 007

## Objetivo
Permitir gerenciar os apps da barra lateral direto nela, com toque longo, e controlar quais botões de sistema aparecem.

## Requisitos
- **R9.1** QUANDO o usuário tocar e segurar (≥ 500 ms) um app na barra lateral, o launcher DEVE abrir um menu ao lado do ícone, com:
  - **Fixar na barra** ou **Desafixar da barra**, conforme o estado;
  - **Remover dos recentes** (só para apps não fixados);
  - **Informações do app** (tela do sistema).
- **R9.2** O mesmo menu DEVE abrir ao tocar e segurar um app na gaveta de apps.
- **R9.3** SE já houver 3 apps fixados, ENTÃO "Fixar na barra" DEVE aparecer desabilitado, com o aviso "Máximo de 3 apps fixados".
- **R9.4** Apps fixados DEVEM ter um indicador discreto (ponto de 6 dp abaixo do ícone) na barra.
- **R9.5** O menu DEVE ter itens de 76 dp de altura, fechar ao tocar fora ou no Voltar e abrir/fechar com animação de ≤ 200 ms.
- **R9.6** Ajustes → Barra lateral DEVE ter as chaves **Mostrar notificações (sino)** e **Mostrar comando de voz**. Quando desligadas, o botão some e o espaço é redistribuído.

## Critérios de aceite
- [ ] **A1** (R9.1, R9.4) Segurar o Waze na barra → "Fixar na barra" → o ícone ganha o indicador e continua na barra depois de reiniciar a multimídia.
- [ ] **A2** (R9.1) "Remover dos recentes" tira o app da barra, e o próximo recente ocupa a vaga.
- [ ] **A3** (R9.2) Segurar um app na gaveta abre o mesmo menu.
- [ ] **A4** (R9.6) Desligar o sino nos ajustes faz o botão sumir na hora.

## Resultados
_(preencher após a validação)_
