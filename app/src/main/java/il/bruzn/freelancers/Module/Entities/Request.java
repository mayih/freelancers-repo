package il.bruzn.freelancers.Module.Entities;

import java.util.Date;

/**
 * Created by Yair on 20/11/2014.
 */
public class Request extends Message{
	private boolean _accepted = false;
	private Opinion _opinion = null;

	public Request(Member author, Member receiver, String text) {
		super(author, receiver, text);
	}
}
