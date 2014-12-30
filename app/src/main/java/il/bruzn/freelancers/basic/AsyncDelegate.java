package il.bruzn.freelancers.basic;

import android.os.AsyncTask;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by Yair on 29/12/2014.
 */
public class AsyncDelegate extends AsyncTask<Delegate, Void, Void> {

	@Override
	protected Void doInBackground(Delegate... params) {
		try {
			params[0].execute();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
}
