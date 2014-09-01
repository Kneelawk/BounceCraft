package com.pommert.jedidiah.bouncecraft2.util

import java.util.HashMap
import java.util.Comparator
import java.util.TreeMap

object ByteComparator extends Comparator[java.lang.Byte] {
	@Override
	def compare(a: java.lang.Byte, b: java.lang.Byte): Int = {
		if (a > b) { return 1 }
		else if (a < b) { return -1 }
		else return 0
	}
}

class ByteMap[V] extends TreeMap[java.lang.Byte, V](ByteComparator) {

	private var largestKey: java.lang.Byte = 0.byteValue()

	@Override
	override def put(key: java.lang.Byte, value: V): V = {
		if (key > largestKey) {
			largestKey = key
		}
		return super.put(key, value)
	}
}