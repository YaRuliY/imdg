package yaruliy.db;
import yaruliy.data.IMDGObject;

public class Node {
    private short partitionCount;
    private short replicationCount;
    private int id;
    private short size = 0;
    private Partitions partitions;

    public Node(){

    }

    public void addObject(String key, IMDGObject object){
        partitions.addObject(key, object);
        this.size++;
    }

    public short getCapacity(){
        return (short) 10;
    }

    public boolean isAvailable(){
        return this.size <= this.getCapacity();
    }
}
