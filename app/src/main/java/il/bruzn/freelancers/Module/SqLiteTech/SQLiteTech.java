package il.bruzn.freelancers.Module.SqLiteTech;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

/**
 * Created by Moshe on 17/12/14.
 */
public abstract class SQLiteTech extends SQLiteOpenHelper {

    public abstract String createReq();
    public abstract String getNameTable();
    public abstract List<ContentValues> tableCopied(Cursor cursor);

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
}