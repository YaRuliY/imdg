package yaruliy.algorithm;
import yaruliy.bloom.BloomFilterMD5;
import yaruliy.data.IMDGObject;
import yaruliy.structure.JoinResult;
import yaruliy.structure.Region;
import yaruliy.util.Logger;
import yaruliy.util.Util;
import java.util.ArrayList;

import static yaruliy.util.Util.getRegionDataWithFilter;
import static yaruliy.util.Util.getValue;
import static yaruliy.util.Util.writeValuesIntoFilter;

public class BloomJoin extends JoinAlgorithm{
    @Override
    public JoinResult executeJOIN(Region leftRegion, Region rightRegion, String field) {
        BloomFilterMD5<String> bloomFilter = Util.getBloomFilter();
        writeValuesIntoFilter(bloomFilter, leftRegion.getName(), field);
        Logger.log("Left Region send BloomFilter (COE: " + leftRegion.getAllRecords().size() + ")");


        ArrayList<IMDGObject> rightSet = getRegionDataWithFilter(bloomFilter, rightRegion.getName(), field);
        Logger.log("From Right Region transfer " + rightSet.size() + " elements");
        Logger.log("Start JOIN calculating");

        JoinResult jr = new JoinResult(this.getClass().toGenericString());
        for (IMDGObject leftObj: leftRegion.getAllRecords())
            rightSet.stream()
                    .filter(rightObj -> getValue(field, leftObj).equals(getValue(field, rightObj)))
                    .forEachOrdered(rightObj -> jr.addObjectsCouple(new IMDGObject[]{leftObj, rightObj}));
        return jr;
    }
}