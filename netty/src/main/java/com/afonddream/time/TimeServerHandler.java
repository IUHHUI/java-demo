package com.afonddream.time;


import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class TimeServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * As explained, the channelActive() method will be invoked when a connection is established and ready to generate traffic.
     * Let's write a 32-bit integer that represents the current time in this method.
     *
     * @param ctx ctx
     */
    @Override
    public void channelActive(final ChannelHandlerContext ctx) { // (1)
        //Get the current ByteBufAllocator via ChannelHandlerContext.alloc() and allocate a new buffer.
        final ByteBuf time = ctx.alloc().buffer(4); // (2)

        //ByteBuf does not have such a method because it has two pointers;
        // one for read operations and the other for write operations.
        // The writer index increases when you write something to a ByteBuf while the reader index does not change.
        // The reader index and the writer index represents where the message starts and ends respectively.
        //再也不用像java bytebuffer那样读写之后需要flip
        time.writeInt((int) (System.currentTimeMillis() / 1000L + 2208988800L));

        final ChannelFuture f = ctx.writeAndFlush(time); // (3)

        f.addListener((ChannelFutureListener)
                future -> {
                    assert f == future;
                    //Please note that, close() also might not close the connection immediately,
                    // and it returns a ChannelFuture.
                    ctx.close();
                }); // (4)
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}

