package il.bruzn.freelancers.Modele.iRepositories;

import java.util.ArrayList;

import il.bruzn.freelancers.Modele.CRUD;
import il.bruzn.freelancers.Modele.Entities.Member;

/**
 * Created by Yair on 08/12/2014.
 */
public interface iMemberRepo extends CRUD<Member> {
	public Member selectByEmail(String email);
	public Member selectByEmailAndPassword(String email, String password);
	public Member selectWithOpinions(String email);
	public ArrayList<Member> selectByIds(String listOfIds);
}
