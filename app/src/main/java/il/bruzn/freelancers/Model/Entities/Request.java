package il.bruzn.freelancers.Model.Entities;

import java.util.Date;

/**
 * Created by Yair on 20/11/2014.
 */
public class Request extends Message{
	private boolean _isAccepted;
	private Opinion _opinion;

	public Request(int id, Member author, Member receiver, Opinion opinion, String text, Date date, boolean isAccepted) {
		super(id, author, receiver, text, date);
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
