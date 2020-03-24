package create;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
public class clientNetwork {
    //Version 1.0
    public static ArrayList<Socket> clients = new ArrayList<Socket>(0); //New array for all client sockets
    public static void addClient(Socket client) { //Adds clients to array
        clients.add(client);
    }
    public static int getIndex(Socket origin) { //Returns array index of Socket
        for (int x = 0; x < clients.size(); x++) {
            if (clients.get(x) == origin)
                return x;
        }
        return 0;
    }
    public static void broadcast(String message, int origin) throws IOException { //Sends message to all clients
        for (int x = 0; x < clients.size(); x++) {
            DataOutputStream os = new DataOutputStream(clients.get(x).getOutputStream());
            if (x != origin)
                os.writeUTF(message);
        }
    }
    public static void update(int leavingIndex) { //Removes client from array
        clients.remove(leavingIndex);
    }
}
