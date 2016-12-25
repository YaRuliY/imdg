package yaruliy.trackstaff.proccess;
import yaruliy.db.Region;
import java.util.HashMap;

public class ProccessManager {
    private static HashMap<String, MProccess> proccess = new HashMap<>();
    private ProccessManager() {}
    public static HashMap<String, MProccess> getProccess(){ return proccess; }
    public static MProccess getProccessByTableName(String tableName){ return proccess.get(tableName); }
    public static void addProccess(String tableName, Region region){ proccess.put(tableName, new MProccess(region));}
}