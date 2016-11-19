package yaruliy.machine;
import yaruliy.bloom.BloomFilter;

public class MainFrame {
    public static void main(String a[]){
        /*IMDGObject object = new User(1, "name", "sername", 5);
        System.out.println("All Dependency:");
        System.out.println(object.getObjectDependency().getAllDependencyInString());*/

        BloomFilter bf = new BloomFilter(1000, 10);

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