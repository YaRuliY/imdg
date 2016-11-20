package yaruliy.data.model;
import yaruliy.data.IMDGObject;

public class User extends IMDGObject {
    public User(long id, String name, String serName, int dependencyCount) {
        super(id, name, serName, dependencyCount);
    }
}
