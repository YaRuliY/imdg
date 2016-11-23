package yaruliy.algorithm;
import yaruliy.db.Region;

public interface JoinAlgorithm {
    Region executeJOIN(Region left, Region right, JoinCondition condition);
}