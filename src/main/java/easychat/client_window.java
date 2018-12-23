package easychat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class client_window {
    private JButton send;
    JTextArea incoming_msg;
    private JTextField print_msg;
    private JPanel rootPanel;
    private JButton connectButton;

    private String message;
    private Channel channel;
    private boolean stayOnline;

    public client_window() {
        send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });
        print_msg.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                   sendMessage();
                }
            }
        });
    }

    public void sendMessage() {
        message = print_msg.getText();
        incoming_msg.append("You: " + message + "\n");
        print_msg.setText("");

        if ("\\name".equals(message)) {
//                        incoming_msg.append("Type your name:\n");
            channel.writeAndFlush("\\name " + message + "\r\n");
            incoming_msg.append("Your name is: " + message + " now\r\n");
        } else if ("\\exit".equals(message)){
            stayOnline =false;
        }

        else {
            channel.writeAndFlush(message + "\r\n");
        }
    }
 /*   public void run(String host, int port) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        String message = "\\name";
        try {

            Bootstrap b = new Bootstrap()
                    .group(group)
                    .channel(NioSocketChannel.class)
                    // pipeline below
                    .handler(new ChatClientInitializer());
            Channel channel = b.connect(host, port).sync().channel();

*//*                if ("\\name".equals(message)) {
                    System.out.println("type your name:");
                    message = in.readLine();
                    channel.writeAndFlush("\\name " + message + "\r\n");
                    continue; // need it?
                }
                System.out.print("You: ");
                message = in.readLine();


//                channel.writeAndFlush(name + ": " + message + "\r\n");
                channel.writeAndFlush(message + "\r\n");*//*
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }

    }*/
/*
    public void runClient(String host, int port) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        message = "\\name";
        try {

            Bootstrap b = new Bootstrap()
                    .group(group)
                    .channel(NioSocketChannel.class)
                    // pipeline below
                    .handler(new ChatClientInitializer());
            channel = b.connect(host, port).sync().channel();

            JFrame frame = new JFrame("client_window");
            frame.setContentPane(new client_window().rootPanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
            main(new String[]{});

            while (true) {
            }

            while (true) {
            {
                if ("\\name".equals(message)) {
                    System.out.println("type your name:");
                    channel.writeAndFlush("\\name " + message + "\r\n");
                    continue; // need it?
                }
                System.out.print("You: ");
//                message = in.readLine();


//                channel.writeAndFlush(name + ": " + message + "\r\n");
                channel.writeAndFlush(message + "\r\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }

    }*/

    public static void main(String[] args) {
        client_window client = new client_window();

        JFrame frame = new JFrame("client_window");
        frame.setContentPane(client.rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        client.speakingToServer(client);
    }

    private void speakingToServer(client_window client) {
        EventLoopGroup group = new NioEventLoopGroup();
        try {

            Bootstrap b = new Bootstrap()
                    .group(group)
                    .channel(NioSocketChannel.class)
                    // pipeline below
                    .handler(new ChatClientInitializer(client));
            client.channel = b.connect("localhost", 8000).sync().channel();
            System.out.println("TRIED");
            while (stayOnline) {}
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }
}
