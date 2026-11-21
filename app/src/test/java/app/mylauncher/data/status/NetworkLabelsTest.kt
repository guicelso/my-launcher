package app.mylauncher.data.status

import android.telephony.TelephonyManager
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class NetworkLabelsTest {
    @Test fun `rotulos por tecnologia`() {
        assertEquals("4G", NetworkLabels.of(TelephonyManager.NETWORK_TYPE_LTE))
        assertEquals("H+", NetworkLabels.of(TelephonyManager.NETWORK_TYPE_HSPAP))
        assertEquals("H", NetworkLabels.of(TelephonyManager.NETWORK_TYPE_HSDPA))
        assertEquals("3G", NetworkLabels.of(TelephonyManager.NETWORK_TYPE_UMTS))
        assertEquals("E", NetworkLabels.of(TelephonyManager.NETWORK_TYPE_EDGE))
        assertEquals("G", NetworkLabels.of(TelephonyManager.NETWORK_TYPE_GPRS))
        assertNull(NetworkLabels.of(TelephonyManager.NETWORK_TYPE_UNKNOWN))
    }
}
