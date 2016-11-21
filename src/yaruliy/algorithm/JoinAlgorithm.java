package yaruliy.algorithm;
import yaruliy.db.Region;

public interface JoinAlgorithm {
    Region executeJOIN(Class<Region> left, Class<Region> right, JoinCondition condition);
}