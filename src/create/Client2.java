package create;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

class Client2 {

    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner sc = new Scanner(System.in);
        Socket me = new Socket("52.91.76.77", 50);
        System.out.println("Connected to server at " + me.getRemoteSocketAddress());
        System.out.println("Welcome to the IMIP Client Version 1.2\n");
        System.out.println("To logoff, type 'logoff' in your command prompt.");
        System.out.print("Please enter a username: ");
        String username = sc.next();
        DataOutputStream out = new DataOutputStream(me.getOutputStream());
        DataInputStream in = new DataInputStream(me.getInputStream());
        System.out.println("You have been connected to US CENTRAL IMIP Server at " + me.getRemoteSocketAddress());
        out.writeUTF(username + " connected from " + me.getLocalSocketAddress());
        AtomicBoolean detector = new AtomicBoolean(true);
        Thread one = new Thread(() -> {
            try {
                Client.send(out, username);
            } catch (IOException e) {
                e.printStackTrace();
                detector.set(false);
            }
        });
        if (detector.get()) {
            Thread two = new Thread(() -> echoClient.echo(in));
            one.start();
            two.start();
        }
        else {
            System.err.println("System experienced an error while initializing echoClient.");
            System.exit(00);
        }
    }

}
