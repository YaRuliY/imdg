package yaruliy.util.trackstaff;
import java.util.HashMap;

public class TTable {
    private HashMap<String, HashMap<String, TElement>> hashTable;
    public TTable(){ hashTable = new HashMap<>(); }
    public HashMap<String, HashMap<String, TElement>> getHash(){ return this.hashTable; }

    public int[] getNodes(String key, String regName){
        if(hashTable.containsKey(key)){
            if(hashTable.get(key).containsKey(regName))
                return hashTable.get(key).get(regName).getNodes();
        }
        return null;
    }

    public void addInfoFromMessage(TMessage message){
        if (hashTable.containsKey(message.getJoinKey())){
            if(hashTable.get(message.getJoinKey()).containsKey(message.getRegionName()))
                hashTable.get(message.getJoinKey()).get(message.getRegionName()).addInfo(message.getNodeIndex(), message.getObjectSize());
            else
                hashTable.get(message.getJoinKey()).put(message.getRegionName(), new TElement(message.getNodeIndex(), message.getObjectSize()));
        }
        else {
            hashTable.put(message.getJoinKey(), new HashMap<>());
            hashTable.get(message.getJoinKey()).put(message.getRegionName(), new TElement(message.getNodeIndex(), message.getObjectSize()));
        }
    }
}