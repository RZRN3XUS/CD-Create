package create;

import java.io.DataInputStream;
import java.io.IOException;

public class echoClient {
    public static void echo(DataInputStream in) throws NullPointerException{
        //echoClient Version 1.0
        while (true) {
            try {
                System.out.println(in.readUTF()); //Reads incoming messages
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
