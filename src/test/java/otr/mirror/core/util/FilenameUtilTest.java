package otr.mirror.core.util;

import java.util.Calendar;
import java.util.Date;
import junit.framework.TestCase;
import otr.mirror.core.model.Format;

/**
 * Tests all methods of FilenameUtil.
 *
 * @author Marcus Krassmann
 */
public class FilenameUtilTest extends TestCase {

    private String[] files = {
        "Katie_Melua_Live_in_Duesseldorf_08.12.31_05-15_3sat_45_TVOON_DE.mpg.avi.otrkey",
        "X_Men_Der_letzte_Widerstand_09.01.01_20-15_rtl_120_TVOON_DE.mpg.HQ.avi.otrkey",
        "Alexander_09.01.01_20-15_pro7_185_TVOON_DE.mpg.mp4.otrkey"
    };
    
    public FilenameUtilTest(String testName) {
        super(testName);
    }

    /**
     * Test of getName method, of class FilenameUtil.
     */
    public void testGetName() {
        System.out.println("getName");
        String filename = files[0];
        String expResult = "Katie Melua Live in Duesseldorf";
        String result = FilenameUtil.getName(filename);
        assertEquals(expResult, result);
    }

    /**
     * Test of getStartDate method, of class FilenameUtil.
     */
    public void testGetStartDate() {
        System.out.println("getStartDate");
        String filename = files[0];
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(2008, 11, 31, 5, 15);
        Date expResult = cal.getTime();
        Date result = FilenameUtil.getStartDate(filename);
        assertEquals(expResult, result);
    }

    /**
     * Test of getDuration method, of class FilenameUtil.
     */
    public void testGetDuration() {
        System.out.println("getDuration");
        String filename = files[2];
        int expResult = 185;
        int result = FilenameUtil.getDuration(filename);
        assertEquals(expResult, result);
    }

    /**
     * Test of getEndDate method, of class FilenameUtil.
     */
    public void testGetEndDate() {
        System.out.println("getEndDate");
        String filename = files[0];
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(2008, 11, 31, 6, 0);
        Date expResult = cal.getTime();
        Date result = FilenameUtil.getEndDate(filename);
        assertEquals(expResult, result);
    }

    /**
     * Test of getTvChannel method, of class FilenameUtil.
     */
    public void testGetTvChannel() {
        System.out.println("getTvChannel");
        String filename = files[0];
        String expResult = "3sat";
        String result = FilenameUtil.getTvChannel(filename);
        assertEquals(expResult, result);
    }

    /**
     * Test of getFormat method, of class FilenameUtil.
     */
    public void testGetFormat() {
        // positive examples
        System.out.println("getFormat");
        String filename = files[0];
        Format expResult = Format.AVI;
        Format result = FilenameUtil.getFormat(filename);
        assertSame(expResult, result);

        filename = files[1];
        expResult = Format.HQ;
        result = FilenameUtil.getFormat(filename);
        assertSame(expResult, result);

        filename = files[2];
        expResult = Format.MP4;
        result = FilenameUtil.getFormat(filename);
        assertSame(expResult, result);

        // negative example
        filename = "Some_crappy_otrkey";
        expResult = Format.UNKNOWN;
        result = FilenameUtil.getFormat(filename);
        assertSame(expResult, result);
    }
}
