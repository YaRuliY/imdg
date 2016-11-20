package yaruliy.db;
import yaruliy.bloom.MurMurHash;
import yaruliy.data.IMDGObject;
import yaruliy.util.WHProperties;
import java.util.LinkedList;

public class Node {
    private short partitionCount;
    private short replicationCount;
    private int id;
    private LinkedList<Partition> partitions;

    public Node(){
        this.id = WHProperties.getNodeCount();
        replicationCount = Short.parseShort(WHProperties.getProperty("replicationCount"));
        partitionCount = Short.parseShort(WHProperties.getProperty("partitionCount"));
        partitions = new LinkedList<>();
        for (int i = 0; i < partitionCount; i++)
            partitions.add(new Partition(this));
        WHProperties.increaseNodeCount();
    }

    public void addObject(String key, IMDGObject object){
        for (int i = 0; i < replicationCount; i++){
            int index = MurMurHash.hash32(key.getBytes(), key.length()) / partitions.size();
            partitions.get(index).addObject(key, object);
        }
    }

    public IMDGObject getObject(String key){
        return partitions.get(0).getObject(key);
    }

    public int getID() {
        return id;
    }
}
