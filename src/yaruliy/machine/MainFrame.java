package yaruliy.machine;
import yaruliy.data.SimpleIMDGObject;
import yaruliy.data.User;

public class MainFrame {
    public static void main(String a[]){
        SimpleIMDGObject object = new User(1, "name", "sername", 5);
        System.out.println("All Dependency:");
        System.out.println(object.getObjectDependency().getAllDependencyInString());
    }
}