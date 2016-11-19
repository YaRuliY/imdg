package yaruliy.db;
import java.util.LinkedList;

class Node {
    private short partitionCount;
    private short replicationCount;
    private LinkedList<Partition> partitions;
}
