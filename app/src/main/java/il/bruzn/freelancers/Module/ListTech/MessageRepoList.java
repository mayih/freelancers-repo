package il.bruzn.freelancers.Module.ListTech;

import java.util.ArrayList;

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
	public ArrayList<Message> selectAllDiscussions(Member self) {
		ArrayList<Message> selected = new ArrayList<Message>();

		for (Message message:_messages)
			if (message.getReceiver() == self || message.getAuthor() == self)
				selected.add(message);

		return selected;
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
