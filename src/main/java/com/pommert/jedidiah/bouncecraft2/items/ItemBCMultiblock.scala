package com.pommert.jedidiah.bouncecraft2.items

import codechicken.multipart.TItemMultiPart
import net.minecraft.item.ItemStack
import codechicken.multipart.TMultiPart
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.world.World
import codechicken.lib.vec.BlockCoord
import codechicken.lib.vec.Vector3
import codechicken.multipart.MultiPartRegistry
import codechicken.multipart.TileMultipart
import com.pommert.jedidiah.bouncecraft2.fmp.BCMultiBlock

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

		val part = MultiPartRegistry.createPart("bc_multiblock", world.isRemote)

		return part
	}

	@Override
	override def getHasSubtypes = true
}