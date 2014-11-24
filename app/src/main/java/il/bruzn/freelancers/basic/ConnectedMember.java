package il.bruzn.freelancers.basic;

import il.bruzn.freelancers.Entities.Member;

/**
 * Created by Yair on 24/11/2014.
 */
public class ConnectedMember {
	private static Member instance = null;
	public static String filename = "connected_member";
	public static String key = "email";

	public static void nullMember(){
		instance = null;
	}
	public static Member initInstance(){
		instance = new Member();
		return instance;
	}
	public static Member getMember(){
		return instance;
	}
	public static void setMember(Member member){
		instance = member;
	}
}
