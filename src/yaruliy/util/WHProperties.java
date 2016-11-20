package yaruliy.util;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class WHProperties {
    private static java.util.Properties properties;
    private static int nodeCount = 0;

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

    public static String getProperty(String property){
        return properties.getProperty(property);
    }

    public static int getNodeCount() {
        return nodeCount;
    }

    public static void increaseNodeCount(){
        nodeCount++;
    }
}