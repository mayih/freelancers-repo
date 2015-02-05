package il.bruzn.freelancers.Model.Entities;

/**
 * Created by Yair on 23/11/2014.
 */
public class Freelancer extends Member{
	private Speciality _speciality;
	/**
	 * Created by Yair on 20/11/2014.
	 */
	public static enum Speciality {
		TEACHING,
		COMPUTER,
	}
}
