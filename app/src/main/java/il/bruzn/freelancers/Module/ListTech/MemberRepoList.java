package il.bruzn.freelancers.Module.ListTech;

import java.util.ArrayList;

import il.bruzn.freelancers.Module.Entities.Member;
import il.bruzn.freelancers.Module.Entities.Opinion;
import il.bruzn.freelancers.Module.Model;
import il.bruzn.freelancers.Module.iRepositories.iMemberRepo;

/**
 * Created by Yair on 08/12/2014.
 */
public class MemberRepoList extends ListTech implements iMemberRepo {
	@Override
	public void add(Member entry) {
		_members.add(entry);
			}

	@Override
	public Member selectByEmail(String email) {
		for (Member m : _members){
			if (m.getEmail().equals(email))
				return m;
		}
		return null;
	}

	@Override
	public Member selectWithOpinions(String email) {
		Member m = selectByEmail(email);
		if (m!=null && m.getOpinionsOnMe().isEmpty())
			for (Opinion op : Model.getOpnionRepo().selectAll()){
				if (op.getSubject() == m){
					m.getOpinionsOnMe().add(op);
				}
			}
		return m;
	}

	@Override
	public ArrayList<Member> selectByIds(String listOfIds) {
		return null;
	} // NO USE

	@Override
	public Member selectById(int Id) {
		for (Member m: _members)
			if (m.getId() == Id)
				return m;
		return null;
	} // NO USE

	@Override
	public ArrayList<Member> selectAll() {
		return _members;
	}

	@Override
	public ArrayList<Member> selectBy(String field, String value) {
		// No need for list
		return null;
	}

	@Override
	public void update(Member entry, int id) {
		// When the object changed the entry, the list is allready updated by reference.
			} // NO USE

	@Override
	public void delete(Member entry, int  id) {
		_members.remove(entry);
	}
}
