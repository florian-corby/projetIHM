package Game;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({Characters.CharactersTestSuite.class, Commands.CommandsTestSuite.class, Containers.ContainersTestSuite.class,
        Doors.DoorsTestSuite.class, Items.ItemsTestSuite.class, Location.LocationTestSuite.class})

public class GameTestSuite {
}