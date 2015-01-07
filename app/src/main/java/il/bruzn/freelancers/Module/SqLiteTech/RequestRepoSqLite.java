package il.bruzn.freelancers.Module.SqLiteTech;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Date;

import il.bruzn.freelancers.Module.Entities.Member;
import il.bruzn.freelancers.Module.Entities.Opinion;
import il.bruzn.freelancers.Module.Entities.Request;
import il.bruzn.freelancers.Module.Model;
import il.bruzn.freelancers.Module.iRepositories.iRequestRepo;

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
		ISACCEPTED("isAccepted");

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
			FIELDS_NAME.OPINION		+ " INTEGER NOT NULL, " +
			FIELDS_NAME.REQUEST	    + " TEXT NOT NULL, "    +
			FIELDS_NAME.DATE	    + " INTEGER NOT NULL "	+ // TimeStamp in seconds
			FIELDS_NAME.ISACCEPTED	+ " INTEGER, " +
			");";


	public RequestRepoSqLite(Context context, String name, int version) {
		super(context, name, version);
	}

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

		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {

			int id				= cursor.getInt(cursor.getColumnIndex(FIELDS_NAME.ID.toString()));
			int author_id		= cursor.getInt(cursor.getColumnIndex(FIELDS_NAME.AUTHOR.toString()));
			int receiver_id		= cursor.getInt(cursor.getColumnIndex(FIELDS_NAME.RECEIVER.toString()));
			int opinion_id		= cursor.getInt(cursor.getColumnIndex(FIELDS_NAME.OPINION.toString()));
			String requestMsg 	= cursor.getString(cursor.getColumnIndex(FIELDS_NAME.REQUEST.toString()));
			Date date	 		= new Date(cursor.getLong(cursor.getColumnIndex(FIELDS_NAME.DATE.toString())) * 1000);
			int isAccepted		= cursor.getInt(cursor.getColumnIndex(FIELDS_NAME.ISACCEPTED.toString()));

			Member authors = Model.getMemberRepo().selectById(author_id),
					receiver = Model.getMemberRepo().selectById(receiver_id);
			Opinion opinion = Model.getOpnionRepo().selectById(opinion_id);

			request = new Request(authors, receiver, requestMsg, isAccepted != 0, opinion);
			requestArrayList.add(request);
		}
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
		content.put(FIELDS_NAME.ISACCEPTED.toString(), entity.getIsAccepted());

		return content;
	}


}
