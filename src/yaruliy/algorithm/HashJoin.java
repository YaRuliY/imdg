package yaruliy.algorithm;
import yaruliy.data.IMDGObject;
import yaruliy.db.Region;
import java.util.HashMap;

public class HashJoin implements JoinAlgorithm{
    @Override
    public Region executeJOIN(Region leftRegion, Region rightRegion, JoinCondition condition) {
        HashMap<String, IMDGObject> leftTable = new HashMap<>();
        for (IMDGObject object: leftRegion.getAllRecords()) {
            leftTable.put(String.valueOf(object.getID()), object);
        }

        Region result = new Region("result");
        for (String key : leftTable.keySet()) {
            for (IMDGObject rightObject : rightRegion.getAllRecords()) {
                if (key.equals(String.valueOf(rightObject.getID()))){
                    //result.addObject(leftTable.get(key));
                    result.addObject(rightObject);
                }
            }
        }
        return result;
    }
}
