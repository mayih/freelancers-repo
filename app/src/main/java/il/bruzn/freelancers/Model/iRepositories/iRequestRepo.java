package il.bruzn.freelancers.Model.iRepositories;

import java.util.ArrayList;

import il.bruzn.freelancers.Model.CRUD;
import il.bruzn.freelancers.Model.Entities.Member;
import il.bruzn.freelancers.Model.Entities.Request;

/**
 * Created by Moshe on 07/01/15.
 */
public interface iRequestRepo extends CRUD<Request>{
	public ArrayList<Request> selectByReceiver(Member receiver);
}
