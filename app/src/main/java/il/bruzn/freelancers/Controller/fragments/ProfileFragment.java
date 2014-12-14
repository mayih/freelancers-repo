package il.bruzn.freelancers.Controller.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import il.bruzn.freelancers.Module.ConnectedMember;
import il.bruzn.freelancers.Module.Entities.Member;
import il.bruzn.freelancers.Module.Module;
import il.bruzn.freelancers.R;

/**
 * Created by Yair on 01/12/2014.
 */
public class ProfileFragment extends Fragment  implements TitledFragment {

	private Member _member;
	public static final String EMAIL_MEMBER_KEY ="member's email";

	public ProfileFragment() {
		_member = ConnectedMember.getMember();
	}

	@Override
	public String getTitle() {
		return _member.getFirstName()+" "+_member.getLastName();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments()!=null && getArguments().containsKey(EMAIL_MEMBER_KEY))
			_member = Module.getMemberRepo().selectByEmail(getArguments().getString(EMAIL_MEMBER_KEY));
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View layout = inflater.inflate(R.layout.fragment_profile, container, false);

		((TextView)layout.findViewById(R.id.profile_welcome)).setText("Profile of "+_member.getFirstName());
		return layout;
	}
}
