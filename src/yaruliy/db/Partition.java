package yaruliy.db;
import yaruliy.data.IMDGObject;
import java.util.ArrayList;

class Partition {
    private String regionName;
    private ArrayList<IMDGObject> objects;

    public Partition(){
        objects = new ArrayList<>();
    }

    public void addObject(IMDGObject object){
        objects.add(object);
    }

    public IMDGObject getObject(String key){
        return objects.get(0);
    }
}