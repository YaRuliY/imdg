package yaruliy.trackstaff.proccess;
import yaruliy.db.Region;
import yaruliy.trackstaff.TMessage;
import yaruliy.util.Util;
import java.util.ArrayList;
import java.util.HashMap;

public class MProccess {
    private String region;
    private HashMap<Integer, ArrayList<TMessage>> messages = new HashMap<>();
    public MProccess(Region region){ this.region = region.getName(); }

    public void sendData(int[] nodes, String joinUniKey){
        for (int i = 0; i < nodes.length - 1; i++){
            Util.getNodes().get(i).getPartitions().get(this.region).getAllRecords()
                    .stream()
                    .filter(object -> object.getName().equals(joinUniKey))
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
}