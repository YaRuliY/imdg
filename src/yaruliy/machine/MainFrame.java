package yaruliy.machine;
import yaruliy.data.IMDGObject;
import yaruliy.db.Region;
import yaruliy.db.Warehouse;

public class MainFrame {
    public static void main(String a[]){
        /*BloomFilter bf = new BloomFilter(5000, 10);

        for (int i = 0; i < 100; i++){
            bf.add(i*100);
        }

        *//*Logger.log("BEGIN: ", false);
        for (int i = 0; i < 150; i++){
            Logger.log("Query for " + i * 100 + ": " + bf.mightContain(i * 100));
        }*//*

        int count = 0;
        for (int i = 100; i < 150; i++){
            if (bf.mightContain(i * 100)){
                count++;
            }
        }
        System.out.println("FALSE_POSITIVE count:" + count);*/

        Warehouse warehouse = new Warehouse();
        IMDGObject book1 = new IMDGObject(0, "name1", "sername2", 3);
        IMDGObject book2 = new IMDGObject(0, "name2", "sername2", 3);
        IMDGObject book3 = new IMDGObject(0, "name3", "sername3", 3);

        warehouse.addObject("book1", book1, Region.class);
        warehouse.addObject("book2", book2, Region.class);
        warehouse.addObject("book3", book3, Region.class);

        System.out.println("---------GETobjects---------");

        System.out.println(warehouse.getObject("book1", Region.class).getName());
        System.out.println(warehouse.getObject("book2", Region.class).getName());
        System.out.println(warehouse.getObject("book3", Region.class).getName());
    }
}