package app.mylauncher.data.vehicle.topway

import org.junit.Assert.assertEquals
import org.junit.Test

class SleepKeepListTest {
    @Test fun `le uma linha por pacote ignorando vazias e espacos`() {
        assertEquals(listOf("app.mylauncher", "com.waze"), SleepKeepList.parse(" app.mylauncher \n\ncom.waze\n"))
    }

    @Test fun `mescla preservando o que ja existe e sem duplicar`() {
        val merged = SleepKeepList.merge(listOf("com.waze", "app.mylauncher"), listOf("app.mylauncher", "com.spotify.music"))
        assertEquals(listOf("com.waze", "app.mylauncher", "com.spotify.music"), merged)
    }

    @Test fun `sem mudanca quando tudo ja esta na lista`() {
        val current = listOf("app.mylauncher")
        assertEquals(current, SleepKeepList.merge(current, listOf("app.mylauncher")))
    }
}
