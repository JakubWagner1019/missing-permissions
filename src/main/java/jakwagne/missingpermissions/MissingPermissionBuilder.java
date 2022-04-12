package jakwagne.missingpermissions;

public class MissingPermissionBuilder {
    private final String[] permissionDenied;
    private String codeSource;
    private String classLoader;

    public MissingPermissionBuilder(String[] permissionDenied) {
        this.permissionDenied = permissionDenied;
    }

    public MissingPermissionBuilder setCodeSource(String codeSource) {
        this.codeSource = codeSource;
        return this;
    }

    public MissingPermissionBuilder setClassLoader(String classLoader) {
        this.classLoader = classLoader;
        return this;
    }

    public MissingPermission createMissingPermission() {
        return new MissingPermission(permissionDenied, codeSource, classLoader);
    }
}