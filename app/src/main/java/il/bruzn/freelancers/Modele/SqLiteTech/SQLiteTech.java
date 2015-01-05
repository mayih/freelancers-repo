package il.bruzn.freelancers.Modele.SqLiteTech;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import il.bruzn.freelancers.Modele.CRUD;

/**
 * Created by Moshe on 17/12/14.
 */
public abstract class SQLiteTech<T> extends SQLiteOpenHelper implements CRUD<T> {

	// Methods to implemments ---
	public abstract String createReq();
    public abstract String getNameTable();
	public abstract ArrayList<T> toEntity(Cursor cursor);
	public abstract ContentValues toContentValues(T entity);
	private String[][] _allRepo = {
			{MemberRepoSqLite.NAME_TABLE, MemberRepoSqLite.CREATE_REQ},
			{MessageRepoSQLite.NAME_TABLE, MessageRepoSQLite.CREATE_REQ},
			{OpinionRepoSQLite.NAME_TABLE, OpinionRepoSQLite.CREATE_REQ},
	};

	// Constructor ---
    SQLiteTech(Context context, String name, int version) {
        super(context, name, null, version);
		getWritableDatabase().execSQL(createReq());
	}

	// SQLiteTech IMPLEMENTATION ---
	public void create(SQLiteDatabase db, String createRequest){
		create(db, createRequest, null);
	}
	public void create(SQLiteDatabase db, String createRequest, List<ContentValues> list) {
		db.execSQL(createRequest);
		if (list != null)
			for (ContentValues content: list)
				db.insert(getNameTable(), null, content);
	}
	public List<ContentValues> tableCopied(Cursor cursor) {
		ArrayList<ContentValues> listCopied = new ArrayList<>();
		ContentValues oneRow;
		int index;

		while (cursor.moveToNext()){ // Moves row to row in the cursor, convert it to a ContentValues, and at last add it to listCopied
			oneRow = new ContentValues();
			for (String columnName : cursor.getColumnNames()){ // Moves collumn to collumn on the row, get the type of the collumn and copy it to the ContentValues
				index = cursor.getColumnIndex(columnName);

				switch (cursor.getType(index)){
					case Cursor.FIELD_TYPE_INTEGER:
						oneRow.put(columnName, cursor.getInt(index));
						break;
					case Cursor.FIELD_TYPE_FLOAT:
						oneRow.put(columnName, cursor.getFloat(index));
						break;
					case Cursor.FIELD_TYPE_STRING:
						oneRow.put(columnName, cursor.getString(index));
						break;
					case Cursor.FIELD_TYPE_BLOB:
						oneRow.put(columnName, cursor.getBlob(index));
						break;
				}
			}
			listCopied.add(oneRow);
		}
		return listCopied;
	}

    @Override
    public void onCreate(SQLiteDatabase db) {
		for (String[] repo : _allRepo)
			create(db, repo[1]);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		for (String[] repo : _allRepo) {
			List<ContentValues> listSaved = (tableCopied(db.rawQuery("SELECT * FROM " + repo[0], null)));
			db.execSQL("DROP TABLE IF EXISTS " + repo[0]);
			create(db, repo[1], listSaved);
		}
    }

   // CRUD IMPLEMENTATION ----
	@Override
	public void add(T entry) {
		getWritableDatabase().insert(getNameTable(), null, toContentValues(entry));
	}

	@Override
	public T selectById(int Id) {
		Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM "+getNameTable()+" WHERE _id = ?", new String[] {""+Id});
		return toEntity(cursor).get(0);
	}

	@Override
	public ArrayList<T> selectAll() {
		return toEntity(getReadableDatabase().rawQuery("SELECT * FROM "+getNameTable(), null));
	}

	@Override
	public ArrayList<T> selectBy(String field, String value) {
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT * FROM " + getNameTable() + " WHERE " + field + " = ?", new String[]{value});
		return toEntity(cursor);
	}

	@Override
	public void update(T entry, int id) {
		getWritableDatabase().update(getNameTable(), toContentValues(entry), "_id = ?", new String[]{id+""});
	}

	@Override
	public void delete(T entry, int id) {
		getWritableDatabase().execSQL("DELETE FROM "+getNameTable()+" WHERE _id = ?", new String[]{id+""});
	}
}