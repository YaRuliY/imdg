package yaruliy.data.model;
import yaruliy.data.IMDGObject;

public class Book extends IMDGObject {
    public Book(long id, String name, String serName, int dependencyCount) {
        super(id, name, serName, dependencyCount);
    }
}