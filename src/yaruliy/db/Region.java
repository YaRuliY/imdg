package yaruliy.db;
import yaruliy.bloom.MurMurHash;
import yaruliy.data.IMDGObject;
import yaruliy.util.WHProperties;
import java.util.ArrayList;
import java.util.HashMap;

public class Region {
    private short nodeCount;
    private short replicationCount;
    private short partitionCount;
    private ArrayList<Node> nodes;
    private String name;

    public Region(String name){
        this.name = name;
        nodeCount = Short.parseShort(WHProperties.getProperty("nodeCount"));
        replicationCount = Short.parseShort(WHProperties.getProperty("replicationCount"));
        partitionCount = Short.parseShort(WHProperties.getProperty("partitionCount"));
        this.nodes = new ArrayList<>();
        for (int i = 0; i < nodeCount; i++) {
            HashMap<String, Partition> partitions = new HashMap<>();
            for (int j = 0; j< partitionCount; j++)
                partitions.put(this.name, new Partition(this));
            nodes.add(new Node(partitions));
        }
    }

    public void addObject(IMDGObject object) {
        String idAsText = "" + object.getID();
        int hashCode = MurMurHash.hash32(idAsText.getBytes(), idAsText.length());
        int index = Math.abs(hashCode) % nodes.size();
        System.out.println("INDEX: " + index);
        nodes.get(index).addObject(this.getName(), object);

        for (int i = 0;i < replicationCount; i++){
            if (index + 1 < nodes.size())
                nodes.get(index + 1).addObject(this.getName(), object);
            else nodes.get(index - 1).addObject(this.getName(), object);
        }
    }

    public IMDGObject getObject(long id) {
        String idAsText = "" + id;
        int hashCode = MurMurHash.hash32(idAsText.getBytes(), idAsText.length());
        int index = Math.abs(hashCode) % nodes.size();
        return nodes.get(index).getObject(this.getName(), id);
    }

    public String getName(){
        return this.name;
    }
}