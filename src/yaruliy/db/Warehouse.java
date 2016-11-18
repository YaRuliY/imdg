package yaruliy.db;
import yaruliy.data.SimpleIMDGObject;
import java.util.ArrayList;

public class Warehouse {
    private ArrayList<Region> regions;

    public Warehouse(){
        this.regions = new ArrayList<>();
    }

    public void addObject(String key, SimpleIMDGObject object){

    }

    public SimpleIMDGObject getObject(String key){
        return null;
    }

    public ArrayList<Region> getRegions() {
        return regions;
    }

    public void setRegions(ArrayList<Region> regions) {
        this.regions = regions;
    }
}