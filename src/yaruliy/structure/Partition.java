package yaruliy.structure;
import yaruliy.data.IMDGObject;
import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;

public class Partition {
    private HashMap<Long, IMDGObject> objects;
    public Partition(){ this.objects = new HashMap<>(); }
    public void addObject(IMDGObject object){ objects.put(object.getID(), object); }
    public IMDGObject getObject(long id){ return objects.get(id); }
    public int getPartitionSize(){ return this.objects.size(); }

    public Set<IMDGObject> getAllRecords(){
        return objects.keySet().stream().map(key -> objects.get(key)).collect(Collectors.toSet());
    }

    public void printContent(String regKey){
        int i = 0;
        for (long key : objects.keySet()) {
            if(i == objects.keySet().size()/2 || objects.size() == 1) {
                System.out.print(regKey + ": ");
                System.out.println(objects.get(key).getHashID() + " [" + objects.get(key).getName() + "]");
            }
            else System.out.println("\t\t " + objects.get(key).getHashID() + " [" + objects.get(key).getName() + "]");
            i++;
        }
    }

    public boolean contains(String Ukey){
        for (long key : objects.keySet()) {
            if(objects.get(key).getHashID().equals(Ukey)) return true;
        }
        return false;
    }
}