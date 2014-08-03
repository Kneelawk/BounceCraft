package com.pommert.jedidiah.bouncecraft2.log;

import org.apache.logging.log4j.Logger;

public class BCLog {

	private static Logger log;

	public static void init(Logger log) {
		BCLog.log = log;
		info("Intering Bounce Craft pre-init stage...");
	}

	public static void debug(Object o) {
		log.debug(o);
	}

	public static void debug(Object o, Throwable t) {
		log.debug(o, t);
	}

	public static void info(Object o) {
		log.info(o);
	}

	public static void warn(Object o) {
		log.warn(o);
	}

	public static void warn(Object o, Throwable t) {
		log.warn(o, t);
	}

	public static void error(Object o) {
		log.error(o);
	}

	public static void error(Object o, Throwable t) {
		log.error(o, t);
	}

	public static void fatal(Object o) {
		log.fatal(o);
	}

	public static void fatal(Object o, Throwable t) {
		log.fatal(o, t);
	}
}
