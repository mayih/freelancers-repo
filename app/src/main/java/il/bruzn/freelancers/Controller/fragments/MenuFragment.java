package il.bruzn.freelancers.Controller.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import il.bruzn.freelancers.R;

/**
 * Created by Yair on 30/11/2014.
 * The activity which use this fragment HAS to implement the menuCommunicate interface /!\
 */
public class MenuFragment extends ListFragment {

	private iMenulistener _activity;
	public enum ItemMenu {
		HOME("Home", R.drawable.home_icon),
		PROFILE("Profile", R.drawable.profile_icon),
		INBOX("Inbox", R.drawable.inbox_icon),
		DISCONNECT("Disconnect",R.drawable.disconnect_icon);

		String _text;
		int _icon;
		Fragment frag;
		ItemMenu(String text, int icon){
			_text = text;
			_icon = icon;
		}
		public String toString(){
			return _text;
		}
		public int getIcon(){
			return _icon;
		}
		public Fragment getFragment(){
			if (frag == null) {
				if (this == HOME)
					frag = new HomeFragment();
				else if (this == PROFILE)
					frag = new ProfileFragment();
				else if (this == INBOX)
					frag = new InboxPagerFragment();
			}

			return frag;
		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		_activity = (iMenulistener)getActivity();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		getListView().setBackgroundColor(0xffffffff);
		setListAdapter(new MenuAdapter()); // Fills the menu
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		getListView().setItemChecked(position, true);    // Show the button has been clicked
		_activity.menuItemClicked(ItemMenu.values()[position]); // Change the main activity
	}


	private class MenuAdapter extends ArrayAdapter<ItemMenu> {
		public MenuAdapter() {
			super(getActivity(), R.layout.item_menu, ItemMenu.values());
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ItemMenu item = getItem(position);

			if (convertView==null)
				convertView = getActivity().getLayoutInflater().inflate(R.layout.item_menu, parent, false);
			((ImageView)convertView.findViewById(R.id.menu_item_icon)).setImageResource(item.getIcon());
			((TextView)convertView.findViewById(R.id.menu_item_text)).setText(item.toString());

			return convertView;
		}
	} // The listView adapter which will fill the view

	// Other classes ---

	//The activity which use this menu fragment has to implement this inerface
	public interface iMenulistener {
		public void menuItemClicked(ItemMenu item);
	}
}
