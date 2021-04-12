package controller.Items;

public interface UsableBy
{
	default void isUsedBy(UsableOn u)
	{
		System.out.println("Error :> This object has no effect here");
	}
}