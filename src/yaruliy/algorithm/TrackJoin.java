package yaruliy.algorithm;
import yaruliy.data.IMDGObject;
import yaruliy.structure.JoinResult;
import yaruliy.structure.Region;
import yaruliy.trackstaff.proccess.MProccessManager;
import yaruliy.trackstaff.proccess.TProccess;
import yaruliy.util.Util;
import java.util.Set;
import static yaruliy.util.Util.printNodesContent;
import static yaruliy.util.Util.getValue;

public class TrackJoin extends JoinAlgorithm{
    @Override
    public JoinResult executeJOIN(Region left, Region right, String field) {
        printNodesContent();
        MProccessManager.getProccessByTableName(left.getName()).sendProjection();
        MProccessManager.getProccessByTableName(right.getName()).sendProjection();

        TProccess tProccess = TProccess.getInstance();
        tProccess.printTable();
        tProccess.phaseTwoFour(left.getName(), right.getName());
        tProccess.printTable();

        Set<Integer> nodes = TProccess.getInstance().getNodesForJoin();
        System.out.println("array of nodes: " + nodes);
        JoinResult jr = new JoinResult(this.getClass().toGenericString());
        if (nodes.size() < 1) return jr;
        int nodeReceiver = (int)nodes.toArray()[nodes.toArray().length - 1];
        for (int i = 0; i < nodes.toArray().length; i++)
            Util.transferDataToNode(i, nodeReceiver);

        //printNodesContent();
        System.out.println("nodes.size():"+nodes.size());
        //int end = (int)nodes.toArray()[nodes.size() - 1];
        for (IMDGObject objectL: Util.getNodes().get(nodeReceiver).getPartitions().get(left.getName()).getAllRecords())
            Util.getNodes().get(nodeReceiver).getPartitions().get(right.getName()).getAllRecords()
                    .stream()
                    .filter(objectR -> getValue(field, objectL).equals(getValue(field, objectR)))
                    .forEachOrdered(objectR -> jr.addObjectsCouple(new IMDGObject[]{objectL, objectR}));
        /*if(nodes.size() > 0){
            int end = (int)nodes.toArray()[nodes.size() - 1];
            for (IMDGObject objectL: Util.getNodes().get(end).getPartitions().get(left.getName()).getAllRecords())
                Util.getNodes().get(end).getPartitions().get(right.getName()).getAllRecords()
                        .stream()
                        .filter(objectR -> getValue(field, objectL).equals(getValue(field, objectR)))
                        .forEachOrdered(objectR -> jr.addObjectsCouple(new IMDGObject[]{objectL, objectR}));
        }*/
        return jr;
    }
}