package yaruliy.algorithm;
import yaruliy.bloom.MurMurHash;
import yaruliy.data.IMDGObject;
import yaruliy.db.JoinResult;
import yaruliy.db.Node;
import yaruliy.db.Region;
import yaruliy.util.Util;
import yaruliy.util.trackstaff.TMessage;
import yaruliy.util.trackstaff.TUtil;

public class TrackJoin extends JoinAlgorithm{
    @Override
    public JoinResult executeJOIN(Region left, Region right, String field) {
        for (Node node: Util.getNodes()) {
            for (IMDGObject object: node.getPartitions().get(left.getName()).getAllRecords()) {
                TMessage message = new TMessage(object.getName(), left.getName(), node.getNodeID());
                TUtil.sendMessage(getNodeIndex(object.getName()), message);
            }
            for (IMDGObject object: node.getPartitions().get(right.getName()).getAllRecords()) {
                TMessage message = new TMessage(object.getName(), right.getName(), node.getNodeID());
                TUtil.sendMessage(getNodeIndex(object.getName()), message);
            }
        }
        return null;
    }

    private int getNodeIndex(String objectName){
        int hashCode = MurMurHash.hash32(objectName.getBytes(), objectName.length());
        return Math.abs(hashCode) % Util.getNodes().size();
    }
}
