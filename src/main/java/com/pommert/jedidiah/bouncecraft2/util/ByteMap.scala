package com.pommert.jedidiah.bouncecraft2.util

import java.util.HashMap
import java.util.Comparator
import java.util.TreeMap

object ByteComparator extends Comparator[Byte] {
	@Override
	def compare(a: Byte, b: Byte): Int = {
		if (a > b) { return 1 }
		else if (a < b) { return -1 }
		else return 0
	}
}

class ByteMap[V] extends TreeMap[Byte, V](ByteComparator) {

	private var largestKey = 0

	@Override
	override def put(key: Byte, value: V): V = {
		if (key > largestKey) {
			largestKey = key
		}
		return super.put(key, value)
	}
}