package yaruliy.algorithm;

public class JoinCondition {
    private String leftOperator;
    private String rightOperator;
    private String operation;

    public JoinCondition(String left, String right, String operation){
        this.leftOperator = left;
        this.rightOperator = right;
        this.operation = operation;
    }

    public String getLeftOperator() {
        return leftOperator;
    }

    public String getRightOperator() {
        return rightOperator;
    }

    public String getOperation() {
        return operation;
    }
}