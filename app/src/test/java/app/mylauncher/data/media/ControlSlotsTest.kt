package app.mylauncher.data.media

import app.mylauncher.data.media.ControlSlots.Slot
import org.junit.Assert.assertEquals
import org.junit.Test

class ControlSlotsTest {
    private fun np(prev: Boolean = true, next: Boolean = true, actions: List<MediaAction> = emptyList(), reservePrev: Boolean = false, reserveNext: Boolean = false) =
        NowPlaying("p", "P", "t", "a", canPrevious = prev, canNext = next, customActions = actions, reservePrevious = reservePrev, reserveNext = reserveNext)

    private fun a(id: String) = MediaAction(id, id)

    @Test fun musicWithManyActionsUsesMore() {
        val l = ControlSlots.layout(np(actions = listOf(a("like"), a("shuffle"), a("repeat"), a("radio"), a("add"), a("extra"))))
        assertEquals(Slot.Action(a("like")), l.first)
        assertEquals(Slot.Previous, l.previous)
        assertEquals(Slot.Next, l.next)
        assertEquals(Slot.More, l.last)
        assertEquals(listOf("shuffle", "repeat", "radio", "add"), l.secondary.map { it.id })
    }

    @Test fun podcastActionsTakeSkipSlots() {
        val l = ControlSlots.layout(np(prev = false, next = false, actions = listOf(a("back15"), a("fwd30"), a("speed"))))
        assertEquals(Slot.Action(a("back15")), l.previous)
        assertEquals(Slot.Action(a("fwd30")), l.next)
        assertEquals(Slot.Action(a("speed")), l.first)
        assertEquals(Slot.Empty, l.last)
    }

    @Test fun reservedSlotsStayEmpty() {
        val l = ControlSlots.layout(np(prev = false, next = false, reservePrev = true, reserveNext = true, actions = listOf(a("like"), a("x"))))
        assertEquals(Slot.Empty, l.previous)
        assertEquals(Slot.Empty, l.next)
        assertEquals(Slot.Action(a("like")), l.first)
        assertEquals(Slot.Action(a("x")), l.last)
    }

    @Test fun mergerAddsShuffleAndRepeatWithoutDuplicates() {
        val merged = MediaActionsMerger.merge(listOf(a("Curtir")), shuffle = true, repeat = RepeatMode.OFF, pkg = "p")
        assertEquals(listOf("Curtir", "lumia.shuffle", "lumia.repeat"), merged.map { it.id })
        val own = MediaActionsMerger.merge(listOf(MediaAction("x", "Aleatório")), shuffle = false, repeat = null, pkg = "p")
        assertEquals(listOf("x"), own.map { it.id })
    }
}
