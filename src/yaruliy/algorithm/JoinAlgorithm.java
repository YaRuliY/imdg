package yaruliy.algorithm;
import yaruliy.structure.Region;
import yaruliy.structure.JoinResult;

public abstract class JoinAlgorithm {
    public abstract JoinResult executeJOIN(Region left, Region right, String field);
}