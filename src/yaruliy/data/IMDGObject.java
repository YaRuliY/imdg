package yaruliy.data;
import yaruliy.util.Util;

public class IMDGObject implements Comparable<IMDGObject>{
    private long id;
    private String hashID;
    private String name;
    private String serName;
    private ObjectDependency objectDependency;
    private int depCount;

    public IMDGObject(String name, String serName, int dependencyCount){
        this.depCount = dependencyCount;
        this.name = name;
        this.serName = serName;
        this.objectDependency = DependencyInjector.getDependency(dependencyCount);
    }

    public int calculateSize(){
        return 64 + 32 + Util.calculateStringSize(hashID) + Util.calculateStringSize(name)
                + Util.calculateStringSize(serName) + objectDependency.getSize();
    }

    @Override
    public int compareTo(IMDGObject o) { return (int) (this.id - o.getID()); }
    public long getID() { return this.id; }
    public int getDepCount() { return depCount; }
    public String getHashID(){ return this.hashID; }
    public String getName() { return name; }
    public String getSerName() { return serName; }
    public void setID(long id) { this.id = id; }
    public void setHashID(String region) { this.hashID = region + "_" + this.id; }
}