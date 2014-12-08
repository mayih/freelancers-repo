package il.bruzn.freelancers.Module.ListTech;

import java.lang.reflect.Field;
import java.util.ArrayList;

import il.bruzn.freelancers.Module.CRUD;
import il.bruzn.freelancers.Module.Entities.Member;
import il.bruzn.freelancers.Module.Entities.Opinion;
import il.bruzn.freelancers.Module.iRepositories.iMemberRepo;

/**
 * Created by Yair on 08/12/2014.
 */
public class MemberRepoList extends ListCRUD implements iMemberRepo {
	@Override
	public CRUD<Member> add(Member entry) {
		_membersArray.add(entry);
		return this;
	}

	@Override
	public Member selectByEmail(String email) {
		for (Member m : _membersArray){
			if (m.getEmail().equals(email))
				return m;
		}
		return null;
	}

	@Override
	public Member selectWithOpinions(String email) {
		Member m = selectByEmail(email);
		if (m!=null && !m.getOpinionsOnMe().isEmpty())
			for (Opinion op : _opinionsArray){
				if (op.getSubject() == m){
					m.getOpinionsOnMe().add(op);
				}
			}
		return m;
	}

	@Override
	public Member selectById(int Id) {
		for (Member m:_membersArray)
			if (m.getId() == Id)
				return m;
		return null;
	}

	@Override
	public ArrayList<Member> selectAll() {
		return _membersArray;
	}

	@Override
	public ArrayList<Member> selectBy(String field, String value) {
		// No need for list
		return null;
	}

	@Override
	public CRUD<Member> update(Member entry) {
		// When the object changed the entry, the list is allready updated by reference.
		return this;
	}

	@Override
	public CRUD<Member> delete(Member entry) {
		_membersArray.remove(entry);
		return this;
	}
}
