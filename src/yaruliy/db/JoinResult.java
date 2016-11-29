package yaruliy.db;
import yaruliy.data.IMDGObject;
import java.util.ArrayList;

public class JoinResult {
    private ArrayList<IMDGObject[]> resultArray;
    public JoinResult() { this.resultArray = new ArrayList<>(); }
    public void addObjectsCouple(IMDGObject[]mas){ this.resultArray.add(mas);}

    public void printResults(){
        if (this.resultArray.size() > 0){
            System.out.println("##########################################");
            System.out.println("JOIN Results(" + this.resultArray.size() + ") :");
            for (int i = 0; i < this.resultArray.size(); i++){
                System.out.printf("%-6s [%-7s] -- ", resultArray.get(i)[0].getName(), resultArray.get(i)[0].getHashID());
                if(resultArray.get(i)[1] != null)
                    System.out.printf("%-6s [%-7s]\n", resultArray.get(i)[1].getName(), resultArray.get(i)[1].getHashID());
                else System.out.println("null");
                if (i < this.resultArray.size()-1){
                    if (!resultArray.get(i)[0].getHashID().equals(resultArray.get(i+1)[0].getHashID()))
                        System.out.println("----------------------------------------");
                }
            }
        }
        else System.out.println("Result Join Set is Empty");
    }
}