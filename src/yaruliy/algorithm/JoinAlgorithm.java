package yaruliy.algorithm;
import yaruliy.db.Region;
import yaruliy.db.JoinResult;

public interface JoinAlgorithm {
    JoinResult executeJOIN(Region left, Region right, String field);
}