package yaruliy.algorithm;
import yaruliy.data.IMDGObject;
import yaruliy.db.JoinResult;
import yaruliy.db.Region;
import yaruliy.util.Logger;
import java.util.ArrayList;
import java.util.HashMap;
import static yaruliy.util.WHUtils.valueGetter;

public class HashJoin extends JoinAlgorithm{
    @Override
    public JoinResult executeJOIN(Region leftRegion, Region rightRegion, String field) {
        HashMap<String, ArrayList<IMDGObject>> leftTable = new HashMap<>();
        for (IMDGObject object: leftRegion.getAllRecords()) {
            String fieldForSearch = valueGetter(field, object);
            if(!(leftTable.containsKey(fieldForSearch))){
                leftTable.put(fieldForSearch, new ArrayList<>());
                leftTable.get(fieldForSearch).add(object);
            }
            else  leftTable.get(fieldForSearch).add(object);
        }

        Logger.log("Left Region send: " + leftRegion.getAllRecords().size() + " elements, total zize: " + leftRegion.getRegionSize());
        Logger.log("Right Region send: " + rightRegion.getAllRecords().size() + " elements, total zize: " + rightRegion.getRegionSize());

        JoinResult result = new JoinResult();

        long start = System.currentTimeMillis();
        Logger.log("Start JOIN comparison");

        for (String key : leftTable.keySet()) {
            for (IMDGObject leftObject: leftTable.get(key)) {
                rightRegion.getAllRecords()
                        .stream()
                        .filter(rightObject -> key.equals(valueGetter(field, rightObject)))
                        .forEachOrdered(rightObject -> result.addObjectsCouple(
                                new IMDGObject[]{ leftObject, rightObject }
                        ));
            }
        }
        long time = System.currentTimeMillis() - start;
        Logger.log("JOIN comparison time: " + time + " ms.");
        return result;
    }
}