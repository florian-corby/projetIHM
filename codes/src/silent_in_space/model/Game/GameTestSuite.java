package silent_in_space.model.Game;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({silent_in_space.model.Characters.CharactersTestSuite.class, silent_in_space.model.Commands.CommandsTestSuite.class, silent_in_space.model.Containers.ContainersTestSuite.class,
        silent_in_space.model.Doors.DoorsTestSuite.class, silent_in_space.model.Items.ItemsTestSuite.class, silent_in_space.model.Location.LocationTestSuite.class})

public class GameTestSuite {
}