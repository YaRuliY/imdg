package yaruliy.data;

public class ObjectDependency {
    private ObjectDependency baseObjectDependency;
    private long id;
    private int dependencyCount;
    private String value;

    public ObjectDependency(long id, int dependencyCount){
        this.id = id;
        this.dependencyCount = dependencyCount;
    }

    public ObjectDependency getBaseObjectDependency() {
        return baseObjectDependency;
    }

    public String getAllDependency(){
        StringBuilder sb = new StringBuilder();
        ObjectDependency ob = this.baseObjectDependency;
        for (int i = 0; i < dependencyCount; i++) {
            sb.append(ob.getId()).append("\n");
            ob = ob.getBaseObjectDependency();
        }
        return sb.toString();
    }

    public ObjectDependency setBaseObjectDependency(ObjectDependency baseObjectDependency) {
        this.baseObjectDependency = baseObjectDependency;
        return this.baseObjectDependency;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}