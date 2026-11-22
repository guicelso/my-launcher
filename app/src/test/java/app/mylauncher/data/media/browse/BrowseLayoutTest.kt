package app.mylauncher.data.media.browse

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class BrowseLayoutTest {
    private fun item(id: String, group: String? = null, browsable: Boolean = false, single: ItemStyle? = null) =
        BrowseItem(id, id, browsable = browsable, playable = !browsable, groupTitle = group, singleStyle = single)

    @Test fun contiguousGroupsOnly() {
        val s = BrowseLayout.sections(listOf(item("a", "G1"), item("b", "G1"), item("c", "G2"), item("d", "G1")), null, null)
        assertEquals(listOf("G1", "G2", "G1"), s.map { it.title })
        assertEquals(listOf(2, 1, 1), s.map { it.items.size })
    }

    @Test fun styleFromParentThenRoot() {
        val parent = BrowseItem("p", "p", browsable = true, childBrowsableStyle = ItemStyle.GRID)
        val root = BrowseRoot("r", false, ItemStyle.LIST, ItemStyle.LIST, emptyList())
        val s = BrowseLayout.sections(listOf(item("a", browsable = true), item("b")), parent, root)
        assertEquals(listOf(ItemStyle.GRID, ItemStyle.LIST), s.map { it.style })
        val single = BrowseLayout.sections(listOf(item("x", single = ItemStyle.CATEGORY_GRID)), parent, root)
        assertEquals(ItemStyle.CATEGORY_GRID, single.first().style)
    }

    @Test fun drivingLimitCutsAt21() {
        val s = BrowseLayout.sections((1..30).map { item("i$it", if (it <= 15) "A" else "B") }, null, null)
        val (cut, wasCut) = BrowseLayout.limit(s, BrowseLayout.DRIVING_MAX_ITEMS)
        assertEquals(21, cut.sumOf { it.items.size })
        assertTrue(wasCut)
    }
}
