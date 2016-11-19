package yaruliy.db;
import yaruliy.data.IMDGObject;

import java.util.LinkedList;

public class Partition {
    private Node owner;
    private LinkedList<IMDGObject> objects;

    public Partition(Node owner){
        this.owner = owner;
    }
}