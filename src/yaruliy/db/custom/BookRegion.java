package yaruliy.db.custom;
import yaruliy.data.IMDGObject;
import yaruliy.db.Node;
import yaruliy.db.Region;
import yaruliy.util.WHProperties;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class BookRegion implements Region {
    private short nodeCount;
    private short replicationCount;
    private ArrayList<Node> nodes;
    private HashMap<String, ArrayList<Integer>> indexHash;

    public BookRegion(){
        nodeCount = Short.parseShort(WHProperties.getProperty("nodeCount"));
        replicationCount = Short.parseShort(WHProperties.getProperty("replicationCount"));

        this.nodes = new ArrayList<>();
        this.indexHash = new HashMap<>();

        for (int i = 0; i < nodeCount; i++){
            nodes.add(new Node());
        }
    }

    @Override
    public void addObject(String key, IMDGObject object) {
        ArrayList<Integer> mas = new ArrayList<>();
        indexHash.put(key, mas);

        for (int i = 0; i < replicationCount; i++){
            int nodeIndex = getRandom(this.nodeCount - 1);
            System.out.println("Random Node Index in BookRegion: " + nodeIndex);
            if (!nodes.get(nodeIndex).contains(key)){
                nodes.get(nodeIndex).addObject(key, object);
            }
            else System.out.println(key + " is already in node");
            mas.add(nodeIndex);
        }
        indexHash.put(key, mas);
    }

    @Override
    public IMDGObject getObject(String key) {
        return nodes.get(indexHash.get(key).get(getRandom(indexHash.get(key).size() - 1))).getObject(key);
    }

    /*private Node getSpareNode(){
        for (Node node : nodes) {
            if (node.isAvailable()){
                return node;
            }
        }
        return nodes.get(getNodeNumber());
    }*/

    private int getRandom(int size){
        int range = size + 1;
        return new Random().nextInt(range);
    }
}