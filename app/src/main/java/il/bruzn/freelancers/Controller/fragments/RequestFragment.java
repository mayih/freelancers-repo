package il.bruzn.freelancers.Controller.fragments;

import android.support.v4.app.ListFragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import il.bruzn.freelancers.Model.ConnectedMember;
import il.bruzn.freelancers.Model.Entities.Member;
import il.bruzn.freelancers.Model.Entities.Request;
import il.bruzn.freelancers.Model.Model;
import il.bruzn.freelancers.R;
import il.bruzn.freelancers.basic.ImageHelper;
import il.bruzn.freelancers.basic.Sleep;

/**
 * Created by Yair on 06/01/2015.
 */
public class RequestFragment extends ListFragment implements TitledFragment {

	ArrayList<ArrayList<Request>> _listOfRequests = new ArrayList<>();
	final static String KEY_REQUESTS = "key for requests in hashmap";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (savedInstanceState != null){
			long hashMapKey = savedInstanceState.getLong(KEY_REQUESTS);
			_listOfRequests = (ArrayList<ArrayList<Request>>) Model.getHashMap().get(hashMapKey);
			Model.getHashMap().remove(hashMapKey);
		}
		else if (_listOfRequests == null) {
			// Async Request..
		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setListAdapter(new RequestFragmentAdapter(_listOfRequests));
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		Sleep.sleep(1);
		long hashMapKey = System.currentTimeMillis();
		Model.getHashMap().put(hashMapKey, _listOfRequests);
		outState.putLong(KEY_REQUESTS, hashMapKey);
	}

	@Override
	public String getTitle() {
		return "Requests";
	}

	class RequestFragmentAdapter extends ArrayAdapter<ArrayList<Request>> {
		RequestFragmentAdapter(List<ArrayList<Request>> list) {
			super(getActivity(), 0, list);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView==null)
				convertView = getActivity().getLayoutInflater().inflate(R.layout.item_discussion, parent, false);

			// Identify the interlocutor
			Member sender = getItem(position).get(0).getAuthor();

			// Fill convertView
			TextView nameView = (TextView) convertView.findViewById(R.id.name_item_discussion);
			nameView.setText(sender.getFirstName() + " " + sender.getLastName());

			ImageView pictureView = (ImageView) convertView.findViewById(R.id.picture_item_discussion);
			Bitmap picture;
			if (sender.getPicture() != null)
				picture = sender.getPicture();
			else
				picture = BitmapFactory.decodeResource(getResources(), R.drawable.default_profile_pic);
			picture = ImageHelper.getRoundedCornerBitmap(picture, 100);
			pictureView.setImageBitmap(picture);

			return convertView;
		}
	}
}
