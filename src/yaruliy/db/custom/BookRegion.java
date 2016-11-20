package yaruliy.db.custom;
import yaruliy.data.IMDGObject;
import yaruliy.db.Node;
import yaruliy.db.Region;
import java.util.ArrayList;

public class BookRegion implements Region {
    private short partitionCount;
    private short replicationCount;
    private ArrayList<Node> nodes;


    public BookRegion(short partitionCount, short replicationCount){
        this.partitionCount = partitionCount;
        this.replicationCount = replicationCount;
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

    @Override
    public void addObject(String key, IMDGObject object) {

    }

    @Override
    public IMDGObject getObject(String key) {
        return null;
    }
}