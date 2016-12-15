package yaruliy.util.trackstaff;

public class TMessage {
    private String joinKey;
    private int nodeIndex;
    private String regionName;

    public TMessage(String joinKey, String regionName, int nodeIndex){
        this.joinKey = joinKey;
        this.regionName = regionName;
        this.nodeIndex = nodeIndex;
    }

    public String getJoinKey() {
        return joinKey;
    }

    public int getNodeIndex() {
        return nodeIndex;
    }

    public String getRegionName() {
        return regionName;
    }
}