package com.afonddream.time.pojo;


import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class TimeServerHandlerPOJO extends ChannelInboundHandlerAdapter {

    /**
     * As explained, the channelActive() method will be invoked when a connection is established and ready to generate traffic.
     * Let's write a 32-bit integer that represents the current time in this method.
     *
     * @param ctx ctx
     */
    @Override
    public void channelActive(final ChannelHandlerContext ctx) {
        ChannelFuture f = ctx.writeAndFlush(new UnixTime());
        f.addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}

