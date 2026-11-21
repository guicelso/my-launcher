package app.mylauncher.core

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class CrashGuardTest {
    @Test fun `um crash nao aciona o fallback`() {
        assertFalse(CrashGuard.shouldFallBack(listOf(1_000L), now = 2_000L))
    }

    @Test fun `dois crashes dentro de 60s acionam o fallback`() {
        assertTrue(CrashGuard.shouldFallBack(listOf(1_000L, 50_000L), now = 55_000L))
    }

    @Test fun `crashes fora da janela sao ignorados`() {
        assertFalse(CrashGuard.shouldFallBack(listOf(1_000L, 100_000L), now = 100_500L))
    }
}
