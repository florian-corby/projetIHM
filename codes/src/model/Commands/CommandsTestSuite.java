package model.Commands;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({model.Commands.CommandIT.class, model.Commands.ConverterIT.class, model.Commands.ParserIT.class})

public class CommandsTestSuite {
}
