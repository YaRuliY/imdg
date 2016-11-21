package yaruliy.db;
import yaruliy.algorithm.JoinAlgorithm;
import yaruliy.algorithm.JoinCondition;
import yaruliy.data.IMDGObject;
import java.util.ArrayList;

public class Warehouse {
    private ArrayList<Region> regions;

    public Warehouse(){
        this.regions = new ArrayList<>();
        this.regions.add(new Region());
    }

    public void addObject(String key, IMDGObject object, Class regionClass){
        getRegion(regionClass).addObject(key, object);
    }

    public IMDGObject getObject(String key, Class regionClass){
        return getRegion(regionClass).getObject(key);
    }

    public Region executeJOIN(Class<Region> left, Class<Region> right, JoinAlgorithm algorithm, JoinCondition condition){
        return algorithm.executeJOIN(left, right, condition);
    }

    private Region getRegion(Class regionClass){
        for (Region region : regions)
            if (region.getClass().equals(regionClass))
                return region;
        return null;
    }
}