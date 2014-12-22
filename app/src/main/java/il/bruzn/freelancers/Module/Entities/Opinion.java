package il.bruzn.freelancers.Module.Entities;


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
		ONE(1), TWO(2) ,THREE(3), FOUR(4), FIVE(5);

		private int _level;
		Level(int level) { _level = level; }
		public int getValue() { return _level; }

		public static Level fromInteger(int level){

			switch (level){
				case 1:
					return ONE;
				case 2:
					return TWO;
				case 3:
					return THREE;
				case 4:
					return FOUR;
				case 5:
					return FIVE;
			}
			return null;

		}
	}
}
