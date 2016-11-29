package yaruliy.db;
import yaruliy.algorithm.JoinAlgorithm;
import yaruliy.data.IMDGObject;
import yaruliy.util.Logger;
import java.util.Collection;
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

    public void addObject(IMDGObject object, String regionName){ regions.get(regionName).addObject(object); }
    public IMDGObject getObject(long id, String regionName){ return regions.get(regionName).getObject(id); }
    public Region getRegionByName(String name){ return regions.get(name); }

    public void addCollection(Collection<IMDGObject> collection, String regionName){
        regions.get(regionName).addCollection(collection);
    }

    public JoinResult executeJOIN(String left, String right, JoinAlgorithm algorithm, String field){
        long start = System.currentTimeMillis();
        String joinName = algorithm.getClass().toString().substring(algorithm.getClass().toString().lastIndexOf('.') + 1);
        Logger.log("Start JOIN (" + joinName + ")");
        JoinResult joinResult = algorithm.executeJOIN(getRegionByName(left), getRegionByName(right), field);
        long time = System.currentTimeMillis() - start;
        Logger.log("JOIN (" + joinName + ") time: " + time + " ms.");
        Logger.log("---------------------------------------------------");
        return joinResult;
    }
}