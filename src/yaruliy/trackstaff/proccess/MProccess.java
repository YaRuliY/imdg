package yaruliy.trackstaff.proccess;
import yaruliy.bloom.MurMurHash;
import yaruliy.data.IMDGObject;
import yaruliy.structure.Node;
import yaruliy.structure.Region;
import yaruliy.trackstaff.TMessage;
import yaruliy.trackstaff.TTransport;
//import yaruliy.util.Logger;
import yaruliy.util.Util;
import java.util.ArrayList;
import java.util.HashMap;

import static yaruliy.util.Util.formatNum;

public class MProccess {
    private String region;
    private HashMap<Integer, ArrayList<TMessage>> messages = new HashMap<>();
    public MProccess(Region region){ this.region = region.getName(); }

    public void sendDataToLastNode(int[] nodes, String joinUniqueKey){
        //Logger.log("Migration Starts... ");
        final int[] migrationCount = {0};
        for (int i = 0; i < nodes.length - 1; i++){
            int ID = i;
            Util.getNodes().get(i).getPartitions().get(this.region).getAllRecords().stream()
                    .filter(object -> object.getName().equals(joinUniqueKey))
                    .forEachOrdered(object -> {
                        Util.getNodes().get(nodes[nodes.length - 1]).addObject(this.region, object);
                        if(Util.getNodes().get(nodes[nodes.length - 1]).getPartitions().get(this.region).contains(object.getHashID())){
                            //Logger.log("\tFrom N[" + ID + "] to N[" + nodes[nodes.length - 1] + "] " +
                                    /*"[" + object.getHashID() + "] " + object.getName() + " " +
                                    "(size: " + object.calculateSize() + ")");*/
                            migrationCount[0]++;
                            Util.joinSize = Util.joinSize + object.calculateSize();
                        }
                    });
        }
        //Logger.log("\n\tMigration Count: " + migrationCount[0]);
        //Logger.log("\tMigration Data Cost: " + formatNum(Util.joinSize));
        Util.trackJoinTransferCount = Util.trackJoinTransferCount + migrationCount[0];
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
        //Logger.log("Region[" + this.region +"] send Projection...");
        int size = 0;
        for (Node node: Util.getNodes()){
            for (IMDGObject object: node.getPartitions().get(this.region).getAllRecords()) {
                TMessage message = new TMessage(object.getName(), this.region, node.getNodeID(), object.calculateSize());
                size = size + message.calculateSize();
                TTransport.sendMessage(getNodeIndex(object.getName()), message, this.region);
            }
        }
        Util.joinSize = Util.joinSize + size;
        //Logger.log("\tProjection cost: " + size);
    }

    private int getNodeIndex(String objectName){
        int hashCode = MurMurHash.hash32(objectName.getBytes(), objectName.length());
        return Math.abs(hashCode) % Util.getNodes().size();
    }

    public void sendMMessage(int[] mas){ TTransport.sendNodesToTProccess(mas); }
}