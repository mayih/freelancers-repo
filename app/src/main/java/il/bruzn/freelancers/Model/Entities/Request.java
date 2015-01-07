package il.bruzn.freelancers.Model.Entities;

/**
 * Created by Yair on 20/11/2014.
 */
public class Request extends Message{
	private boolean _isAccepted;
	private Opinion _opinion;

	public Request(Member author, Member receiver, String text) {
		super(author, receiver, text);
		_isAccepted = false;
		_opinion = null;
	}

	public boolean getIsAccepted() {
		return _isAccepted;
	}
	public Opinion getOpinion() {
		return _opinion;
	}

	public Request setAccepted(boolean isAccepted) {
		_isAccepted = isAccepted;
		return this;
	}
	public Request setOpinion(Opinion opinion) {
		_opinion = opinion;
		return this;
	}
}
