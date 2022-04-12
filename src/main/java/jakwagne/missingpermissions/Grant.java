package jakwagne.missingpermissions;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Grant {
    private final String codeBase;
    private final Map<Permission, Set<String>> permissions = new HashMap<>();

    public Grant(String codeBase) {
        this.codeBase = codeBase;
    }

    public void addPermission(String[] permission, String classLoader) {
        Set<String> strings = permissions.computeIfAbsent(new Permission(permission), e -> new HashSet<>());
        strings.add(classLoader);
    }

    public String asPolicyEntry(boolean showClassLoaders) {
        StringBuilder sb = new StringBuilder();
        sb.append("grant ");
        if (!codeBase.equals("null")) {
            sb.append("codeBase ");
            sb.append('"');
            sb.append(codeBase);
            sb.append('"');
            sb.append(' ');
        }
        sb.append('{');
        sb.append('\n');
        for (Map.Entry<Permission, Set<String>> mapEntry : permissions.entrySet()) {
            Permission permission = mapEntry.getKey();
            Set<String> classLoaders = mapEntry.getValue();
            if (showClassLoaders) {
                if (classLoaders.size() == 1) {
                    sb.append(permission.asPolicyEntry());
                    sb.append(" //");
                    sb.append(classLoaders.iterator().next());
                } else {
                    for (String classLoader : classLoaders) {
                        sb.append("\t//");
                        sb.append(classLoader);
                        sb.append('\n');
                    }
                    sb.append(permission.asPolicyEntry());
                }
            } else {
                sb.append(permission.asPolicyEntry());

            }
            sb.append('\n');
        }
        sb.append('}');
        sb.append(';');
        sb.append('\n');
        return sb.toString();
    }
}
