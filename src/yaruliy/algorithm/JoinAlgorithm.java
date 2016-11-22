package yaruliy.algorithm;
import yaruliy.db.Region;

public interface JoinAlgorithm {
    Region executeJOIN(String left, String right, JoinCondition condition);
}