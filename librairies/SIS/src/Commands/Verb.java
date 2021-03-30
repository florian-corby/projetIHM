package Commands;

public enum Verb {
	ATTACK("attack"), BACK("back"), DROP("drop"), GO("go"), HELP("help"),
	INFO("info"), INVENTORY("inventory"), LOAD("load"), LOOK("look"), QUIT("quit"),
	SAVE("save"), SEARCH("search"), TAKE("take"), TALK("talk"), USE("use"), GIVE("give");
	private final String STRVALUE;

	Verb(String s)
	{
		this.STRVALUE = s;
	}

	public String getString()
	{
		return this.STRVALUE;
	}
}