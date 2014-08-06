package com.pommert.jedidiah.bouncecraft2.fmp.logic

import net.minecraft.entity.Entity
import com.pommert.jedidiah.bouncecraft2.log.BCLog

object PositionableMotionLogic {
	val rotations: Array[(Entity, Double) => Unit] = Array(
		(e: Entity, s: Double) => {
			e.motionY = -s
		},
		(e: Entity, s: Double) => {
			e.motionY = s
		},
		(e: Entity, s: Double) => {
			e.motionZ = -s
		},
		(e: Entity, s: Double) => {
			e.motionZ = s
		},
		(e: Entity, s: Double) => {
			e.motionX = -s
		},
		(e: Entity, s: Double) => {
			e.motionX = s
		})
}