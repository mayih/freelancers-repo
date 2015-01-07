package il.bruzn.freelancers.Model.Entities;

/**
 * Created by Yair on 20/11/2014.
 */
public class Request extends Message{
	private boolean _isAccepted;
	private Opinion _opinion;

	public Request(Member author, Member receiver, String text, boolean isAccepted, Opinion opinion) {
		super(author, receiver, text);
		_isAccepted = isAccepted;
		_opinion = opinion;

	}

	public boolean getIsAccepted() {
		return _isAccepted;
	}

	public Opinion getOpinion() {
		return _opinion;
	}
}
