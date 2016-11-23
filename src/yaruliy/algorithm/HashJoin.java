package yaruliy.algorithm;
import yaruliy.data.IMDGObject;
import yaruliy.db.JoinResult;
import yaruliy.db.Region;
import java.util.HashMap;

public class HashJoin implements JoinAlgorithm{
    @Override
    public JoinResult executeJOIN(Region leftRegion, Region rightRegion, String field) {
        HashMap<String, IMDGObject> leftTable = new HashMap<>();
        for (IMDGObject object: leftRegion.getAllRecords()) {
            leftTable.put(String.valueOf(object.getID()), object);
        }

        JoinResult result = new JoinResult();
        /*for (String key : leftTable.keySet()) {
            for (IMDGObject rightObject : rightRegion.getAllRecords()) {
                if (key.equals(String.valueOf(rightObject.getID()))){
                    result.addObjects(new IMDGObject[]{leftTable.get(key), rightObject});
                }
            }
        }*/
        for (String key : leftTable.keySet()) {
            rightRegion.getAllRecords()
                    .stream()
                    .filter(rightObject -> key.equals(String.valueOf(rightObject.getID())))
                    .forEachOrdered(rightObject -> result.addObjects(new IMDGObject[]{leftTable.get(key), rightObject}));
        }
        return result;
    }
}