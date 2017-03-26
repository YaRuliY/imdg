package yaruliy.machine;
import yaruliy.algorithm.BloomJoin;
import yaruliy.algorithm.HashJoin;
import yaruliy.algorithm.TrackJoin;
import yaruliy.data.IMDGObject;
import yaruliy.data.build.OGenerator;
import yaruliy.db.JoinResult;
import yaruliy.db.Warehouse;
import yaruliy.util.Logger;
import java.util.ArrayList;

public class MainFrame {
    public static void main(String a[]){
        Logger.clearLog();
        Warehouse warehouse = new Warehouse();
        OGenerator generator = new OGenerator();
        generator.setElementsCountInRegion(30)
                .setObjectDependencies(new int[]{20, 50, 20, 10}, new int[]{3,7,20,15})
                .setJoinKeyFrequency("Jenna", 50)
                .setJoinKeyFrequency("Tom", 20)
                .setJoinKeyFrequency("Sam", 15)
                .setJoinKeyFrequency("Kevin", 15);

        ArrayList<IMDGObject> first = generator.generateObjectArray();
        generator.clear();

        generator.setObjectDependencies(new int[]{70, 10, 20}, new int[]{9,7,5})
                .setJoinKeyFrequency("Jack", 35)
                .setJoinKeyFrequency("Kate", 40)
                .setJoinKeyFrequency("Bobby", 25);
        ArrayList<IMDGObject> second = generator.generateObjectArray();

        String region0 = "Region0";
        String region1 = "Region1";
        warehouse.addCollection(first, region0);
        warehouse.addCollection(second, region1);

        warehouse.getRegionByName(region0).printRecords(true);
        warehouse.getRegionByName(region1).printRecords(false);

        JoinResult jr1 = warehouse.executeJOIN(region0, region1, new HashJoin(), "name");
        JoinResult jr2 = warehouse.executeJOIN(region0, region1, new BloomJoin(), "name");
        JoinResult jr3 = warehouse.executeJOIN(region0, region1, new TrackJoin(), "name");
        jr1.printResults();
        jr2.printResults();
        jr3.printResults();
        Logger.writeEnd();
    }
}