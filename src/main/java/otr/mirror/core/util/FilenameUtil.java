package otr.mirror.core.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.apache.log4j.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Provides some useful methods to extract the nested information of an OTRKEY
 * filename.
 * 
 * @author Marcus Krassmann
 */
public class FilenameUtil {

    private static final Logger LOGGER = Logger.getLogger(FilenameUtil.class);
    /**
     * This pattern is divided into six groups:
     * 1. the filename ("Name_of_the_Recording")
     * 2. the date of the recording ("08.12.31")
     * 3. the start time of the recording ("16-45")
     * 4. the tv channel ("pro7")
     * 5. the duration of the recording in minutes ("135")
     * 6. the video format ("avi", "HQ.avi" or "mp4")
     */
    private static final Pattern PATTERN = Pattern.compile("(\\w+)_(\\d\\d\\.\\d\\d\\.\\d\\d)_(\\d\\d-\\d\\d)_(\\w+)_(\\d+)_TVOON_DE\\.mpg\\.(avi|HQ\\.avi|mp4)\\.otrkey");

    private FilenameUtil() {
        // static utility class
    }

    public static String getName(String filename) {
        Matcher m = PATTERN.matcher(filename);
        String result = "";
        if (m.matches()) {
            result = m.group(1).replace('_', ' ');
        }
        return result;
    }

    public static Date getStartDate(String filename) {
        DateFormat df = new SimpleDateFormat("yy.MM.dd HH-mm");
        Matcher m = PATTERN.matcher(filename);
        if (m.matches()) {
            try {
                return df.parse(m.group(2) + " " + m.group(3));
            } catch (ParseException ex) {
                LOGGER.error("Should never happen! Regex ensures format!", ex);
            }
        }
        return null;
    }

    public static Date getEndDate(String filename) {
        Matcher m = PATTERN.matcher(filename);
        if (m.matches()) {
            int duration = Integer.valueOf(m.group(5));
            Date start = getStartDate(filename);
            Calendar cal = Calendar.getInstance();
            cal.setTime(start);
            cal.add(Calendar.MINUTE, duration);
            return cal.getTime();
        }
        return null;


    }
}
