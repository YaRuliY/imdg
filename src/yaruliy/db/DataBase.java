package yaruliy.db;
import yaruliy.data.SimpleIMDGObject;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

public class DataBase {
    private short partitionCount;
    private short replicationCount;
    private ArrayList<Node> nodes;
    private Properties properties;

    public DataBase(){
        initProperties();
        this.partitionCount = Short.parseShort(properties.getProperty("partitionCount"));
        this.replicationCount = Short.parseShort(properties.getProperty("replicationCount"));
        nodes = new ArrayList<>(Integer.parseInt(properties.getProperty("nodeCount")));
    }

    private void initProperties() {
        InputStream input = null;
        properties = new Properties();
        try {
            input = new FileInputStream("imdg.properties");
            this.properties.load(input);
        }
        catch (IOException ex) { ex.printStackTrace(); }
        finally {
            if (input != null) {
                try { input.close(); }
                catch (IOException e) { e.printStackTrace(); }
            }
        }
    }

    public short getPartitionCount() {
        return partitionCount;
    }

    public short getReplicationCount() {
        return replicationCount;
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public void addObject(String key, SimpleIMDGObject object){

    }

    public SimpleIMDGObject getObject(String key){
        return null;
    }
}