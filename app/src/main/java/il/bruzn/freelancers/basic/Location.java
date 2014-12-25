package il.bruzn.freelancers.basic;

/**
 * Created by Yair on 20/11/2014.
 */
public class Location {
	private String	_country = "",
					_city = "",
					_address ="";

	public String toString(){
		return _country + _city + _address;
	}
}