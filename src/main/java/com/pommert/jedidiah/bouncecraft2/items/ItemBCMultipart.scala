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
import codechicken.multipart.TileMultipart

class ItemBCMultipart extends BCItem with TItemMultiPart {

	@Override
	def newPart(item: ItemStack, player: EntityPlayer, world: World, pos: BlockCoord, side: Int, vhit: Vector3): TMultiPart = {
		val part = MultiPartRegistry.createPart("bc_multipart", world.isRemote).asInstanceOf[BCMultiPart]
		part.facing = ForgeDirection.VALID_DIRECTIONS(side).getOpposite()
		part.logic = BCPartLogic.newLogic(item.getItemDamage(), part)
		BCLog.info("Player facing: " + player.rotationYawHead)
		if(side == 0 || side == 1){
			part.rotation = (((player.rotationYawHead + 45) % 340) / 90).floor.asInstanceOf[Byte]
		}
		part
	}

	override def onItemUse(item: ItemStack, player: EntityPlayer, world: World, x: Int, y: Int, z: Int, side: Int, hitX: Float, hitY: Float, hitZ: Float): Boolean = {
		val pos = new BlockCoord(x, y, z)
		val vhit = new Vector3(hitX, hitY, hitZ)
		val d = getHitDepth(vhit, side)

		def place(): Boolean = {
			val part = newPart(item, player, world, pos, side, vhit)

			def compareParts(): Boolean = {
				val tmp = TileMultipart.getTile(world, pos)
				if (tmp == null) return true
				val partList = tmp.partList
				for (ct <- partList) {
					if (ct.isInstanceOf[BCMultiPart] && part.isInstanceOf[BCMultiPart]) {
						val bcct = ct.asInstanceOf[BCMultiPart]
						val bcpart = part.asInstanceOf[BCMultiPart]
						if (bcct.facing.equals(bcpart.facing))
							return false
					}
				}
				true
			}

			if (part == null || !TileMultipart.canPlacePart(world, pos, part) || !compareParts())
				return false

			if (!world.isRemote)
				TileMultipart.addPart(world, pos, part)
			if (!player.capabilities.isCreativeMode)
				item.stackSize -= 1
			return true
		}

		if (d < 1 && place())
			return true

		pos.offset(side)
		return place()
	}

	@Override
	def hasSubTypes = true
}