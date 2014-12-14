package il.bruzn.freelancers.Module;

import java.util.HashMap;
import java.util.Objects;

import il.bruzn.freelancers.Module.ListTech.MemberRepoList;
import il.bruzn.freelancers.Module.ListTech.MessageRepoList;
import il.bruzn.freelancers.Module.ListTech.OpinionRepoList;
import il.bruzn.freelancers.Module.iRepositories.iMemberRepo;
import il.bruzn.freelancers.Module.iRepositories.iMessageRepo;
import il.bruzn.freelancers.Module.iRepositories.iOpinionRepo;

/**
 * Created by Yair on 08/12/2014.
 */
public class Module {
	static final Technology tech = Technology.List;
	enum Technology{ SqlLite, Server, List };

	// Android FAQ: How do I pass data between Activities/Services within a single application?
	// http://developer.android.com/guide/faq/framework.html
	private static final HashMap<Long, Object> _hashMap = new HashMap<>();
	public static HashMap<Long, Object> getHashMap() {
		return _hashMap;
	}

	// Repositories
	static iMemberRepo	_memberRepo;
	static iOpinionRepo _opnionRepo;
	static iMessageRepo _messageRepo;

	public static void create(){
		switch (tech){
			case List:
				_memberRepo = new MemberRepoList();
				_opnionRepo = new OpinionRepoList();
				_messageRepo = new MessageRepoList();
				// ...
		}
		// ...
	}



	public static Technology getTech() {
		return tech;
	}
	public static iOpinionRepo getOpnionRepo() {
		return _opnionRepo;
	}
	public static iMemberRepo getMemberRepo() {
		return _memberRepo;
	}
	public static iMessageRepo getMessageRepo() {
		return _messageRepo;
	}
}