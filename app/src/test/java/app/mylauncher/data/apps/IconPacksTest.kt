package app.mylauncher.data.apps

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class IconPacksTest {
    @Test fun `le componente do appfilter`() {
        assertEquals("com.spotify.music/com.spotify.music.MainActivity", IconPacks.parseComponent("ComponentInfo{com.spotify.music/com.spotify.music.MainActivity}"))
        assertEquals("com.waze/com.waze.FreeMapAppActivity", IconPacks.parseComponent("ComponentInfo{com.waze/.FreeMapAppActivity}"))
        assertNull(IconPacks.parseComponent("ComponentInfo{:LAUNCHER_ACTION_APP_DRAWER}"))
    }
}
