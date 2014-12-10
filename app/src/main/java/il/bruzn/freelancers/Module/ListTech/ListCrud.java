package il.bruzn.freelancers.Module.ListTech;

import java.util.ArrayList;
import java.util.Date;

import il.bruzn.freelancers.Module.Entities.Member;
import il.bruzn.freelancers.Module.Entities.Message;
import il.bruzn.freelancers.Module.Entities.Opinion;

/**
 * Created by Yair on 08/12/2014.
 */
public class ListCRUD {
	protected static ArrayList<Member> _members;
	protected static ArrayList<Opinion> _opinions;
	protected static ArrayList<Message> _messages;

	public ListCRUD() {
		_members = new ArrayList<Member>(){{
			add(new Member().setEmail("yairopro").setPassword("").setFirstName("yair").setLastName("Behar"));
			add(new Member().setEmail("mayih").setPassword("").setFirstName("Moshe").setLastName("Uzan"));
			add(new Member().setEmail("moshe").setPassword("").setFirstName("Moshe").setLastName("Lubliner"));
			add(new Member().setEmail("ruben").setPassword("").setFirstName("Ruben").setLastName("Dardour"));
			add(new Member().setEmail("jeremie").setPassword("").setFirstName("Jeremie").setLastName("Berrebi"));
		}};

		_opinions = new ArrayList<Opinion>(){{
			add(new Opinion().setAuthor(_members.get(0)).setSubject(_members.get(1)).setLevel(Opinion.Level.FOUR).setText("Job well done.").setDone(true));
			add(new Opinion().setAuthor(_members.get(2)).setSubject(_members.get(1)).setLevel(Opinion.Level.FIVE).setText("You did good.").setDone(true));
			add(new Opinion().setAuthor(_members.get(2)).setSubject(_members.get(0)).setLevel(Opinion.Level.THREE).setText("Bof bof.").setDone(true));
			add(new Opinion().setAuthor(_members.get(4)).setSubject(_members.get(0)).setLevel(Opinion.Level.TWO).setText("Just do it better.").setDone(false));
			add(new Opinion().setAuthor(_members.get(0)).setSubject(_members.get(3)).setLevel(Opinion.Level.FOUR).setText("Job well done too.").setDone(true));
		}};

		_messages = new ArrayList<Message>(){{
			// Discussion yair & Jeremie
			add(new Message(_members.get(0),_members.get(4), "Yo frero ca va?").setDate(new Date(new Date().getTime() - 5*60*1000)));
			add(new Message(_members.get(4),_members.get(0), "Ca roule et toi?").setDate(new Date(new Date().getTime() - 5*30*1000)));
			add(new Message(_members.get(0),_members.get(4), "b\"h, tu saurais comment faire des pates?").setDate(new Date(new Date().getTime() - 4*60*1000)));
			add(new Message(_members.get(4),_members.get(0), "Tu sais pas faire des pates??").setDate(new Date(new Date().getTime() - 4*30*1000)));
			add(new Message(_members.get(4),_members.get(0), "Nhiiiii!!!").setDate(new Date(new Date().getTime() - 3*60*1000)));
			add(new Message(_members.get(0),_members.get(4), "mdrrr").setDate(new Date(new Date().getTime() - 2*60*1000)));

			// Discussion Yair & Ruben
			add(new Message(_members.get(3),_members.get(0), "file moi ton crane!").setDate(new Date(new Date().getTime() - 2*60*1000)));
			add(new Message(_members.get(0),_members.get(3), "CHATIMENT").setDate(new Date(new Date().getTime() - 2*30*1000)));
			add(new Message(_members.get(3),_members.get(0), "LOOL").setDate(new Date(new Date().getTime() - 1*60*1000)));
		}};
	}
}
