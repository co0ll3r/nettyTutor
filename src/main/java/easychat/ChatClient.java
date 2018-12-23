package easychat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.Console;
import java.io.InputStreamReader;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChatClient {
    private final String host;
    private final int port;

    public static Console console;
/** UI PART */

    /*
    private JButton send;
    private JTextArea incoming_msg;
    private JTextField print_msg;
    private JPanel rootPanel;

    public ChatClient() {
        send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s = print_msg.getText();
                incoming_msg.insert(s + "\n", 0);
                print_msg.setText("");

            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("client_window");
        frame.setContentPane(new client_window().rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
   */
/* UI PART END  */
    public static void main(String[] args) throws Exception {
//        client_window.main(new String[]{""});

        System.out.println("input an ip address");
        String ipHost = "1"; // input.next();
        if ("1".equals(ipHost)) {
            ipHost = "localhost";
            System.out.println("ip = localhost");
        } else if ("2".equals(ipHost)) {
            ipHost = "192.168.1.248";
            System.out.println("ip = 192.168.1.248");
        }
        new ChatClient(ipHost, 8000).run();
    }

    public ChatClient(String host, int port) {
        this.port = port;
        this.host = host;
    }

    public void run() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String message = "\\name";
        try {

            Bootstrap b = new Bootstrap()
                    .group(group)
                    .channel(NioSocketChannel.class)
                    // pipeline below
                    .handler(new ChatClientInitializer());
            Channel channel = b.connect(host, port).sync().channel();

            while (true) {
                if ("\\name".equals(message)) {
                    System.out.println("type your name:");
                    message = in.readLine();
                    channel.writeAndFlush("\\name " + message + "\r\n");
                    continue; // need it?
                }
                System.out.print("You: ");
                message = in.readLine();


//                channel.writeAndFlush(name + ": " + message + "\r\n");
                channel.writeAndFlush(message + "\r\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }

    }
}
