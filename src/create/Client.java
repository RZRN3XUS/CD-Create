package create;

import java.util.*;
import java.net.*;
import java.io.*;

public class Client {
    public static void main(String args[]) throws IOException {
        Scanner sc = new Scanner(System.in);
        try {
            Socket me = new Socket("192.168.2.229", 50);
            System.out.println("Connected to server at " + me.getRemoteSocketAddress());
            System.out.println("Welcome to the IMIP Client Version 1.1\n");
            System.out.println("To logoff, type 'logoff' in your command prompt.");
            System.out.print("Please enter a username: ");
            String username = sc.next();
            DataOutputStream out = new DataOutputStream(me.getOutputStream());
            DataInputStream in = new DataInputStream(me.getInputStream());
            System.out.println("You have been connected to US CENTRAL IMIP Server at " + me.getRemoteSocketAddress());
            out.writeUTF(username + " connected from " + me.getLocalSocketAddress());
            echoClient.echo(in);
            while (true) {
                String input = sc.next();
                if (!(input.equals(null)))
                    out.writeUTF(input);
                if (input.equals("logoff"))
                    me.close();
                    System.exit(0);
            }
        } catch (java.net.ConnectException e){
            e.printStackTrace();
        }
    }
}
