package yaruliy.db;
import yaruliy.algorithm.JoinAlgorithm;
import yaruliy.data.IMDGObject;
import yaruliy.util.Logger;
import yaruliy.util.WHUtils;
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
        String joinName = algorithm.getClass().toString().substring(algorithm.getClass().toString().lastIndexOf('.') + 1);
        long start = System.nanoTime();
        Logger.log("Start JOIN (" + joinName + ")");
        JoinResult joinResult = algorithm.executeJOIN(getRegionByName(left), getRegionByName(right), field);
        long time = System.nanoTime() - start;
        Logger.log("JOIN (" + joinName + ") time: " + time + " ns.");
        Logger.log("---------------------------------------------------");
        return joinResult;
    }

    public void printNodesContent() {
        for (Node node: WHUtils.getNodes()) {
            System.out.println("==============Node[" + node.getNodeID() + "]=BEGIN============");
            for (String regKey: node.getPartition().keySet()){
                System.out.println("-------------------------------");
                node.getPartition().get(regKey).printContent(regKey);
            }
            if (node.getPartition().keySet().size() < 1) System.out.println("Node is empty");
            else System.out.println("-------------------------------");
            System.out.println("==============Node[" + node.getNodeID() + "]=END==============");
            System.out.println("#######################################");
        }
    }

    public void printNodesContent(String name) {
        for (Node node: WHUtils.getNodes()) {
            System.out.println("==============Node[" + node.getNodeID() + "]=BEGIN============");
            for (String regKey: node.getPartition().keySet()){
                System.out.println("-------------------------------");
                node.getPartition().get(regKey).printContent(regKey, name);
            }
            if (node.getPartition().keySet().size() < 1) System.out.println("Node is empty");
            else System.out.println("-------------------------------");
            System.out.println("==============Node[" + node.getNodeID() + "]=END==============");
            System.out.println("#######################################");
        }
    }
}