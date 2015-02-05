package il.bruzn.freelancers.Model.iRepositories;

import il.bruzn.freelancers.Model.CRUD;
import il.bruzn.freelancers.Model.Entities.Member;

/**
 * Created by Yair on 08/12/2014.
 */
public interface iMemberRepo extends CRUD<Member> {
	public Member selectByEmail(String email);
	public Member selectByEmailAndPassword(String email, String password);
	public Member selectWithOpinions(String email);
}
