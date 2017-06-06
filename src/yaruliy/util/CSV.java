package yaruliy.util;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CSV {
    private static String source = Util.getSource();

    public static void writeCSVIntoFile(String csv, String joinType){
        try(Writer writer = new BufferedWriter(new FileWriter(source + "/" + joinType + ".csv", true))){
            writer.write(csv);
            writer.write("\n");
            writer.close();
        } catch (IOException e) { e.printStackTrace(); }
    }

    public static void writeCSVIntoFile(ArrayList ar, String joinType){
        writeCSVIntoFile(ar.toString(), joinType);
    }
}