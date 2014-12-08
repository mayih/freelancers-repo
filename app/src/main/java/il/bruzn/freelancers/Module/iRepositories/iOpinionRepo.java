package il.bruzn.freelancers.Module.iRepositories;

import java.util.List;

import il.bruzn.freelancers.Module.CRUD;
import il.bruzn.freelancers.Module.Entities.Member;
import il.bruzn.freelancers.Module.Entities.Opinion;

/**
 * Created by Yair on 08/12/2014.
 */
public interface iOpinionRepo extends CRUD<Opinion> {
	public List<Opinion> getBySubject(Member subject);
	public List<Opinion> getByAuthor(Member subject);
}
