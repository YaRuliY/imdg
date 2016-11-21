package yaruliy.db;
import yaruliy.bloom.MurMurHash;
import yaruliy.data.IMDGObject;
import yaruliy.util.WHProperties;
import java.util.HashMap;
import java.util.LinkedList;

public class Node {
    private int id;
    private LinkedList<Partition> partitions;
    private HashMap<String, Integer> partHash;

    public Node(){
        partHash = new HashMap<>();
        this.id = WHProperties.getNodeCount();
        short partitionCount = Short.parseShort(WHProperties.getProperty("partitionCount"));
        partitions = new LinkedList<>();
        for (int i = 0; i < partitionCount; i++)
            partitions.add(new Partition(this));
        WHProperties.increaseNodeCount();
    }

    public void addObject(String key, IMDGObject object){
        int hashCode = MurMurHash.hash32(key.getBytes(), key.length());
        int index = Math.abs(hashCode) % partitions.size();
        System.out.println("Index: " + index);
        System.out.println("HashCode: " + hashCode + " Key: " + key);
        System.out.println();
        partHash.put(key, index);
        partitions.get(index).addObject(key, object);
    }

    public IMDGObject getObject(String key){
        return partitions.get(partHash.get(key)).getObject(key);
    }

    public int getID() {
        return id;
    }
}
