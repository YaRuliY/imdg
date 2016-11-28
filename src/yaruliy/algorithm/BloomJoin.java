package yaruliy.algorithm;
import yaruliy.bloom.BloomFilterMD5;
import yaruliy.data.IMDGObject;
import yaruliy.db.JoinResult;
import yaruliy.db.Region;
import yaruliy.util.WHUtils;
import java.util.ArrayList;

public class BloomJoin extends JoinAlgorithm{
    @Override
    public JoinResult executeJOIN(Region leftRegion, Region rightRegion, String field) {
        BloomFilterMD5<String> bloomFilter = WHUtils.getBloomFilter();
        leftRegion.writeValuesIntoFilter(bloomFilter);
        ArrayList<IMDGObject> rightSet = rightRegion.getFilteredRecords(bloomFilter);
        JoinResult jr = new JoinResult();
        for (IMDGObject leftObj: leftRegion.getAllRecords())
            for (IMDGObject rightObj: rightSet) {
                if (leftObj.getName().equals(rightObj.getName()))
                    jr.addObjectsCouple(new IMDGObject[]{leftObj, rightObj});
                //else jr.addObjectsCouple(new IMDGObject[]{leftObj, null});
            }
        return jr;
    }
}