package il.bruzn.freelancers.Model;

import il.bruzn.freelancers.Model.Entities.Member;

/**
 * Created by Yair on 24/11/2014.
 */
public class ConnectedMember {
	private static Member theOneConnected = null;
	public static String filename = "connected_member";
	public static String key = "email";

	public static void nullMember(){
		theOneConnected = null;
	}
	public static Member getMember(){
		return theOneConnected;
	}
	public static void setMember(Member member){
		theOneConnected = member;
	}
}
