# 012 — Destinos sem digitação

**Status:** Rascunho · **Depende de:** 005, 006

## Objetivo
Cadastrar Casa, Trabalho e favoritos sem digitar endereço nos Ajustes, aproveitando o que o carro já sabe: onde ele estaciona.

## Decisões de UX (boas práticas)
- **"Salvar onde estou"** é o caminho mais rápido. Parado em casa, um toque grava a posição do GPS.
- **Tocar e segurar no mapa** grava aquele ponto (como no Google Maps).
- **Compartilhar para o Lumia:** no Google Maps ou no WhatsApp, "Compartilhar local → Lumia Launcher" salva o destino. O app vira destino de compartilhamento (*share target*).
- **Sugestão automática com confirmação:** o launcher aprende onde o carro costuma ficar desligado. Ao detectar um lugar frequente à noite, sugere "Salvar como Casa?" no card de sugestões.
  - O aprendizado é **opcional**, desligado por padrão, e os dados **ficam só na multimídia**.
- **Busca por endereço com autocompletar** (Photon/OSM, gratuito) fica como alternativa. A voz entra pelo microfone do teclado.

## Requisitos
- **R12.1** O card de sugestões e o mapa DEVEM oferecer "Salvar local atual como… Casa / Trabalho / Favorito".
- **R12.2** Tocar e segurar no mapa DEVE abrir "Salvar este ponto como…".
- **R12.3** O app DEVE receber `ACTION_SEND` (texto com link do Maps ou Waze) e `geo:`, extrair as coordenadas ou o endereço e perguntar o nome do destino.
- **R12.4** Com o aprendizado ligado, a cada `ACC_OFF` (spec 006) o launcher DEVE registrar a posição. Um lugar com ≥ 3 paradas noturnas (20h–6h) num raio de 150 m vira a sugestão "Casa"; o horário comercial gera a sugestão "Trabalho".
- **R12.5** Os destinos DEVEM aceitar mais de 2 favoritos, cada um com nome e ícone, gerenciáveis em Ajustes → Navegação.
- **R12.6** Nenhuma posição pode sair da multimídia: os dados ficam em armazenamento local e há o botão "Apagar histórico de locais".

## Critérios de aceite
- [ ] **A1** Parado em casa, "Salvar local atual como Casa" e depois "Casa" no card navega até lá.
- [ ] **A2** Compartilhar um local do Google Maps para o Lumia salva o destino.
- [ ] **A3** Com o aprendizado ligado, depois de 3 noites a sugestão "Salvar como Casa?" aparece.
