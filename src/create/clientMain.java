package create;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

class clientMain {

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        boolean isConnected = false;
        boolean id = false;
        int connectionAttempts = 0;
        while (!(isConnected)) {
            int port = 0;
            try {
                isConnected = true;
                //Greeting and version
                System.out.println("Welcome to the IMIP Client Version 1.3\n");
                System.out.println("To logoff, type 'logoff' in your command prompt.");
                System.out.print("Please enter a username: ");
                String username = sc.next(); //Grabbing username for ID
                username+=sc.nextLine();
                boolean confirmroom = true;
                do {
                    try {
                        System.out.println("Please enter a room ID. Enter 99 to create a new room.");
                        port = sc.nextInt();
                        confirmroom = false;
                    } catch (InputMismatchException e) {
                        System.err.println("Not a valid room ID");
                    }
                } while (confirmroom);
                if (id) {
                    port = clientNetwork.findNewRoom();
                }
                Socket me = new Socket("52.91.76.77", port); //Connecting to Server IP
                DataOutputStream out = new DataOutputStream(me.getOutputStream());
                DataInputStream in = new DataInputStream(me.getInputStream());
                System.out.println("You have been connected to US CENTRAL IMIP Server at " + me.getRemoteSocketAddress());
                out.writeUTF(username + " connected from " + me.getLocalSocketAddress());
                AtomicBoolean detector = new AtomicBoolean(true); //Detector for IOException on 2nd Thread Init to kill program
                String finalUsername = username;
                Thread one = new Thread(() -> {
                    try {
                        Client.send(out, finalUsername); //starting client service to recieve inputs
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
            } catch (ConnectException c) {
                connectionAttempts++;
                if (connectionAttempts == 3) {
                    System.err.println("Could not connect to the Server.");
                    System.exit(0);
                }
            }
        }
    }
}
