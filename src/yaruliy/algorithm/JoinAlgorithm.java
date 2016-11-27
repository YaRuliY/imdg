package yaruliy.algorithm;
import yaruliy.data.IMDGObject;
import yaruliy.db.Region;
import yaruliy.db.JoinResult;
import java.lang.reflect.InvocationTargetException;

public abstract class JoinAlgorithm {
    public abstract JoinResult executeJOIN(Region left, Region right, String field);

    protected String getFieldValue(String field, IMDGObject object){
        String result = null;
        try { result = IMDGObject.class.getMethod("get"+field).invoke(object).toString(); }
        catch (NoSuchMethodException e) {
            System.out.println("No Such Field Exception!!!");
            System.exit(2);
        }
        catch (InvocationTargetException | IllegalAccessException e) { e.printStackTrace(); }
        return result;
    }
}