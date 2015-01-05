package il.bruzn.freelancers.Modele.SqLiteTech;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import il.bruzn.freelancers.Modele.Entities.Member;
import il.bruzn.freelancers.Modele.iRepositories.iMemberRepo;

/**
 * Created by Yair on 17/12/2014.
 */
public class MemberRepoSqLite extends  SQLiteTech<Member> implements iMemberRepo {

	// Table's name
	static final String NAME_TABLE = "Member";

	// Fields' name
	enum FIELDS_NAME {  ID("_id"),
		LASTNAME("lastName"),
		FIRSTNAME("firstName"),
		SPECIALITY("speciality"),
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
		public String toString() {
			return _name;
		}
	};
	// Request to create the table
	static final String CREATE_REQ = "CREATE TABLE IF NOT EXISTS " + NAME_TABLE + " (" +
			FIELDS_NAME.ID			+ " INTEGER PRIMARY KEY AUTOINCREMENT, " +
			FIELDS_NAME.EMAIL		+ " TEXT NOT NULL, " +
			FIELDS_NAME.PASSWORD	+ " TEXT NOT NULL, " +
			FIELDS_NAME.FIRSTNAME	+ " TEXT, " +
			FIELDS_NAME.LASTNAME	+ " TEXT, " +
			FIELDS_NAME.SPECIALITY	+ " TEXT, "	+
			FIELDS_NAME.ADDRESS		+ " TEXT, " +
			FIELDS_NAME.PHONENUMBER	+ " TEXT, " +
			FIELDS_NAME.PICTURE		+ " TEXT, " +
			FIELDS_NAME.GOOGLE		+ " TEXT, " +
			FIELDS_NAME.LINKEDIN	+ " TEXT "	+
			");";

	public MemberRepoSqLite(Context context, String name, int version) {
		super(context, name, version);
	}


	// iMemberRepo IMPLEMENTATION ---
	@Override
	public Member selectByEmail(String email) {
		List<Member> selectedMember = selectBy(FIELDS_NAME.EMAIL.toString(), email);
		if (selectedMember.size() > 0)
			return selectedMember.get(0);
		return null;
	}
	@Override
	public Member selectByEmailAndPassword(String email, String password) {
		Member member = selectByEmail(email);
		if (member!= null && member.authenticate(email, password))
			return member;
		else
			return null;
	}


	@Override
	public Member selectWithOpinions(String email) {
		return null;
	}
	@Override
	public ArrayList<Member> selectByIds(String listOfIds) {
		String request = "SELECT * FROM " + getNameTable() + " WHERE _id IN " + listOfIds;
		Cursor cursor = getReadableDatabase().rawQuery(request, null);
		return toEntity(cursor);
	}

	// SQLiteTech IMPLEMENTATION ---
	@Override
	public ArrayList<Member> toEntity(Cursor cursor){
		ArrayList<Member> memberArrayList = new ArrayList<>();
		Member member;
		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
			member = new Member();

			int id				= cursor.getInt(cursor.getColumnIndex(FIELDS_NAME.ID.toString()));
			String email		= cursor.getString( cursor.getColumnIndex(FIELDS_NAME.EMAIL.toString()) );
			String password		= cursor.getString( cursor.getColumnIndex(FIELDS_NAME.PASSWORD.toString()) );
			String firstname	= cursor.getString( cursor.getColumnIndex(FIELDS_NAME.FIRSTNAME.toString()) );
			String lastname		= cursor.getString( cursor.getColumnIndex(FIELDS_NAME.LASTNAME.toString()) );
			String speciality	= cursor.getString( cursor.getColumnIndex(FIELDS_NAME.SPECIALITY.toString()) );
			String phonenumber	= cursor.getString( cursor.getColumnIndex(FIELDS_NAME.PHONENUMBER.toString()) );
			String google		= cursor.getString( cursor.getColumnIndex(FIELDS_NAME.GOOGLE.toString()) );
			String linkedin		= cursor.getString( cursor.getColumnIndex(FIELDS_NAME.LINKEDIN.toString()) );
			// Download picture...

			member	.setId(id)
					.setEmail(email)
					.setPassword(password)
					.setFirstName(firstname)
					.setLastName(lastname)
					.setSpeciality(speciality)
					.setPhoneNumber(phonenumber)
					.setGooglePlus(google)
					.setLinkedIn(linkedin);
			// Add picture...

			memberArrayList.add(member);
		}
		return memberArrayList;
	}
	@Override
	public ContentValues toContentValues(Member member) {
		ContentValues content = new ContentValues();
		content.put(FIELDS_NAME.EMAIL.toString(), member.getEmail());
		content.put(FIELDS_NAME.PASSWORD.toString(), member.getPassword());
		content.put(FIELDS_NAME.FIRSTNAME.toString(), member.getFirstName());
		content.put(FIELDS_NAME.LASTNAME.toString(), member.getLastName());
		content.put(FIELDS_NAME.SPECIALITY.toString(), member.getSpeciality());
		if (member.getAddress() != null)
			content.put(FIELDS_NAME.ADDRESS.toString(), member.getAddress().toString());
		content.put(FIELDS_NAME.PHONENUMBER.toString(), member.getPhoneNumber());
		// Get the picture link...
		content.put(FIELDS_NAME.GOOGLE.toString(), member.getGooglePlus());
		content.put(FIELDS_NAME.LINKEDIN.toString(), member.getLinkedIn());
		return content;
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
//			for (FIELDS_NAME field : FIELDS_NAME.values()) {
//				if (field != FIELDS_NAME.ID) {
//					index = cursor.getColumnIndex(field.toString());
//					if (index >= 0) {
//						content.put(field.toString(), cursor.getString(index));
//					}
//				}
//			}
//			listSaved.add(content); // Insert the content
//		}
//		return listSaved;
//	}
}