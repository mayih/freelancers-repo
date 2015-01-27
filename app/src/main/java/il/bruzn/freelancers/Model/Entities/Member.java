package il.bruzn.freelancers.Model.Entities;

import android.graphics.Bitmap;

import java.math.BigDecimal;
import java.util.ArrayList;
import il.bruzn.freelancers.basic.WayToPay;
import il.bruzn.freelancers.basic.Location;

public class Member {

	private int _id;
	private String _email;
	private String _password;
	private String _firstName;
	private String _lastName;
	private String _speciality;
	private Location _address = new Location();
	private String _phoneNumber;
	private Bitmap _picture; // Ddownload from an link
	private String _googlePlus;
	private String _linkedIn;
	private WayToPay _payment;

	// Not in table
	private ArrayList<Opinion> _opinionsOnMe = new ArrayList<Opinion>();
	private ArrayList<ArrayList<Message>> discussions = new ArrayList<ArrayList<Message>>();
	private ArrayList<Member> _favorites;

	// Methods  ----
	public double getAverage(){
		double average = 0d;
		if (_opinionsOnMe.size() != 0) {
			for (Opinion op : _opinionsOnMe) {
				average += op.getLevel().getValue();
			}
			average /= _opinionsOnMe.size();
		}
		BigDecimal bigDecimal = new BigDecimal(average);
		bigDecimal = bigDecimal.setScale(1, BigDecimal.ROUND_HALF_UP);
		average = bigDecimal.doubleValue();
		return average;
	}

	// Getters & Setters ----

	public Member setId(int id) {
		this._id = id;
		return this;
	}
	public Member setPassword(String password) {
		_password = password;
		return this;
	}
	public Member setEmail(String email) {
		_email = email;
		return this;
	}
	public Member setFirstName(String firstName) {
		_firstName = firstName;
		return this;
	}
	public Member setLastName(String lastName) {
		_lastName = lastName;
		return this;
	}
	public Member setSpeciality(String speciality) {
		_speciality = speciality;
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
	public Member setLinkedIn(String linkedIn) {
		_linkedIn = linkedIn;
		return this;
	}
	public Member setGooglePlus(String googlePlus) {
		_googlePlus = googlePlus;
		return this;
	}
	public Member setPicture(Bitmap picture) {
		_picture = picture;
		return this;
	}
	public Member setFavorites(ArrayList<Member> favorites) {
		_favorites = favorites;
		return this;
	}
	public Member setPhoneNumber(String phoneNumber) {
		_phoneNumber = phoneNumber;
		return this;
	}
	public Member setAddress(Location address) {
		_address = address;
		return this;
	}

	public String getPassword() {
		return _password;
	}
	public String getEmail() {
		return _email;
	}
	public int getId() {
		return _id;
	}
	public String getFirstName() {
		return _firstName;
	}
	public String getLastName() {
		return _lastName;
	}
	public String getSpeciality() {return _speciality;}
	public Location getAddress() {
		return _address;
	}
	public String getPhoneNumber() {
		return _phoneNumber;
	}
	public String getGooglePlus() {
		return _googlePlus;
	}
	public String getLinkedIn() {
		return _linkedIn;
	}

	public ArrayList<Member> getFavorites() {
		return _favorites;
	}
	public Bitmap getPicture() {
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
