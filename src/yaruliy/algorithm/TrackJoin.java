package yaruliy.algorithm;
import yaruliy.bloom.MurMurHash;
import yaruliy.data.IMDGObject;
import yaruliy.db.JoinResult;
import yaruliy.db.Node;
import yaruliy.db.Region;
import yaruliy.util.Util;
import yaruliy.trackstaff.TMessage;
import yaruliy.trackstaff.proccess.TProccess;
import yaruliy.trackstaff.TTransport;
import static yaruliy.util.Util.printNodesContent;

public class TrackJoin extends JoinAlgorithm{
    @Override
    public JoinResult executeJOIN(Region left, Region right, String field) {
        printNodesContent();
        for (Node node: Util.getNodes()) {
            sendProjections(node, left);
            sendProjections(node, right);
        }

        TProccess tProccess = TProccess.getInstance();
        tProccess.printTable();
        tProccess.doTransfer(left.getName(), right.getName());
        tProccess.getTable().getHash().get("Jenna").get("Region0").calculateMigrationCost();
        tProccess.printTable();

        //printNodesContent();
        return new JoinResult();
    }

    private int getNodeIndex(String objectName){
        int hashCode = MurMurHash.hash32(objectName.getBytes(), objectName.length());
        return Math.abs(hashCode) % Util.getNodes().size();
    }

    private void sendProjections(Node node, Region region){
        for (IMDGObject object: node.getPartitions().get(region.getName()).getAllRecords()) {
            TMessage message = new TMessage(object.getName(), region.getName(), node.getNodeID(), object.calculateSize());
            int i = getNodeIndex(object.getName());
            TTransport.sendMessage(i, message);
        }
    }
}