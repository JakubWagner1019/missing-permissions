Missing permissions
-
Translates STDERR output of *-Djava.security.debug=access,failure* to required policy entries

Note: The tool wasn't originally built to be robust. 
Expect mistakes when parsing output with many permission exceptions thrown concurrently at the same time.

---

Build (Java 8+)

mvn clean package

Run 

*java -jar missing-permissions.jar <std_err_file>*

or

*java -jar missing-permissions.jar <std_err_file> verbose*

to print additional info about classloaders