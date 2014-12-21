package il.bruzn.freelancers.Module.SqLiteTech;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import il.bruzn.freelancers.Module.ConnectedMember;
import il.bruzn.freelancers.Module.Entities.Member;
import il.bruzn.freelancers.Module.Entities.Message;
import il.bruzn.freelancers.Module.Module;
import il.bruzn.freelancers.Module.iRepositories.iMemberRepo;
import il.bruzn.freelancers.Module.iRepositories.iMessageRepo;

/**
 * Created by Moshe on 17/12/14.
 */
public class MessageRepoSQLite extends SQLiteTech<Message> implements iMessageRepo {

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
			FIELDS_NAME.DATE	    + " INTEGER NOT NULL, " + // TimeStamp in seconds
			");";

	// SQLiteTech IMPLEMENTATION ---
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
				index = cursor.getColumnIndex(field.toString());
				if (field != FIELDS_NAME.ID && index >= 0) {
					if (field == FIELDS_NAME.MESSAGE){
							content.put(field.toString(), cursor.getString(index));
					}
					else {
							content.put(field.toString(), cursor.getInt(index));
					}
				}
			}
			listSaved.add(content); // Insert the content
		}
		return listSaved;
	}
	@Override
	public ArrayList<Message> toEntity(Cursor cursor) {
		// recup les ids
		String listOfIds = "(";
		int author_id, receiver_id;
		while (cursor.moveToNext()){
			author_id	= cursor.getInt( cursor.getColumnIndex(FIELDS_NAME.AUTHOR.toString()));
			receiver_id	= cursor.getInt( cursor.getColumnIndex(FIELDS_NAME.RECEIVER.toString()));
			listOfIds += author_id + ", " + receiver_id + ", ";
		}
		listOfIds += ")";

		// requete



		ArrayList<Message> messageArrayList = new ArrayList<>();
		Message message;

		for (cursor.moveToFirst(); cursor.isAfterLast(); cursor.moveToNext()){

			int id			= cursor.getInt(cursor.getColumnIndex(FIELDS_NAME.ID.toString()));
			author_id	= cursor.getInt(cursor.getColumnIndex(FIELDS_NAME.AUTHOR.toString()));
			receiver_id	= cursor.getInt(cursor.getColumnIndex(FIELDS_NAME.RECEIVER.toString()));
			String messageTxt=cursor.getString(cursor.getColumnIndex(FIELDS_NAME.MESSAGE.toString()));
			long timeStamp = cursor.getLong(cursor.getColumnIndex(FIELDS_NAME.DATE.toString()));

			// Load the author and receiver
			iMemberRepo memberRepo = Module.getMemberRepo();
			Member author	= memberRepo.selectById(author_id);
			Member receiver	= memberRepo.selectById(receiver_id);

			// Convert the timestamp in date
			Date dateOfMessage = new Date(timeStamp * 1000); // milliseconds to seconds

			// Create the message
			message = new Message(author, receiver, messageTxt).setId(id).setDate(dateOfMessage);

			messageArrayList.add(message);
		}
		return messageArrayList;
	}
	@Override
	public ContentValues toContentValues(Message message) {
		ContentValues content = new ContentValues();

		content.put(FIELDS_NAME.AUTHOR.toString(), message.getAuthor().getId());
		content.put(FIELDS_NAME.RECEIVER.toString(), message.getReceiver().getId());
		content.put(FIELDS_NAME.MESSAGE.toString(), message.getText());
		content.put(FIELDS_NAME.DATE.toString(), message.getDate().getTime()/1000);

		return content;
	}

	// iMessageRepo IMPLEMENTATION ---
	@Override
	public ArrayList<ArrayList<Message>> selectAllDiscussions(Member self) {
		Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM " + getNameTable() + " WHERE "
			+ FIELDS_NAME.AUTHOR.toString() + 	" = ? OR "
			+ FIELDS_NAME.RECEIVER.toString() +	" = ? ",
			new String[]{self.getId()+"", self.getId()+""});

		ArrayList<Message> arrayListMessages = toEntity(cursor);
		ArrayList<ArrayList<Message>> discussionsSelected = new ArrayList<>();
		Member interlocutor;
		boolean discussionExist;

		for (final Message message:arrayListMessages){
			// Check if one of the two persons is our member, if so, the oposite one is the interlocutor
			interlocutor = null;
			if (message.getAuthor() == ConnectedMember.getMember())
				interlocutor = message.getReceiver();
			else if (message.getReceiver() == ConnectedMember.getMember())
				interlocutor = message.getAuthor();

			// If the message is indeed linked to our member, it will pass on every discussion already created to check if the interlocutor is in one of them.
			// If so, it puts the message inside.
			// Else, it creates a new list of message and add the message to it.
			if (interlocutor != null){
				discussionExist = false;
				for (ArrayList<Message> discussion:discussionsSelected){
					if (discussion.get(0).getAuthor() == interlocutor || discussion.get(0).getReceiver() == interlocutor) {
						discussion.add(message);
						discussionExist = true;
					}
				}
				if (!discussionExist)
					discussionsSelected.add(new ArrayList<Message>() {{ add(message); }});
			}
		}

		// Sort each message in each discussion according its date.
		for (ArrayList<Message> discussion:discussionsSelected)
			Collections.sort(discussion);
		return discussionsSelected;
	}

	@Override
	public ArrayList<Message> selectDiscussion(Member self, Member other) {
		return null;
	}
}
