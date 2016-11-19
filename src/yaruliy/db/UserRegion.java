package yaruliy.db;
import java.util.ArrayList;

class UserRegion implements Region{
    private short partitionCount;
    private short replicationCount;
    private ArrayList<Node> nodes;


    public UserRegion(){
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