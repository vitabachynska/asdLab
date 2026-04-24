package service;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class NetworkCodec {
    public static void write(DataOutputStream out, String message) throws IOException {
        byte[] bytes = message.getBytes();
        out.writeInt(bytes.length);
        out.write(bytes);
        out.flush();
    }

    public static String read(DataInputStream in) throws IOException {
        int length = in.readInt();
        byte[] bytes = new byte[length];
        in.readFully(bytes);
        return new String(bytes);
    }
}
