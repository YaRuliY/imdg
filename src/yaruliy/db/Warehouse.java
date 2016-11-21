package yaruliy.db;
import yaruliy.algorithm.JoinAlgorithm;
import yaruliy.algorithm.JoinCondition;
import yaruliy.data.IMDGObject;
import java.util.HashMap;

public class Warehouse {
    private HashMap<String, Region> regions;

    public Warehouse(){
        this.regions = new HashMap<>();
        Region region = new Region("Region0");
        this.regions.put(region.getName(), region);
    }

    public void addObject(IMDGObject object, String regionName){
        regions.get(regionName).addObject(object);
    }

    public IMDGObject getObject(long id, String regionName){
        return regions.get(regionName).getObject(id);
    }

    public Region executeJOIN(Class<Region> left, Class<Region> right, JoinAlgorithm algorithm, JoinCondition condition){
        return algorithm.executeJOIN(left, right, condition);
    }
}