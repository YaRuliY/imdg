package yaruliy.algorithm;
import yaruliy.bloom.BloomFilterMD5;
import yaruliy.data.IMDGObject;
import yaruliy.structure.JoinResult;
import yaruliy.structure.Region;
import yaruliy.util.Logger;
import yaruliy.util.Util;
import java.util.ArrayList;
import static yaruliy.util.Util.getValue;

public class BloomJoin extends JoinAlgorithm{
    @Override
    public JoinResult executeJOIN(Region leftRegion, Region rightRegion, String field) {
        long bloominit = System.nanoTime();
        BloomFilterMD5<String> bloomFilter = Util.getBloomFilter();
        leftRegion.writeValuesIntoFilter(bloomFilter, field);
        long bloomInitTime = System.nanoTime() - bloominit;
        Logger.log("BloomFilter init and hashed into time: " + bloomInitTime + " ns.");
        Logger.log("Left Region send Bloom Filter {count: " + leftRegion.getAllRecords().size() + "}");
        ArrayList<IMDGObject> rightSet = rightRegion.getRecordsWithFilter(bloomFilter, field);
        int size = 0;
        for (IMDGObject object: rightSet) size = size + object.calculateSize();
        Logger.log("Right Region(filtered) send: " + rightSet.size() + " elements, total zize: " + size);

        long start = System.nanoTime();
        Logger.log("Start JOIN calculating");

        JoinResult jr = new JoinResult(this.getClass().toGenericString());
        for (IMDGObject leftObj: leftRegion.getAllRecords())
            rightSet.stream()
                    .filter(rightObj -> getValue(field, leftObj).equals(getValue(field, rightObj)))
                    .forEachOrdered(rightObj -> jr.addObjectsCouple(new IMDGObject[]{leftObj, rightObj}));

        long time = System.nanoTime() - start;
        Logger.log("JOIN calculating time: " + time + " ns.");
        return jr;
    }
}