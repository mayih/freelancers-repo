package il.bruzn.freelancers.Controller.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import il.bruzn.freelancers.Module.ConnectedMember;
import il.bruzn.freelancers.Module.Entities.Message;
import il.bruzn.freelancers.Module.Module;
import il.bruzn.freelancers.R;

/**
 * Created by Yair on 01/12/2014.
 */
public class InboxFragment extends Fragment {

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View layout = inflater.inflate(R.layout.fragment_inbox, container, false);
		return layout;
	}

	private class InboxArrayAdapter extends ArrayAdapter<Message>{

		public InboxArrayAdapter() {
			super(getActivity(), R.layout.item_discussion, Module.getMessageRepo().selectAllDiscussions(ConnectedMember.getMember()));
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			return super.getView(position, convertView, parent);
		}
	}
}