package yaruliy.structure;
import yaruliy.data.IMDGObject;
import java.util.HashMap;

public class Node {
    private HashMap<String, Partition> partitions;
    private int nodeID;
    public IMDGObject getObject(String regionName, long id){ return partitions.get(regionName).getObject(id);}
    public HashMap<String, Partition> getPartitions(){ return this.partitions; }
    public int getNodeID() { return nodeID; }
    public String toString(){ return "Node [" + this.nodeID + "]"; }

    public Node(int id){
        this.partitions = new HashMap<>();
        this.nodeID = id;
    }

    public void addObject(String rName, IMDGObject object){
        if(partitions.containsKey(rName)){
            if(!partitions.get(rName).getAllRecords().contains(object))
                partitions.get(rName).addObject(object);
        }
        else {
            partitions.put(rName, new Partition());
            partitions.get(rName).addObject(object);
        }
    }

    public boolean containsObject(String rName, IMDGObject object) {
        return partitions.containsKey(rName) && partitions.get(rName).getAllRecords().contains(object);
    }

    public void clear() {
        this.partitions = new HashMap<>();
    }
}