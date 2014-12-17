package il.bruzn.freelancers.Module.SqLiteTech;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import il.bruzn.freelancers.Module.Entities.Member;
import il.bruzn.freelancers.Module.iRepositories.iMemberRepo;

/**
 * Created by Yair on 17/12/2014.
 */
public class MemberRepoSqLite implements iMemberRepo {

	@Override
	public void add(Member entry) {

	}

	@Override
	public Member selectByEmail(String email) {
		return null;
	}

	@Override
	public Member selectWithOpinions(String email) {
		return null;
	}

	@Override
	public Member selectById(int Id) {
		return null;
	}

	@Override
	public ArrayList<Member> selectAll() {
		return null;
	}

	@Override
	public ArrayList<Member> selectBy(String field, String value) {
		return null;
	}

	@Override
	public void update(Member entry) {

	}

	@Override
	public void delete(Member entry) {

	}

	static class MemberTableManager extends SQLiteOpenHelper {
		// Table's name
		static final String NAME_TABLE = "Member";

		// Fields' name
		enum FIELDS_NAME {
							ID("_id"),
							LASTNAME("LastName"),
							FIRSTNAME("FirstName"),
							EMAIL("Email"),
							PASSWORD("Password"),
							ADDRESS("Adsress"),
							PHONENUMBER("PhoneNumber"),
							PICTURE("Picture"),
							GOOGLE("Google"),
							LINKEDIN("LinkedIn");

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
				FIELDS_NAME.ID			+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
				FIELDS_NAME.EMAIL		+" TEXT, " +
				FIELDS_NAME.PASSWORD	+" TEXT, " +
				FIELDS_NAME.FIRSTNAME	+" TEXT NOT NULL, " +
				FIELDS_NAME.LASTNAME	+" TEXT NOT NULL, " +
				FIELDS_NAME.ADDRESS		+" TEXT, " +
				FIELDS_NAME.PHONENUMBER	+" TEXT, " +
				FIELDS_NAME.PICTURE		+" TEXT, " +
				FIELDS_NAME.GOOGLE		+" TEXT, " +
				FIELDS_NAME.LINKEDIN	+" TEXT, " +
				");";

		MemberTableManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
			super(context, name, factory, version);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			create(db, null);
		}
		public void create(SQLiteDatabase db, List<ContentValues> list){
			db.execSQL(CREATE_REQ);
			for (ContentValues content: list)
				db.insert(NAME_TABLE, null, content);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

			ContentValues content;
			int index;
			Cursor cursor = db.rawQuery("Select * from "+NAME_TABLE, null); // Get the whole table

			// Copy each line of the cursor into a content and add it to the list
			ArrayList<ContentValues> listSaved = new ArrayList<>();
			while (cursor.moveToNext()){
				content = new ContentValues();
				for (FIELDS_NAME field :FIELDS_NAME.values()){
					index = cursor.getColumnIndex(field.getName());

					if (field.equals(FIELDS_NAME.ID))
						content.put(field.getName(), cursor.getInt(index));
					else
						content.put(field.getName(), cursor.getString(index));
				}
				listSaved.add(content); // Insert the content
			}

			db.execSQL("DROP TABLE IF EXIST "+NAME_TABLE);
			create(db, listSaved);
		}
	}
}
