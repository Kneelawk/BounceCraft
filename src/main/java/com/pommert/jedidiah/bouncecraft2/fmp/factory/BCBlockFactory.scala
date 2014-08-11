package com.pommert.jedidiah.bouncecraft2.fmp.factory

import codechicken.multipart.TMultiPart
import com.pommert.jedidiah.bouncecraft2.fmp.BCMultiBlock

class BCBlockFactory extends IBCPartFactory {
	
	@Override
	def createPart(name: String, client: Boolean): TMultiPart = {
		return new BCMultiBlock(client)
	}
}