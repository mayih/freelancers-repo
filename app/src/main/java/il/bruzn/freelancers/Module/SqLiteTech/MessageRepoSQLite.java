package il.bruzn.freelancers.Module.SqLiteTech;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import il.bruzn.freelancers.Module.Entities.Member;
import il.bruzn.freelancers.Module.Entities.Message;
import il.bruzn.freelancers.Module.iRepositories.iMessageRepo;

/**
 * Created by Moshe on 17/12/14.
 */
public class MessageRepoSQLite implements iMessageRepo {
    @Override
    public ArrayList<ArrayList<Message>> selectAllDiscussions(Member self) {
        return null;
    }

    @Override
    public ArrayList<Message> selectDiscussion(Member self, Member other) {
        return null;
    }

    @Override
    public void add(Message entry) {

    }

    @Override
    public Message selectById(int Id) {
        return null;
    }

    @Override
    public ArrayList<Message> selectAll() {
        return null;
    }

    @Override
    public ArrayList<Message> selectBy(String field, String value) {
        return null;
    }

    @Override
    public void update(Message entry) {

    }

    @Override
    public void delete(Message entry) {

    }

    static class MessageTableManager extends SQLiteTech {
        // Table's name
        static final String NAME_TABLE = "Message";

        // Fields' name
        enum FIELDS_NAME {  ID("_id"),
                            AUTHOR("author_id"),
                            RECEIVER("receiver_id"),
                            MESSAGE("message"),
                            DATE("date_of_message");

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
                FIELDS_NAME.AUTHOR	    + " INTEGER NOT NULL, " +
                FIELDS_NAME.RECEIVER	+ " INTEGER NOT NULL, " +
                FIELDS_NAME.MESSAGE	    + " TEXT NOT NULL, "    +
                FIELDS_NAME.DATE	    + " INTEGER NOT NULL, " +
                ");";

        MessageTableManager(Context context, String name, int version) {
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
							if (field == FIELDS_NAME.MESSAGE) {
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
