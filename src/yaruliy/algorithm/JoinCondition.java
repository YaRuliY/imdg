package yaruliy.algorithm;

public class JoinCondition {
    private String field;
    private Operation operation;
    public enum Operation { MORE, LESS, EQUALLY }

    public JoinCondition(String field, Operation operation){
        this.field = field;
        this.operation = operation;
    }

    public String getField() {
        return field;
    }

    public Operation getOperation() {
        return operation;
    }
}