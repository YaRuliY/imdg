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
        generator.setRegionElementsCount(7).setMaxDependencySize(5).setMinDependencySize(3);

        ArrayList<IMDGObject> first = generator.generateObjectArray();
        ArrayList<IMDGObject> second = generator.generateObjectArray();

        String region0 = "Region0";
        String region1 = "Region1";
        warehouse.addCollection(first, region0);
        warehouse.addCollection(second, region1);

        warehouse.getRegionByName(region0).printRecords(false);
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