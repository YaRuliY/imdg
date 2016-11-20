package yaruliy.db;
import yaruliy.data.IMDGObject;
import java.util.LinkedList;

class Partitions {
    private Node owner;
    private LinkedList<IMDGObject> objects;

    public Partitions(Node owner){
        this.owner = owner;
    }

    public void addObject(String key, IMDGObject object){

    }

    public Node getOwner() {
        return owner;
    }
}