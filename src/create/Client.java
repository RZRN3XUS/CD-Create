package create;

import java.util.*;
import java.io.*;

public class Client {
    //As of v1.1 Client has been converted to clientMain
    //Client v1.2.1
    public static void send(DataOutputStream out, String username) throws IOException {
        while (true) {
            Scanner sc = new Scanner(System.in);
            String input = sc.next();
            input += sc.nextLine();
            if (!(input.equals(null)))
                out.writeUTF("[" + username + "]: " + input);
            else if (input.equals("logoff")) {
                out.writeUTF("[" + username + "] left the server.");
                System.exit(0);
            }
        }
    }
}
