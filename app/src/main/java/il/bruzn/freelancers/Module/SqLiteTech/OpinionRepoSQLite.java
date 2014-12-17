package il.bruzn.freelancers.Module.SqLiteTech;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import il.bruzn.freelancers.Module.Entities.Member;
import il.bruzn.freelancers.Module.Entities.Opinion;
import il.bruzn.freelancers.Module.iRepositories.iOpinionRepo;

/**
 * Created by Moshe on 17/12/14.
 */
public class OpinionRepoSQLite implements iOpinionRepo {

	@Override
	public ArrayList<Opinion> getBySubject(Member subject) {
		return null;
	}

	@Override
	public ArrayList<Opinion> getByAuthor(Member subject) {
		return null;
	}

	@Override
	public Member fillMember(Member member) {
		return null;
	}

	@Override
	public void add(Opinion entry) {

	}

	@Override
	public Opinion selectById(int Id) {
		return null;
	}

	@Override
	public ArrayList<Opinion> selectAll() {
		return null;
	}

	@Override
	public ArrayList<Opinion> selectBy(String field, String value) {
		return null;
	}

	@Override
	public void update(Opinion entry) {

	}

	@Override
	public void delete(Opinion entry) {

	}
	static class MemberTableManager extends SQLiteTech {
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
			public String getName() {
				return _name;
			}
		};
		// Request to create the table
		static final String CREATE_REQ = "CREATE TABLE " + NAME_TABLE + " (" +
				FIELDS_NAME.ID				+ " INTEGER PRIMARY KEY AUTOINCREMENT, " +
				FIELDS_NAME.LEVEL			+ " INTEGER NOT NULL, " +
				FIELDS_NAME.IS_DONE			+ " INTEGER, " +
				FIELDS_NAME.OPINION_MESSAGE	+ " TEXT, " 			+
				FIELDS_NAME.AUTHORS			+ " INTEGER NOT NULL, " +
				FIELDS_NAME.SUBJECT			+ " INTEGER NOT NULL, " +
				FIELDS_NAME.DATE			+ " INTEGER NOT NULL, " +
				");";

		MemberTableManager(Context context, String name, int version) {
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
		public List<ContentValues> tableCopied(Cursor cursor) {
			ArrayList<ContentValues> listSaved = new ArrayList<>();
			ContentValues content;
			int index;

			// Copy each line of the cursor into a content and add it to the list
			while (cursor.moveToNext()){
				content = new ContentValues();
				for (FIELDS_NAME field : FIELDS_NAME.values()){
					if (field != FIELDS_NAME.ID) {
						index = cursor.getColumnIndex(field.getName());
						if (index >= 0) {
							if (field == FIELDS_NAME.OPINION_MESSAGE) {
								content.put(field.getName(), cursor.getString(index));
							} else {
								content.put(field.getName(), cursor.getInt(index));
							}
						}
					}
				}
				listSaved.add(content); // Insert the content
			}
			return listSaved;
		}
	}
}
