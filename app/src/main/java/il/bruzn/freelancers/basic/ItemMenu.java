package il.bruzn.freelancers.basic;

import android.app.Fragment;

/**
 * Created by Yair on 30/11/2014.
 */
public class ItemMenu {
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
}
