package yaruliy.util;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class WHProperties {
    private static java.util.Properties properties;
    private static int nodeCount = 0;
    private static long warehouseSize = 0;

    static {
        InputStream input = null;
        properties = new java.util.Properties();
        try {
            input = new FileInputStream("imdg.properties");
            properties.load(input);
        }
        catch (IOException ex) { ex.printStackTrace(); }
        finally {
            if (input != null) { try { input.close(); }
            catch (IOException e) { e.printStackTrace(); }}
        }
    }

    public static byte getProperty(String property){
        return Byte.parseByte(properties.getProperty(property));
    }

    public static int getNodeCount() {
        return nodeCount;
    }

    public static void increaseNodeCount(){
        nodeCount++;
    }

    public static long getWarehouseSize() {
        return warehouseSize;
    }

    public static void increaseWarehouseSize(){
        warehouseSize++;
    }
}