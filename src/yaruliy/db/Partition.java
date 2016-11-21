package yaruliy.db;
import yaruliy.data.IMDGObject;
import java.util.ArrayList;
import java.util.HashSet;

class Partition {
    private String regionName;
    private ArrayList<IMDGObject> objects;
    HashSet<IMDGObject> objectsH = new HashSet<>();

    public Partition(String regionName){
        this.regionName = regionName;
        objects = new ArrayList<>();
    }

    public void addObject(IMDGObject object){
        objects.add(object);
    }

    public IMDGObject getObject(String key){
        //objectsH
        return objects.get(0);
    }
}