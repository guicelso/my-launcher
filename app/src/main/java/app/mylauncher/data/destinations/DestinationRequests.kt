package app.mylauncher.data.destinations

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Pedido de "Salvar como…" vindo de qualquer lugar (card, toque longo no mapa, compartilhamento
 * de outro app ou sugestão aprendida). A tela raiz mostra o painel de escolha.
 */
class DestinationRequests {
    private val _pending = MutableStateFlow<SharedPlace?>(null)
    val pending: StateFlow<SharedPlace?> = _pending.asStateFlow()

    fun request(place: SharedPlace) {
        _pending.value = place
    }

    fun dismiss() {
        _pending.value = null
    }
}
