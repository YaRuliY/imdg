package yaruliy.machine;
import yaruliy.algorithm.HashJoin;
import yaruliy.data.IMDGObject;
import yaruliy.db.JoinResult;
import yaruliy.db.Warehouse;

public class MainFrame {
    public static void main(String a[]){
        Warehouse warehouse = new Warehouse();
        IMDGObject book1 = new IMDGObject("name1", "sername2", 3);
        IMDGObject book2 = new IMDGObject("name2", "sername2", 3);
        IMDGObject book3 = new IMDGObject("name3", "sername3", 3);
        IMDGObject user3 = new IMDGObject("user3", "sername3", 3);
        IMDGObject user4 = new IMDGObject("user4", "sername3", 3);

        IMDGObject user5 = new IMDGObject("user5", "sername3", 3);
        IMDGObject user6 = new IMDGObject("user6", "sername3", 3);
        IMDGObject user7 = new IMDGObject("user6", "sername3", 3);

        String region = "Region0";
        warehouse.addObject(book1, region);
        warehouse.addObject(book2, region);
        warehouse.addObject(book3, region);
        warehouse.addObject(user3, region);
        warehouse.addObject(user4, region);

        String region2 = "Region1";
        user5.setId(0);
        user6.setId(1);
        warehouse.addObject(user5, region2);
        warehouse.addObject(user6, region2);
        warehouse.addObject(user7, region2);

        System.out.println("---------GET objects---------");
        for (IMDGObject object : warehouse.getRegionByName(region).getAllRecords())
            System.out.println(object.getName() + "[" + object.getID() + "]");
        System.out.println("Region 2:");
        for (IMDGObject object : warehouse.getRegionByName(region2).getAllRecords())
            System.out.println(object.getName() + "[" + object.getID() + "]");

        JoinResult temporary = warehouse.executeJOIN(region, region2, new HashJoin(), "name");

        System.out.println("---------GET JOIN objects---------");
        for (IMDGObject[] objectCouple : temporary.getResultArray())
            System.out.println(objectCouple[0].getName() + " -- " + objectCouple[1].getName());
    }
}