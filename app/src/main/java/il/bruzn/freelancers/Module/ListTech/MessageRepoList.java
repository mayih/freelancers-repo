package il.bruzn.freelancers.Module.ListTech;

import java.util.ArrayList;
import java.util.Collections;

import il.bruzn.freelancers.Module.ConnectedMember;
import il.bruzn.freelancers.Module.Entities.Member;
import il.bruzn.freelancers.Module.Entities.Message;
import il.bruzn.freelancers.Module.iRepositories.iMessageRepo;

/**
 * Created by Yair on 10/12/2014.
 */
public class MessageRepoList extends ListCRUD implements iMessageRepo {
	@Override
	public void add(Message entry) {
		_messages.add(entry);
	}

	@Override
	public ArrayList<ArrayList<Message>> selectAllDiscussions(Member self) {
		ArrayList<ArrayList<Message>> discussionsSelected = new ArrayList<ArrayList<Message>>();
		Member interlocutor;
		boolean discussionExist;

		for (final Message message:_messages){
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
		ArrayList<Message> selected = new ArrayList<Message>();

		for (Message message:_messages)
			if ((message.getReceiver() == self || message.getAuthor() == self) && (message.getReceiver() == other || message.getAuthor() == other))
				selected.add(message);

		return selected;
	}

	@Override
	public Message selectById(int Id) {
		for (Message message:_messages)
			if (message.getId() == Id)
				return message;
		return null;
	}

	// DO NOT USE IT!!!
	@Override
	public ArrayList<Message> selectAll() {
		return null;
	}

	// useless in lists
	@Override
	public ArrayList<Message> selectBy(String field, String value) {
		return null;
	}
	// useless in lists
	@Override
	public void update(Message entry) {	}

	@Override
	public void delete(Message entry) {
		_messages.remove(entry);
	}
}