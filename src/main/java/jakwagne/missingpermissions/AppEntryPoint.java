package jakwagne.missingpermissions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class AppEntryPoint {

    public static void main(String[] args) throws IOException {
        if (args.length < 1 || args.length > 2) {
            printUsage();
            System.exit(1);
        }
        String arg = args[0];
        Path path = Paths.get(arg);

        boolean verbose = false;
        if(args.length == 2){
            if(args[1].equalsIgnoreCase("verbose")){
                verbose = true;
            } else {
                printUsage();
                System.exit(2);
            }
        }

        PublishingConsumer<String, MissingPermission> permissionPublishingConsumer = new MissingPermissionsExtractor();
        SortedSet<MissingPermission> sortedSet = new TreeSet<>();
        permissionPublishingConsumer.addListener(sortedSet::add);
        Files.lines(path).forEach(permissionPublishingConsumer);

        Map<String, Grant> grantMap = new HashMap<>();
        sortedSet.forEach(missingPermission ->
                grantMap.computeIfAbsent(missingPermission.getCodeSource(), Grant::new)
                        .addPermission(
                                missingPermission.getPermissionDenied(),
                                missingPermission.getClassLoader())
        );

        boolean finalVerbose = verbose;
        String policyOutput = grantMap.values().stream()
                .map(grant -> grant.asPolicyEntry(finalVerbose))
                .collect(Collectors.joining("\n"));
        System.out.print(policyOutput);
    }

    private static void printUsage() {
        System.out.println("Usage:");
        System.out.println("java -jar missing-permissions.jar <std_err_file>");
        System.out.println("or");
        System.out.println("java -jar missing-permissions.jar <std_err_file> verbose");
        System.out.println("to print additional information about classloaders.");
    }

}
