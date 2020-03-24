package create;

import java.net.*;
import java.io.*;
import java.time.*;
import java.util.Timer;

public class Server {
    public static void main(String args[]) throws IOException {
        System.out.println("Server is running IMIP Version 1.2");
        System.out.println("Server started and is hosted at: " + InetAddress.getLocalHost());
        ServerSocket Server = new ServerSocket(50, 10, InetAddress.getLocalHost()); //Starting up Server
        while (true)
        {
            Socket connection = null;
            try {
                connection = Server.accept(); //Listening for new connections
                DataInputStream inputStream = new DataInputStream(connection.getInputStream());
                DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
                System.out.println("Client connected @ " + connection.getInetAddress()); //Moving client socket to ClientHandler
                System.out.println("Migrating " + connection.getInetAddress() + " to handler");
                clientNetwork.addClient(connection);
                Thread clients = new ClientHandler(connection, inputStream, outputStream);
                clients.start();
                System.out.println("Successfully migrated " + connection.getInetAddress() + " to handler");
            } catch (Exception error) {
                System.err.println("Failed to migrate " + connection.getInetAddress() + " to handler");
                connection.close();
                error.printStackTrace();
            }
        }
    }
}
class ClientHandler extends Thread
{
    DataInputStream sockin;
    DataOutputStream sockout;
    Socket csock;

    public ClientHandler(Socket csock, DataInputStream sockin, DataOutputStream sockout)
    {
        this.csock = csock;
        this.sockin = sockin;
        this.sockout = sockout; //Designating streams and socket
    }
    @Override
    public void run()
    {
        boolean detector = true; //Detector for errors to stop program
        while (detector) {
            String in = "*(!!)&&null&&(!!)*"; //special null code for debugging
            try {
                in = this.sockin.readUTF();
            } catch (IOException e) {
                detector = false;

            }
            if (!(in.equals("*(!!)&&null&&(!!)*"))) {
                if (in.equals("logoff") || csock.isClosed()) {
                    try {
                        clientNetwork.update(clientNetwork.getIndex(this.csock)); //adding this client to array
                        this.csock.close();
                        this.sockin.close();
                        this.sockout.close();
                    } catch (IOException e) {
                        detector = false;
                    }
                }
                try {
                    clientNetwork.broadcast(in, clientNetwork.getIndex(this.csock)); //sends message to from this to all clients
                } catch (IOException e) {}
            }
        }
        try {
            this.csock.close();
            this.sockin.close();
            this.sockout.close(); //closing all streams for program exit
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
}


