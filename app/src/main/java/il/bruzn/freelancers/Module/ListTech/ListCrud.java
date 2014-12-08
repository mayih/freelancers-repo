package il.bruzn.freelancers.Module.ListTech;

import java.util.ArrayList;

import il.bruzn.freelancers.Module.CRUD;
import il.bruzn.freelancers.Module.Entities.Member;
import il.bruzn.freelancers.Module.Entities.Opinion;

/**
 * Created by Yair on 08/12/2014.
 */
public class ListCRUD {
	public static ArrayList<Member> _membersArray;
	public static ArrayList<Opinion> _opinionsArray;

	public ListCRUD() {
		_membersArray = new ArrayList(){{
			add(new Member().setEmail("yairopro").setPassword("").setFirstName("yair").setLastName("Behar"));
			add(new Member().setEmail("mayih").setPassword("").setFirstName("Moshe").setLastName("Uzan"));
			add(new Member().setEmail("moshe").setPassword("").setFirstName("Moshe").setLastName("Lubliner"));
			add(new Member().setEmail("ruben").setPassword("").setFirstName("Ruben").setLastName("Dardour"));
			add(new Member().setEmail("jeremie").setPassword("").setFirstName("Jeremie").setLastName("Berrebi"));
		}};

		_opinionsArray = new ArrayList(){{
			add(new Opinion().setAuthor(_membersArray.get(0)).setSubject(_membersArray.get(1)).setLevel(Opinion.Level.FOUR).setText("Job well done.").setDone(true));
			add(new Opinion().setAuthor(_membersArray.get(2)).setSubject(_membersArray.get(1)).setLevel(Opinion.Level.FIVE).setText("You did good.").setDone(true));
			add(new Opinion().setAuthor(_membersArray.get(2)).setSubject(_membersArray.get(0)).setLevel(Opinion.Level.THREE).setText("Bof bof.").setDone(true));
			add(new Opinion().setAuthor(_membersArray.get(4)).setSubject(_membersArray.get(0)).setLevel(Opinion.Level.TWO).setText("Just do it better.").setDone(false));
			add(new Opinion().setAuthor(_membersArray.get(0)).setSubject(_membersArray.get(3)).setLevel(Opinion.Level.FOUR).setText("Job well done too.").setDone(true));
		}};
	}
}
