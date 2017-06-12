package yaruliy.data;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public final class DependencyInjector {
    private DependencyInjector(){}

    static IMDGObject getDependency(int nestingLevel) {
        IMDGObject objectDependency = new IMDGObject(9999, nestingLevel, "Father Dependency");
        IMDGObject original = objectDependency;
        for (int i = 0; i < nestingLevel; i++) {
            objectDependency = objectDependency.setBaseObjectDependency(new IMDGObject(i, nestingLevel, getDependencyValue()));
        }
        return original;
    }

    private static String getDependencyValue(){
        try {
            BufferedReader br = new BufferedReader(new FileReader("resources/text.txt"));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            return sb.toString();
        }
        catch (IOException e){e.printStackTrace();}
        return "File Not Read";
    }
}