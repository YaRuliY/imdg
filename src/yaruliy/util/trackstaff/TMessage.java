package yaruliy.util.trackstaff;

public class TMessage {
    private String joinKey;
    private int[] nodes;
    private String regionName;

    public TMessage(String joinKey, String regionName, int[] nodes){
        this.joinKey = joinKey;
        this.regionName = regionName;
        this.nodes = nodes;
    }
}