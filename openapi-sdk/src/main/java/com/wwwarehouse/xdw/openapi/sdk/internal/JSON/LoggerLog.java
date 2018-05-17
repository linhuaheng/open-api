package com.wwwarehouse.xdw.openapi.sdk.internal.JSON;

import java.lang.reflect.Method;

class LoggerLog
		extends AbstractLogger {
	private final Object _logger;
	private final Method _debugMT;
	private final Method _debugMAA;
	private final Method _infoMT;
	private final Method _infoMAA;
	private final Method _warnMT;
	private final Method _warnMAA;
	private final Method _setDebugEnabledE;
	private final Method _getLoggerN;
	private final Method _getName;
	private volatile boolean _debug;

	public LoggerLog(Object logger) {
		try {
			this._logger = logger;
			Class<?> lc = logger.getClass();
			this._debugMT = lc.getMethod("debug", new Class[]{String.class, Throwable.class});
			this._debugMAA = lc.getMethod("debug", new Class[]{String.class, java.lang.Object.class});
			this._infoMT = lc.getMethod("info", new Class[]{String.class, Throwable.class});
			this._infoMAA = lc.getMethod("info", new Class[]{String.class, java.lang.Object.class});
			this._warnMT = lc.getMethod("warn", new Class[]{String.class, Throwable.class});
			this._warnMAA = lc.getMethod("warn", new Class[]{String.class, java.lang.Object.class});
			Method _isDebugEnabled = lc.getMethod("isDebugEnabled", new Class[0]);
			this._setDebugEnabledE = lc.getMethod("setDebugEnabled", new Class[]{Boolean.TYPE});
			this._getLoggerN = lc.getMethod("getLogger", new Class[]{String.class});
			this._getName = lc.getMethod("getName", new Class[0]);
			this._debug = ((Boolean) _isDebugEnabled.invoke(this._logger, new Object[0])).booleanValue();
		} catch (Exception x) {
			throw new IllegalStateException(x);
		}
	}

	@Override
	public String getName() {
		try {
			return (String) this._getName.invoke(this._logger, new Object[0]);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void warn(String msg, Object... args) {
		try {
			this._warnMAA.invoke(this._logger, args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void warn(Throwable thrown) {
		warn("", thrown);
	}

	@Override
	public void warn(String msg, Throwable thrown) {
		try {
			this._warnMT.invoke(this._logger, new Object[]{msg, thrown});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void info(String msg, Object... args) {
		try {
			this._infoMAA.invoke(this._logger, args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void info(Throwable thrown) {
		info("", thrown);
	}

	@Override
	public void info(String msg, Throwable thrown) {
		try {
			this._infoMT.invoke(this._logger, new Object[]{msg, thrown});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean isDebugEnabled() {
		return this._debug;
	}

	@Override
	public void setDebugEnabled(boolean enabled) {
		try {
			this._setDebugEnabledE.invoke(this._logger, new Object[]{Boolean.valueOf(enabled)});
			this._debug = enabled;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void debug(String msg, Object... args) {
		if (!this._debug) {
			return;
		}
		try {
			this._debugMAA.invoke(this._logger, args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void debug(Throwable thrown) {
		debug("", thrown);
	}

	@Override
	public void debug(String msg, Throwable th) {
		if (!this._debug) {
			return;
		}
		try {
			this._debugMT.invoke(this._logger, new Object[]{msg, th});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void ignore(Throwable ignored) {
		if (Log.isIgnored()) {
			warn("IGNORED ", ignored);
		}
	}

	@Override
	protected Logger newLogger(String fullname) {
		try {
			Object logger = this._getLoggerN.invoke(this._logger, new Object[]{fullname});
			return new LoggerLog(logger);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this;
	}
}
