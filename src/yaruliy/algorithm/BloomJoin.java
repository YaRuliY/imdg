package yaruliy.algorithm;
import yaruliy.bloom.BloomFilterMD5;
import yaruliy.data.IMDGObject;
import yaruliy.db.JoinResult;
import yaruliy.db.Region;
import yaruliy.util.Logger;
import yaruliy.util.WHUtils;
import java.util.ArrayList;
import static yaruliy.util.WHUtils.valueGetter;

public class BloomJoin extends JoinAlgorithm{
    @Override
    public JoinResult executeJOIN(Region leftRegion, Region rightRegion, String field) {
        BloomFilterMD5<String> bloomFilter = WHUtils.getBloomFilter();
        leftRegion.writeValuesIntoFilter(bloomFilter, field);
        ArrayList<IMDGObject> rightSet = rightRegion.getFilteredRecords(bloomFilter, field);
        int size = 0;
        for (IMDGObject object: rightSet)
            size = size + object.calculateSize();
        Logger.log("Left Region send: " + leftRegion.getAllRecords().size() + " elements, total zize: " + leftRegion.getRegionSize());
        Logger.log("Right Region(filtered) send: " + rightSet.size() + " elements, total zize: " + size);
        JoinResult jr = new JoinResult();
        long start = System.currentTimeMillis();
        Logger.log("Start JOIN comparison");
        for (IMDGObject leftObj: leftRegion.getAllRecords())
            rightSet.stream()
                    .filter(rightObj -> valueGetter(field, leftObj).equals(valueGetter(field, rightObj)))
                    .forEachOrdered(rightObj -> jr.addObjectsCouple(new IMDGObject[]{leftObj, rightObj}));
        long time = System.currentTimeMillis() - start;
        Logger.log("JOIN comparison time: " + time + " ms.");
        return jr;
    }
}