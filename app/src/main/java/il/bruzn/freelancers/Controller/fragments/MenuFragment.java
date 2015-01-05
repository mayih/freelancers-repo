package il.bruzn.freelancers.Controller.fragments;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.os.Bundle;
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
	static ItemMenu[] _menu = {	new ItemMenu(R.drawable.home_icon,		"Home",		new HomeFragment()),
								new ItemMenu(R.drawable.profile_icon,	"Profile",	new ProfileFragment()),
								new ItemMenu(R.drawable.inbox_icon,		"Inbox",	new InboxPagerFragment()),
								new ItemMenu(R.drawable.disconnect_icon,"Disconnect",	null),
	};
	public static ItemMenu[] getMenu(){
		return _menu;
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
		_activity.menuItemClicked(_menu[position]); // Change the main activity
	}


	private class MenuAdapter extends ArrayAdapter<ItemMenu> {
		public MenuAdapter() {
			super(getActivity(), R.layout.item_menu, _menu);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ItemMenu item = getItem(position);

			if (convertView==null)
				convertView = getActivity().getLayoutInflater().inflate(R.layout.item_menu, parent, false);
			((ImageView)convertView.findViewById(R.id.menu_item_icon)).setImageResource(item.getImage());
			((TextView)convertView.findViewById(R.id.menu_item_text)).setText(item.getText());

			return convertView;
		}
	} // The listView adapter which will fill the view

	// Other classes ---
	public static class ItemMenu {
		private int _image;
		private String _text;
		private Fragment _fragment;

		public ItemMenu(int image, String text, Fragment fragment){
			setImage(image);
			setText(text);
			setFragment(fragment);
		}

		public int getImage() {
			return _image;
		}
		public String getText() {
			return _text;
		}
		public Fragment getFragment() {
			return _fragment;
		}

		public ItemMenu setImage(int image) {
			this._image = image;
			return this;
		}
		public ItemMenu setText(String text) {
			_text = text;
			return this;
		}
		public ItemMenu setFragment(Fragment fragment) {
			_fragment = fragment;
			return this;
		}
	} // An item in the menu list
	public interface iMenulistener {
		public void menuItemClicked(ItemMenu item);
	} //The activity which use this menu fragment has to implement this inerface
}
