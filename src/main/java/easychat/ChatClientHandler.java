package easychat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ChatClientHandler extends SimpleChannelInboundHandler<String> {


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        // just print message???0_o
//        System.out.print("client handle");
        System.out.println(s);
//        System.out.println("Got chat handler");
    }
}
