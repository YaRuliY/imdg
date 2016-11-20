package yaruliy.db;
import yaruliy.data.IMDGObject;

public interface Region {
    void addObject(String key, IMDGObject object);
    IMDGObject getObject(String key);
}