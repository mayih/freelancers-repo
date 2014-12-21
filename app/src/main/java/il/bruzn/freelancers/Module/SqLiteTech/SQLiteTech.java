package il.bruzn.freelancers.Module.SqLiteTech;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import il.bruzn.freelancers.Module.CRUD;
import il.bruzn.freelancers.Module.Entities.Member;

/**
 * Created by Moshe on 17/12/14.
 */
public abstract class SQLiteTech<T> extends SQLiteOpenHelper implements CRUD<T> {

	protected abstract ContentValues		toContentValues(Member member);

	// Methods to implemments
	public abstract String createReq();
    public abstract String getNameTable();
    public abstract List<ContentValues> tableCopied(Cursor cursor);
	public abstract ArrayList<T> toEntity(Cursor cursor);
	public abstract ContentValues toContentValues(T entity);


    SQLiteTech(Context context, String name, int version) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        create(db, null);
    }

    public void create(SQLiteDatabase db, List<ContentValues> list){
        db.execSQL(createReq());
        for (ContentValues content: list)
            db.insert(getNameTable(), null, content);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        List<ContentValues> listSaved = tableCopied(db.rawQuery("SELECT * FROM " + getNameTable(), null));

        db.execSQL("DROP TABLE IF EXIST " + getNameTable());
        create(db, listSaved);
    }

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
		return toEntity(getReadableDatabase().rawQuery("SELECT * FROM " + getNameTable() + " WHERE " + field + " = ?", new String[]{value}));
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