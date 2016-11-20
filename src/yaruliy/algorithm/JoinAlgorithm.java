package yaruliy.algorithm;
import yaruliy.db.Region;
import yaruliy.db.custom.TemporaryRegion;

public interface JoinAlgorithm {
    TemporaryRegion executeJOIN(Class<Region> left, Class<Region> right, JoinCondition condition);
}