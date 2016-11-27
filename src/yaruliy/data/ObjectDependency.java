package yaruliy.data;
import yaruliy.util.WHUtils;

public class ObjectDependency {
    private ObjectDependency baseObjectDependency;
    private long id;
    private int dependencyCount;
    private String value;

    ObjectDependency(long id, int dependencyCount, String value){
        this.id = id;
        this.dependencyCount = dependencyCount;
        this.value = value;
    }

    public String getAllDependencyInString(){
        StringBuilder sb = new StringBuilder();
        ObjectDependency ob = this.baseObjectDependency;
        for (int i = 0; i < dependencyCount; i++) {
            sb.append("ID: ").append(ob.getId()).append("; DepLength:").append(ob.getValue().length()).append("\n");
            ob = ob.getBaseObjectDependency();
        }
        return sb.toString();
    }

    public ObjectDependency setBaseObjectDependency(ObjectDependency baseObjectDependency) {
        this.baseObjectDependency = baseObjectDependency;
        return this.baseObjectDependency;
    }

    public long getId() { return id; }
    public String getValue() { return value; }
    public ObjectDependency getBaseObjectDependency() { return baseObjectDependency; }

    public int getSize() {
        if (baseObjectDependency != null) {
            return 64 + 32 + WHUtils.calculateStringSize(this.value) + baseObjectDependency.getSize();
        }
        else {
            return 64 + 32 + WHUtils.calculateStringSize(this.value);
        }
    }
}