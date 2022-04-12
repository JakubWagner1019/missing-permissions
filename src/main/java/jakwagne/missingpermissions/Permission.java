package jakwagne.missingpermissions;

import java.util.Arrays;
import java.util.Objects;

public class Permission {
    private final String className;
    private final String[] arguments;

    public Permission(String[] permission) {
        this.className = permission[0];
        this.arguments = new String[permission.length - 1];
        System.arraycopy(permission, 1, this.arguments, 0, permission.length - 1);
    }

    public String asPolicyEntry() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arguments.length; i++) {
            if (i > 0) {
                sb.append(',');
            }
            sb.append(' ');
            sb.append('"');
            sb.append(arguments[i]);
            sb.append('"');
        }
        return String.format(
                "\tpermission %s%s;",
                className,
                sb
        );
    }

    @Override
    public String toString() {
        return "Permission{" +
                "className='" + className + '\'' +
                ", arguments=" + Arrays.toString(arguments) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Permission that = (Permission) o;
        return Objects.equals(className, that.className) && Arrays.equals(arguments, that.arguments);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(className);
        result = 31 * result + Arrays.hashCode(arguments);
        return result;
    }
}
