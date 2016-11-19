package yaruliy.db;
import yaruliy.data.SimpleIMDGObject;
import java.util.LinkedList;

public class Partition {
    private Node owner;
    private LinkedList<SimpleIMDGObject> objects;

    public Partition(Node owner){
        this.owner = owner;
    }
}