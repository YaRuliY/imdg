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
        Region region1 = new Region("Region1");
        this.regions.put(region.getName(), region);
        this.regions.put(region1.getName(), region1);
    }

    public void addObject(IMDGObject object, String regionName){
        regions.get(regionName).addObject(object);
    }

    public IMDGObject getObject(long id, String regionName){
        return regions.get(regionName).getObject(id);
    }

    public Region executeJOIN(String left, String right, JoinAlgorithm algorithm, JoinCondition condition){
        return algorithm.executeJOIN(getRegionByName(left), getRegionByName(right), condition);
    }

    public Region getRegionByName(String name){
        return regions.get(name);
    }
}