package yaruliy.db;
import yaruliy.data.IMDGObject;
import java.util.ArrayList;

public class JoinResult {
    private ArrayList<IMDGObject[]> resultArray;

    public JoinResult() {
        this.resultArray = new ArrayList<>();
    }

    public void addObjectsCouple(IMDGObject[]mas){
        this.resultArray.add(mas);
    }

    public ArrayList<IMDGObject[]> getResultArray(){
        return this.resultArray;
    }

    public void printResults(){
        if (this.resultArray.size() > 0){
            System.out.println("JOIN Results: ");
            for (IMDGObject[] objectCouple : this.resultArray)
                System.out.println(objectCouple[0].getName() + "[" + objectCouple[0].getHashID() + "]" + " -- " +
                        objectCouple[1].getName() + "[" + objectCouple[0].getHashID() + "]");
        }
    }
}