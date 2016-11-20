package yaruliy.db;
import yaruliy.algorithm.JoinAlgorithm;
import yaruliy.algorithm.JoinCondition;
import yaruliy.data.IMDGObject;
import yaruliy.db.custom.TemporaryRegion;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

public class Warehouse {
    private ArrayList<Region> regions;
    private Properties properties;

    public Warehouse(){
        initProperties();
        this.regions = new ArrayList<Region>();
    }

    public void addObject(String key, IMDGObject object){

    }

    public IMDGObject getObject(String key){
        return null;
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

    public Region executeJOIN(Class<Region> left, Class<Region> right, JoinAlgorithm algorithm, JoinCondition condition){
        return new TemporaryRegion();
    }
}