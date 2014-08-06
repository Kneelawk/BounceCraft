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
import net.minecraft.item.Item
import net.minecraft.creativetab.CreativeTabs
import java.util.{ List => JList }
import com.pommert.jedidiah.bouncecraft2.fmp.logic.BCPartLogic.Index
import cpw.mods.fml.relauncher.SideOnly
import cpw.mods.fml.relauncher.Side

class ItemBCMultipart extends BCItem with TItemMultiPart {

	@Override
	def newPart(item: ItemStack, player: EntityPlayer, world: World, pos: BlockCoord, side: Int, vhit: Vector3): TMultiPart = {
		val part = MultiPartRegistry.createPart("bc_multipart", world.isRemote).asInstanceOf[BCMultiPart]
		part.facing = ForgeDirection.VALID_DIRECTIONS(side).getOpposite
		part.setLogic(BCPartLogic.newLogic(item.getItemDamage(), part))
		if (side == 0 || side == 1) {
			part.rotation = (((player.rotationYawHead + 45) % 340) / 90).floor.asInstanceOf[Byte]
		}
		part
	}

	@Override
	override def onItemUse(item: ItemStack, player: EntityPlayer, world: World, x: Int, y: Int, z: Int, s: Int, hitX: Float, hitY: Float, hitZ: Float): Boolean = {
		var side = s
		val pos = new BlockCoord(x, y, z)
		val vhit = new Vector3(hitX, hitY, hitZ)
		val d = getHitDepth(vhit, side)

		def place(): Boolean = {
			def compareDirection(): Boolean = {
				val tmp = TileMultipart.getTile(world, pos)
				if (tmp == null) return true
				val partList = tmp.partList
				for (ct <- partList) {
					if (ct.isInstanceOf[BCMultiPart]) {
						val bcct = ct.asInstanceOf[BCMultiPart]
						if (bcct.facing.equals(ForgeDirection.VALID_DIRECTIONS(side).getOpposite))
							return false
					}
				}
				true
			}

			if (!compareDirection())
				return false

			val part = newPart(item, player, world, pos, side, vhit)

			if (part == null || !TileMultipart.canPlacePart(world, pos, part))
				return false

			if (!world.isRemote)
				TileMultipart.addPart(world, pos, part)
			if (!player.capabilities.isCreativeMode)
				item.stackSize -= 1
			return true
		}

		if (d < 1 && place())
			return true

		if (player.isSneaking()) {
			side = ForgeDirection.OPPOSITES(side)
			if (place())
				return true

			side = ForgeDirection.OPPOSITES(side)
		}

		pos.offset(side)
		return place()
	}

	@Override
	override def getHasSubtypes = true

	@Override
	@SideOnly(Side.CLIENT)
	override def getSubItems(item: Item, ct: CreativeTabs, list$: JList[_]) {
		val list = list$.asInstanceOf[JList[ItemStack]]
		val values = Index.values
		val bcitem = BCItems.items.get("itemBCMultiPart")
		for (i <- 0 to (values.length - 2)) {
			list.add(new ItemStack(bcitem, 1, values(i).getId()))
		}
	}
}