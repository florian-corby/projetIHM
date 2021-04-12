package controller.Characters;

public interface Attacker
{
	default void attack(Attackable a)
	{
		a.isAttacked(this);
	}
}