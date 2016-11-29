package yaruliy.algorithm;
import yaruliy.data.IMDGObject;
import yaruliy.db.JoinResult;
import yaruliy.db.Region;
import java.util.HashMap;
import static yaruliy.util.WHUtils.valueGetter;

public class HashJoin extends JoinAlgorithm{
    @Override
    public JoinResult executeJOIN(Region leftRegion, Region rightRegion, String field) {
        HashMap<String, IMDGObject> leftTable = new HashMap<>();
        for (IMDGObject object: leftRegion.getAllRecords()) {
            leftTable.put(valueGetter(field, object), object);
        }

        JoinResult result = new JoinResult();
        for (String key : leftTable.keySet()) {
            rightRegion.getAllRecords()
                    .stream()
                    .filter(rightObject -> key.equals(valueGetter(field, rightObject)))
                    .forEachOrdered(rightObject -> result.addObjectsCouple(
                            new IMDGObject[]{ leftTable.get(key), rightObject }
                    ));
        }
        return result;
    }
}