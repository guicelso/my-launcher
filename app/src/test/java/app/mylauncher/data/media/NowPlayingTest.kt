package app.mylauncher.data.media

import org.junit.Assert.assertEquals
import org.junit.Test

class NowPlayingTest {
    private val base = NowPlaying("p", "P", "t", "a", durationMs = 200_000, positionMs = 10_000, positionUpdatedAt = 1_000, playing = true)

    @Test fun `interpola enquanto toca`() {
        assertEquals(15_000, base.positionAt(6_000))
    }

    @Test fun `parado nao avanca`() {
        assertEquals(10_000, base.copy(playing = false).positionAt(60_000))
    }

    @Test fun `nao passa da duracao`() {
        assertEquals(200_000, base.positionAt(10_000_000))
    }

    @Test fun `formata tempo`() {
        assertEquals("1:24", formatTime(84_000))
        assertEquals("1:02:03", formatTime(3_723_000))
    }

    @Test fun `escolhe a ultima sessao que tocou`() {
        val picked = SessionPicker.pick(
            listOf(
                SessionPicker.Candidate("spotify", playing = false, lastPlayingAt = 100),
                SessionPicker.Candidate("ytmusic", playing = true, lastPlayingAt = 50),
            ),
        )
        assertEquals("ytmusic", picked)
        assertEquals(
            "spotify",
            SessionPicker.pick(
                listOf(
                    SessionPicker.Candidate("spotify", false, 100),
                    SessionPicker.Candidate("ytmusic", false, 50),
                ),
            ),
        )
    }

    @Test fun `preferencia do usuario vale ate outra sessao tocar depois`() {
        val c = listOf(
            SessionPicker.Candidate("spotify", playing = false, lastPlayingAt = 10),
            SessionPicker.Candidate("ytmusic", playing = true, lastPlayingAt = 50),
        )
        assertEquals("spotify", SessionPicker.pick(c, preferred = "spotify", preferredAt = 100))
        assertEquals("ytmusic", SessionPicker.pick(c, preferred = "spotify", preferredAt = 20))
    }
}
