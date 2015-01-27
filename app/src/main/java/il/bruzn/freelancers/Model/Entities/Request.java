package il.bruzn.freelancers.Model.Entities;

import java.util.Date;

/**
 * Created by Yair on 20/11/2014.
 */
public class Request extends Message{
	private Boolean _isAccepted;
	private Opinion _opinion;
	private boolean _isDone;

	public Request(Member author, Member receiver, String text) {
		super(author, receiver, text);
		_isDone = false;
	}

	public Request(int id, Member author, Member receiver, Opinion opinion, String text, Date date, Boolean isAccepted, boolean isDone) {
		super(id, author, receiver, text, date);
		_isAccepted = isAccepted;
		_opinion = opinion;
		_isDone = isDone;


	}

	public Boolean getIsAccepted() {
		return _isAccepted;
	}

	public Opinion getOpinion() {
		return _opinion;
	}

	public boolean getisDone() {
		return _isDone;
	}

	public void setAccepted(Boolean isAccepted) {
		_isAccepted = isAccepted;
	}

	public void setOpinion(Opinion opinion) {
		_opinion = opinion;
	}

	public void setDone(boolean isDone) {
		_isDone = isDone;
	}
}
