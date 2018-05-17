package com.wwwarehouse.xdw.openapi.sdk.internal.util.codec;

public abstract interface BinaryEncoder extends Encoder {
	public abstract byte[] encode(byte[] paramArrayOfByte)
			throws EncoderException;
}