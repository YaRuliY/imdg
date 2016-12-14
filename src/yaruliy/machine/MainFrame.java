package yaruliy.machine;
import yaruliy.algorithm.TrackJoin;
import yaruliy.data.IMDGObject;
import yaruliy.db.Warehouse;
import yaruliy.util.Logger;
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
        /*printNodesContent();

        transferDataToNode(findNodeWithMaxElemCount(region0, region1), findNodeWithMaxElemCount(region1, region0));
        sendSignalToAll(findNodeWithMaxElemCount(region1, region0), region1);
        printNodesContent();*/

        warehouse.executeJOIN(region0, region1, new TrackJoin(), "name");
        /*JoinResult jr1 = warehouse.executeJOIN(region0, region1, new HashJoin(), "name");
        JoinResult jr2 = warehouse.executeJOIN(region0, region1, new BloomJoin(), "name");
        jr1.printResults();
        jr2.printResults();
        Logger.writeEnd();*/
    }

    private static ArrayList<IMDGObject> prepareObjects(){
        ArrayList<IMDGObject> objects = new ArrayList<>();
        String[] names = {"Jonh", "Sam", "Dean", "Tom", "Piter", "Natan", "Jenna", "Sophia", "Jack", "Kelly", "Robert"};
        String alpfa = "_abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random r = new Random();
        int n = 10;
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