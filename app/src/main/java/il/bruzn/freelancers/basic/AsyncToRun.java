package il.bruzn.freelancers.basic;

import android.os.AsyncTask;

/**
 * Created by Yair on 31/12/2014.
 */
public class AsyncToRun extends AsyncTask<ToRun<ToRun>, Void, ToRun> {
	@Override
	protected ToRun doInBackground(ToRun<ToRun>... params) {
		return params[0].run();
	}

	@Override
	protected void onPostExecute(ToRun toRun) {
		super.onPostExecute(toRun);
		toRun.run();
	}
}
