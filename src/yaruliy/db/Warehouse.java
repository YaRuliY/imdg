package yaruliy.db;
import yaruliy.data.IMDGObject;

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
        this.regions = new ArrayList<>();
    }

    public void addObject(String key, IMDGObject object){

    }

    public IMDGObject getObject(String key){
        return null;
    }

    public ArrayList<Region> getRegions() {
        return regions;
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
}