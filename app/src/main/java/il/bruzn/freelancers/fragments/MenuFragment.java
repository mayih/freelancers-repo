package il.bruzn.freelancers.fragments;

import android.app.Activity;
import android.app.Fragment;
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

import il.bruzn.freelancers.R;
import il.bruzn.freelancers.basic.ItemMenu;

/**
 * Created by Yair on 30/11/2014.
 * The activity which use this fragment HAS to implement the menuCommunicate interface /!\
 */
public class MenuFragment extends Fragment {

	private iMenulistener _activity;
	private ListView _listView;
	static ItemMenu[] _menu = {	new ItemMenu(R.drawable.home_icon,		"Home",		new HomeFragment()),
								new ItemMenu(R.drawable.profile_icon,	"Profile",	new ProfileFragment()),
								new ItemMenu(R.drawable.inbox_icon,		"Inbox",	new InboxFragment()),
								new ItemMenu(R.drawable.disconnect_icon,"Disconnect"), };

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		_activity = (iMenulistener)activity;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		_listView = (ListView) inflater.inflate(R.layout.fragment_menu, container, false);

		_listView.setAdapter(new MenuAdapter()); // Fills the menu

		_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			_listView.setItemChecked(position, true);	// Show the button has been clicked
			_activity.menuItemClicked(position); // Change the main activity
			}
		});

		return _listView;
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

	public interface iMenulistener {
		public void menuItemClicked(int item);
	}
}
