package yaruliy.data;

public class IMDGObject {
    private long id;
    private String hashID;
    private String name;
    private String serName;
    private ObjectDependency objectDependency;

    public IMDGObject(String name, String serName, int dependencyCount){
        this.name = name;
        this.serName = serName;
        this.objectDependency = DependencyInjector.getDependency(dependencyCount);
    }

    public long getID() {
        return this.id;
    }

    public String getHashID(){
        return this.hashID;
    }

    public String getName() {
        return name;
    }

    public String getSerName() {
        return serName;
    }

    public ObjectDependency getObjectDependency() {
        return objectDependency;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSerName(String serName) {
        this.serName = serName;
    }

    public void setObjectDependency(ObjectDependency objectDependency) {
        this.objectDependency = objectDependency;
    }

    public void setID(long id) { this.id = id; }
    public void setHashID(String region) { this.hashID = region + "_" + this.id; }
}