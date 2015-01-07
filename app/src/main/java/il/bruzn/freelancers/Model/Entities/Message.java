package il.bruzn.freelancers.Model.Entities;

import java.util.Date;

/**
 * Created by Yair on 20/11/2014.
 */
public class Message implements Comparable<Message>{

	private int _id;
	private Member _author, _receiver;
	private String _text;
	private Date _date = new Date();

	public Message(Member author, Member receiver, String text) {
		_author = author;
		_receiver = receiver;
		_text = text;
	}

	public int getId() {
		return _id;
	}
	public Member getAuthor() {
		return _author;
	}
	public Member getReceiver() {
		return _receiver;
	}
	public String getText() {
		return _text;
	}
	public Date getDate() {
		return _date;
	}

	public Message setId(int id) {
		_id = id;
		return this;
	}
	public Message setAuthor(Member author) {
		_author = author;
		return this;
	}
	public Message setReceiver(Member receiver) {
		_receiver = receiver;
		return this;
	}
	public Message setText(String text) {
		_text = text;
		return this;
	}
	public Message setDate(Date date) {
		_date = date;
		return this;
	}

	@Override
	public int compareTo(Message another) {
		return getDate().compareTo(another.getDate());
	}
}
