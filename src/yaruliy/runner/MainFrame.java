package yaruliy.runner;
import yaruliy.algorithm.BloomJoin;
import yaruliy.algorithm.HashJoin;
import yaruliy.algorithm.TrackJoin;
import yaruliy.data.IMDGObject;
import yaruliy.data.build.ObjectGenerator;
import yaruliy.structure.JoinResult;
import yaruliy.structure.Warehouse;
import yaruliy.util.Logger;
import java.util.ArrayList;

public class MainFrame {
    static String region0 = "Region0";
    static String region1 = "Region1";
    public static void main(String a[]){
        Logger.clearLog();
        Warehouse warehouse = new Warehouse();
        ObjectGenerator generator = new ObjectGenerator();

        generator.setElementsCountInRegion(10)
                .setObjectDependencies(new int[]{100}, new int[]{30})
                .setJoinKeyFrequency("Jenna", 50)
                .setJoinKeyFrequency("Tom", 50);

        ArrayList<IMDGObject> first = generator.generateObjectArray(region0);

        generator.reInit();
        generator.setObjectDependencies(new int[]{100}, new int[]{30})
                .setJoinKeyFrequency("Jenna", 50)
                .setJoinKeyFrequency("Tom", 50);

        ArrayList<IMDGObject> second = generator.generateObjectArray(region1);

        warehouse.addCollection(first, region0);
        warehouse.addCollection(second, region1);

        warehouse.getRegionByName(region0).printRecords(true);
        warehouse.getRegionByName(region1).printRecords(false);

        Logger.logSystemInfo();
        JoinResult jr1 = warehouse.executeJOIN(region0, region1, new HashJoin(), "name");
        JoinResult jr2 = warehouse.executeJOIN(region0, region1, new BloomJoin(), "name");
        JoinResult jr3 = warehouse.executeJOIN(region0, region1, new TrackJoin(), "name");
        jr1.printResults();
        jr2.printResults();
        jr3.printResults();
        Logger.writeEnd();
    }
}