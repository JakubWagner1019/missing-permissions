package jakwagne.missingpermissions;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class UtilsTest {

    @Test
    public void extractsPermission() {
        String[] expected = new String[]{
                "java.net.SocketPermission",
                "127.0.0.1:1099",
                "connect,resolve"
        };
        String line = "access: access denied (\"java.net.SocketPermission\" \"127.0.0.1:1099\" \"connect,resolve\")\n";
        assertArrayEquals(expected,Utils.extractPermission(line));
    }

    @Test
    public void extractsCodeSource() {
        String expected = "file:/usr/local/tomcat/bin/tomcat-juli.jar";
        String line = "access: domain that failed ProtectionDomain  (file:/usr/local/tomcat/bin/tomcat-juli.jar <no signer certificates>)\n";
        assertEquals(expected, Utils.extractCodeSource(line));
    }

    @Test
    public void extractsClassLoader() {
        String expected = "sun.misc.Launcher$AppClassLoader@764c12b6";
        String line = " sun.misc.Launcher$AppClassLoader@764c12b6";
        assertEquals(expected, Utils.extractClassLoader(line));
    }
}