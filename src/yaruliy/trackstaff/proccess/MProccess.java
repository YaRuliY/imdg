package yaruliy.trackstaff.proccess;
import yaruliy.bloom.MurMurHash;
import yaruliy.data.IMDGObject;
import yaruliy.db.Node;
import yaruliy.db.Region;
import yaruliy.trackstaff.TMessage;
import yaruliy.trackstaff.TTransport;
import yaruliy.util.Util;
import java.util.ArrayList;
import java.util.HashMap;

public class MProccess {
    private String region;
    private HashMap<Integer, ArrayList<TMessage>> messages = new HashMap<>();
    public MProccess(Region region){ this.region = region.getName(); }

    public void sendDataToLastNode(int[] nodes, String joinUniqueKey){
        for (int i = 0; i < nodes.length - 1; i++){
            Util.getNodes().get(i).getPartitions().get(this.region).getAllRecords()
                    .stream()
                    .filter(object -> object.getName().equals(joinUniqueKey))
                    .forEachOrdered(object -> Util.getNodes().get(nodes[nodes.length - 1]).addObject(this.region, object));
        }
    }

    public void receiveMessage(int node, TMessage message){
        if(this.messages.containsKey(node))
            this.messages.get(node).add(message);
        else {
            this.messages.put(node, new ArrayList<>());
            this.messages.get(node).add(message);
        }
    }

    public void sendProjection(){
        for (Node node: Util.getNodes()){
            for (IMDGObject object: node.getPartitions().get(this.region).getAllRecords()) {
                TMessage message = new TMessage(object.getName(), this.region, node.getNodeID(), object.calculateSize());
                TTransport.sendMessage(getNodeIndex(object.getName()), message, this.region);
            }
        }
    }

    private int getNodeIndex(String objectName){
        int hashCode = MurMurHash.hash32(objectName.getBytes(), objectName.length());
        return Math.abs(hashCode) % Util.getNodes().size();
    }

    public void sendMMessage(int[] mas, String joinUniKey) {
        TTransport.sendNodesToTProccess(mas, joinUniKey);
    }
}