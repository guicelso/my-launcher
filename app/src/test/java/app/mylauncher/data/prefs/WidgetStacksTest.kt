package app.mylauncher.data.prefs

import app.mylauncher.data.prefs.WidgetType.CLOCK
import app.mylauncher.data.prefs.WidgetType.MEDIA
import app.mylauncher.data.prefs.WidgetType.NOTIFICATIONS
import app.mylauncher.data.prefs.WidgetType.SUGGESTIONS
import app.mylauncher.data.prefs.WidgetType.VEHICLE
import app.mylauncher.data.prefs.WidgetType.WEATHER
import app.mylauncher.data.prefs.WidgetType.WEATHER_HOURLY
import org.junit.Assert.assertEquals
import org.junit.Test

/** Regras das pilhas de widgets (spec 017). */
class WidgetStacksTest {
    private val base = listOf(listOf(MEDIA, WEATHER), listOf(SUGGESTIONS, CLOCK), listOf(VEHICLE))

    @Test fun `configuracao antiga vira pilhas de um widget`() {
        // R17.1: o que estava salvo na spec 013 continua igual.
        val legacy = Widgets.normalize(listOf(WEATHER, MEDIA, VEHICLE))
        assertEquals(listOf(listOf(WEATHER), listOf(MEDIA), listOf(VEHICLE)), WidgetStacks.normalize(legacy.map { listOf(it) }))
    }

    @Test fun `codifica e decodifica`() {
        assertEquals(base, WidgetStacks.decode(WidgetStacks.encode(base)))
    }

    @Test fun `decodifica ignorando nomes desconhecidos`() {
        assertEquals(listOf(listOf(MEDIA), listOf(CLOCK), listOf(VEHICLE)), WidgetStacks.decode("MEDIA,XYZ|CLOCK|VEHICLE"))
    }

    @Test fun `normaliza repetidos, vazios e o limite de 7`() {
        val n = WidgetStacks.normalize(listOf(WidgetType.entries.toList(), listOf(MEDIA), emptyList()))
        assertEquals(WidgetStacks.MAX, n[0].size)
        assertEquals(WidgetStacks.SLOTS, n.size)
        assertEquals(n.flatten().size, n.flatten().distinct().size)
        n.forEach { assertEquals(true, it.isNotEmpty()) }
    }

    @Test fun `adicionar coloca no fim`() {
        assertEquals(listOf(MEDIA, WEATHER, NOTIFICATIONS), WidgetStacks.add(base, 0, NOTIFICATIONS)[0])
    }

    @Test fun `adicionar widget de outra pilha muda ele de lugar`() {
        val r = WidgetStacks.add(base, 0, CLOCK)
        assertEquals(listOf(MEDIA, WEATHER, CLOCK), r[0])
        assertEquals(listOf(SUGGESTIONS), r[1])
    }

    @Test fun `adicionar o unico widget de outra pilha preenche a que esvaziou`() {
        val r = WidgetStacks.add(base, 0, VEHICLE)
        assertEquals(listOf(MEDIA, WEATHER, VEHICLE), r[0])
        assertEquals(1, r[2].size)
        assertEquals(r.flatten().size, r.flatten().distinct().size)
    }

    @Test fun `pilha cheia nao aceita mais`() {
        val full = WidgetStacks.normalize(listOf(WidgetType.entries.take(7), listOf(WidgetType.entries[7]), listOf(WidgetType.entries[8])))
        assertEquals(full, WidgetStacks.add(full, 0, WidgetType.entries[9]))
    }

    @Test fun `remover nunca esvazia a pilha`() {
        assertEquals(listOf(MEDIA), WidgetStacks.remove(base, 0, WEATHER)[0])
        assertEquals(listOf(VEHICLE), WidgetStacks.remove(base, 2, VEHICLE)[2])
    }

    @Test fun `mover reordena dentro da pilha`() {
        assertEquals(listOf(WEATHER, MEDIA), WidgetStacks.move(base, 0, 1, 0)[0])
        assertEquals(base, WidgetStacks.move(base, 0, 0, 5))
    }

    @Test fun `trocar por widget de outra pilha troca os dois de lugar`() {
        val r = WidgetStacks.replace(base, 0, 1, CLOCK)
        assertEquals(listOf(MEDIA, CLOCK), r[0])
        assertEquals(listOf(SUGGESTIONS, WEATHER), r[1])
    }

    @Test fun `trocar por widget livre`() {
        assertEquals(listOf(WEATHER_HOURLY, WEATHER), WidgetStacks.replace(base, 0, 0, WEATHER_HOURLY)[0])
    }

    @Test fun `desfazer deixa so o principal`() {
        assertEquals(listOf(MEDIA), WidgetStacks.unstack(base, 0)[0])
    }

    @Test fun `encontra a pilha do widget`() {
        assertEquals(1, WidgetStacks.slotOf(base, CLOCK))
        assertEquals(null, WidgetStacks.slotOf(base, NOTIFICATIONS))
    }
}
