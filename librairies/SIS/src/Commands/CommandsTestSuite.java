package Commands;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({Commands.CommandIT.class, Commands.ConverterIT.class, Commands.ParserIT.class})

public class CommandsTestSuite {
}
