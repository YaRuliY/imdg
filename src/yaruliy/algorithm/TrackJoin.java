package yaruliy.algorithm;
import yaruliy.data.IMDGObject;
import yaruliy.db.JoinResult;
import yaruliy.db.Region;
import yaruliy.trackstaff.proccess.MProccessManager;
import yaruliy.trackstaff.proccess.TProccess;
import yaruliy.util.Util;
import java.util.Set;
import static yaruliy.util.Util.printNodesContent;
import static yaruliy.util.Util.valueGetter;

public class TrackJoin extends JoinAlgorithm{
    @Override
    public JoinResult executeJOIN(Region left, Region right, String field) {
        printNodesContent();
        MProccessManager.getProccessByTableName(left.getName()).sendProjection();
        MProccessManager.getProccessByTableName(right.getName()).sendProjection();

        TProccess tProccess = TProccess.getInstance();
        tProccess.printTable();
        tProccess.doTransfer(left.getName(), right.getName());
        tProccess.printTable();
        printNodesContent();

        Set<Integer> nodes = TProccess.getInstance().getNodesForJoin();
        System.out.println(nodes);
        for (int i = 0; i < nodes.toArray().length; i++)
            Util.transferDataToNode(i, (int)nodes.toArray()[nodes.toArray().length - 1]);

        int end = (int)nodes.toArray()[nodes.size() - 1];
        JoinResult jr = new JoinResult(this.getClass().toGenericString());
        for (IMDGObject objectL: Util.getNodes().get(end).getPartitions().get(left.getName()).getAllRecords())
            Util.getNodes().get(end).getPartitions().get(right.getName()).getAllRecords()
                    .stream()
                    .filter(objectR -> valueGetter(field, objectL).equals(valueGetter(field, objectR)))
                    .forEachOrdered(objectR -> jr.addObjectsCouple(new IMDGObject[]{objectL, objectR}));
        return jr;
    }
}