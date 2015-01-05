package il.bruzn.freelancers.Modele.Entities;

/**
 * Created by Yair on 20/11/2014.
 */
public class Recommendation extends Message {
	private Member	_subject;

	public Recommendation(Member author, Member receiver, String text, Member subject) {
		super(author, receiver, text);
		_subject = subject;
	}

}
