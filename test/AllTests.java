import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ ArrayTest.class, BooleanTest.class, ElementTest.class,
		NullTest.class, NumberTest.class, StringTest.class })
public class AllTests {

}
