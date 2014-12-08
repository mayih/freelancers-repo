package il.bruzn.freelancers.Controller.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.ListFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import il.bruzn.freelancers.Controller.MainActivity;
import il.bruzn.freelancers.R;

/**
 * Created by Yair on 30/11/2014.
 * The activity which use this fragment HAS to implement the menuCommunicate interface /!\
 */
public class MenuFragment extends ListFragment {

	private iMenulistener _activity;
	static ItemMenu[] _menu = {	new ItemMenu(R.drawable.home_icon,		"Home",		new HomeFragment()),
								new ItemMenu(R.drawable.profile_icon,	"Profile",	new ProfileFragment()),
								new ItemMenu(R.drawable.inbox_icon,		"Inbox",	new InboxFragment()),
								new ItemMenu(R.drawable.disconnect_icon,"Disconnect"), };

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		iMenulistener a = new MainActivity();
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		ListView listView = (ListView) inflater.inflate(R.layout.fragment_menu, container, false);
		return listView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		setListAdapter(new MenuAdapter()); // Fills the menu

		getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				getListView().setItemChecked(position, true);    // Show the button has been clicked
				((iMenulistener)getActivity()).menuItemClicked(position); // Change the main activity
			}
		});
	}

	public static ItemMenu[] getMenu(){
		return _menu;
	}

	private class MenuAdapter extends ArrayAdapter<ItemMenu> {
		public MenuAdapter() {
			super(getActivity().getApplicationContext(), R.layout.item_menu, _menu);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ItemMenu item = _menu[position];

			View itemView = getActivity().getLayoutInflater().inflate(R.layout.item_menu, parent, false);
			((ImageView)itemView.findViewById(R.id.menu_item_icon)).setImageResource(item.getImage());
			((TextView)itemView.findViewById(R.id.menu_item_text)).setText(item.getText());

			return itemView;
		}
	} // The listView adapter which will fill the view

	// Other classes ---
	public static class ItemMenu {
		private int _image;
		private String _text;
		private Fragment _fragment = null;

		public ItemMenu(int image, String text){
			setImage(image);
			setText(text);
		}
		public ItemMenu(int image, String text, Fragment fragment){
			setImage(image);
			setText(text);
			setFragment(fragment);
		}

		public int getImage() {
			return _image;
		}

		public void setImage(int image) {
			this._image = image;
		}

		public String getText() {
			return _text;
		}

		public void setText(String text) {
			this._text = text;
		}

		public Fragment getFragment() {
			return _fragment;
		}

		public void setFragment(Fragment fragment) {
			_fragment = fragment;
		}
	} // An item in the menu list
	public interface iMenulistener {
		public void menuItemClicked(int item);
	} //The activity which use this menu fragment has to implement this inerface
}
