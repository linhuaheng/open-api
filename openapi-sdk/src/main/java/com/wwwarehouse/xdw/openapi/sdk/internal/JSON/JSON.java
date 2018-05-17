package com.wwwarehouse.xdw.openapi.sdk.internal.JSON;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public class JSON {
	static final Logger LOG = Log.getLogger(JSON.class);
	public static final JSON DEFAULT = new JSON();
	private Map<String, Convertor> _convertors = new ConcurrentHashMap();
	private int _stringBufferSize = 1024;

	public int getStringBufferSize() {

		return this._stringBufferSize;
	}

	public void setStringBufferSize(int stringBufferSize) {

		this._stringBufferSize = stringBufferSize;
	}

	public static void registerConvertor(Class forClass, Convertor convertor) {
		DEFAULT.addConvertor(forClass, convertor);
	}

	public static JSON getDefault() {
		return DEFAULT;
	}

	public static String toString(Object object) {
		StringBuilder buffer = new StringBuilder(DEFAULT.getStringBufferSize());
		DEFAULT.append(buffer, object);
		return buffer.toString();
	}

	public static String toString(Map object) {
		StringBuilder buffer = new StringBuilder(DEFAULT.getStringBufferSize());
		DEFAULT.appendMap(buffer, object);
		return buffer.toString();
	}

	public static String toString(Object[] array) {
		StringBuilder buffer = new StringBuilder(DEFAULT.getStringBufferSize());
		DEFAULT.appendArray(buffer, array);
		return buffer.toString();
	}

	public static Object parse(String s) {
		return DEFAULT.parse(new StringSource(s), false);
	}

	public static Object parse(String s, boolean stripOuterComment) {
		return DEFAULT.parse(new StringSource(s), stripOuterComment);
	}

	public static Object parse(Reader in)
			throws IOException {
		return DEFAULT.parse(new ReaderSource(in), false);
	}

	public static Object parse(Reader in, boolean stripOuterComment)
			throws IOException {
		return DEFAULT.parse(new ReaderSource(in), stripOuterComment);
	}

	public String toJSON(Object object) {
		StringBuilder buffer = new StringBuilder(getStringBufferSize());
		append(buffer, object);
		return buffer.toString();
	}

	public Object fromJSON(String json) {
		Source source = new StringSource(json);
		return parse(source);
	}

	public void append(Appendable buffer, Object object) {
		try {
			if (object == null) {
				buffer.append("null");
			} else if ((object instanceof Convertible)) {
				appendJSON(buffer, (Convertible) object);
			} else if ((object instanceof Generator)) {
				appendJSON(buffer, (Generator) object);
			} else if ((object instanceof Map)) {
				appendMap(buffer, (Map) object);
			} else if ((object instanceof Collection)) {
				appendArray(buffer, (Collection) object);
			} else if (object.getClass().isArray()) {
				appendArray(buffer, object);
			} else if ((object instanceof Number)) {
				appendNumber(buffer, (Number) object);
			} else if ((object instanceof Boolean)) {
				appendBoolean(buffer, (Boolean) object);
			} else if ((object instanceof Character)) {
				appendString(buffer, object.toString());
			} else if ((object instanceof String)) {
				appendString(buffer, (String) object);
			} else {
				Convertor convertor = getConvertor(object.getClass());
				if (convertor != null) {
					appendJSON(buffer, convertor, object);
				} else {
					appendString(buffer, object.toString());
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void appendNull(Appendable buffer) {
		try {
			buffer.append("null");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void appendJSON(Appendable buffer, final Convertor convertor, final Object object) {
		appendJSON(buffer, new Convertible() {
			public void fromJSON(Map object) {
			}

			public void toJSON(Output out) {
				convertor.toJSON(object, out);
			}
		});
	}

	public void appendJSON(Appendable buffer, Convertible converter) {
		ConvertableOutput out = new ConvertableOutput(buffer);
		converter.toJSON(out);
		out.complete();
	}

	public void appendJSON(Appendable buffer, Generator generator) {
		generator.addJSON(buffer);
	}

	public void appendMap(Appendable buffer, Map<?, ?> map) {
		try {
			if (map == null) {
				appendNull(buffer);
				return;
			}
			buffer.append('{');
			Iterator<?> iter = map.entrySet().iterator();
			while (iter.hasNext()) {
				Entry<?, ?> entry = (Entry) iter.next();
				QuotedStringTokenizer.quote(buffer, entry.getKey().toString());
				buffer.append(':');
				append(buffer, entry.getValue());
				if (iter.hasNext()) {
					buffer.append(',');
				}
			}
			buffer.append('}');
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void appendArray(Appendable buffer, Collection collection) {
		try {
			if (collection == null) {
				appendNull(buffer);
				return;
			}
			buffer.append('[');
			Iterator iter = collection.iterator();
			boolean first = true;
			while (iter.hasNext()) {
				if (!first) {
					buffer.append(',');
				}
				first = false;
				append(buffer, iter.next());
			}
			buffer.append(']');
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void appendArray(Appendable buffer, Object array) {
		try {
			if (array == null) {
				appendNull(buffer);
				return;
			}
			buffer.append('[');
			int length = Array.getLength(array);
			for (int i = 0; i < length; i++) {
				if (i != 0) {
					buffer.append(',');
				}
				append(buffer, Array.get(array, i));
			}
			buffer.append(']');
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void appendBoolean(Appendable buffer, Boolean b) {
		try {
			if (b == null) {
				appendNull(buffer);
				return;
			}
			buffer.append(b.booleanValue() ? "true" : "false");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void appendNumber(Appendable buffer, Number number) {
		try {
			if (number == null) {
				appendNull(buffer);
				return;
			}
			buffer.append(String.valueOf(number));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void appendString(Appendable buffer, String string) {
		if (string == null) {
			appendNull(buffer);
			return;
		}
		QuotedStringTokenizer.quote(buffer, string);
	}

	protected String toString(char[] buffer, int offset, int length) {
		return new String(buffer, offset, length);
	}

	protected Map<String, Object> newMap() {
		return new HashMap();
	}

	protected Object[] newArray(int size) {
		return new Object[size];
	}

	protected JSON contextForArray() {
		return this;
	}

	protected JSON contextFor(String field) {
		return this;
	}

	protected Object convertTo(Class type, Map map) {
		if ((type != null) && (Convertible.class.isAssignableFrom(type))) {
			try {
				Convertible conv = (Convertible) type.newInstance();
				conv.fromJSON(map);
				return conv;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		Convertor convertor = getConvertor(type);
		if (convertor != null) {
			return convertor.fromJSON(map);
		}
		return map;
	}

	public void addConvertor(Class forClass, Convertor convertor) {
		this._convertors.put(forClass.getName(), convertor);
	}

	protected Convertor getConvertor(Class forClass) {
		Class cls = forClass;
		Convertor convertor = (Convertor) this._convertors.get(cls.getName());
		if ((convertor == null) && (this != DEFAULT)) {
			convertor = DEFAULT.getConvertor(cls);
		}
		while ((convertor == null) && (cls != null) && (cls != Object.class)) {
			Class[] ifs = cls.getInterfaces();
			int i = 0;
			while ((convertor == null) && (ifs != null) && (i < ifs.length)) {
				convertor = (Convertor) this._convertors.get(ifs[(i++)].getName());
			}
			if (convertor == null) {
				cls = cls.getSuperclass();
				convertor = (Convertor) this._convertors.get(cls.getName());
			}
		}
		return convertor;
	}

	public void addConvertorFor(String name, Convertor convertor) {
		this._convertors.put(name, convertor);
	}

	public Convertor getConvertorFor(String name) {
		String clsName = name;
		Convertor convertor = (Convertor) this._convertors.get(clsName);
		if ((convertor == null) && (this != DEFAULT)) {
			convertor = DEFAULT.getConvertorFor(clsName);
		}
		return convertor;
	}

	public Object parse(Source source, boolean stripOuterComment) {
		int comment_state = 0;
		if (!stripOuterComment) {
			return parse(source);
		}
		int strip_state = 1;
		Object o = null;
		while (source.hasNext()) {
			char c = source.peek();
			if (comment_state == 1) {
				switch (c) {
					case '/':
						comment_state = -1;
						break;
					case '*':
						comment_state = 2;
						if (strip_state == 1) {
							comment_state = 0;
							strip_state = 2;
						}
						break;
				}
			} else if (comment_state > 1) {
				switch (c) {
					case '*':
						comment_state = 3;
						break;
					case '/':
						if (comment_state == 3) {
							comment_state = 0;
							if (strip_state == 2) {
								return o;
							}
						} else {
							comment_state = 2;
						}
						break;
					default:
						comment_state = 2;
						break;
				}
			} else if (comment_state < 0) {
				switch (c) {
					case '\n':
					case '\r':
						comment_state = 0;
				}
			} else if (!Character.isWhitespace(c)) {
				if (c == '/') {
					comment_state = 1;
				} else if (c == '*') {
					comment_state = 3;
				} else if (o == null) {
					o = parse(source);
					continue;
				}
			}
			source.next();
		}
		return o;
	}

	public Object parse(Source source) {
		int comment_state = 0;
		while (source.hasNext()) {
			char c = source.peek();
			if (comment_state == 1) {
				switch (c) {
					case '/':
						comment_state = -1;
						break;
					case '*':
						comment_state = 2;
				}
			} else if (comment_state > 1) {
				switch (c) {
					case '*':
						comment_state = 3;
						break;
					case '/':
						if (comment_state == 3) {
							comment_state = 0;
						} else {
							comment_state = 2;
						}
						break;
					default:
						comment_state = 2;
						break;
				}
			} else if (comment_state < 0) {
				switch (c) {
					case '\n':
					case '\r':
						comment_state = 0;
						break;
				}
			} else {
				switch (c) {
					case '{':
						return parseObject(source);
					case '[':
						return parseArray(source);
					case '"':
						return parseString(source);
					case '-':
						return parseNumber(source);
					case 'n':
						complete("null", source);
						return null;
					case 't':
						complete("true", source);
						return Boolean.TRUE;
					case 'f':
						complete("false", source);
						return Boolean.FALSE;
					case 'u':
						complete("undefined", source);
						return null;
					case 'N':
						complete("NaN", source);
						return null;
					case '/':
						comment_state = 1;
						break;
					default:
						if (Character.isDigit(c)) {
							return parseNumber(source);
						}
						if (!Character.isWhitespace(c)) {
							return handleUnknown(source, c);
						}
						break;
				}
			}
			source.next();
		}
		return null;
	}

	protected Object handleUnknown(Source source, char c) {
		throw new IllegalStateException("unknown char '" + c + "'(" + c + ") in " + source);
	}

	protected Object parseObject(Source source) {
		if (source.next() != '{') {
			throw new IllegalStateException();
		}
		Map<String, Object> map = newMap();
		char next = seekTo("\"}", source);
		while (source.hasNext()) {
			if (next == '}') {
				source.next();
				break;
			}
			String name = parseString(source);
			seekTo(':', source);
			source.next();
			Object value = contextFor(name).parse(source);
			map.put(name, value);
			seekTo(",}", source);
			next = source.next();
			if (next == '}') {
				break;
			}
			next = seekTo("\"}", source);
		}
		String classname = (String) map.get("class");
		if (classname != null) {
			try {
				Class c = Loader.loadClass(JSON.class, classname);
				return convertTo(c, map);
			} catch (ClassNotFoundException e) {
				LOG.warn(e);
			}
		}
		return map;
	}

	protected Object parseArray(Source source) {
		if (source.next() != '[') {
			throw new IllegalStateException();
		}
		int size = 0;
		ArrayList list = null;
		Object item = null;
		boolean coma = true;
		while (source.hasNext()) {
			char c = source.peek();
			switch (c) {
				case ']':
					source.next();
					switch (size) {
						case 0:
							return newArray(0);
						case 1:
							Object array = newArray(1);
							Array.set(array, 0, item);
							return array;
					}
					return list.toArray(newArray(list.size()));
				case ',':
					if (coma) {
						throw new IllegalStateException();
					}
					coma = true;
					source.next();
					break;
				default:
					if (Character.isWhitespace(c)) {
						source.next();
					} else {
						coma = false;
						if (size++ == 0) {
							item = contextForArray().parse(source);
						} else if (list == null) {
							list = new ArrayList();
							list.add(item);
							item = contextForArray().parse(source);
							list.add(item);
							item = null;
						} else {
							item = contextForArray().parse(source);
							list.add(item);
							item = null;
						}
					}
					break;
			}
		}
		throw new IllegalStateException("unexpected end of array");
	}

	protected String parseString(Source source) {
		if (source.next() != '"') {
			throw new IllegalStateException();
		}
		boolean escape = false;
		StringBuilder b = null;
		char[] scratch = source.scratchBuffer();
		if (scratch != null) {
			int i = 0;
			while (source.hasNext()) {
				if (i >= scratch.length) {
					b = new StringBuilder(scratch.length * 2);
					b.append(scratch, 0, i);
					break;
				}
				char c = source.next();
				if (escape) {
					escape = false;
					switch (c) {
						case '"':
							scratch[(i++)] = '"';
							break;
						case '\\':
							scratch[(i++)] = '\\';
							break;
						case '/':
							scratch[(i++)] = '/';
							break;
						case 'b':
							scratch[(i++)] = '\b';
							break;
						case 'f':
							scratch[(i++)] = '\f';
							break;
						case 'n':
							scratch[(i++)] = '\n';
							break;
						case 'r':
							scratch[(i++)] = '\r';
							break;
						case 't':
							scratch[(i++)] = '\t';
							break;
						case 'u':
							char uc = (char) ((TypeUtil.convertHexDigit((byte) source.next()) << 12) + (TypeUtil.convertHexDigit((byte) source.next()) << 8) + (TypeUtil.convertHexDigit((byte) source.next()) << 4) + TypeUtil.convertHexDigit((byte) source.next()));
							scratch[(i++)] = uc;
							break;
						default:
							scratch[(i++)] = c;
					}
				} else if (c == '\\') {
					escape = true;
				} else {
					if (c == '"') {
						return toString(scratch, 0, i);
					}
					scratch[(i++)] = c;
				}
			}
			if (b == null) {
				return toString(scratch, 0, i);
			}
		} else {
			b = new StringBuilder(getStringBufferSize());
		}
		StringBuilder builder = b;
		while (source.hasNext()) {
			char c = source.next();
			if (escape) {
				escape = false;
				switch (c) {
					case '"':
						builder.append('"');
						break;
					case '\\':
						builder.append('\\');
						break;
					case '/':
						builder.append('/');
						break;
					case 'b':
						builder.append('\b');
						break;
					case 'f':
						builder.append('\f');
						break;
					case 'n':
						builder.append('\n');
						break;
					case 'r':
						builder.append('\r');
						break;
					case 't':
						builder.append('\t');
						break;
					case 'u':
						char uc = (char) ((TypeUtil.convertHexDigit((byte) source.next()) << 12) + (TypeUtil.convertHexDigit((byte) source.next()) << 8) + (TypeUtil.convertHexDigit((byte) source.next()) << 4) + TypeUtil.convertHexDigit((byte) source.next()));
						builder.append(uc);
						break;
					default:
						builder.append(c);
				}
			} else if (c == '\\') {
				escape = true;
			} else {
				if (c == '"') {
					break;
				}
				builder.append(c);
			}
		}
		return builder.toString();
	}

	public Number parseNumber(Source source) {
		boolean minus = false;
		long number = 0L;
		StringBuilder buffer = null;
		while (source.hasNext()) {
			char c = source.peek();
			switch (c) {
				case '0':
				case '1':
				case '2':
				case '3':
				case '4':
				case '5':
				case '6':
				case '7':
				case '8':
				case '9':
					number = number * 10L + (c - '0');
					source.next();
					break;
				case '+':
				case '-':
					if (number != 0L) {
						throw new IllegalStateException("bad number");
					}
					minus = true;
					source.next();
					break;
				case '.':
				case 'E':
				case 'e':
					buffer = new StringBuilder(16);
					if (minus) {
						buffer.append('-');
					}
					buffer.append(number);
					buffer.append(c);
					source.next();
					break;
				case ',':
				case '/':
				case ':':
				case ';':
				case '<':
				case '=':
				case '>':
				case '?':
				case '@':
				case 'A':
				case 'B':
				case 'C':
				case 'D':
				case 'F':
				case 'G':
				case 'H':
				case 'I':
				case 'J':
				case 'K':
				case 'L':
				case 'M':
				case 'N':
				case 'O':
				case 'P':
				case 'Q':
				case 'R':
				case 'S':
				case 'T':
				case 'U':
				case 'V':
				case 'W':
				case 'X':
				case 'Y':
				case 'Z':
				case '[':
				case '\\':
				case ']':
				case '^':
				case '_':
				case '`':
				case 'a':
				case 'b':
				case 'c':
				case 'd':
				default:
					break;
			}
		}
		if (buffer == null) {
			return Long.valueOf(minus ? -1L * number : number);
		}
		while (source.hasNext()) {
			char c = source.peek();
			switch (c) {
				case '+':
				case '-':
				case '.':
				case '0':
				case '1':
				case '2':
				case '3':
				case '4':
				case '5':
				case '6':
				case '7':
				case '8':
				case '9':
				case 'E':
				case 'e':
					buffer.append(c);
					source.next();
					break;
				case ',':
				case '/':
				case ':':
				case ';':
				case '<':
				case '=':
				case '>':
				case '?':
				case '@':
				case 'A':
				case 'B':
				case 'C':
				case 'D':
				case 'F':
				case 'G':
				case 'H':
				case 'I':
				case 'J':
				case 'K':
				case 'L':
				case 'M':
				case 'N':
				case 'O':
				case 'P':
				case 'Q':
				case 'R':
				case 'S':
				case 'T':
				case 'U':
				case 'V':
				case 'W':
				case 'X':
				case 'Y':
				case 'Z':
				case '[':
				case '\\':
				case ']':
				case '^':
				case '_':
				case '`':
				case 'a':
				case 'b':
				case 'c':
				case 'd':
				default:
			}
		}
		return new Double(buffer.toString());
	}

	protected void seekTo(char seek, Source source) {
		while (source.hasNext()) {
			char c = source.peek();
			if (c == seek) {
				return;
			}
			if (!Character.isWhitespace(c)) {
				throw new IllegalStateException("Unexpected '" + c + " while seeking '" + seek + "'");
			}
			source.next();
		}
		throw new IllegalStateException("Expected '" + seek + "'");
	}

	protected char seekTo(String seek, Source source) {
		while (source.hasNext()) {
			char c = source.peek();
			if (seek.indexOf(c) >= 0) {
				return c;
			}
			if (!Character.isWhitespace(c)) {
				throw new IllegalStateException("Unexpected '" + c + "' while seeking one of '" + seek + "'");
			}
			source.next();
		}
		throw new IllegalStateException("Expected one of '" + seek + "'");
	}

	protected static void complete(String seek, Source source) {
		int i = 0;
		while ((source.hasNext()) && (i < seek.length())) {
			char c = source.next();
			if (c != seek.charAt(i++)) {
				throw new IllegalStateException("Unexpected '" + c + " while seeking  \"" + seek + "\"");
			}
		}
		if (i < seek.length()) {
			throw new IllegalStateException("Expected \"" + seek + "\"");
		}
	}

	private final class ConvertableOutput
			implements Output {
		private final Appendable _buffer;
		char c = '{';

		private ConvertableOutput(Appendable buffer) {
			this._buffer = buffer;
		}

		public void complete() {
			try {
				if (this.c == '{') {
					this._buffer.append("{}");
				} else if (this.c != 0) {
					this._buffer.append("}");
				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		public void add(Object obj) {
			if (this.c == 0) {
				throw new IllegalStateException();
			}
			JSON.this.append(this._buffer, obj);
			this.c = '\000';
		}

		public void addClass(Class type) {
			try {
				if (this.c == 0) {
					throw new IllegalStateException();
				}
				this._buffer.append(this.c);
				this._buffer.append("\"class\":");
				JSON.this.append(this._buffer, type.getName());
				this.c = ',';
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		public void add(String name, Object value) {
			try {
				if (this.c == 0) {
					throw new IllegalStateException();
				}
				this._buffer.append(this.c);
				QuotedStringTokenizer.quote(this._buffer, name);
				this._buffer.append(':');
				JSON.this.append(this._buffer, value);
				this.c = ',';
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		public void add(String name, double value) {
			try {
				if (this.c == 0) {
					throw new IllegalStateException();
				}
				this._buffer.append(this.c);
				QuotedStringTokenizer.quote(this._buffer, name);
				this._buffer.append(':');
				JSON.this.appendNumber(this._buffer, new Double(value));
				this.c = ',';
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		public void add(String name, long value) {
			try {
				if (this.c == 0) {
					throw new IllegalStateException();
				}
				this._buffer.append(this.c);
				QuotedStringTokenizer.quote(this._buffer, name);
				this._buffer.append(':');
				JSON.this.appendNumber(this._buffer, Long.valueOf(value));
				this.c = ',';
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		public void add(String name, boolean value) {
			try {
				if (this.c == 0) {
					throw new IllegalStateException();
				}
				this._buffer.append(this.c);
				QuotedStringTokenizer.quote(this._buffer, name);
				this._buffer.append(':');
				JSON.this.appendBoolean(this._buffer, value ? Boolean.TRUE : Boolean.FALSE);
				this.c = ',';
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public static abstract interface Source {
		public abstract boolean hasNext();

		public abstract char next();

		public abstract char peek();

		public abstract char[] scratchBuffer();
	}

	public static class StringSource
			implements Source {
		private final String string;
		private int index;
		private char[] scratch;

		public StringSource(String s) {
			this.string = s;
		}

		public boolean hasNext() {
			if (this.index < this.string.length()) {
				return true;
			}
			this.scratch = null;
			return false;
		}

		public char next() {
			return this.string.charAt(this.index++);
		}

		public char peek() {
			return this.string.charAt(this.index);
		}

		public String toString() {
			return this.string.substring(0, this.index) + "|||" + this.string.substring(this.index);
		}

		public char[] scratchBuffer() {
			if (this.scratch == null) {
				this.scratch = new char[this.string.length()];
			}
			return this.scratch;
		}
	}

	public static class ReaderSource
			implements Source {
		private Reader _reader;
		private int _next = -1;
		private char[] scratch;

		public ReaderSource(Reader r) {
			this._reader = r;
		}

		public void setReader(Reader reader) {
			this._reader = reader;
			this._next = -1;
		}

		public boolean hasNext() {
			getNext();
			if (this._next < 0) {
				this.scratch = null;
				return false;
			}
			return true;
		}

		public char next() {
			getNext();
			char c = (char) this._next;
			this._next = -1;
			return c;
		}

		public char peek() {
			getNext();
			return (char) this._next;
		}

		private void getNext() {
			if (this._next < 0) {
				try {
					this._next = this._reader.read();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}

		public char[] scratchBuffer() {
			if (this.scratch == null) {
				this.scratch = new char[1024];
			}
			return this.scratch;
		}
	}

	public static abstract interface Output {
		public abstract void addClass(Class paramClass);

		public abstract void add(Object paramObject);

		public abstract void add(String paramString, Object paramObject);

		public abstract void add(String paramString, double paramDouble);

		public abstract void add(String paramString, long paramLong);

		public abstract void add(String paramString, boolean paramBoolean);
	}

	public static abstract interface Convertible {
		public abstract void toJSON(Output paramOutput);

		public abstract void fromJSON(Map paramMap);
	}

	public static abstract interface Convertor {
		public abstract void toJSON(Object paramObject, Output paramOutput);

		public abstract Object fromJSON(Map paramMap);
	}

	public static abstract interface Generator {
		public abstract void addJSON(Appendable paramAppendable);
	}

	public static class Literal
			implements Generator {
		private String _json;

		public Literal(String json) {
			if (JSON.LOG.isDebugEnabled()) {
				JSON.parse(json);
			}
			this._json = json;
		}

		public String toString() {
			return this._json;
		}

		public void addJSON(Appendable buffer) {
			try {
				buffer.append(this._json);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}
}
