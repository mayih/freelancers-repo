package il.bruzn.freelancers.Modele.SqLiteTech;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Date;

import il.bruzn.freelancers.Modele.Entities.Member;
import il.bruzn.freelancers.Modele.Entities.Opinion;
import il.bruzn.freelancers.Modele.Modele;
import il.bruzn.freelancers.Modele.iRepositories.iOpinionRepo;

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
		AUTHORS("authors"),
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
			FIELDS_NAME.AUTHORS			+ " INTEGER NOT NULL, " +
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

//	@Override
//	public List<ContentValues> tableCopied(Cursor cursor) {
//		ArrayList<ContentValues> listSaved = new ArrayList<>();
//		ContentValues content;
//		int index;
//
//		// Copy each line of the cursor into a content and add it to the list
//		while (cursor.moveToNext()){
//			content = new ContentValues();
//			for (FIELDS_NAME field : FIELDS_NAME.values()){
//				if (field != FIELDS_NAME.ID) {
//					index = cursor.getColumnIndex(field.toString());
//					if (index >= 0) {
//						if (field == FIELDS_NAME.OPINION_MESSAGE) {
//							content.put(field.toString(), cursor.getString(index));
//						} else {
//							content.put(field.toString(), cursor.getInt(index));
//						}
//					}
//				}
//			}
//			listSaved.add(content); // Insert the content
//		}
//		return listSaved;
//	}

	@Override
	public ArrayList<Opinion> toEntity(Cursor cursor) {
		ArrayList<Opinion> opinionArrayList = new ArrayList<>();
		Opinion opinion;

			for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
				opinion = new Opinion();

				int id = cursor.getInt(cursor.getColumnIndex(FIELDS_NAME.ID.toString()));
				int levelInteger = cursor.getInt(cursor.getColumnIndex(FIELDS_NAME.LEVEL.toString()));
				int done = cursor.getInt(cursor.getColumnIndex(FIELDS_NAME.IS_DONE.toString()));
				String message = cursor.getString(cursor.getColumnIndex(FIELDS_NAME.OPINION_MESSAGE.toString()));
				int authors_id = cursor.getInt(cursor.getColumnIndex(FIELDS_NAME.AUTHORS.toString()));
				int subject_id = cursor.getInt(cursor.getColumnIndex(FIELDS_NAME.SUBJECT.toString()));
				Date date = new Date(cursor.getLong(cursor.getColumnIndex(FIELDS_NAME.DATE.toString())) * 1000);

				Member authors = Modele.getMemberRepo().selectById(authors_id);
				Member subject = Modele.getMemberRepo().selectById(subject_id);
				Opinion.Level level = Opinion.Level.fromInteger(levelInteger);

				opinion.setId(id)
						.setLevel(level)
						.setDone(done != 0)
						.setText(message)
						.setAuthor(authors)
						.setSubject(subject)
						.setDate(date);

				opinionArrayList.add(opinion);
			}
			return opinionArrayList;
	}

	@Override
	public ContentValues toContentValues(Opinion opinion) {
		ContentValues content = new ContentValues();

		content.put(FIELDS_NAME.LEVEL.toString(), opinion.getLevel().getValue());
		content.put(FIELDS_NAME.IS_DONE.toString(), opinion.isDone());
		content.put(FIELDS_NAME.OPINION_MESSAGE.toString(), opinion.getText() );
		content.put(FIELDS_NAME.AUTHORS.toString(), opinion.getAuthor().getId());
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