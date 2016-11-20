package yaruliy.db.custom;
import yaruliy.data.IMDGObject;
import yaruliy.db.Node;
import yaruliy.db.Region;
import java.util.ArrayList;

public class UserRegion implements Region {
    private ArrayList<Node> nodes;


    public UserRegion(){}

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    @Override
    public void addObject(String key, IMDGObject object) {

    }

    @Override
    public IMDGObject getObject(String key) {
        return null;
    }
}