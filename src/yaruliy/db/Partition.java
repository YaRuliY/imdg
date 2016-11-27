package yaruliy.db;
import yaruliy.data.IMDGObject;
import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;

public class Partition {
    private HashMap<Long, IMDGObject> objects;

    public Partition(){
        objects = new HashMap<>();
    }

    public void addObject(IMDGObject object){
        objects.put(object.getID(), object);
    }

    public IMDGObject getObject(long id){
        return objects.get(id);
    }

    public Set<IMDGObject> getAllRecords(){
        /*Set<IMDGObject> set = new HashSet<>();
        for (long key: objects.keySet()) {
            set.add(objects.get(key));
        }*/
        return objects.keySet().stream().map(key -> objects.get(key)).collect(Collectors.toSet());
    }
}