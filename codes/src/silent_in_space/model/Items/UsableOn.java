package silent_in_space.model.Items;

public interface UsableOn
{
	default void isUsedOn(UsableBy u)
	{
		u.isUsedBy(this);
	}
}