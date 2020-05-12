package create;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

class clientMain {

    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner sc = new Scanner(System.in);
        Socket me;
        try {
            System.out.println("Please enter the Server ip: ");
            Scanner ip = new Scanner(System.in);
            String addr = ip.next();
            try {
                me = new Socket(addr, 50); //Connecting to Server IP
            } catch (ConnectException e) {
                System.err.println("Couldn't connect to server at " + addr);
                me = null;
            }

            System.out.println("Connected to server at " + me.getRemoteSocketAddress());
            //Greeting and version
            System.out.println("Welcome to the IMIP Client Version 1.2\n");
            System.out.println("To logoff, type 'logoff' in your command prompt.");
            System.out.print("Please enter a username: ");
            String username = sc.next();
            //Grabbing username for ID
            DataOutputStream out = new DataOutputStream(me.getOutputStream());
            DataInputStream in = new DataInputStream(me.getInputStream());
            System.out.println("You have been connected to US CENTRAL IMIP Server at " + me.getRemoteSocketAddress());
            out.writeUTF(username + " connected from " + me.getLocalSocketAddress());
            AtomicBoolean detector = new AtomicBoolean(true); //Detector for IOException on 2nd Thread Init to kill program
            Thread one = new Thread(() -> {
                try {
                    Client.send(out, username); //starting client service to recieve inputs
                } catch (IOException e) {
                    e.printStackTrace();
                    detector.set(false); //setting to false to stop program
                }
            });
            if (detector.get()) {
                Thread two = new Thread(() -> echoClient.echo(in)); //Recieving service to read messages
                one.start(); //starting both threads to read/write simultaneously
                two.start();
            }
            else {
                System.err.println("System experienced an error while initializing echoClient."); //Stopping program if IO Error
                System.exit(00);
            }
        } catch (ConnectException e) {
            System.err.println("Couldn't connect to server.");
            System.exit(00);
        }
    }
}