package yaruliy.data;

public class DependencyInjector {
    public static ObjectDependency getDependency(int nestingLevel) {
        ObjectDependency objectDependency = new ObjectDependency(9999, nestingLevel);
        ObjectDependency original = objectDependency;
        for (int i = 0; i < nestingLevel; i++) {
            objectDependency = objectDependency.setBaseObjectDependency(new ObjectDependency(i, nestingLevel));
        }
        return original;
    }
}