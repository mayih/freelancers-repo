package il.bruzn.freelancers.Module.iRepositories;

import il.bruzn.freelancers.Module.CRUD;
import il.bruzn.freelancers.Module.Entities.Member;

/**
 * Created by Yair on 08/12/2014.
 */
public interface iMemberRepo extends CRUD<Member> {
	public Member selectByEmail(String email);
	public Member selectWithOpinions(String email);
}
