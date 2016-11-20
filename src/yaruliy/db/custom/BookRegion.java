package yaruliy.db.custom;
import yaruliy.data.IMDGObject;
import yaruliy.db.Node;
import yaruliy.db.Region;
import java.util.ArrayList;
import java.util.Random;

public class BookRegion implements Region {
    private short nodeCount = 5;
    private ArrayList<Node> nodes;

    public BookRegion(){
        this.nodes = new ArrayList<>();
        for (int i = 0; i < nodeCount; i++){
            nodes.add(new Node());
        }
    }

    @Override
    public void addObject(String key, IMDGObject object) {
        getSpareNode().addObject(key, object);
    }

    @Override
    public IMDGObject getObject(String key) {
        return nodes.get(0).getObject(key);
    }

    private Node getSpareNode(){
        /*for (Node node : nodes) {
            if (node.isAvailable()){
                return node;
            }
        }*/
        return nodes.get(getNodeNumber());
    }

    private int getNodeNumber(){
        int range = this.nodeCount + 1;
        return new Random().nextInt(range);
    }
}