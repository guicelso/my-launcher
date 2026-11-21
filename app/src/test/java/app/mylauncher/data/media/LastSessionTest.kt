package app.mylauncher.data.media

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class LastSessionTest {
    private val base = LastSession("com.spotify.music", "Spotify", "Faixa", "Artista")

    @Test fun `tocando ao desligar a chave retoma`() {
        assertTrue(base.copy(playing = true).wasPlayingAt(accOffAt = 100_000))
    }

    @Test fun `pausa da TopWay chegando junto com o ACC conta como tocando`() {
        assertTrue(base.copy(playing = false, pausedAt = 99_500).wasPlayingAt(accOffAt = 100_000))
    }

    @Test fun `pausa do usuario segundos antes de tirar a chave nao retoma`() {
        // Caso medido no carro: pausa 14 s antes do ACC_OFF.
        assertFalse(base.copy(playing = false, pausedAt = 86_000).wasPlayingAt(accOffAt = 100_000))
    }

    @Test fun `pausa antiga do usuario nao retoma`() {
        assertFalse(base.copy(playing = false, pausedAt = 10_000).wasPlayingAt(accOffAt = 100_000))
    }

    @Test fun `sem pausa registrada e parada nao retoma`() {
        assertFalse(base.copy(playing = false, pausedAt = 0).wasPlayingAt(accOffAt = 100_000))
    }
}
