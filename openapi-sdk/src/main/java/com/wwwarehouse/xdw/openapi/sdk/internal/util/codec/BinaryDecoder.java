package com.wwwarehouse.xdw.openapi.sdk.internal.util.codec;

public abstract interface BinaryDecoder extends Decoder {
	public abstract byte[] decode(byte[] paramArrayOfByte)
			throws DecoderException;
}
