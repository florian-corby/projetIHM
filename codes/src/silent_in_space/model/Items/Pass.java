package silent_in_space.model.Items;


import silent_in_space.model.Utils.Scalar2D;

import java.io.Serializable;

public class Pass extends Item implements UsableOn, Serializable {

	private final PassType PASSTYPE;

	public Pass(String tag, String description, PassType p) {
		super(tag, description, new Scalar2D(0, 0), true, false);
		this.PASSTYPE = p;
	}

	public Pass(String tag, String description, PassType p, Scalar2D scalar2D) {
		super(tag, description, scalar2D, true, false);
		this.PASSTYPE = p;
	}

    public PassType getPassType() {
		return this.PASSTYPE;
	}

	@Override
	public void isUsed(UsableBy u) {
		System.out.println("Your pass must be used on something !");
	}
}

