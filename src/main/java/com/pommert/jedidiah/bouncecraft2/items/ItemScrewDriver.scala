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
		// FIXME detect shift-clicks on server-side
		BCLog.debug("Use Screw Driver, player is sneaking: " + player.isSneaking() + ", " + (if (player.worldObj.isRemote) "Client Side" else "Server Side"))
		// causes client to server desync
		//if (player.isSneaking()) {
		//	return world.getBlock(x, y, z).onBlockActivated(world, x, y, z, player, side, hitX, hitY, hitZ)
		//}
		false
	}
}