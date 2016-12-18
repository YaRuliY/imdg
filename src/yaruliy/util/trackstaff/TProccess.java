package yaruliy.util.trackstaff;
import yaruliy.data.IMDGObject;
import yaruliy.db.Node;
import yaruliy.util.Util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TProccess {
    private TTable table;
    private static volatile TProccess instance;
    private TProccess(){ this.table = new TTable(); }
    public void writeIntoTable(TMessage message){ table.addInfoFromMessage(message); }

    public static TProccess getInstance() {
        TProccess localInstance = instance;
        if (localInstance == null) {
            synchronized (TProccess.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new TProccess();
                }
            }
        }
        return localInstance;
    }

    public void printTable() {
        System.out.println("[--------------------" + "Hash-Table-[" + this.table.getHash().size() + "]:" + "--------------------]");
        int max = 0;
        for(Map.Entry<String, HashMap<String, TElement>> entry : this.table.getHash().entrySet()) {
            String key = entry.getKey();
            if(this.table.getNodes(key, "Region0") != null)
                if(max < this.table.getNodes(key, "Region0").length)
                    max = this.table.getNodes(key, "Region0").length;
        }
        int t = (max*3 + 1);
        System.out.print("| UnicKey ");
        System.out.printf("| %-"+t+"s| %-"+t+"s \n", "    Region0     ", "     Region1");
        System.out.println("|-------------------------------------------------------]");
        for(Map.Entry<String, HashMap<String, TElement>> entry : this.table.getHash().entrySet()) {
            String key = entry.getKey();
            HashMap<String, TElement> value = entry.getValue();
            System.out.printf("| %-8s| ", key);
            if(value.get("Region0") != null){
                System.out.printf("%-"+t+"s| ", value.get("Region0"));
            }
            else System.out.printf("%-"+t+"s| ", "[null] ");
            if(value.get("Region1") != null){
                System.out.printf("%-"+t+"s \n", value.get("Region1"));
            }
            else System.out.printf("%-"+t+"s \n", "[null]");
        }
        System.out.println("[-------------------------------------------------------]");
    }

    public void doTransfer(String leftRegion, String rightRegion) {
        for(Map.Entry<String, HashMap<String, TElement>> entry : this.table.getHash().entrySet()) {
            String key = entry.getKey();// key = уникальный ключ
            System.out.printf("%-8s", key);
            System.out.print(": ");
            HashMap<String, TElement> value = entry.getValue();// value = значение ключа(конкретный регион)
            if(value.get(leftRegion) != null && value.get(rightRegion) != null){
                if (value.get(leftRegion).getNodes().length < value.get(rightRegion).getNodes().length){
                    System.out.println(leftRegion + " -> " + rightRegion);
                    for (int leftRegionIndex: value.get(leftRegion).getNodes()) {
                        Node nodeForLeft = Util.getNodes().get(leftRegionIndex);
                        for (String regKey : nodeForLeft.getPartitions().keySet()) {
                            for (IMDGObject object: nodeForLeft.getPartitions().get(regKey).getAllRecords()) {
                                if (object.getName().equals(key)){
                                    int[] rightRegionIndexs = value.get(rightRegion).getNodes();
                                    for (int rightRegionIndex: rightRegionIndexs){
                                        Util.getNodes().get(rightRegionIndex).addObject(object.getRegion(), object);
                                    }
                                }
                            }
                        }
                    }
                }
                else {
                    System.out.println(leftRegion + " <- " + rightRegion);
                }
            }
            else System.out.println("transfer not done!");
        }
    }
}