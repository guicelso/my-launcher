# 006 — Integração com o veículo (TopWay)

**Status:** Rascunho · **Depende de:** 001 · **Referência:** `docs/00-discovery.md` §4

## Objetivo
Receber os sinais do carro (faróis, ré, ignição, velocidade, tensão, freio de mão, fonte de áudio) pelos serviços da TopWay e expô-los como `VehicleSource.state`. O primeiro uso é o tema automático pelos faróis.

## Abordagem
Implementar o `TopWayVehicleSource` em camadas, da mais limpa para a mais invasiva. Cada uma é um *spike* com resultado registrado:

| Camada | Canal | Sinais |
|---|---|---|
| **C1** | Bind em `com.tw.carinfoservice/.CarService`, com `transact` cru usando o descriptor `com.tw.carinfoservice.CarServiceAidl` | farol, tensão, velocidade, freio de mão |
| **C2** | Bind em `com.tw.service.xt/.CommandService` (`ITWCommandAidl`) | ré, ACC/sleep, volume, fonte, iluminação (`light_recieve`) |
| **C3** | Broadcasts `com.unisound.intent.action.ACC_*` | ignição |
| **C4** | Propriedade `persist.tw.theme.mode` (consultada a cada 5 s apenas se C1 e C2 falharem) | dia/noite |
| **C5** | `android.tw.john.TWUtil` via reflexão (`0x0204`, `0x9F1C`, `0x0202`, `0x050B`) | todos, se `open()` funcionar para um app comum |

## Requisitos
- **R6.1** `VehicleState` DEVE conter: `headlightsOn`, `reverse`, `acc`, `speedKmh`, `batteryV`, `handbrake`, `audioSource` e `availability` (por sinal: DISPONÍVEL / INDISPONÍVEL).
- **R6.2** O `TopWayVehicleSource` DEVE tentar C1 → C2 → C3 → C4 → C5 e combinar o que funcionar. Cada sinal fica com o canal de maior prioridade disponível.
- **R6.3** SE um serviço da TopWay morrer (`binderDied`), ENTÃO a fonte DEVE refazer o bind com backoff (1 s, 2 s, 5 s, máx. 30 s), mantendo o último estado conhecido.
- **R6.4** Os códigos de transação e descriptors DEVEM ficar isolados em `data/vehicle/topway/Protocol.kt`, com comentário apontando a evidência (arquivo:linha no decompilado).
- **R6.5** QUANDO `headlightsOn` mudar e a preferência de tema for AUTO, o tema DEVE trocar em ≤ 1 s (ver spec 002).
- **R6.6** ENQUANTO `reverse` for verdadeiro, o launcher NÃO DEVE abrir nenhuma tela nem tocar som. A câmera da TopWay tem prioridade.
- **R6.7** Uma tela de diagnóstico (Ajustes → Sistema → Veículo) DEVE mostrar cada sinal, o canal de origem e o horário do último evento.
- **R6.8** O launcher NÃO DEVE escrever comandos na MCU (`write`) nesta spec. O escopo é somente leitura.

## Critérios de aceite
- [ ] **A1** (R6.2) A tela de diagnóstico mostra o farol mudando ao ligar e desligar a luz, com o canal identificado.
- [ ] **A2** (R6.5) Ligar os faróis com o tema em AUTO muda para noite em ≤ 1 s, e desligar volta para dia.
- [ ] **A3** Ao engatar a ré, a câmera da TopWay aparece e `reverse=true` no diagnóstico. Ao desengatar, o launcher volta intacto.
- [ ] **A4** Ao desligar e religar a ignição, a sequência ACC_OFF → ACC_ON fica registrada sem crash.
- [ ] **A5** A velocidade no diagnóstico confere com o painel do carro (±3 km/h).
- [ ] **A6** (R6.3) `am force-stop com.tw.carinfoservice` → o launcher refaz o bind e volta a receber o farol.

## Spike S6.1: viabilidade (fazer primeiro)
Um app de teste mínimo (pode ser uma activity de debug no próprio launcher) que:
1. faz bind em C1 e C2 e registra no log todos os callbacks;
2. tenta `TWUtil.open(new short[]{0x0204})` via reflexão e registra o resultado;
3. registra em `Resultados` quais canais funcionam como app comum.

## Resultados
_(preencher após a validação)_
