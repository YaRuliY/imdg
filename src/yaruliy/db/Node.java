package yaruliy.db;
import java.util.LinkedList;

public class Node {
    private short partitionCount;
    private short replicationCount;
    private LinkedList<Partition> partitions;

    public Node(){
        partitions = new LinkedList<>();
    }
}
