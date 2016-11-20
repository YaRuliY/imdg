package yaruliy.db;
import yaruliy.bloom.MurMurHash;
import yaruliy.data.IMDGObject;
import yaruliy.util.Properties;
import java.util.LinkedList;

public class Node {
    private short partitionCount;
    private short replicationCount;
    private int id;
    private LinkedList<Partition> partitions;

    public Node(){
        this.id = Properties.getNodeCount();
        replicationCount = Short.parseShort(Properties.getProperty("replicationCount"));
        partitionCount = Short.parseShort(Properties.getProperty("partitionCount"));
        partitions = new LinkedList<>();
        for (int i = 0; i < partitionCount; i++)
            partitions.add(new Partition(this));
        Properties.increaseNodeCount();
    }

    public void addObject(String key, IMDGObject object){
        int index = MurMurHash.hash32(key.getBytes(), key.length()) / partitions.size();
        partitions.get(index).addObject(key, object);
        /*for (int i = 0; i < replicationCount; i++){
            partitions.get(index).addObject(key, object);
        }*/
    }

    public int getId() {
        return id;
    }
}
