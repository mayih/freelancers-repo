package il.bruzn.freelancers.Module.iRepositories;

import java.util.ArrayList;

import il.bruzn.freelancers.Module.CRUD;
import il.bruzn.freelancers.Module.Entities.Member;
import il.bruzn.freelancers.Module.Entities.Opinion;

/**
 * Created by Yair on 08/12/2014.
 */
public interface iOpinionRepo extends CRUD<Opinion> {
	public ArrayList<Opinion> getBySubject(Member subject);
	public ArrayList<Opinion> getByAuthor(Member subject);
	public Member		fillMember(Member member);
}
