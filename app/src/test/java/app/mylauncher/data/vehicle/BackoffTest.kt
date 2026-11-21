package app.mylauncher.data.vehicle

import app.mylauncher.data.vehicle.topway.Backoff
import org.junit.Assert.assertEquals
import org.junit.Test

class BackoffTest {
    @Test fun `backoff cresce e satura em 30s`() {
        assertEquals(1_000, Backoff.delayFor(0))
        assertEquals(2_000, Backoff.delayFor(1))
        assertEquals(5_000, Backoff.delayFor(2))
        assertEquals(30_000, Backoff.delayFor(4))
        assertEquals(30_000, Backoff.delayFor(99))
    }
}
