package il.bruzn.freelancers.Model.Entities;

import java.util.Date;

/**
 * Created by Yair on 20/11/2014.
 */
public class Request extends Message{
	private boolean _isAccepted;
	private boolean _done;
	private Opinion _opinion;

	// CONSTRUCTORS ---
	public Request(Member author, Member receiver, String text) {
		super(author, receiver, text);
		_done = false;
	}
	public Request(int id, Member author, Member receiver, String text, Date date, boolean isAccepted, boolean done, Opinion opinion) {
		super(id, author, receiver, text, date);
		_isAccepted = isAccepted;
		_done = done;
		_opinion = opinion;
	}

	// Getters & setters ---
	public boolean isAccepted() {
		return _isAccepted;
	}
	public Opinion getOpinion() {
		return _opinion;
	}
	public boolean isDone() {
		return _done;
	}

	public Request setAccepted(boolean isAccepted) {
		_isAccepted = isAccepted;
		return this;
	}
	public Request setOpinion(Opinion opinion) {
		_opinion = opinion;
		return this;
	}
	public Request setDone(boolean done) {
		_done = done;
		return this;
	}
}
