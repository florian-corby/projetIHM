package model.Items;

import model.Game.Message;

public interface UsableBy
{
	default void isUsedBy(UsableOn u)
	{
		Message.sendGameMessage("Error :> This object has no effect here");
	}
}