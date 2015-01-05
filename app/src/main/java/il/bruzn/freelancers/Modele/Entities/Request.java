package il.bruzn.freelancers.Modele.Entities;

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
