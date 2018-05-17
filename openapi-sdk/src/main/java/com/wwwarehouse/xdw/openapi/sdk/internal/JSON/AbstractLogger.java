package com.wwwarehouse.xdw.openapi.sdk.internal.JSON;

abstract class AbstractLogger
		implements Logger {
	public final Logger getLogger(String name) {

		if (isBlank(name)) {
			return this;
		}
		String basename = getName();
		String fullname = basename + "." + name;
		Logger logger = (Logger) Log.getLoggers().get(fullname);
		if (logger == null) {
			Logger newlog = newLogger(fullname);
			logger = (Logger) Log.getMutableLoggers().putIfAbsent(fullname, newlog);
			if (logger == null) {
				logger = newlog;
			}
		}
		return logger;
	}

	protected abstract Logger newLogger(String paramString);

	private static boolean isBlank(String name) {
		if (name == null) {
			return true;
		}
		int size = name.length();
		for (int i = 0; i < size; i++) {
			char c = name.charAt(i);
			if (!Character.isWhitespace(c)) {
				return false;
			}
		}
		return true;
	}
}
