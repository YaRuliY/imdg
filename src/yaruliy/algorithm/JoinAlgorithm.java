package yaruliy.algorithm;
import yaruliy.db.Region;
import yaruliy.db.JoinResult;

public abstract class JoinAlgorithm {
    public abstract JoinResult executeJOIN(Region left, Region right, String field);
}