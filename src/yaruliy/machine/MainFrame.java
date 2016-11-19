package yaruliy.machine;
import yaruliy.data.SimpleIMDGObject;

public class MainFrame {
    public static void main(String a[]){
        SimpleIMDGObject object = new SimpleIMDGObject(1, "name", "sername", 5);
        System.out.println("All Dependency:");
        System.out.println(object.getObjectDependency().getAllDependencyInString());
    }
}