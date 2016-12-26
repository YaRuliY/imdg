package yaruliy.trackstaff.proccess;
import yaruliy.db.Region;
import java.util.HashMap;

public class MProccessManager {
    private static HashMap<String, MProccess> proccesses = new HashMap<>();
    private MProccessManager() {}
    //public static HashMap<String, MProccess> getProccesses(){ return proccesses; }
    public static MProccess getProccessByTableName(String tableName){ return proccesses.get(tableName); }
    public static void addProccess(String tableName, Region region){ proccesses.put(tableName, new MProccess(region));}
}