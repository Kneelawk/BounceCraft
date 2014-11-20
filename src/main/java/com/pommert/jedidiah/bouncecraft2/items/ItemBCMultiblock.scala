package com.pommert.jedidiah.bouncecraft2.items

import java.util.{ List => JList }
import com.pommert.jedidiah.bouncecraft2.fmp.BCMultiBlock
import com.pommert.jedidiah.bouncecraft2.fmp.logic.BCBlockLogic
import codechicken.lib.vec.BlockCoord
import codechicken.lib.vec.Vector3
import codechicken.multipart.MultiPartRegistry
import codechicken.multipart.TItemMultiPart
import codechicken.multipart.TMultiPart
import codechicken.multipart.TileMultipart
import cpw.mods.fml.relauncher.SideOnly
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.world.World
import cpw.mods.fml.relauncher.Side
import net.minecraftforge.common.util.ForgeDirection

class ItemBCMultiblock extends BCItem with TItemMultiPart {

	@Override
	def newPart(item: ItemStack, player: EntityPlayer, world: World, pos: BlockCoord, side: Int, vhit: Vector3): TMultiPart = {
		val tile = TileMultipart.getTile(world, pos)
		if (tile != null) {
			val partList = tile.partList
			for (ct <- partList) {
				if (ct.isInstanceOf[BCMultiBlock])
					return null
			}
		}

		val part = MultiPartRegistry.createPart("bc_multiblock", world.isRemote).asInstanceOf[BCMultiBlock]

		part.logic = BCBlockLogic.newLogic(item.getItemDamage().asInstanceOf[Byte], part)
		part.dir = part.logic.directionWhenPlaced(ForgeDirection.getOrientation(side))

		return part
	}

	@Override
	override def getHasSubtypes = true

	@Override
	@SideOnly(Side.CLIENT)
	override def getSubItems(item: Item, ct: CreativeTabs, list$: JList[_]) {
		val list = list$.asInstanceOf[JList[ItemStack]]
		val keys = BCBlockLogic.Index.VALUES.keySet().iterator()
		val bcitem = BCItems.items.get("itemBCMultiBlock")
		while (keys.hasNext()) {
			val id = keys.next()
			// TODO remove || true when done testing
			if ((id != BCBlockLogic.Index.NULL_BCBLOCKLOGIC.getId) || true)
				list.add(new ItemStack(bcitem, 1, id.intValue()))
		}
	}
}