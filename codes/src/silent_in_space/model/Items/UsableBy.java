package silent_in_space.model.Items;

import silent_in_space.model.Game.Message;

public interface UsableBy
{
	default void isUsedBy(UsableOn u)
	{
		Message.sendGameMessage("Error :> This object has no effect here");
	}
}