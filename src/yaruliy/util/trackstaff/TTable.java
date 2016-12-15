package yaruliy.util.trackstaff;
import java.util.HashMap;

public class TTable {
    private HashMap<String, HashMap<String, int[]>> hashTable;
    public TTable(){ hashTable = new HashMap<>(); }
    public HashMap<String, HashMap<String, int[]>> getHash(){ return this.hashTable; }

    public HashMap<String, int[]> getRegionColumn(String key){
        return hashTable.get(key);
    }

    public int[] getNodes(String key, String regName){
        if(hashTable.containsKey(key)){
            if(hashTable.get(key).containsKey(regName))
                return hashTable.get(key).get(regName);
        }
        return null;
    }

    public void addInfoFromMessage(TMessage message){
        if (hashTable.containsKey(message.getJoinKey())){
            if(!hashTable.get(message.getJoinKey()).containsKey(message.getRegionName())){
                hashTable.get(message.getJoinKey()).put(message.getRegionName(), new int[]{message.getNodeIndex()});
            }
            else {
                int[] mas = hashTable.get(message.getJoinKey()).get(message.getRegionName());
                int[] newmas = new int[mas.length + 1];
                System.arraycopy(mas, 0, newmas, 0, mas.length);
                newmas[newmas.length - 1] = message.getNodeIndex();
                hashTable.get(message.getJoinKey()).put(message.getRegionName(), newmas);
            }
        }
        else {
            hashTable.put(message.getJoinKey(), new HashMap<>());
            hashTable.get(message.getJoinKey()).put(message.getRegionName(), new int[]{message.getNodeIndex()});
        }
    }
}