package com.pommert.jedidiah.bouncecraft2.util;

import net.minecraftforge.common.util.ForgeDirection;
import codechicken.lib.vec.Quat;
import codechicken.lib.vec.Vector3;

public class NumberUtils {
	public static int rotate(int value, int min, int max, int direction) {
		value += direction;
		value = mod2(value, min, max);
		return value;
	}

	public static int mod2(int value, int min, int max) {
		int size = max - min;
		if (size == 0)
			throw new IllegalArgumentException(
					"min and max can not have the same value");
		if (size < 0)
			throw new IllegalArgumentException(
					"min can not have a value grater than max");
		if (value >= max) {
			int quot = (int) Math.floor((value - min) / size);
			value = (value - (size * quot)) + min;
		} else if (value < min) {
			int quot = (int) Math.ceil((((double) value) - ((double) max))
					/ ((double) size));
			value = (value - (size * quot)) + min;
		}
		return value;
	}

	public static double rotate(double value, double min, double max,
			double direction) {
		value += direction;
		value = mod2(value, min, max);
		return value;
	}

	public static double mod2(double value, double min, double max) {
		double size = max - min;
		if (size == 0)
			throw new IllegalArgumentException(
					"min and max can not have the same value");
		if (size < 0)
			throw new IllegalArgumentException(
					"min can not have a value grater than max");
		if (value < min || value >= max) {
			double quot = (value - min) / size;
			quot -= Math.floor(quot);
			value = (size * quot) + min;
		}
		return value;
	}

	public static Quat createRotation(ForgeDirection direction,
			double multiplier) {
		return createRotation(direction.ordinal(), multiplier);
	}

	public static Quat createRotation(int direction, double multiplier) {
		switch (direction) {
		case 0:
			return new Quat(multiplier, 0, 1, 0);
		case 1:
			return new Quat(-multiplier, 0, 1, 0);
		case 2:
			return new Quat(multiplier, 0, 0, 1);
		case 3:
			return new Quat(-multiplier, 0, 0, 1);
		case 4:
			return new Quat(multiplier, 1, 0, 0);
		case 5:
			return new Quat(-multiplier, 1, 0, 0);
		default:
			return new Quat(0, 0, 0, 0);
		}
	}
}
