package create;

import java.util.*;
import java.net.*;
import java.io.*;

public class Client {
    public static void main(String args[]) throws IOException {
        Scanner sc = new Scanner(System.in);
        try {
            Socket me = new Socket("10.151.161.240", 50);
            System.out.println("Connected to server at " + me.getRemoteSocketAddress());
            System.out.println("Welcome to the IMIP Client Version 1.0\n");
            System.out.println("To logoff, type 'logoff' in your command prompt.");
            System.out.print("Please enter a username: ");
            String username = sc.nextLine();
            DataOutputStream out = new DataOutputStream(me.getOutputStream());
            DataInputStream in = new DataInputStream(me.getInputStream());
            System.out.println("You have been connected to US CENTRAL IMIP Server at " + me.getRemoteSocketAddress());
            out.writeUTF(username + " connected from " + me.getLocalSocketAddress());
            while (true) {
                String input = sc.nextLine();
                out.writeUTF(input);
                System.out.println(in.readUTF());
            }
        } catch (java.net.ConnectException e){
            e.printStackTrace();
        }
    }
}
