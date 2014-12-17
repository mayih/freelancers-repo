package il.bruzn.freelancers.Module.SqLiteTech;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import il.bruzn.freelancers.Module.Entities.Member;
import il.bruzn.freelancers.Module.iRepositories.iMemberRepo;

/**
 * Created by Yair on 17/12/2014.
 */
public class MemberRepoSQLite implements iMemberRepo {

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

	static class MemberTableManager extends SQLiteTech {
		// Table's name
		static final String NAME_TABLE = "Member";

		// Fields' name
		enum FIELDS_NAME {  ID("_id"),
							LASTNAME("lastName"),
							FIRSTNAME("firstName"),
							EMAIL("email"),
							PASSWORD("password"),
							ADDRESS("adsress"),
							PHONENUMBER("phoneNumber"),
							PICTURE("picture"),
							GOOGLE("google"),
							LINKEDIN("linkedIn");

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
                        FIELDS_NAME.ID			+ " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        FIELDS_NAME.EMAIL		+ " TEXT NOT NULL, " +
                        FIELDS_NAME.PASSWORD	+ " TEXT NOT NULL, " +
                        FIELDS_NAME.FIRSTNAME	+ " TEXT, " +
                        FIELDS_NAME.LASTNAME	+ " TEXT, " +
                        FIELDS_NAME.ADDRESS		+ " TEXT, " +
                        FIELDS_NAME.PHONENUMBER	+ " TEXT, " +
                        FIELDS_NAME.PICTURE		+ " TEXT, " +
                        FIELDS_NAME.GOOGLE		+ " TEXT, " +
                        FIELDS_NAME.LINKEDIN	+ " TEXT, " +
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
                for (FIELDS_NAME field : FIELDS_NAME.values()) {
					if (field != FIELDS_NAME.ID) {
						index = cursor.getColumnIndex(field.getName());
						if (index >= 0) {
							content.put(field.getName(), cursor.getString(index));
						}
					}
				}
                listSaved.add(content); // Insert the content
            }
            return listSaved;
        }
	}
}
