package model.Items;


import java.io.Serializable;

public class Pass extends Item implements UsableOn, Serializable {

	private final PassType PASSTYPE;

	public Pass(String tag, String description, PassType p) {
		super(tag, description, true, false);
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

