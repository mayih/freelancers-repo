package il.bruzn.freelancers.Model.SqLiteTech;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import il.bruzn.freelancers.Model.Entities.Member;
import il.bruzn.freelancers.Model.Entities.Opinion;
import il.bruzn.freelancers.Model.Entities.Request;
import il.bruzn.freelancers.Model.Model;
import il.bruzn.freelancers.Model.iRepositories.iRequestRepo;

/**
 * Created by Moshe on 07/01/15.
 */
public class RequestRepoSqLite extends SQLiteTech<Request> implements iRequestRepo {

	// Table's name
	static final String NAME_TABLE = "Request";

	// Fields' name
	enum FIELDS_NAME {  ID("_id"),
		AUTHOR("author_id"),
		RECEIVER("receiver_id"),
		OPINION("opinion_id"),
		REQUEST("request"),
		DATE("date_of_request"),
		ISACCEPTED("isAccepted"),
		DONE("isDone");

		private String _name;
		FIELDS_NAME(String name) {
			_name = name;
		}
		public String toString() {
			return _name;
		}
	};

	// Request to create the table
	static final String CREATE_REQ = "CREATE TABLE IF NOT EXISTS " + NAME_TABLE + " (" +
			FIELDS_NAME.ID			+ " INTEGER PRIMARY KEY AUTOINCREMENT, " +
			FIELDS_NAME.AUTHOR	    + " INTEGER NOT NULL, " +
			FIELDS_NAME.RECEIVER	+ " INTEGER NOT NULL, " +
			FIELDS_NAME.OPINION		+ " INTEGER, " +
			FIELDS_NAME.REQUEST	    + " TEXT NOT NULL, "    +
			FIELDS_NAME.DATE	    + " INTEGER NOT NULL, "	+ // TimeStamp in seconds
			FIELDS_NAME.ISACCEPTED	+ " INTEGER " +
			FIELDS_NAME.DONE		+ " INTEGER " +
			");";

	// CONSTRUCTOR  ---
	public RequestRepoSqLite(Context context, String name, int version) {
		super(context, name, version);
	}

	// SQLiteTech IMPLEMENTATION
	@Override
	public String createReq() {
		return CREATE_REQ;
	}

	@Override
	public String getNameTable() {
		return NAME_TABLE;
	}

	@Override
	public ArrayList<Request> toEntity(Cursor cursor) {
		ArrayList<Request> requestArrayList = new ArrayList<>();
		Request request;

		// get list of ids for members and opinions
		ArrayList<Integer> listOfIdsMembers = new ArrayList<>();
		ArrayList<Integer> listOfIdsOpinions = new ArrayList<>();
		while (cursor.moveToNext()) {
			int author_id = cursor.getInt(cursor.getColumnIndex(FIELDS_NAME.AUTHOR.toString()));
			if (listOfIdsMembers.contains(author_id))
				listOfIdsMembers.add(author_id);

			int receiver_id	= cursor.getInt(cursor.getColumnIndex(FIELDS_NAME.RECEIVER.toString()));
			if (listOfIdsMembers.contains(receiver_id))
				listOfIdsMembers.add(receiver_id);

			if (!cursor.isNull( cursor.getColumnIndex(FIELDS_NAME.OPINION.toString()) ))
				listOfIdsOpinions.add( cursor.getInt(cursor.getColumnIndex(FIELDS_NAME.OPINION.toString())) );
		}

		// Query..
		ArrayList<Member> listOfMembers = Model.getMemberRepo().selectWhereIdIn(listOfIdsMembers);
		ArrayList<Opinion> listOfOpinions = Model.getOpnionRepo().selectWhereIdIn(listOfIdsOpinions);


		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {

			int id				= cursor.getInt(cursor.getColumnIndex(FIELDS_NAME.ID.toString()));
			int author_id		= cursor.getInt(cursor.getColumnIndex(FIELDS_NAME.AUTHOR.toString()));
			int receiver_id		= cursor.getInt(cursor.getColumnIndex(FIELDS_NAME.RECEIVER.toString()));
			String requestMsg	= cursor.getString(cursor.getColumnIndex(FIELDS_NAME.REQUEST.toString()));
			Date date	 		= new Date(cursor.getLong(cursor.getColumnIndex(FIELDS_NAME.DATE.toString())) * 1000);

			boolean isAccepted	= !cursor.isNull(cursor.getColumnIndex(FIELDS_NAME.ISACCEPTED.toString()))
								&& cursor.getInt(cursor.getColumnIndex(FIELDS_NAME.ISACCEPTED.toString())) == 1;
			boolean isDone		= !cursor.isNull(cursor.getColumnIndex(FIELDS_NAME.DONE.toString()))
					&& cursor.getInt(cursor.getColumnIndex(FIELDS_NAME.DONE.toString())) == 1;

			Integer opinion_id	= null;
			if (!cursor.isNull( cursor.getColumnIndex(FIELDS_NAME.OPINION.toString()) ))
				opinion_id = cursor.getInt(cursor.getColumnIndex(FIELDS_NAME.OPINION.toString()));

			// search the author and receiver from the list
			Member author = null, receiver = null;
			for (Member member : listOfMembers) {
				if (member.getId() == author_id)
					author = member;

				if (member.getId() == receiver_id)
					receiver = member;

				if (author != null && receiver != null)
					break;
			}

			// search the opinion on this request from the list
			Opinion opinion = null;
			for (Opinion opinionInList : listOfOpinions) {
				if (opinion_id == opinionInList.getId()) {
					opinion = opinionInList;
					break;
				}
			}

			request = new Request(id, author, receiver, requestMsg, date, isAccepted, isDone, opinion );
			requestArrayList.add(request);
		}
		cursor.close();
		return requestArrayList;
	}

	@Override
	public ContentValues toContentValues(Request entity) {
		ContentValues content = new ContentValues();

		content.put(FIELDS_NAME.AUTHOR.toString(), entity.getAuthor().getId());
		content.put(FIELDS_NAME.RECEIVER.toString(),entity.getReceiver().getId());
		content.put(FIELDS_NAME.REQUEST.toString(), entity.getText());
		content.put(FIELDS_NAME.OPINION.toString(), entity.getOpinion().getId());
		content.put(FIELDS_NAME.DATE.toString(), entity.getDate().getTime()/1000);
		content.put(FIELDS_NAME.ISACCEPTED.toString(), entity.isAccepted());
		content.put(FIELDS_NAME.DONE.toString(), entity.isDone());

		return content;
	}

//	iRequestRepo IMPLMENTATION ---

	@Override
	public ArrayList<Request> selectByReceiver(Member receiver) {
		ArrayList<Request> selected = selectBy(FIELDS_NAME.RECEIVER.toString(), receiver.getId()+"");
		Collections.sort(selected);
		Collections.reverse(selected);
		return selected;
	}
}
