package il.bruzn.freelancers.Module.Entities;

import android.graphics.Picture;
import java.util.ArrayList;

import il.bruzn.freelancers.Module.Module;
import il.bruzn.freelancers.basic.WayToPay;
import il.bruzn.freelancers.basic.Location;

public class Member {
	private int id;
	private String _lastName;
	private String _firstName;
	private String _email;
	private String _password;
	private Location _address;
	private String _phoneNumber;
	private ArrayList<Member> _favorites;
	private Picture _picture;
	private String _googlePlus;
	private String _linkedIn;
	private WayToPay _payment;

	// Not in table
	private ArrayList<Opinion> _opinionsOnMe = new ArrayList<Opinion>();
	private ArrayList<ArrayList<Message>> discussions = new ArrayList<ArrayList<Message>>();

	// Methods  ----
	public double getAverage(){
		double average = 0d;
		if (_opinionsOnMe.size() != 0) {
			for (Opinion op : _opinionsOnMe) {
				average += op.getLevel().getValue();
			}
			average /= _opinionsOnMe.size();
		}
		return average;
	}

	// Getters & Setters ----
	public Member setPassword(String password) {
		_password = password;
		return this;
	}
	public Member setEmail(String email) {
		_email = email;
		return this;
	}
	public Member setLastName(String lastName) {
		_lastName = lastName;
		return this;
	}
	public Member setFirstName(String firstName) {
		_firstName = firstName;
		return this;
	}
	public Member setOpinionsOnMe(ArrayList<Opinion> opinionsOnMe) {
		_opinionsOnMe = opinionsOnMe;
		return this;
	}
	public Member setDiscussions(ArrayList<ArrayList<Message>> discussions) {
		this.discussions = discussions;
		return this;
	}

	public String getPassword() {
		return _password;
	}
	public String getEmail() {
		return _email;
	}
	public int getId() {
		return id;
	}
	public String getLastName() {
		return _lastName;
	}
	public String getFirstName() {
		return _firstName;
	}
	public Location getAddress() {
		return _address;
	}
	public String getPhoneNumber() {
		return _phoneNumber;
	}
	public ArrayList<Member> getFavorites() {
		return _favorites;
	}
	public Picture getPicture() {
		return _picture;
	}
	public ArrayList<Opinion> getOpinionsOnMe() {
		return _opinionsOnMe;
	}
	public ArrayList<ArrayList<Message>> getDiscussions() {
		return discussions;
	}

	public boolean authenticate(String email, String password){
		return (_email.equals(email) && _password.equals(password));
	}
}
