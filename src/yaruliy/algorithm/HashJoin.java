package yaruliy.algorithm;
import yaruliy.db.Region;
import yaruliy.db.custom.TemporaryRegion;

public class HashJoin implements JoinAlgorithm{
    @Override
    public TemporaryRegion executeJOIN(Class<Region> left, Class<Region> right, JoinCondition condition) {
        return null;
    }
}
