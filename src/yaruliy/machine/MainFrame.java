package yaruliy.machine;
import yaruliy.algorithm.BloomJoin;
import yaruliy.algorithm.HashJoin;
import yaruliy.data.IMDGObject;
import yaruliy.db.Warehouse;
import java.util.ArrayList;
import java.util.Random;

public class MainFrame {
    private static int n = 3;
    private static String region0 = "Region0";
    private static String region1 = "Region1";

    public static void main(String a[]){
        Warehouse warehouse = new Warehouse();
        ArrayList<IMDGObject> first = prepareObjects();
        ArrayList<IMDGObject> second = prepareObjects();

        warehouse.addCollection(first, region0);
        warehouse.addCollection(second, region1);

        warehouse.getRegionByName(region0).printRecords();
        warehouse.getRegionByName(region1).printRecords();

        warehouse.executeJOIN(region0, region1, new HashJoin(), "SerName").printResults();
        warehouse.executeJOIN(region0, region1, new BloomJoin(), "Name").printResults();
    }

    private static ArrayList<IMDGObject> prepareObjects(){
        ArrayList<IMDGObject> objects = new ArrayList<>();
        String alpfa = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random r = new Random();
        for (int i = 0; i < n; i++){
            String name = "";
            for (int j = 0; j < 7; j++) name = name + alpfa.charAt(r.nextInt((alpfa.length() - 1)));
            String serName = "";
            for (int j = 0; j < 10; j++) serName = serName + alpfa.charAt(r.nextInt((alpfa.length() - 1)));
            objects.add(new IMDGObject(name, serName, r.nextInt((3)) + 1));
        }
        return objects;
    }
}