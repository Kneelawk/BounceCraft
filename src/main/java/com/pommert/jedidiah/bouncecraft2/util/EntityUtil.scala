package com.pommert.jedidiah.bouncecraft2.util

import java.lang.reflect.Method

import com.pommert.jedidiah.bouncecraft2.log.BCLog

import net.minecraft.entity.Entity

object EntityUtil {
	def fall(e: Entity) {
		var clazz: Class[_] = e.getClass()
		var method: Method = null

		var continue = true
		while (continue) {
			try {
				method = clazz.getDeclaredMethod("fall", classOf[Float])
				continue = false
			} catch {
				case nsme: NoSuchMethodException => {
					if (clazz.isAssignableFrom(classOf[Entity])) {
						continue = false
					}
					clazz = clazz.getSuperclass()
				}
				case t: Throwable => {
					BCLog.warn("Unable to fall entity", t)
					return
				}
			}
		}

		if (method == null) {
			BCLog.warn("Unable to find method fall for entity: " + e.getClass().getName())
			return
		}

		try {
			method.setAccessible(true)
			method.invoke(e, e.fallDistance.asInstanceOf[Object])
			e.fallDistance = 0
		} catch {
			case t: Throwable => {
				BCLog.warn("Unable to fall entity", t)
			}
		}
	}
}