package com.afonddream.discard;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Discards any incoming data.
 */
public class DiscardServer {

    private int port;

    public DiscardServer(int port) {
        this.port = port;
    }

    public void run() throws Exception {
        //NioEventLoopGroup is a multithreaded event loop that handles I/O operation.
        //The first one, often called 'boss', accepts an incoming connection.
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //The second one, often called 'worker',
        // handles the traffic of the accepted connection
        // once the boss accepts the connection and registers the accepted connection to the worker.
        EventLoopGroup workerGroup = new NioEventLoopGroup();


        try {
            //ServerBootstrap is a helper class that sets up a server.
            // You can set up the server using a Channel directly.
            // However, please note that this is a tedious process, and you do not need to do that in most cases.
            ServerBootstrap b = new ServerBootstrap();


            b.group(bossGroup, workerGroup)
                    /*
                    Here, we specify to use the NioServerSocketChannel class
                    which is used to instantiate a new Channel to accept incoming connections.
                    */
                    .channel(NioServerSocketChannel.class) // (3)
                    /*
                    The handler specified here will always be evaluated by a newly accepted Channel.
                    The ChannelInitializer is a special handler that is purposed to help a user configure a new Channel.
                    1. 每一个新创建的Channel都会分配给一个ChannelPipeline
                    */
                    .childHandler(new ChannelInitializer<SocketChannel>() { // (4)
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new DiscardServerHandler());
                        }
                    })
                    /*
                    You can also set the parameters which are specific to the Channel implementation.
                    option() is for the NioServerSocketChannel that accepts incoming connections.
                    option主要是针对boss线程组，child主要是针对worker线程组*/
                    .option(ChannelOption.SO_BACKLOG, 128)          // (5)
                    /*
                    childOption() is for the Channels accepted by the parent ServerChannel,
                    which is NioSocketChannel in this case.
                    */
                    .childOption(ChannelOption.SO_KEEPALIVE, true); // (6)

            // Bind and start to accept incoming connections.
            /* You can now call the bind() method as many times as you want (with different bind addresses.*/
            ChannelFuture f = b.bind(port).sync(); // (7)

            // Wait until the server socket is closed.
            // In this example, this does not happen, but you can do that to gracefully
            // shut down your server.
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        int port = 8080;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        }

        new DiscardServer(port).run();
    }
}

