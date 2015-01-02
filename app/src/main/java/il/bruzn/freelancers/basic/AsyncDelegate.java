package il.bruzn.freelancers.basic;

import android.os.AsyncTask;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by Yair on 29/12/2014.
 */
public class AsyncDelegate extends AsyncTask<Delegate<Delegate>, Void, Delegate> {

	@Override
	protected Delegate doInBackground(Delegate<Delegate>... params) {
		Delegate postExecute = null;
		try {
			postExecute = params[0].execute();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return postExecute;
	}

	@Override
	protected void onPostExecute(Delegate delegate) {
		super.onPostExecute(delegate);

		try {
			delegate.execute();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}
