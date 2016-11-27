package yaruliy.algorithm;
import yaruliy.data.IMDGObject;
import yaruliy.db.JoinResult;
import yaruliy.db.Region;
import java.util.Set;

public class BloomJoin extends JoinAlgorithm{
    @Override
    public JoinResult executeJOIN(Region leftRegion, Region rightRegion, String field) {
        Set<IMDGObject> leftFilteredSet = leftRegion.getFilteredRecords(rightRegion.getBloomFilter());
        Set<IMDGObject> rightFilteredSet = rightRegion.getFilteredRecords(leftRegion.getBloomFilter());
        return new JoinResult();
    }
}