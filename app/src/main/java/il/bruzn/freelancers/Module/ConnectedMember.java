package il.bruzn.freelancers.Module;

import java.util.ArrayList;

import il.bruzn.freelancers.Module.Entities.Member;

/**
 * Created by Yair on 24/11/2014.
 */
public class ConnectedMember {
	private static Member theOneConnected = null;
	public static String filename = "connected_member";
	public static String key = "email";

    public static ArrayList<Member> MembersArray = new ArrayList(){{
            add(new Member().setEmail("yairopro").setPassword(""));
            add(new Member().setEmail("mayih").setPassword(""));
            add(new Member().setEmail("moshe").setPassword(""));
            add(new Member().setEmail("ruben").setPassword(""));
            add(new Member().setEmail("jeremy").setPassword(""));}};

	public static void nullMember(){
		theOneConnected = null;
	}
	public static Member initInstance(){
		theOneConnected = new Member();
		return theOneConnected;
	}
	public static Member getMember(){
		return theOneConnected;
	}
	public static void setMember(Member member){
		theOneConnected = member;
	}
}
