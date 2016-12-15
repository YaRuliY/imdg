package yaruliy.util.trackstaff;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TProccess {
    private TTable table;
    private static volatile TProccess instance;
    private TProccess(){ this.table = new TTable(); }

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

    public void writeIntoTable(TMessage message){ table.addInfoFromMessage(message); }

    public void printTable() {
        System.out.println("Hash-Table-[" + this.table.getHash().size() + "]:");
        int max = 0;
        for(Map.Entry<String, HashMap<String, int[]>> entry : this.table.getHash().entrySet()) {
            String key = entry.getKey();
            if(this.table.getNodes(key, "Region0") != null){
                if(max < this.table.getNodes(key, "Region0").length)
                    max = this.table.getNodes(key, "Region0").length;
            }
        }
        int t = (max*3 + 1);
        System.out.print("| UnicKey ");
        System.out.printf("| %-"+t+"s| %-"+t+"s \n", "      Region0     ", "     Region1");
        for(Map.Entry<String, HashMap<String, int[]>> entry : this.table.getHash().entrySet()) {
            String key = entry.getKey();
            HashMap<String, int[]> value = entry.getValue();
            System.out.printf("| %-8s| ", key);
            if(value.get("Region0") != null){
                System.out.printf("%-"+t+"s| ", Arrays.toString(value.get("Region0")));
            }
            else System.out.printf("%-"+t+"s| ", "[null] ");
            if(value.get("Region1") != null){
                System.out.printf("%-"+t+"s \n", Arrays.toString(value.get("Region1")));
            }
            else System.out.printf("%-"+t+"s \n", "[null]");
        }
    }
}