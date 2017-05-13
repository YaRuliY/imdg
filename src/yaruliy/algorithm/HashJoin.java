package yaruliy.algorithm;
import yaruliy.data.IMDGObject;
import yaruliy.structure.JoinResult;
import yaruliy.structure.Region;
//import yaruliy.util.Logger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import static yaruliy.util.Util.getRegionDataFromNodes;
import static yaruliy.util.Util.getValue;

public class HashJoin extends JoinAlgorithm{
    @Override
    public JoinResult executeJOIN(Region leftRegion, Region rightRegion, String field) {
        //-----------------Transfering-Data----------------");
        List<IMDGObject> leftRegionObjects = getRegionDataFromNodes(leftRegion.getName());
        List<IMDGObject> rightRegionObjects = getRegionDataFromNodes(rightRegion.getName());
        //-----------------End-Transfering-----------------");

        //Hash Table Building
        HashMap<String, ArrayList<IMDGObject>> leftTable = new HashMap<>();
        for (IMDGObject object: leftRegionObjects) {
            String joinField = getValue(field, object);
            if(leftTable.containsKey(joinField))
                leftTable.get(joinField).add(object);
            else{
                leftTable.put(joinField, new ArrayList<>());
                leftTable.get(joinField).add(object);
            }
        }

        JoinResult result = new JoinResult(this.getClass().toGenericString());
        for (String key : leftTable.keySet()){
            for (IMDGObject leftObject: leftTable.get(key)){
                rightRegionObjects.stream()
                        .filter(rightObject -> key.equals(getValue(field, rightObject)))
                        .forEachOrdered(rightObject -> result.addObjectsCouple(new IMDGObject[]{ leftObject, rightObject }));
            }
        }
        return result;
    }
}