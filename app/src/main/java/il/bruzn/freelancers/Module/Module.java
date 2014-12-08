package il.bruzn.freelancers.Module;

import il.bruzn.freelancers.Module.ListTech.MemberRepoList;
import il.bruzn.freelancers.Module.ListTech.OpinionRepoList;
import il.bruzn.freelancers.Module.iRepositories.iMemberRepo;
import il.bruzn.freelancers.Module.iRepositories.iOpinionRepo;

/**
 * Created by Yair on 08/12/2014.
 */
public class Module {
	static final Technology tech = Technology.List;
	enum Technology{ SqlLite, Server, List };

	// Repositories
	static iMemberRepo	_memberRepo;
	static iOpinionRepo _opnionRepo;

	public static void create(){
		switch (tech){
			case List:
				_memberRepo = new MemberRepoList();
				_opnionRepo = new OpinionRepoList();
				// ...
		}
		// ...
	}



	public static Technology getTech() {
		return tech;
	}
	public static iOpinionRepo get_opnionRepo() {
		return _opnionRepo;
	}
	public static iMemberRepo get_memberRepo() {
		return _memberRepo;
	}
}
