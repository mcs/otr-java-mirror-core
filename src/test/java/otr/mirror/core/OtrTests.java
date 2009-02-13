package otr.mirror.core;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

/**
 * This convenience class sets a default value for ContextConfiguration.
 * Its locations-parameter defaults to <tt>classpath:spring-context.xml</tt>
 *
 * @author Marcus Krassmann
 */
@ContextConfiguration(locations = "classpath:spring-context.xml")
public abstract class OtrTests extends AbstractTransactionalJUnit4SpringContextTests {

}
