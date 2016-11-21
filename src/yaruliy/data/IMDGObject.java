package yaruliy.data;

public class IMDGObject {
    private long id;
    private String name;
    private String serName;
    private ObjectDependency objectDependency;

    public IMDGObject(long id, String name, String serName, int dependencyCount){
        this.id = id;
        this.name = name;
        this.serName = serName;
        this.objectDependency = DependencyInjector.getDependency(dependencyCount);
    }

    public long getID() {
        return this.id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSerName() {
        return serName;
    }

    public void setSerName(String serName) {
        this.serName = serName;
    }

    public ObjectDependency getObjectDependency() {
        return objectDependency;
    }

    public void setObjectDependency(ObjectDependency objectDependency) {
        this.objectDependency = objectDependency;
    }

    public void setId(long id) {
        this.id = id;
    }
}