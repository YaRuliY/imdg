package yaruliy.data;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

class DependencyInjector {
    private DependencyInjector(){}

    static ObjectDependency getDependency(int nestingLevel) {
        ObjectDependency objectDependency = new ObjectDependency(9999, nestingLevel, "Father Dependency");
        ObjectDependency original = objectDependency;
        for (int i = 0; i < nestingLevel; i++) {
            objectDependency = objectDependency.setBaseObjectDependency(new ObjectDependency(i, nestingLevel, getDependencyValue()));
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