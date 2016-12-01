package yaruliy.db;
import yaruliy.bloom.BloomFilterMD5;
import yaruliy.bloom.MurMurHash;
import yaruliy.data.IMDGObject;
import yaruliy.util.WHUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.Collections;
import java.util.stream.Collectors;
import static yaruliy.util.WHUtils.valueGetter;

public class Region {
    private short nodeCount = WHUtils.getProperty("nodeCount");
    private short replicationCount = WHUtils.getProperty("replicationCount");
    private short partitionCount = WHUtils.getProperty("partitionCount");
    private long elementCount = 0;
    private String name;
    private ArrayList<Node> nodes;

    public Region(String name){
        this.name = name;
        this.nodes = new ArrayList<>();
        for (int i = 0; i < nodeCount; i++) {
            HashMap<String, Partition> partitions = new HashMap<>();
            for (int j = 0; j< partitionCount; j++)
                partitions.put(this.name, new Partition());
            nodes.add(new Node(partitions));
        }
    }

    public void addCollection(Collection<IMDGObject> collection){ collection.forEach(this::addObject); }
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

    public void printRecords(boolean flag){
        if (this.getAllRecords().size() <= 0) System.out.println(this.name + " is Empty.");
        else {
            System.out.println("[----------------" + this.name
                    + "[" + this.getAllRecords().size() + "]"
                    + " Records: " + "----------------------]");
            System.out.println("| ID | HashID     | Name    | SerName    | DCount| Size(bt)|");
            System.out.println("|----|------------|---------|------------|-------|---------|");
            ArrayList<IMDGObject> objects = new ArrayList<>(this.getAllRecords());
            if (flag) Collections.sort(objects);
            for (IMDGObject object : objects) {
                System.out.printf("| %-3d| %-10s | %-7s | %-10s |%6d | %7d |\n",
                        object.getID(), object.getHashID(),
                        object.getName(), object.getSerName(),
                        object.getDepCount(), object.calculateSize());
            }
            System.out.println("[-------------------------------------------------------]");
            System.out.println();
        }
    }

    public String getName(){ return this.name; }

    public BloomFilterMD5<String> writeValuesIntoFilter(BloomFilterMD5<String> bloomFilter, String field){
        for (IMDGObject object: this.getAllRecords()) {
            bloomFilter.add(valueGetter(field, object));
        }
        return bloomFilter;
    }

    public ArrayList<IMDGObject> getFilteredRecords(BloomFilterMD5<String> bloomFilter, String field){
        Set<IMDGObject> result = this.getAllRecords()
                .stream()
                .filter(object -> bloomFilter.contains(valueGetter(field, object)))
                .collect(Collectors.toSet());
        ArrayList<IMDGObject> objects = new ArrayList<>();
        objects.addAll(result);
        return objects;
    }

    public int getRegionSize(){
        int size = 0;
        for (IMDGObject object: this.getAllRecords())
            size = size + object.calculateSize();
        return size;
    }
}