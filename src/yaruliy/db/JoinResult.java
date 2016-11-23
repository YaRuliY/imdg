package yaruliy.db;
import yaruliy.data.IMDGObject;
import java.util.ArrayList;

public class JoinResult {
    private ArrayList<IMDGObject[]> resultArray;

    public JoinResult() {
        this.resultArray = new ArrayList<>();
    }

    public void addObjects(IMDGObject[]mas){
        this.resultArray.add(mas);
    }

    public ArrayList<IMDGObject[]> getResultArray(){
        return this.resultArray;
    }
}