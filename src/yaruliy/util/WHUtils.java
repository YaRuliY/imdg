package yaruliy.util;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class WHUtils {
    private static java.util.Properties properties;

    static {
        InputStream input = null;
        properties = new java.util.Properties();
        try {
            input = new FileInputStream("resources/imdg.properties");
            properties.load(input);
        }
        catch (IOException ex) { ex.printStackTrace(); }
        finally {
            if (input != null) { try { input.close(); }
            catch (IOException e) { e.printStackTrace(); }}
        }
    }

    public static int calculateStringSize(String s){
        int size = 12 + (s.length()*2);
        int diff = 8 - (size % 8);
        size = size + diff + 24;
        return size;
    }

    public static byte getProperty(String property){ return Byte.parseByte(properties.getProperty(property)); }
}