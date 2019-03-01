import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.server.ServerEndpoint;
import java.net.InetSocketAddress;
import java.util.HashSet;
import java.util.Set;

//To open the conection you should run next lines:
//WebsocketServer wss = new WebsocketServer();
//wss.start();

@ServerEndpoint(value = "/point")
public class WebsocketServer extends WebSocketServer {
    public static final String hostName = "127.0.0.1";
    public static final int port = 8090;
    private Set<WebSocket> connections;

    public WebsocketServer(){
            super(new InetSocketAddress(hostName, port));
        try {
            connections = new HashSet<>();
        }catch(Exception e){
            System.out.println("wrong");
            e.printStackTrace();
        }
    }

    public void onStart(){
        System.out.println("Socket Server has started");
    }

    @OnOpen
    public void onOpen(WebSocket conn, ClientHandshake handshake){
        connections.add(conn);
        System.out.println("New user: " + conn.getRemoteSocketAddress());
    }

    @OnClose
    public void onClose(WebSocket conn, int code, String reason, boolean remote){
        connections.remove(conn);
        System.out.println("User left: " + conn.getRemoteSocketAddress());
    }

    @Override
    public void onMessage(WebSocket conn, String message){
        for(WebSocket sock : connections){
        	if(!sock.equals(conn))
        		sock.send(message);
        }
    }

    @OnError
    public void onError(WebSocket conn, Exception e){
        System.out.println("Something went wrong: ");
        e.printStackTrace();
    }

    public int getUserAmount(){
        return connections.size();
    }
}
