package yaruliy.db;

public class Node {
    private short partitionCount;
    private short replicationCount;

    public short getPartitionCount() {
        return partitionCount;
    }

    public void setPartitionCount(short partitionCount) {
        this.partitionCount = partitionCount;
    }

    public short getReplicationCount() {
        return replicationCount;
    }

    public void setReplicationCount(short replicationCount) {
        this.replicationCount = replicationCount;
    }
}
