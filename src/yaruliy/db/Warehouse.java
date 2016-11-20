package yaruliy.db;
import yaruliy.algorithm.JoinAlgorithm;
import yaruliy.algorithm.JoinCondition;
import yaruliy.data.IMDGObject;
import yaruliy.db.custom.BookRegion;
import yaruliy.db.custom.TemporaryRegion;
import yaruliy.db.custom.UserRegion;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

public class Warehouse {
    private ArrayList<Region> regions;
    private Properties properties;
    private short partitionCount;
    private short replicationCount;

    public Warehouse(){
        initProperties();
        partitionCount = Short.parseShort(properties.getProperty("partitionCount"));
        replicationCount = Short.parseShort(properties.getProperty("replicationCount"));
        this.regions = new ArrayList<>();
        this.regions.add(new UserRegion(partitionCount, replicationCount));
        this.regions.add(new BookRegion(partitionCount, replicationCount));
    }

    public void addObject(String key, IMDGObject object, Class<Region> regionClass){
        getRegion(regionClass).addObject(key, object);
    }

    public IMDGObject getObject(String key, Class<Region> regionClass){
        return getRegion(regionClass).getObject(key);
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

    public TemporaryRegion executeJOIN(Class<Region> left, Class<Region> right, JoinAlgorithm algorithm, JoinCondition condition){
        return algorithm.executeJOIN(left, right, condition);
    }

    private Region getRegion(Class<Region> regionClass){
        for (Region region : regions) {
            if (region.getClass().equals(regionClass))
                return region;
        }
        return null;
    }
}