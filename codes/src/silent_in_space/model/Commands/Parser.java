package silent_in_space.model.Commands;

import silent_in_space.model.Game.Message;

public class Parser {

	private final Verb VERB;

	public Parser(String verb) throws UnknownVerb
	{
		try {
			this.VERB = this.isValidVerb(verb.toLowerCase());
		}

		catch(UnknownVerb e)
		{
			Message.sendGameMessage("This verb doesn't exist");
			throw e;
		}
	}

	public Verb isValidVerb(String verb) throws UnknownVerb
	{
		Verb[] verbs = Verb.values();
		Verb res = null;

		for(Verb v : verbs)
		{
			if(verb.equals(v.getString()))
				res = v;
		}

		if(res != null)
			return res;

		else
			throw new UnknownVerb();
	}

	public Verb getVerb()
	{
		return this.VERB;
	}
}