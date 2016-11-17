package yaruliy.machine;
import yaruliy.db.DataBase;

public class MainFrame {
    public static void main(String a[]){
        DataBase dataBase = new DataBase();
        System.out.println(dataBase.getPartitionCount());
        System.out.println(dataBase.getReplicationCount());
    }
}