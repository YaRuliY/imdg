package yaruliy.algorithm;
import yaruliy.bloom.BloomFilter;
import yaruliy.db.JoinResult;
import yaruliy.db.Region;

public class BloomJoin implements JoinAlgorithm{
    private final int bloomCapacity = 1000;
    private final int hashFunctionCount = 10;

    @Override
    public JoinResult executeJOIN(Region leftRegion, Region rightRegion, String field) {
        BloomFilter leftBloom = new BloomFilter(bloomCapacity, hashFunctionCount);
        BloomFilter rightBloom = new BloomFilter(bloomCapacity, hashFunctionCount);

        /*leftBloom.add();*/
        return null;
    }
}