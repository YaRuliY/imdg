package yaruliy.machine;
import yaruliy.bloom.BloomFilter;
import yaruliy.data.SimpleIMDGObject;
import yaruliy.data.User;

public class MainFrame {
    public static void main(String a[]){
        /*SimpleIMDGObject object = new User(1, "name", "sername", 5);
        System.out.println("All Dependency:");
        System.out.println(object.getObjectDependency().getAllDependencyInString());*/

        BloomFilter bf = new BloomFilter(1000, 10);
        System.out.println("Query for 2000: " + bf.mightContain(2000));
        System.out.println("Adding 2000");
        bf.add(2000);
        System.out.println("Query for 2000: " + bf.mightContain(2000));

        for (int i = 0; i < 100; i++){
            bf.add(i*100);
            System.out.println("Adding for " + i*100);
        }
        System.out.println();

        for (int i = 0; i < 150; i++){

                System.out.println("Query for " + i * 100 + ": " + bf.mightContain(i * 100));

        }
    }
}