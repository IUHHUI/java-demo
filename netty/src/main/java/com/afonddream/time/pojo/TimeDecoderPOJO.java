package com.afonddream.time.pojo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * ByteToMessageDecoder is an implementation of ChannelInboundHandler
 * which makes it easy to deal with the fragmentation issue.
 */
public class TimeDecoderPOJO extends ByteToMessageDecoder { // (1)

    /**
     * ByteToMessageDecoder calls the decode() method with an internally maintained cumulative buffer
     * whenever new data is received.
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) { // (2)
        if (in.readableBytes() < 4) {
            return;
        }

        out.add(new UnixTime(in.readUnsignedInt()));
    }
}
/*
// ReplayingDecoder which simplifies the decoder even more.
public class TimeDecoder extends ReplayingDecoder<Void> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        out.add(in.readBytes(4));
    }
}
*/

