package il.bruzn.freelancers.Model.iRepositories;

import java.util.ArrayList;

import il.bruzn.freelancers.Model.CRUD;
import il.bruzn.freelancers.Model.Entities.Member;
import il.bruzn.freelancers.Model.Entities.Message;

/**
 * Created by Yair on 10/12/2014.
 */
public interface iMessageRepo extends CRUD<Message> {
	public ArrayList<Message> selectDiscussion(Member self, Member interlocutor);
	public ArrayList<ArrayList<Message>> selectAllDiscussions(Member self);
}
