package easychat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatServerHandler extends SimpleChannelInboundHandler<String> {
    // took executor from the comment to the video
    private static final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        for (Channel channel : channels) {
            channel.writeAndFlush("[СЕРВАК] - " + incoming.remoteAddress() + " присоединился\n");
        }

        channels.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        for (Channel channel : channels) {
            channel.writeAndFlush("[СЕРВАК] - " + incoming.remoteAddress() + " отлетел\n");
        }

        channels.remove(ctx.channel());
    }


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        Channel incoming = channelHandlerContext.channel();
 //       Matcher regExp = Pattern.compile("(^.+:\\s)(.+)\r\n").matcher(s);
//        String incomingMessage = regExp.group(2);
        String incomingMessage = s;
        System.out.println("mes " + incomingMessage);
        // sending a message to everybody, except the sender
        if ("\\list".equals(incomingMessage)) {
            int i = 0;
            StringBuilder userList = new StringBuilder();
            for (Channel channel : channels) {
                userList.append("User ").append(++i).append(" ip = ").append(incoming.remoteAddress()).append("/\n");
            }
            for (Channel channel : channels) {
                channel.writeAndFlush(userList.toString());
            }
        }
        for (Channel channel : channels) {
            if (channel != incoming) {
                channel.writeAndFlush("[" + incoming.remoteAddress() + "]" + s + "\n");
            }
        }
    }
}
