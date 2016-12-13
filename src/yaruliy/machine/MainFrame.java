package yaruliy.machine;
import yaruliy.data.IMDGObject;
import yaruliy.db.Warehouse;
import yaruliy.util.Logger;
import yaruliy.util.Util;
import java.util.ArrayList;
import java.util.Random;

public class MainFrame {
    public static void main(String a[]){
        Logger.clearLog();
        Warehouse warehouse = new Warehouse();
        ArrayList<IMDGObject> first = prepareObjects();
        ArrayList<IMDGObject> second = prepareObjects();

        String region0 = "Region0";
        String region1 = "Region1";
        warehouse.addCollection(first, region0);
        warehouse.addCollection(second, region1);

        warehouse.getRegionByName(region0).printRecords(false);
        warehouse.getRegionByName(region1).printRecords(false);
        //Util.printNodesContent(warehouse.getRegionByName(region0).getObject(2).getName());
        Util.printNodesContent();
        Util.findNodeWithMaxElem(region0);
        Util.findNodeWithMaxElem(region1);

        /*JoinResult jr1 = warehouse.executeJOIN(region0, region1, new HashJoin(), "name");
        JoinResult jr2 = warehouse.executeJOIN(region0, region1, new BloomJoin(), "name");
        jr1.printResults();
        jr2.printResults();
        Logger.writeEnd();*/
    }

    private static ArrayList<IMDGObject> prepareObjects(){
        ArrayList<IMDGObject> objects = new ArrayList<>();
        String[] names = {"Jonh", "Sam", "Dean", "Tom", "Piter", "Natan", "Jenna", "Sophia", "Jack"};
        String alpfa = "_abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random r = new Random();
        int n = 5;
        for (int i = 0; i < n; i++){
            String name = names[r.nextInt((names.length))];
            String serName = "";
            for (int j = 0; j < r.nextInt((6)) + 5; j++)
                serName = serName + alpfa.charAt(r.nextInt((alpfa.length() - 1)));
            objects.add(new IMDGObject(name, serName, r.nextInt((3)) + 1));
        }
        return objects;
    }
}