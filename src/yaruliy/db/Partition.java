package yaruliy.db;
import yaruliy.data.IMDGObject;
import java.util.HashMap;

class Partition {
    private final Region owner;
    private HashMap<Long, IMDGObject> objects;

    public Partition(Region owner){
        this.owner = owner;
        objects = new HashMap<>();
    }

    public void addObject(IMDGObject object){
        objects.put(object.getID(), object);
    }

    public IMDGObject getObject(long id){
        return objects.get(id);
    }

    public Region getOwner() {
        return owner;
    }
}