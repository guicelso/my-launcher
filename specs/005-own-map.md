# 005 — Mapa próprio (MapLibre)

**Status:** Rascunho · **Depende de:** 002 · **Design:** artboards "Mapa em tela cheia" e card de mapa do painel

## Objetivo
Primeira implementação do `NavigationPane`: um mapa vetorial próprio que mostra a posição e o rumo do carro. A navegação em si é delegada ao Waze ou ao Maps. A spec 008 depois substitui este painel pelo app embutido.

## Requisitos
- **R5.1** O `OwnMapPane` DEVE usar o **MapLibre Native Android**, com estilos vetoriais noite e dia que seguem o tema (spec 002).
- **R5.2** A fonte de tiles DEVE ser configurável. Padrão: **OpenFreeMap** (sem chave), com a atribuição exigida pela licença ODbL/OpenStreetMap visível.
- **R5.3** A posição DEVE vir do `LocationManager` (provedor GPS, 1 Hz). O marcador DEVE indicar o rumo, e a câmera acompanha o carro até o usuário arrastar o mapa.
- **R5.4** O modo CARD (painel) DEVE mostrar a busca "Para onde?", a velocidade (km/h, do GPS) e o botão recentrar.
- **R5.5** O modo FULL DEVE adicionar:
  - atalhos Casa, Trabalho e Postos;
  - zoom +/−;
  - bússola (norte para cima / rumo para cima);
  - nome da rua atual, se disponível offline; caso contrário, o rótulo fica oculto;
  - o botão "Abrir no <app padrão>".
- **R5.6** QUANDO o usuário tocar na busca ou em um destino, o launcher DEVE abrir o app de navegação padrão com o deep link correspondente:
  - Waze: `https://waze.com/ul?q=…` / `?ll=lat,lon&navigate=yes`;
  - Maps: `google.navigation:q=…`.
- **R5.7** Ao ficar sem rede, o mapa DEVE continuar com o cache de tiles (≥ 200 MB em disco) e não pode travar a UI.
- **R5.8** Com o mapa animando, a renderização DEVE manter ≥ 30 fps na GPU PowerVR GE8322.

## Critérios de aceite
- [ ] **A1** (R5.3) Dirigindo, o marcador segue o carro com atraso ≤ 1 s e o rumo correto.
- [ ] **A2** (R5.1) Acender os faróis troca o estilo do mapa para o noturno (depende da 006 ou do fake).
- [ ] **A3** (R5.6) "Casa" abre o Waze já navegando para o endereço salvo.
- [ ] **A4** (R5.8) `dumpsys gfxinfo app.mylauncher` mostra ≤ 10% de quadros lentos durante 60 s de mapa.
- [ ] **A5** (R5.7) No modo avião, a área já visitada continua visível.

## Pesquisa aberta
- Validar o deep link de favoritos do Waze (`waze://?favorite=home`). Se não funcionar, usar as coordenadas salvas nos ajustes.
- Ver se existe geocodificação reversa offline viável para o nome da rua. Se não existir, o rótulo fica de fora.

## Resultados
_(preencher após a validação)_
