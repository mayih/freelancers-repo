package il.bruzn.freelancers.Model.iRepositories;

import java.util.ArrayList;

import il.bruzn.freelancers.Model.CRUD;
import il.bruzn.freelancers.Model.Entities.Member;
import il.bruzn.freelancers.Model.Entities.Opinion;

/**
 * Created by Yair on 08/12/2014.
 */
public interface iOpinionRepo extends CRUD<Opinion> {
	public ArrayList<Opinion> selectBySubject(Member subject);
	public ArrayList<Opinion> selectByAuthor(Member subject);
	public Member		fillMember(Member member);
}
