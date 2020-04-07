package create;

import java.util.*;
import java.io.*;

public class Client {
    //As of v1.1 Client has been converted to clientMain
    //Client v1.2
    public static void send(DataOutputStream out, String username) throws IOException {
        while (true) {
            Scanner sc = new Scanner(System.in);
            String input = sc.next();
            input += sc.nextLine();
            if (!(input.equals(null)))
                out.writeUTF("[" + username + "]: " + input);
            if (input.equals("logoff")) {
                System.exit(0);
            }
        }
    }
}
