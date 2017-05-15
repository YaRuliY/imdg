package yaruliy.structure;
import yaruliy.algorithm.JoinAlgorithm;
import yaruliy.data.IMDGObject;
import yaruliy.util.Logger;
import yaruliy.util.Util;

import java.util.Collection;
import java.util.HashMap;

import static yaruliy.util.Util.formatNum;

public class Warehouse {
    private HashMap<String, Region> regions;

    public Warehouse(){
        this.regions = new HashMap<>();
        Region region = new Region("Region0");
        Region region1 = new Region("Region1");
        this.regions.put(region.getName(), region);
        this.regions.put(region1.getName(), region1);
    }

    //public void addObject(IMDGObject object, String regionName){ regions.get(regionName).addObject(object); }
    //public IMDGObject getObject(long id, String regionName){ return regions.get(regionName).getObject(id); }
    public Region getRegionByName(String name){ return regions.get(name); }

    public void addCollection(Collection<IMDGObject> collection, String regionName){
        Util.addRegionNameSize(regionName, collection.size());
        regions.get(regionName).addCollection(collection);
    }

    public JoinResult executeJOIN(String left, String right, JoinAlgorithm algorithm, String field){
        Util.joinSize = 0;
        String joinName = algorithm.getClass().toString().substring(algorithm.getClass().toString().lastIndexOf('.') + 1);
        Logger.log("###############---JOIN (" + joinName + ") Starts---################\n");
        JoinResult joinResult = algorithm.executeJOIN(getRegionByName(left), getRegionByName(right), field);
        long time = Util.joinSize / Util.getProperty("landwidth") + Util.getProperty("latency");
        Logger.log("JOIN (" + joinName + ") " +
                "time: " + formatNum(time) + " ns. " +
                "Total Size: " + formatNum(Util.joinSize) + "\n");
        Logger.log("###############---JOIN (" + joinName + ") Ends---##################\n\n");
        return joinResult;
    }
}