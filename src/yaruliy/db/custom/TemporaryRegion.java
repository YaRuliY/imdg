package yaruliy.db.custom;
import yaruliy.db.Node;
import yaruliy.db.Region;
import java.util.ArrayList;

public class TemporaryRegion implements Region {
    private short partitionCount;
    private short replicationCount;
    private ArrayList<Node> nodes;


    public TemporaryRegion(){
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