package yaruliy.machine;
import yaruliy.algorithm.BloomJoin;
import yaruliy.algorithm.HashJoin;
import yaruliy.data.IMDGObject;
import yaruliy.db.Warehouse;
import java.util.ArrayList;
import java.util.Random;

public class MainFrame {

    public static void main(String a[]){
        Warehouse warehouse = new Warehouse();
        ArrayList<IMDGObject> first = prepareObjects();
        ArrayList<IMDGObject> second = prepareObjects();

        String region0 = "Region0";
        String region1 = "Region1";
        warehouse.addCollection(first, region0);
        warehouse.addCollection(second, region1);

        warehouse.getRegionByName(region0).printRecords(false);
        warehouse.getRegionByName(region1).printRecords(false);

        warehouse.executeJOIN(region0, region1, new HashJoin(), "ID").printResults();
        warehouse.executeJOIN(region0, region1, new BloomJoin(), "ID").printResults();
    }

    private static ArrayList<IMDGObject> prepareObjects(){
        ArrayList<IMDGObject> objects = new ArrayList<>();
        String[] names = {"Jonh", "Sam", "Dean", "Tom", "Piter", "Natan", "Jenna", "Sophia", "Jack"};
        String alpfa = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_";
        Random r = new Random();
        int n = 10;
        for (int i = 0; i < n; i++){
            String name = names[r.nextInt((names.length))];
            String serName = "";
            for (int j = 0; j < 10; j++) serName = serName + alpfa.charAt(r.nextInt((alpfa.length() - 1)));
            objects.add(new IMDGObject(name, serName, r.nextInt((3)) + 1));
        }
        return objects;
    }
}