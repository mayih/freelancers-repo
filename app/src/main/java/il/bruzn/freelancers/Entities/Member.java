package il.bruzn.freelancers.Entities;

import android.graphics.Picture;
import java.util.ArrayList;
import il.bruzn.freelancers.basic.Speciality;
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

}
