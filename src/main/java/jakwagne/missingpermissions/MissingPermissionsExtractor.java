package jakwagne.missingpermissions;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

public class MissingPermissionsExtractor implements PublishingConsumer<String, MissingPermission> {

    private final List<Consumer<? super MissingPermission>> listenerList = new CopyOnWriteArrayList<>();
    private boolean collectingClassloader = false;

    private Optional<MissingPermissionBuilder> missingPermissionBuilder = Optional.empty();

    @Override
    public <K extends Consumer<? super MissingPermission>> void addListener(K consumer) {
        listenerList.add(consumer);
    }

    @Override
    public void accept(String line) {
        if (missingPermissionBuilder.isPresent()) {
            if (collectingClassloader) {
                String classLoader = Utils.extractClassLoader(line);
                missingPermissionBuilder.get().setClassLoader(classLoader);
                emit(missingPermissionBuilder.get().createMissingPermission());
                missingPermissionBuilder = Optional.empty();
                collectingClassloader = false;
            } else if (line.contains(Utils.DOMAIN_THAT_FAILED)) {
                String codeSource = Utils.extractCodeSource(line);
                missingPermissionBuilder.get().setCodeSource(codeSource);
                collectingClassloader = true;
            }
        } else {
            if (line.contains(Utils.ACCESS_DENIED)) {
                String[] permissionDenied = Utils.extractPermission(line);
                missingPermissionBuilder = Optional.of(new MissingPermissionBuilder(permissionDenied));
            }
        }
    }

    void emit(MissingPermission missingPermission) {
        listenerList.forEach(c -> c.accept(missingPermission));
    }
}
