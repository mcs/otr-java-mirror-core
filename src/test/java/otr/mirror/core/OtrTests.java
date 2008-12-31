package otr.mirror.core;

import org.springframework.test.AbstractTransactionalSpringContextTests;

/**
 * This convenience class sets a default value for {@link #getConfigLocations()}.
 * It defaults to <tt>classpath:spring-context.xml</tt>
 *
 * @author Marcus Krassmann
 */
public class OtrTests extends AbstractTransactionalSpringContextTests {

    public OtrTests(String name) {
        super(name);
    }

    public OtrTests() {
    }

    @Override
    protected String[] getConfigLocations() {
        return new String[] {"classpath:spring-context.xml"};
    }


}
