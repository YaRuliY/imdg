package yaruliy.algorithm;
import yaruliy.data.IMDGObject;
import yaruliy.structure.JoinResult;
import yaruliy.structure.Node;
import yaruliy.structure.Region;
import yaruliy.util.Logger;
import yaruliy.util.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static yaruliy.util.Util.getRegionDataFromNodes;
import static yaruliy.util.Util.getValue;

public class HashJoin extends JoinAlgorithm{
    @Override
    public JoinResult executeJOIN(Region leftRegion, Region rightRegion, String field) {
        HashMap<String, ArrayList<IMDGObject>> leftTable = new HashMap<>();
        //-----------------Transfering-Data----------------
        List<IMDGObject> leftRegionObjects = getRegionDataFromNodes(leftRegion.getName());
        List<IMDGObject> rightRegionObjects = getRegionDataFromNodes(rightRegion.getName());
        //-----------------End-Transfering-----------------

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
        long start = System.nanoTime();
        Logger.log("Start JOIN calculating");

        for (String key : leftTable.keySet()){
            for (IMDGObject leftObject: leftTable.get(key)){
                rightRegionObjects.stream()
                        .filter(rightObject -> key.equals(getValue(field, rightObject)))
                        .forEachOrdered(rightObject -> result.addObjectsCouple(new IMDGObject[]{ leftObject, rightObject }));
            }
        }

        long time = System.nanoTime() - start;
        Logger.log("JOIN calculating time: " + time + " ns.");
        return result;
    }
}