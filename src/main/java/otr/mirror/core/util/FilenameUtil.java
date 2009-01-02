package otr.mirror.core.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.apache.log4j.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import otr.mirror.core.model.Format;

/**
 * Provides some useful methods to extract the nested information of an OTRKEY
 * filename.
 * 
 * @author Marcus Krassmann
 */
public class FilenameUtil {

    public static final int NAME = 1;
    public static final int START_DATE = 2;
    public static final int START_TIME = 3;
    public static final int TV_CHANNEL = 4;
    public static final int DURATION = 5;
    public static final int FORMAT = 6;
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
        String result = "<unknown name>";
        if (m.matches()) {
            result = m.group(NAME).replace('_', ' ');
        }
        return result;
    }

    public static Date getStartDate(String filename) {
        Date result = null;
        DateFormat df = new SimpleDateFormat("yy.MM.dd HH-mm");
        Matcher m = PATTERN.matcher(filename);
        if (m.matches()) {
            try {
                result = df.parse(m.group(START_DATE) + " " + m.group(START_TIME));
            } catch (ParseException ex) {
                LOGGER.error("Should never happen! Regex ensures format!", ex);
            }
        }
        return result;
    }

    public static int getDuration(String filename) {
        int duration = Integer.MIN_VALUE;
        Matcher m = PATTERN.matcher(filename);
        if (m.matches()) {
            duration = Integer.valueOf(m.group(DURATION));
        }
        return duration;
    }

    public static Date getEndDate(String filename) {
        Date start = getStartDate(filename);
        Calendar cal = Calendar.getInstance();
        cal.setTime(start);
        cal.add(Calendar.MINUTE, getDuration(filename));
        return cal.getTime();
    }

    public static String getTvChannel(String filename) {
        Matcher m = PATTERN.matcher(filename);
        String result = "<unknown tv channel>";
        if (m.matches()) {
            result = m.group(TV_CHANNEL);
        }
        return result;
    }

    public static Format getFormat(String filename) {
        Matcher m = PATTERN.matcher(filename);
        Format result = Format.UNKNOWN;
        if (m.matches()) {
            String format = m.group(FORMAT);
            if (format.equalsIgnoreCase("avi")) {
                result = Format.AVI;
            } else if (format.equalsIgnoreCase("hq.avi")) {
                result = Format.HQ;
            } else if (format.equalsIgnoreCase("mp4")) {
                result = Format.MP4;
            }
        }
        return result;
        
    }
}
