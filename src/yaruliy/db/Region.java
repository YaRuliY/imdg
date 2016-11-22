package yaruliy.db;
import yaruliy.bloom.MurMurHash;
import yaruliy.data.IMDGObject;
import yaruliy.util.WHProperties;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Region {
    private short nodeCount;
    private short replicationCount;
    private short partitionCount;
    private ArrayList<Node> nodes;
    private String name;

    public Region(String name){
        this.name = name;
        this.nodeCount = WHProperties.getProperty("nodeCount");
        this.replicationCount = WHProperties.getProperty("replicationCount");
        this.partitionCount = WHProperties.getProperty("partitionCount");
        this.nodes = new ArrayList<>();
        for (int i = 0; i < nodeCount; i++) {
            HashMap<String, Partition> partitions = new HashMap<>();
            for (int j = 0; j< partitionCount; j++)
                partitions.put(this.name, new Partition(this));
            nodes.add(new Node(partitions));
        }
    }

    public void addObject(IMDGObject object) {
        int index = getNodeIndex(object.getID());
        nodes.get(index).addObject(this.getName(), object);

        for (int i = 0;i < replicationCount; i++){
            int in = ++index;
            if (in < nodes.size()) {
                nodes.get(in).addObject(this.getName(), object);
            }
            else {
                nodes.get(0).addObject(this.getName(), object);
            }
        }
    }

    public IMDGObject getObject(long id) {
        return nodes.get(getNodeIndex(id)).getObject(this.getName(), id);
    }

    public String getName(){
        return this.name;
    }

    private int getNodeIndex(long id){
        String idAsText = "" + id;
        int hashCode = MurMurHash.hash32(idAsText.getBytes(), idAsText.length());
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