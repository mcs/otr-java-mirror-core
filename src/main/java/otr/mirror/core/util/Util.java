package otr.mirror.core.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author Marcus Krassmann
 */
public class Util {

    private static final Log logger = LogFactory.getLog(Util.class);
    
    private Util() {
        // static utility class!
    }

    /**
     * Returns the system load on a *NIX system, parsed by the command "uptime".
     * <p>
     * The result is an array with three values:
     * <ul>
     * <li>load average for the past one minute</li>
     * <li>load average for the past five minutes</li>
     * <li>load average for the past fifteen minutes</li>
     * </ul>
     * If the call of uptime fails, the result is an double[] with length zero.
     * @return the system loads
     */
    public static double[] getLoadAverages() {
        try {
            // execute uptime command
            Process exec = Runtime.getRuntime().exec("uptime");
            // catch output
            InputStream is = exec.getInputStream();
            byte[] buf = new byte[1024];
            // read output into (oversized) buffer
            is.read(buf);
            String s = new String(buf).trim();

            // parse load averages
            String[] parts = s.split("load average:");
            s = parts[1];
            parts = s.split(",");
            double[] load = new double[3];
            for (int i = 0; i < parts.length; i++) {
                load[i] = Double.parseDouble(parts[i].trim());
            }
            return load;
        } catch (IOException ex) {
            return new double[0];
        }

    }
}
