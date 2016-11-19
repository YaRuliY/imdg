package yaruliy.db;

import java.util.ArrayList;

public class BookRegion implements Region{
    private short partitionCount;
    private short replicationCount;
    private ArrayList<Node> nodes;


    public BookRegion(){
    }

    public short getPartitionCount() {
        return partitionCount;
    }

    public short getReplicationCount() {
        return replicationCount;
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }
}