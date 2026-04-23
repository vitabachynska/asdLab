package service;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class NetworkCodec {
    public static void write(DataOutputStream out, String message) throws IOException {
        byte[] bytes = message.getBytes();
        out.writeInt(bytes.length); // Пишемо 4 байти довжини
        out.write(bytes);           // Пишемо саме повідомлення
        out.flush();
    }

    public static String read(DataInputStream in) throws IOException {
        int length = in.readInt();   // Читаємо довжину
        byte[] bytes = new byte[length];
        in.readFully(bytes);         // Читаємо точно стільки байтів, скільки вказано
        return new String(bytes);
    }
}
