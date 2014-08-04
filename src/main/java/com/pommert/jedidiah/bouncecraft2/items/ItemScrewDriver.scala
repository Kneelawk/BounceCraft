package com.pommert.jedidiah.bouncecraft2.items

import net.minecraft.item.ItemStack
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.world.World
import codechicken.lib.raytracer.RayTracer
import net.minecraft.world.IBlockAccess
import codechicken.multipart.TileMultipart
import codechicken.multipart.TMultiPart
import codechicken.multipart.TMultiPart
import net.minecraft.util.MovingObjectPosition
import codechicken.lib.raytracer.ExtendedMOP
import codechicken.multipart.TMultiPart
import com.pommert.jedidiah.bouncecraft2.fmp.BCMultiPart
import net.minecraftforge.common.util.ForgeDirection

class ItemScrewDriver extends BCItem {

	@Override
	def hasSubTypes = false

	@Override
	override def onItemUseFirst(stack: ItemStack, player: EntityPlayer, world: World, x: Int, y: Int, z: Int, side: Int, hitX: Float, hitY: Float, hitZ: Float): Boolean = {
		if (player.isSneaking()) {
			val hit = RayTracer.retraceBlock(world, player, x, y, z)
			if (hit == null)
				return false

			val tile = getTile(world, x, y, z)
			if (tile == null)
				return false

			val (index, mop) = reduceMOP(hit)

			val part = tile.partList(index)
			if (part.isInstanceOf[BCMultiPart]) {
				val bcpart = part.asInstanceOf[BCMultiPart]
				bcpart.facing = ForgeDirection.VALID_DIRECTIONS((bcpart.facing.ordinal() + 1) % 6)
				true
			}
		}
		false
	}

	def getTile(world: IBlockAccess, x: Int, y: Int, z: Int) = world.getTileEntity(x, y, z) match {
		case t: TileMultipart if !t.partList.isEmpty => t
		case _ => null
	}

	def reduceMOP(hit: MovingObjectPosition): (Int, ExtendedMOP) = {
		val ehit = hit.asInstanceOf[ExtendedMOP]
		val data: (Int, _) = ExtendedMOP.getData(hit)
		return (data._1, new ExtendedMOP(ehit, data._2, ehit.dist))
	}
}