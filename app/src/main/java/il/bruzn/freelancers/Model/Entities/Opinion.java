package il.bruzn.freelancers.Model.Entities;


import java.util.Date;

/**
 * Created by Yair on 20/11/2014.
 */
public class Opinion {
	private int	id;
	private Level	_level;
	private boolean	_done;
	private String	_text;
	private Member	_author;
	private Member	_subject;
	private Date _date = new Date();
	private int _requestId;

	public Opinion(Member author, Member subject,Level level, boolean done) {
		_level = level;
		_done = done;
		_author = author;
		_subject = subject;
		_date = new Date();
	}

	public Opinion(Member author, Member subject, double level, String text, int requestId) {
		_level = Level.fromDouble(level);
		_requestId = requestId;
		_author = author;
		_subject = subject;
		_date = new Date();
		_text = text;
	}
	public Opinion(int id, double level, boolean done, String text, Member author, Member subject, int requestId, Date date) {
		this.id = id;
		_level = Level.fromDouble(level);
		_done = done;
		_text = text;
		_author = author;
		_subject = subject;
		_requestId = requestId;
		_date = date;
	}

	public int getId() {
		return id;
	}
	public Level getLevel() {
		return _level;
	}
	public boolean isDone() {
		return _done;
	}
	public String getText() {
		return _text;
	}
	public Member getAuthor() {
		return _author;
	}
	public Member getSubject() {
		return _subject;
	}
	public int getRequestId() {
		return _requestId;
	}
	public Date getDate() {
		return _date;
	}

	public Opinion setId(int id) {
		this.id = id;
		return this;
	}
	public Opinion setLevel(Level level) {
		_level = level;
		return this;
	}
	public Opinion setDone(boolean done) {
		_done = done;
		return this;
	}
	public Opinion setText(String text) {
		_text = text;
		return this;
	}
	public Opinion setAuthor(Member author) {
		_author = author;
		return this;
	}
	public Opinion setSubject(Member subject) {
		_subject = subject;
		return this;
	}
	public Opinion setDate(Date date) {
		_date = date;
		return this;
	}

	public static enum Level {
		HALF(0.5), ONE(1), ONEANDHALF(1.5), TWO(2), TWOANDHALF(2.5), THREE(3), THREEANDHALF(3.5), FOUR(4), FOURANDHALF(4.5), FIVE(5);

		private double _level;
		Level(double level) { _level = level; }
		public double getValue() { return _level; }

		public static Level fromDouble(double level){
			if (level == 0.5){
				return HALF;
			}
			else if (level == 1) {
				return ONE;
			}
			else if (level == 1.5){
				return ONEANDHALF;
			}
			else if (level == 2){
				return TWO;
			}
			else if (level == 2.5){
				return TWOANDHALF;
			}
			else if (level == 3){
				return THREE;
			}
			else if (level == 3.5){
				return THREEANDHALF;
			}
			else if (level == 4){
				return FOUR;
			}
			else if (level == 4.5){
				return FOURANDHALF;
			}
			else if (level == 5){
				return FIVE;
			}

			return null;

		}
	}
}
