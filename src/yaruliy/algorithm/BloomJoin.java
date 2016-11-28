package yaruliy.algorithm;
import yaruliy.bloom.BloomFilterMD5;
import yaruliy.data.IMDGObject;
import yaruliy.db.JoinResult;
import yaruliy.db.Region;
import yaruliy.util.WHUtils;
import java.util.ArrayList;
import static yaruliy.util.WHUtils.getFieldValue;

public class BloomJoin extends JoinAlgorithm{
    @Override
    public JoinResult executeJOIN(Region leftRegion, Region rightRegion, String field) {
        BloomFilterMD5<String> bloomFilter = WHUtils.getBloomFilter();
        leftRegion.writeValuesIntoFilter(bloomFilter, field);
        ArrayList<IMDGObject> rightSet = rightRegion.getFilteredRecords(bloomFilter, field);
        JoinResult jr = new JoinResult();
        for (IMDGObject leftObj: leftRegion.getAllRecords())
            rightSet.stream()
                    .filter(rightObj -> getFieldValue(field, leftObj).equals(getFieldValue(field, rightObj)))
                    .forEachOrdered(rightObj -> jr.addObjectsCouple(new IMDGObject[]{leftObj, rightObj}));
        return jr;
    }
}