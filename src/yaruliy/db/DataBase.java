package yaruliy.db;
import java.util.ArrayList;
import java.util.Properties;

public class DataBase {
    short partitionCount;
    short replicationCount;
    private ArrayList<Node> nodes;
    Properties properties = new Properties();

    public DataBase(){
        initProperties();
        this.partitionCount = Short.parseShort(properties.getProperty("partitionCount"));
        this.replicationCount = Short.parseShort(properties.getProperty("replicationCount"));
        nodes = new ArrayList<>(Integer.parseInt(properties.getProperty("replicationCount")));
    }

    private void initProperties() {

    }
}
