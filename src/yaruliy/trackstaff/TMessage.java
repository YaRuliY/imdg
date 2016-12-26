package yaruliy.trackstaff;

public class TMessage {
    private int nodeIndex;
    private int objectSize;
    private String joinKey;
    private String regionName;

    public TMessage(String joinKey, String regionName, int nodeIndex, int objectSize){
        this.joinKey = joinKey;
        this.regionName = regionName;
        this.nodeIndex = nodeIndex;
        this.objectSize = objectSize;
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

    public int getObjectSize() {
        return objectSize;
    }

    public String toString(){
        return "\tTMessage:" +
                "\tNode[" + nodeIndex + "]" + "\n" +
                "\tObject Size: " + objectSize + "\n" +
                "\tJoin Key: " + joinKey + "\n";
    }
}