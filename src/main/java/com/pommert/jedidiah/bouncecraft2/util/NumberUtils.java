package com.pommert.jedidiah.bouncecraft2.util;

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
		if (value < min || value >= max) {
			int quot = (value - min) / size;
			quot -= Math.floor(quot);
			value = (size * quot) + min;
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
}
