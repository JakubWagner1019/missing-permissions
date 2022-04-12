package jakwagne.missingpermissions;

import java.util.Arrays;
import java.util.Objects;

public class MissingPermission implements Comparable<MissingPermission> {

    private final String[] permissionDenied;
    private final String codeSource;
    private final String classLoader;

    public MissingPermission(String[] permissionDenied, String codeSource, String classLoader) {
        this.permissionDenied = permissionDenied;
        this.codeSource = codeSource;
        this.classLoader = classLoader;
    }

    public String[] getPermissionDenied() {
        return permissionDenied;
    }

    public String getCodeSource() {
        return codeSource;
    }

    public String getClassLoader() {
        return classLoader;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MissingPermission that = (MissingPermission) o;
        return Arrays.equals(permissionDenied, that.permissionDenied) && Objects.equals(codeSource, that.codeSource) && Objects.equals(classLoader, that.classLoader);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(codeSource, classLoader);
        result = 31 * result + Arrays.hashCode(permissionDenied);
        return result;
    }

    @Override
    public String toString() {
        return "MissingPermission{" +
                "permissionDenied=" + Arrays.toString(permissionDenied) +
                ", codeSource='" + codeSource + '\'' +
                ", classLoader='" + classLoader + '\'' +
                '}';
    }


    @Override
    public int compareTo(MissingPermission o) {
        if (codeSource.compareTo(o.codeSource) != 0)
            return codeSource.compareTo(o.codeSource);
        if (classLoader.compareTo(o.classLoader) != 0)
            return classLoader.compareTo(o.classLoader);
        if (permissionDenied.length != o.permissionDenied.length)
            return Integer.compare(permissionDenied.length, o.permissionDenied.length);
        for (int i = 0; i < permissionDenied.length; i++) {
            if (permissionDenied[i].compareTo(o.permissionDenied[i]) != 0)
                return permissionDenied[i].compareTo(o.permissionDenied[i]);
        }
        return 0;
    }
}
