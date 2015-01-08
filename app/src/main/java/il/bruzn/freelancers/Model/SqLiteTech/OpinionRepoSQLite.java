package il.bruzn.freelancers.Model.SqLiteTech;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Date;

import il.bruzn.freelancers.Model.Entities.Member;
import il.bruzn.freelancers.Model.Entities.Opinion;
import il.bruzn.freelancers.Model.Model;
import il.bruzn.freelancers.Model.iRepositories.iOpinionRepo;

/**
 * Created by Moshe on 17/12/14.
 */
public class OpinionRepoSQLite extends SQLiteTech<Opinion> implements iOpinionRepo {

	// Table's name
	static final String NAME_TABLE = "Opinion";
	// Fields' name
	enum FIELDS_NAME {  ID("_id"),
		LEVEL("level"),
		IS_DONE("done"),
		OPINION_MESSAGE("message"),
		AUTHOR("author"),
		SUBJECT("subject"),
		DATE("date_of_opinion");

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
			FIELDS_NAME.ID				+ " INTEGER PRIMARY KEY AUTOINCREMENT, " +
			FIELDS_NAME.LEVEL			+ " INTEGER NOT NULL, " +
			FIELDS_NAME.IS_DONE			+ " INTEGER, " +
			FIELDS_NAME.OPINION_MESSAGE	+ " TEXT, " 			+
			FIELDS_NAME.AUTHOR + " INTEGER NOT NULL, " +
			FIELDS_NAME.SUBJECT			+ " INTEGER NOT NULL, " +
			FIELDS_NAME.DATE			+ " INTEGER NOT NULL" +
			");";

	public OpinionRepoSQLite(Context context, String name, int version) {
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
	public ArrayList<Opinion> toEntity(Cursor cursor) {
		ArrayList<Opinion> opinionArrayList = new ArrayList<>();

		// get list of ids for author and subject
		ArrayList<Integer> listOfIds = new ArrayList<>();
		while (cursor.moveToNext()){
			int author_id	= cursor.getInt( cursor.getColumnIndex(FIELDS_NAME.AUTHOR.toString()));
			int subject_id	= cursor.getInt( cursor.getColumnIndex(FIELDS_NAME.SUBJECT.toString()));

			if (!listOfIds.contains(author_id))
				listOfIds.add(author_id);
			if ((!listOfIds.contains(subject_id)))
				listOfIds.add(subject_id);
		}
		// query
		ArrayList<Member> listOfMember = Model.getMemberRepo().selectWhereIdIn(listOfIds);

		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {

			int id = cursor.getInt(cursor.getColumnIndex(FIELDS_NAME.ID.toString()));
			int levelInteger = cursor.getInt(cursor.getColumnIndex(FIELDS_NAME.LEVEL.toString()));
			String message = cursor.getString(cursor.getColumnIndex(FIELDS_NAME.OPINION_MESSAGE.toString()));
			int author_id = cursor.getInt(cursor.getColumnIndex(FIELDS_NAME.AUTHOR.toString()));
			int subject_id = cursor.getInt(cursor.getColumnIndex(FIELDS_NAME.SUBJECT.toString()));
			Date date = new Date(cursor.getLong(cursor.getColumnIndex(FIELDS_NAME.DATE.toString())) * 1000);

			boolean done = !cursor.isNull(cursor.getColumnIndex(FIELDS_NAME.IS_DONE.toString()))
						&& cursor.getInt(cursor.getColumnIndex(FIELDS_NAME.IS_DONE.toString())) == 1;

			Member author = null, subject = null;
			for (Member member : listOfMember){
				if (member.getId() == author_id)
					author = member;

				if (member.getId() == subject_id)
					subject = member;

				if (author != null && subject != null)
					break;
			}

			Opinion.Level level = Opinion.Level.fromInteger(levelInteger);

			Opinion opinion = new Opinion(id, level, done, message, author, subject, date);
			opinionArrayList.add(opinion);
		}
		cursor.close();
		return opinionArrayList;
	}

	@Override
	public ContentValues toContentValues(Opinion opinion) {
		ContentValues content = new ContentValues();

		content.put(FIELDS_NAME.LEVEL.toString(), opinion.getLevel().getValue());
		content.put(FIELDS_NAME.IS_DONE.toString(), opinion.isDone());
		content.put(FIELDS_NAME.OPINION_MESSAGE.toString(), opinion.getText() );
		content.put(FIELDS_NAME.AUTHOR.toString(), opinion.getAuthor().getId());
		content.put(FIELDS_NAME.SUBJECT.toString(), opinion.getSubject().getId());
		content.put(FIELDS_NAME.DATE.toString(), opinion.getDate().getTime() / 1000);

		return content;
	}

	@Override
	public ArrayList<Opinion> selectBySubject(Member subject) {
		return selectBy(FIELDS_NAME.SUBJECT.toString(), Integer.toString(subject.getId()));
	}

	@Override
	public ArrayList<Opinion> selectByAuthor(Member author) {
		return selectBy(FIELDS_NAME.SUBJECT.toString(), Integer.toString(author.getId()));
	}

	@Override
	public Member fillMember(Member member) {
			member.setOpinionsOnMe(selectBySubject(member));
		return member;
	}
}