package com.pommert.jedidiah.bouncecraft2.items

import codechicken.lib.vec.BlockCoord
import codechicken.lib.vec.Vector3
import codechicken.multipart.MultiPartRegistry
import codechicken.multipart.TItemMultiPart
import codechicken.multipart.TMultiPart
import com.pommert.jedidiah.bouncecraft2.fmp.logic.BCPartLogic
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.world.World
import net.minecraftforge.common.util.ForgeDirection
import com.pommert.jedidiah.bouncecraft2.log.BCLog
import com.pommert.jedidiah.bouncecraft2.fmp.BCMultiPart

class ItemBCMultipart extends BCItem with TItemMultiPart {

	@Override
	def newPart(item: ItemStack, player: EntityPlayer, world: World, pos: BlockCoord, side: Int, vhit: Vector3): TMultiPart = {
		val part = MultiPartRegistry.createPart("bc_multipart", world.isRemote).asInstanceOf[BCMultiPart]
		part.facing = ForgeDirection.VALID_DIRECTIONS(side).getOpposite()
		part.logic = BCPartLogic.newLogic(item.getItemDamage())
		BCLog.info("part.logic: " + part.logic)
		part
	}

	@Override
	def hasSubTypes = true
}