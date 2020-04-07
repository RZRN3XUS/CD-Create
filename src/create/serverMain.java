package create;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;

public class serverMain {
    public static void main(String args[]) throws IOException, UnknownHostException {
        ServerSocket authServer = new ServerSocket(999, 1000, InetAddress.getLocalHost());
        int port = clientNetwork.getRoom();
        Thread server = new ClientHandler2(port);
        server.start();
    }
}
class ClientHandler2 extends Thread{
    int port;
    public ClientHandler2(int port) {
        this.port = port;
    }
    @Override
    public void run(){
        try {
            Server.Server(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
