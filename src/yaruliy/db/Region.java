package yaruliy.db;
import yaruliy.bloom.MurMurHash;
import yaruliy.data.IMDGObject;
import yaruliy.util.WHProperties;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Region {
    private short nodeCount = WHProperties.getProperty("nodeCount");
    private short replicationCount = WHProperties.getProperty("replicationCount");
    private short partitionCount = WHProperties.getProperty("partitionCount");
    private long elementCount = 0;
    private String name;
    private ArrayList<Node> nodes;

    public Region(String name){
        this.name = name;
        this.nodes = new ArrayList<>();
        for (int i = 0; i < nodeCount; i++) {
            HashMap<String, Partition> partitions = new HashMap<>();
            for (int j = 0; j< partitionCount; j++)
                partitions.put(this.name, new Partition(this));
            nodes.add(new Node(partitions));
        }
    }

    public void addObject(IMDGObject object) {
        object.setID(this.elementCount);
        object.setHashID(this.name);
        int index = getNodeIndex(object.getHashID());
        nodes.get(index).addObject(this.getName(), object);

        for (int i = 0;i < replicationCount; i++){
            int in = ++index;
            if (in < nodes.size())
                nodes.get(in).addObject(this.getName(), object);
            else
                nodes.get(0).addObject(this.getName(), object);
        }
        this.elementCount++;
    }

    public IMDGObject getObject(long id) {
        return nodes.get(getNodeIndex(this.name + "_" + id)).getObject(this.getName(), id);
    }

    public String getName(){
        return this.name;
    }

    private int getNodeIndex(String hashID){
        int hashCode = MurMurHash.hash32(hashID.getBytes(), hashID.length());
        return Math.abs(hashCode) % nodes.size();
    }

    public Set<IMDGObject> getAllRecords(){
        Set<IMDGObject> array = new HashSet<>();
        for (Node node : nodes) {
            for (String key: node.getPartition().keySet()) {
                array.addAll(node.getPartition().get(key).getAllRecords());
            }
        }
        return array;
    }
}