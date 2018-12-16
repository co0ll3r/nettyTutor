package easychat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ChatClient {
    private  final  String host;
    private final int port;

    public static void main(String[] args) throws Exception {
        new ChatClient("localhost", 8000).run();
    }

    public static void main2(String[] args) throws Exception{
        new ChatClient("localhost", 8000).run();
    }

    public ChatClient(String host, int port){
        this.port = port;
        this.host = host;
    }

    public void run() throws Exception{
        EventLoopGroup group = new NioEventLoopGroup();
        try{
            Bootstrap b = new Bootstrap()
                    .group(group)
                    .channel(NioSocketChannel.class)
                    // pipeline below
                    .handler(new ChatClientInitializer());
            Channel channel = b.connect(host, port).sync().channel();
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            while(true){
                channel.writeAndFlush(in.readLine() + "\r\n");
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }

    }
}
