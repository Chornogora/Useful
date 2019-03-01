import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

/*to connect to the server socket use next lines:

try {
    Websocket client = new Websocket(new URI(http://localhost:8080/server));
    client.connect();
}catch(URISyntaxException e){
    System.out.println("Cannot create URI");
}
*/

public class Websocket extends WebSocketClient{

    public Websocket(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {

    }

    @Override
    public void onMessage(String s){
        System.out.println(s);
    }

    @Override
    public void onClose(int i, String s, boolean b) {

    }

    @Override
    public void onError(Exception e) {
        System.out.println("Something went wrong on a client: ");
        e.printStackTrace();
    }
}
