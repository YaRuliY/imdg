package yaruliy.machine;
import yaruliy.algorithm.HashJoin;
import yaruliy.algorithm.JoinCondition;
import yaruliy.data.IMDGObject;
import yaruliy.db.Region;
import yaruliy.db.Warehouse;

public class MainFrame {
    public static void main(String a[]){
        Warehouse warehouse = new Warehouse();
        IMDGObject book1 = new IMDGObject("name1", "sername2", 3);
        IMDGObject book2 = new IMDGObject("name2", "sername2", 3);
        IMDGObject book3 = new IMDGObject("name3", "sername3", 3);
        IMDGObject user3 = new IMDGObject("user3", "sername3", 3);
        IMDGObject user4 = new IMDGObject("user4", "sername3", 3);

        String region = "Region0";
        warehouse.addObject(book1, region);
        warehouse.addObject(book2, region);
        warehouse.addObject(book3, region);
        warehouse.addObject(user3, region);
        warehouse.addObject(user4, region);

        System.out.println("---------GETobjects---------");
        for (IMDGObject object : warehouse.getRegionByName(region).getAllRecords())
            System.out.println(object.getName());

        Region temporaryResion = warehouse.executeJOIN("", "", new HashJoin(), new JoinCondition("name", JoinCondition.Operation.EQUALLY));
        //temporaryResion.getAllRecords();
    }
}