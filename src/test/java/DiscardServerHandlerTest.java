import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DiscardServerHandlerTest {

    @Test
    void channelRead(String[] args) throws Exception {
        int port = 8080;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        }
        new DiscardServer(port).run();
    }
}