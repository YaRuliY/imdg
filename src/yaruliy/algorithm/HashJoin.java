package yaruliy.algorithm;
import yaruliy.data.IMDGObject;
import yaruliy.db.JoinResult;
import yaruliy.db.Region;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class HashJoin implements JoinAlgorithm{
    @Override
    public JoinResult executeJOIN(Region leftRegion, Region rightRegion, String field) {
        HashMap<String, IMDGObject> leftTable = new HashMap<>();
        for (IMDGObject object: leftRegion.getAllRecords()) {
            leftTable.put(getFieldValue(field, object), object);
        }

        JoinResult result = new JoinResult();
        for (String key : leftTable.keySet()) {
            for (IMDGObject rightObject : rightRegion.getAllRecords()) {
                if (key.equals(getFieldValue(field, rightObject))){
                    result.addObjects(new IMDGObject[]{leftTable.get(key), rightObject});
                }
            }
        }
        return result;
    }

    private String getFieldValue(String field, IMDGObject object){
        String result = null;
        try { result = IMDGObject.class.getMethod("get"+field).invoke(object).toString(); }
        catch (NoSuchMethodException e) { System.out.println("No such field!!!"); }
        catch (InvocationTargetException | IllegalAccessException e) { e.printStackTrace(); }
        return result;
    }
}