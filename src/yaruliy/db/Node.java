package yaruliy.db;
import yaruliy.data.IMDGObject;
import java.util.HashMap;

class Node {
    private HashMap<String, Partition> partitions;

    public Node(HashMap<String, Partition> partitions){
        this.partitions = partitions;
    }

    public void addObject(String regionName, IMDGObject object){
        partitions.get(regionName).addObject(object);
    }

    public IMDGObject getObject(String regionName, long id){
        return partitions.get(regionName).getObject(id);
    }

    public HashMap<String, Partition> getPartition(){
        return this.partitions;
    }
}
