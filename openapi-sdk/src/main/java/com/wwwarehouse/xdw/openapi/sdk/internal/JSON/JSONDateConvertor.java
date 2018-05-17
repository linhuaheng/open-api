package com.wwwarehouse.xdw.openapi.sdk.internal.JSON;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public class JSONDateConvertor
		implements JSON.Convertor {
	private static final Logger LOG = Log.getLogger(JSONDateConvertor.class);
	private boolean _fromJSON;
	DateCache _dateCache;
	SimpleDateFormat _format;

	public JSONDateConvertor() {
		this(false);
	}

	public JSONDateConvertor(boolean fromJSON) {
		this(DateCache.DEFAULT_FORMAT, TimeZone.getTimeZone("GMT"), fromJSON);
	}

	public JSONDateConvertor(String format, TimeZone zone, boolean fromJSON) {
		this._dateCache = new DateCache(format);
		this._dateCache.setTimeZone(zone);
		this._fromJSON = fromJSON;
		this._format = new SimpleDateFormat(format);
		this._format.setTimeZone(zone);
	}

	public JSONDateConvertor(String format, TimeZone zone, boolean fromJSON, Locale locale) {
		this._dateCache = new DateCache(format, locale);
		this._dateCache.setTimeZone(zone);
		this._fromJSON = fromJSON;
		this._format = new SimpleDateFormat(format, new DateFormatSymbols(locale));
		this._format.setTimeZone(zone);
	}

	public Object fromJSON(Map map) {
		if (!this._fromJSON) {
			throw new UnsupportedOperationException();
		}
		try {
			synchronized (this._format) {
				return this._format.parseObject((String) map.get("value"));
			}
		} catch (Exception e) {
			LOG.warn(e);
		}
		return null;
	}

	public void toJSON(Object obj, JSON.Output out) {
		String date = this._dateCache.format((Date) obj);
		if (this._fromJSON) {
			out.addClass(obj.getClass());
			out.add("value", date);
		} else {
			out.add(date);
		}
	}
}
