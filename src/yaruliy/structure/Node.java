package yaruliy.structure;
import yaruliy.data.IMDGObject;
import java.util.HashMap;

public class Node {
    private HashMap<String, Partition> partitions;
    private int nodeID;
    public IMDGObject getObject(String regionName, long id){ return partitions.get(regionName).getObject(id);}
    public HashMap<String, Partition> getPartitions(){ return this.partitions; }
    public int getNodeID() { return nodeID; }

    public Node(int id){
        this.partitions = new HashMap<>();
        this.nodeID = id;
    }

    public void addObject(String regionName, IMDGObject object){
        if(partitions.containsKey(regionName)){
            if (!partitions.get(regionName).getAllRecords().contains(object))
                partitions.get(regionName).addObject(object);
        }
        else {
            partitions.put(regionName, new Partition());
            partitions.get(regionName).addObject(object);
        }
    }
}