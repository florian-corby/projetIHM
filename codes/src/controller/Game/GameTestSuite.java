package controller.Game;

import controller.Characters.CharactersTestSuite;
import controller.Commands.CommandsTestSuite;
import controller.Containers.ContainersTestSuite;
import controller.Doors.DoorsTestSuite;
import controller.Items.ItemsTestSuite;
import controller.Location.LocationTestSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({CharactersTestSuite.class, CommandsTestSuite.class, ContainersTestSuite.class,
        DoorsTestSuite.class, ItemsTestSuite.class, LocationTestSuite.class})

public class GameTestSuite {
}