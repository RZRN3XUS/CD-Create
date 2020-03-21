package create;

import java.net.*;
import java.io.*;
import java.time.*;
import java.util.Timer;

public class Server {
    public static void main(String args[]) throws IOException {
        System.out.println("Server is running IMIP Version 1.1");
        System.out.println("Server started and is hosted at: " + InetAddress.getLocalHost());
        ServerSocket Server = new ServerSocket(50, 10, InetAddress.getLocalHost());
        while (true)
        {
            Socket connection = null;
            try
            {
                connection = Server.accept();
                DataInputStream inputStream = new DataInputStream(connection.getInputStream());
                DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
                System.out.println("Client connected @ " + connection.getInetAddress());
                System.out.println("Migrating " + connection.getInetAddress() + " to handler");
                clientNetwork.addClient(connection);
                Thread clients = new ClientHandler(connection, inputStream, outputStream);
                clients.start();
                System.out.println("Successfully migrated " + connection.getInetAddress() + " to handler");
            }
            catch (Exception error) {
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
        this.sockout = sockout;
    }
    @Override
    public void run()
    {
        boolean run = true;
        while (run = true) {
            String in = "*(!!)&&null&&(!!)*";
            try {
                in = this.sockin.readUTF();
            } catch (IOException e) {
                e.printStackTrace();
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e1){
                    run = false;
                }
            }
            if (!(in.equals("*(!!)&&null&&(!!)*"))) {
                if (in.equals("logoff") || csock.isClosed()) {
                    try {
                        clientNetwork.update(clientNetwork.getIndex(this.csock));
                        this.csock.close();
                        this.sockin.close();
                        this.sockout.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    clientNetwork.broadcast(in, clientNetwork.getIndex(this.csock));
                } catch (IOException e) {}
            }
        }
        try {
            this.csock.close();
            this.sockin.close();
            this.sockout.close();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
}


