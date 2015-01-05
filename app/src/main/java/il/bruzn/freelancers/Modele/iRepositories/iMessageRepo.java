package il.bruzn.freelancers.Modele.iRepositories;

import java.util.ArrayList;

import il.bruzn.freelancers.Modele.CRUD;
import il.bruzn.freelancers.Modele.Entities.Member;
import il.bruzn.freelancers.Modele.Entities.Message;

/**
 * Created by Yair on 10/12/2014.
 */
public interface iMessageRepo extends CRUD<Message> {
	public ArrayList<Message> selectDiscussion(Member self, Member interlocutor);
	public ArrayList<ArrayList<Message>> selectAllDiscussions(Member self);
}
