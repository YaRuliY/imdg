package yaruliy.db;
import yaruliy.data.IMDGObject;

import java.util.HashMap;

class Partition {
    private Node owner;
    private HashMap<String, IMDGObject> objects;

    public Partition(Node owner){
        this.owner = owner;
        objects = new HashMap<>();
    }

    public void addObject(String key, IMDGObject object){
        objects.put(key, object);
    }

    public IMDGObject getObject(String key){
        return objects.get(key);
    }

    public Node getOwner() {
        return owner;
    }
}