package yaruliy.structure;
import yaruliy.bloom.MurMurHash;
import yaruliy.data.IMDGObject;
import yaruliy.trackstaff.proccess.MProccessManager;
import yaruliy.util.Util;
import java.util.*;
import static yaruliy.util.Util.getNodes;

public class Region {
    private short replicationCount = (short)Util.getProperty("replicationCount");
    private long elementCount = 0;
    private String name;
    private ArrayList<Node> nodes;

    public Region(String name){
        this.name = name;
        this.nodes = getNodes();
        MProccessManager.addProccess(this.getName(), this);
    }

    public void addCollection(Collection<IMDGObject> collection){ collection.forEach(this::addObject); }
    public String getName(){ return this.name; }

    public void addObject(IMDGObject object) {
        object.setID(this.elementCount);
        object.setHashID(this.name);
        int index = getNodeIndex(object.getHashID());
        nodes.get(index).addObject(this.getName(), object);
        for (int i = 0;i < replicationCount - 1; i++){
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
        Set<IMDGObject> result = new HashSet<>();
        for (Node node : nodes) {
            //System.out.println(node);
            node.getPartitions().keySet().stream()
                    .filter(key -> key.contains(this.name))
                    .forEachOrdered(key -> {
                        /*
                        System.out.println("GET All Records from " + this.getName());
                        System.out.println("Node: " + node.getNodeID());
                        for (IMDGObject object: node.getPartitions().get(key).getAllRecords())
                            System.out.println("\t" + object.getHashID());
                        */
                        //result.add();
                        for (IMDGObject object: node.getPartitions().get(key).getAllRecords()){
                            if(!(result.contains(object))) {
                                result.add(object);
                                //System.out.println("\t" + object.getHashID() + " (" + object.getName() + ")");
                            }
                        }
                        //System.out.println("-----------------------------------------------");
                        //result.addAll(node.getPartitions().get(key).getAllRecords());
                    });
        }
        //System.out.println("#####################################################");
        return result;
    }

    public void printRecords(boolean flag){
        if (this.getAllRecords().size() <= 0) System.out.println(this.name + " is Empty.");
        else {
            System.out.println("[----------------" + this.name
                    + "[" + this.getAllRecords().size() + "]"
                    + " Records: " + "---------------------]");
            System.out.println("| ID | HashID     | Name    | SerName    | DCount| Size(bt)|");
            System.out.println("|----|------------|---------|------------|-------|---------|");
            ArrayList<IMDGObject> objects = new ArrayList<>(this.getAllRecords());
            if (flag) Collections.sort(objects);
            for (IMDGObject object : objects){
                System.out.printf("| %-3d| %-10s | %-7s | %-10s |%6d | %7d |\n",
                        object.getID(), object.getHashID(),
                        object.getName(), object.getSerName(),
                        object.getDepCount(), object.calculateSize());
            }
            System.out.println("[----------------------------------------------------------]");
            System.out.println();
        }
    }
}