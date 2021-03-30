package model.Game;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({model.Characters.CharactersTestSuite.class, model.Commands.CommandsTestSuite.class, model.Containers.ContainersTestSuite.class,
        model.Doors.DoorsTestSuite.class, model.Items.ItemsTestSuite.class, model.Location.LocationTestSuite.class})

public class GameTestSuite {
}