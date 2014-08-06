package com.pommert.jedidiah.bouncecraft2.items

import scala.util.control.Breaks._
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
import codechicken.lib.vec.BlockCoord
import com.pommert.jedidiah.bouncecraft2.log.BCLog

class ItemScrewDriver extends BCItem {

	@Override
	override def getHasSubtypes = false

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
				var direction = bcpart.facing;

				breakable {
					for (i <- 0 to 5) {
						direction = ForgeDirection.VALID_DIRECTIONS((direction.ordinal() + 1) % 6)

						if (compareDirection(world, new BlockCoord(x, y, z), direction))
							break
					}
				}

				if (direction.equals(bcpart.facing))
					return false

				bcpart.facing = direction;
				true
			}
		}
		false
	}

	def compareDirection(world: World, pos: BlockCoord, direction: ForgeDirection): Boolean = {
		val tmp = TileMultipart.getTile(world, pos)
		if (tmp == null) return true
		val partList = tmp.partList
		for (ct <- partList) {
			if (ct.isInstanceOf[BCMultiPart]) {
				val bcct = ct.asInstanceOf[BCMultiPart]
				if (bcct.facing.equals(direction))
					return false
			}
		}
		true
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