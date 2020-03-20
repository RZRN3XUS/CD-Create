package create;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
public class clientNetwork {
    public static ArrayList<Socket> clients = new ArrayList<Socket>(0);
    public static void addClient(Socket client) {
        clients.add(client);
    }
    public static int getIndex(Socket origin) {
        for (int x = 0; x < clients.size(); x++) {
            if (clients.get(x).getLocalSocketAddress() == origin.getLocalSocketAddress())
                return x;
        }
        return 0;
    }
    public static void broadcast(String message, int origin) throws IOException {
        for (int x = 0; x < clients.size(); x++) {
            DataOutputStream os = new DataOutputStream(clients.get(x).getOutputStream());
            if (x != origin)
                os.writeUTF(message);
        }
    }
    public static void update(int leavingIndex) {
        clients.remove(leavingIndex);
    }
}
