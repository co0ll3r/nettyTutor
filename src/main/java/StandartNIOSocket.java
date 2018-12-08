import java.io.IOException;
import java.nio.channels.*;
import java.util.concurrent.Future;

/*public class StandartNIOSocket {
    void setServer() {
        AsynchronousServerSocketChannel server = null;
        try {
            server = AsynchronousServerSocketChannel.open();
            //server.bind(new InetSocketAddress("127.0.0.1", 4555));
            server.bind(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Future<AsynchronousSocketChannel> acceptFuture = server.accept();
        AsynchronousSocketChannel worker = future.get();

    }

    public void runServer() {
    clientChannel = acceptResult.get();
    if ((clientChannel != null) && (clientChannel.isOpen())) {
        while (true)
            ByteBuffer buffer = ByteBuffer.allocate(32);
            Future<Integer> readResult  = clientChannel.read(buffer);

            // perform other computations

            readResult.get();

            buffer.flip();
            Future<Integer> writeResult = clientChannel.write(buffer);

            // perform other computations

            writeResult.get();
            buffer.clear();
        }
        clientChannel.close();
        serverChannel.close();
    }
}
}*/
