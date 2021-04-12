package controller.Items;

public interface UsableOn
{
	default void isUsedOn(UsableBy u)
	{
		u.isUsedBy(this);
	}
}