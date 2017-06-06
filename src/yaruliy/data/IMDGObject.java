package yaruliy.data;
import yaruliy.util.Util;

public class IMDGObject implements Comparable<IMDGObject>{
    private long id;
    private String hashID;
    private String name;
    private String serName;
    private ObjectDependency objectDependency;
    private int depCount;
    private int realSize = 0;

    public IMDGObject(String name, String serName, int dependencyCount, int realSize){
        this.depCount = dependencyCount;
        this.name = name;
        this.serName = serName;
        this.objectDependency = DependencyInjector.getDependency(dependencyCount);
        this.realSize = realSize;
    }

    public int calculateSize(){
        return /*(64 + 32 + Util.calculateStringSize(hashID) + Util.calculateStringSize(name)
                + Util.calculateStringSize(serName) + objectDependency.getSize()) / 1024;*/
        this.getRealSize()/* * 1024*/;
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
    public String getRegion(){ return this.hashID.substring(0, this.hashID.indexOf('_'));}
    public int getRealSize() { return realSize; }
}