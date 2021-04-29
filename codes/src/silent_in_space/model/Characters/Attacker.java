package silent_in_space.model.Characters;

public interface Attacker
{
	default void attack(Attackable a)
	{
		a.isAttacked(this);
	}
}