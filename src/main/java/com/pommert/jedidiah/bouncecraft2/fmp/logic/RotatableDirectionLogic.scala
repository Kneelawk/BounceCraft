package com.pommert.jedidiah.bouncecraft2.fmp.logic

import net.minecraftforge.common.util.{ ForgeDirection => FD }

object RotatableDirectionLogic {
	val rotations: Array[Array[FD]] = Array(
		Array(FD.SOUTH, FD.WEST, FD.NORTH, FD.EAST),
		Array(FD.SOUTH, FD.WEST, FD.NORTH, FD.EAST),
		Array(FD.UP, FD.EAST, FD.DOWN, FD.WEST),
		Array(FD.UP, FD.WEST, FD.DOWN, FD.EAST),
		Array(FD.UP, FD.NORTH, FD.DOWN, FD.SOUTH),
		Array(FD.UP, FD.SOUTH, FD.DOWN, FD.NORTH))

	def getDirection(dir: FD, rotation: Int): FD = {
		return rotations(dir.ordinal())(rotation)
	}
}