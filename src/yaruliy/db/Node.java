package yaruliy.db;
import yaruliy.data.IMDGObject;
import yaruliy.util.WHProperties;
import java.util.HashMap;

//Тупое хранилище без логики
class Node {
    private int id;
    private HashMap<String, Partition> partitions;

    public Node(){
        this.id = WHProperties.getNodeCount();
        partitions = new HashMap<>();
        WHProperties.increaseNodeCount();
    }

    public void addObject(String regionName, IMDGObject object){
        //нода принемает обьект и имя региона
        //достает нужную партицию(имя региона)
        //и кладет в нее данные
        //partHash.put(key, index);
        partitions.get(regionName).addObject(object);
    }

    public IMDGObject getObject(String key){
        return partitions.get(key).getObject(key);
    }
}
