package il.bruzn.freelancers.Module.ListTech;

import java.util.ArrayList;

import il.bruzn.freelancers.Module.Entities.Member;
import il.bruzn.freelancers.Module.Entities.Opinion;
import il.bruzn.freelancers.Module.iRepositories.iOpinionRepo;

/**
 * Created by Yair on 08/12/2014.
 */
public class OpinionRepoList extends ListTech implements iOpinionRepo {
	@Override
	public void add(Opinion entry) {
		_opinions.add(entry);
		}

	@Override
	public ArrayList<Opinion> getBySubject(Member subject) {
		ArrayList<Opinion> selected = new ArrayList<Opinion>();
		for (Opinion op: _opinions)
			if (op.getSubject() == subject)
				selected.add(op);
		return selected;
	}

	@Override
	public ArrayList<Opinion> getByAuthor(Member subject) {
		ArrayList<Opinion> selected = new ArrayList<Opinion>();
		for (Opinion op: _opinions)
			if (op.getAuthor() == subject)
				selected.add(op);
		return selected;
	}

	@Override
	public Member fillMember(Member member) {
		if (member.getOpinionsOnMe().isEmpty())
			member.setOpinionsOnMe(getBySubject(member));
		return member;
	}

	@Override
	public Opinion selectById(int Id) {
		for (Opinion op: _opinions)
			if (op.getId() == Id)
				return op;
		return null;
	}

	@Override
	public ArrayList<Opinion> selectAll() {
		return _opinions;
	}

	@Override
	public ArrayList<Opinion> selectBy(String field, String value) {
		// Useless for List technology
		return null;
	}

	@Override
	public void update(Opinion entry, int id) {
		// In list technology, entities are already updated by reference.
		}

	@Override
	public void delete(Opinion entry, int id) {
		_opinions.remove(entry);
	}
}
