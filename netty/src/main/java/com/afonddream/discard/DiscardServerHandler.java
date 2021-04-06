package com.afonddream.discard;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

/**
 * Handles a server-side channel.
 * <p>
 * DiscardServerHandler extends ChannelInboundHandlerAdapter, which is an implementation of ChannelInboundHandler.
 * ChannelInboundHandler provides various event handler methods that you can override.
 * For now, it is just enough to extend ChannelInboundHandlerAdapter rather than to implement the handler interface by yourself.
 */
public class DiscardServerHandler extends ChannelInboundHandlerAdapter {


    //
    //This method is called with the received message, whenever new data is received from a client.
    //ByteBuf is a reference-counted object which has to be released explicitly via the release() method.
    //
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        // Discard the received data silently.
        // ((ByteBuf) msg).release();

        //A ChannelHandlerContext object provides various operations that enable you to trigger various I/O events and operations.
        // Here, we invoke write(Object) to write the received message in verbatim.

        //Please note that we did not release the received message unlike we did in the DISCARD example.
        // It is because Netty releases it for you when it is written out to the wire.
        /*
         ctx.write(msg); // (1)
         ctx.flush(); // (2)
         //Alternatively, you could call ctx.writeAndFlush(msg) for brevity.
         */

        /**
         * This inefficient loop can actually be simplified to:
         * System.out.println(in.toString(io.netty.util.CharsetUtil.US_ASCII))
         */
        ByteBuf in = (ByteBuf) msg;
        try {
            while (in.isReadable()) { // (1)
                System.out.print((char) in.readByte());
                System.out.flush();
            }
        } finally {
            ReferenceCountUtil.release(msg); // (2)
        }
    }

    /**
     * The exceptionCaught() event handler method is called with a Throwable when an exception was raised by Netty
     * due to an I/O error or by a handler implementation due to the exception thrown while processing events.
     * In most cases, the caught exception should be logged and its associated channel should be closed here,
     * although the implementation of this method can be different depending on what you want to do to deal with an exceptional situation.
     * For example, you might want to send a response message with an error code before closing the connection.
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
}
