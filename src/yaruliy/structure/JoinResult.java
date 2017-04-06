package yaruliy.structure;
import yaruliy.data.IMDGObject;
import java.util.ArrayList;

public class JoinResult {
    private ArrayList<IMDGObject[]> resultArray;
    private String joinType;
    public void addObjectsCouple(IMDGObject[]mas){ this.resultArray.add(mas);}
    public JoinResult(String joinType) {
        this.joinType = joinType.substring(joinType.lastIndexOf('.') + 1, joinType.length());
        this.resultArray = new ArrayList<>();
    }

    public void printResults(){
        System.out.println("\n##########################################");
        if (this.resultArray.size() > 0){
            System.out.println("JOIN(" + this.joinType + ") Results(" + this.resultArray.size() + ") :");
            for (int i = 0; i < this.resultArray.size(); i++){
                System.out.printf("%-6s [%-10s] -- ", resultArray.get(i)[0].getName(), resultArray.get(i)[0].getHashID());
                if(resultArray.get(i)[1] != null)
                    System.out.printf("%-6s [%-10s]\n", resultArray.get(i)[1].getName(), resultArray.get(i)[1].getHashID());
                else System.out.println("null");
                if (i < this.resultArray.size()-1){
                    if (!resultArray.get(i)[0].getHashID().equals(resultArray.get(i+1)[0].getHashID()))
                        System.out.println("------------------------------------------");
                }
            }
        }
        else System.out.println("JOIN(" + this.joinType + ") Results(0) : Set is Empty");
    }
}