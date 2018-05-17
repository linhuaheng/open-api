package com.wwwarehouse.xdw.openapi.sdk.internal.util.codec;

public abstract interface Decoder {
	public abstract Object decode(Object paramObject)
			throws DecoderException;
}