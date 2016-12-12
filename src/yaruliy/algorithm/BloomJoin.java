package yaruliy.algorithm;
import yaruliy.bloom.BloomFilterMD5;
import yaruliy.data.IMDGObject;
import yaruliy.db.JoinResult;
import yaruliy.db.Region;
import yaruliy.util.Logger;
import yaruliy.util.Util;
import java.util.ArrayList;
import static yaruliy.util.Util.valueGetter;

public class BloomJoin extends JoinAlgorithm{
    @Override
    public JoinResult executeJOIN(Region leftRegion, Region rightRegion, String field) {
        long bloominit = System.nanoTime();
        BloomFilterMD5<String> bloomFilter = Util.getBloomFilter();
        leftRegion.writeValuesIntoFilter(bloomFilter, field);
        long bloomInitTime = System.nanoTime() - bloominit;
        Logger.log("BloomFilter init and hash into time: " + bloomInitTime + " ns.");
        ArrayList<IMDGObject> rightSet = rightRegion.getFilteredRecords(bloomFilter, field);

        int size = 0;
        for (IMDGObject object: rightSet)
            size = size + object.calculateSize();

        Logger.log("Left Region send: " + leftRegion.getAllRecords().size() + " elements, total zize: " + leftRegion.getRegionSize());
        Logger.log("Right Region(filtered) send: " + rightSet.size() + " elements, total zize: " + size);

        JoinResult jr = new JoinResult();

        long start = System.nanoTime();
        Logger.log("Start JOIN comparison");

        for (IMDGObject leftObj: leftRegion.getAllRecords())
            rightSet.stream()
                    .filter(rightObj -> valueGetter(field, leftObj).equals(valueGetter(field, rightObj)))
                    .forEachOrdered(rightObj -> jr.addObjectsCouple(new IMDGObject[]{leftObj, rightObj}));
        long time = System.nanoTime() - start;

        Logger.log("JOIN comparison time: " + time + " ns.");
        return jr;
    }
}