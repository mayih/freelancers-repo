package il.bruzn.freelancers.Module.iRepositories;

import java.util.ArrayList;

import il.bruzn.freelancers.Module.CRUD;
import il.bruzn.freelancers.Module.Entities.Member;
import il.bruzn.freelancers.Module.Entities.Message;

/**
 * Created by Yair on 10/12/2014.
 */
public interface iMessageRepo extends CRUD<Message> {
	public ArrayList<ArrayList<Message>> selectAllDiscussions(Member self);
	public ArrayList<Message> selectDiscussion(Member self, Member other);
}
