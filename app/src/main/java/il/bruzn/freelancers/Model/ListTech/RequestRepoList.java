package il.bruzn.freelancers.Model.ListTech;

import java.util.ArrayList;
import java.util.List;

import il.bruzn.freelancers.Model.Entities.Member;
import il.bruzn.freelancers.Model.Entities.Request;
import il.bruzn.freelancers.Model.iRepositories.iRequestRepo;

/**
 * Created by Yair on 07/01/2015.
 */
public class RequestRepoList extends ListTech implements iRequestRepo {

	//iRequestRepo IMPLEMENTATION
	@Override
	public ArrayList<Request> selectByReceiver(Member receiver) {
		ArrayList<Request> selected = new ArrayList<>();
		for (Request req : _requests)
			if (req.getReceiver() == receiver)
				selected.add(req);
		return selected;
	}

	// CRUD IMPLEMENTATION ---
	@Override
	public void add(Request entry) {
		_requests.add(entry);
	}

	@Override
	public Request selectById(int Id) {
		for (Request req : _requests)
			if (req.getId() == Id)
				return req;
		return null;
	}

	@Override
	public ArrayList<Request> selectWhereIdIn(List<Integer> listOfIds) {
		ArrayList<Request> selected = new ArrayList<>();

		for (int id : listOfIds)
			selected.add( selectById(id) );

		return selected;
	}

	@Override
	public ArrayList<Request> selectAll() {
		return _requests;
	}

	@Override
	public ArrayList<Request> selectBy(String field, String value) {
		return null;
	} // DONT USE THIS FUNCTION

	@Override
	public void update(Request entry, int id) {

	} // NO NEED IN LIST TECHNOLOGY

	@Override
	public void delete(Request entry, int id) {
		_requests.remove(entry);
	}

	public Request selectLastInProgress(Member author, Member receiver){
		return null;
	}

	public ArrayList<Request> selectFinishedRequest(Member receiver){
		return null;
	}
}
