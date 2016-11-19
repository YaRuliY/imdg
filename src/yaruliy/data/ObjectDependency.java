package yaruliy.data;

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
            sb.append(ob.getId()).append("\n").append(ob.getValue().length()).append("\n");
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

    public ObjectDependency getBaseObjectDependency() {
        return baseObjectDependency;
    }
}