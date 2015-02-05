package il.bruzn.freelancers.Model;

import android.content.Context;

import java.util.HashMap;

import il.bruzn.freelancers.Model.ListTech.MemberRepoList;
import il.bruzn.freelancers.Model.ListTech.MessageRepoList;
import il.bruzn.freelancers.Model.ListTech.OpinionRepoList;
import il.bruzn.freelancers.Model.ListTech.RequestRepoList;
import il.bruzn.freelancers.Model.SqLiteTech.MemberRepoSqLite;
import il.bruzn.freelancers.Model.SqLiteTech.MessageRepoSQLite;
import il.bruzn.freelancers.Model.SqLiteTech.OpinionRepoSQLite;
import il.bruzn.freelancers.Model.SqLiteTech.RequestRepoSqLite;
import il.bruzn.freelancers.Model.iRepositories.iMemberRepo;
import il.bruzn.freelancers.Model.iRepositories.iMessageRepo;
import il.bruzn.freelancers.Model.iRepositories.iOpinionRepo;
import il.bruzn.freelancers.Model.iRepositories.iRequestRepo;

/**
 * Created by Yair on 08/12/2014.
 */
public class Model {
	static final Technology tech = Technology.SQLite;
	enum Technology{SQLite, Server, List };

	// DataBase Name
	public final static String DB_NAME = "DB";
	public final static int DB_VERSION = 15;

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
	static iRequestRepo _requestRepo;

	public static void create(Context context, String dbName, int version){
		switch (tech){
			case List:
				_memberRepo = new MemberRepoList();
				_opnionRepo = new OpinionRepoList();
				_messageRepo = new MessageRepoList();
				_requestRepo = new RequestRepoList();
				// ...
				break;
			case SQLite:
				_memberRepo = new MemberRepoSqLite(context, dbName, version);
				_messageRepo = new MessageRepoSQLite(context, dbName, version);
				_opnionRepo = new OpinionRepoSQLite(context, dbName, version);
				_requestRepo = new RequestRepoSqLite(context, dbName, version);
				// ...
				break;
			// ...
		}
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
	public static iRequestRepo getRequestRepo() {
		return _requestRepo;
	}
}
