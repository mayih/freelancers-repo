package il.bruzn.freelancers.Module.ListTech;

import java.util.ArrayList;
import java.util.List;

import il.bruzn.freelancers.Module.CRUD;
import il.bruzn.freelancers.Module.Entities.Member;
import il.bruzn.freelancers.Module.Entities.Opinion;
import il.bruzn.freelancers.Module.iRepositories.iOpinionRepo;

/**
 * Created by Yair on 08/12/2014.
 */
public class OpinionRepoList extends ListCRUD implements iOpinionRepo {
	@Override
	public CRUD<Opinion> add(Opinion entry) {
		_opinionsArray.add(entry);
		return this;
	}

	@Override
	public List<Opinion> getBySubject(Member subject) {
		ArrayList<Opinion> selected = new ArrayList<Opinion>();
		for (Opinion op:_opinionsArray)
			if (op.getSubject() == subject)
				selected.add(op);
		return selected;
	}

	@Override
	public List<Opinion> getByAuthor(Member subject) {
		ArrayList<Opinion> selected = new ArrayList<Opinion>();
		for (Opinion op:_opinionsArray)
			if (op.getAuthor() == subject)
				selected.add(op);
		return selected;
	}

	@Override
	public Opinion selectById(int Id) {
		for (Opinion op:_opinionsArray)
			if (op.getId() == Id)
				return op;
		return null;
	}

	@Override
	public ArrayList<Opinion> selectAll() {
		return _opinionsArray;
	}

	@Override
	public ArrayList<Opinion> selectBy(String field, String value) {
		// Useless for List technology
		return null;
	}

	@Override
	public CRUD<Opinion> update(Opinion entry) {
		// In list technology, entities are already updated by reference.
		return this;
	}

	@Override
	public CRUD<Opinion> delete(Opinion entry) {
		_opinionsArray.remove(entry);
		return this;
	}
}
