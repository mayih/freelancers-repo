package il.bruzn.freelancers.basic;

import android.os.AsyncTask;

/**
 * Created by Yair on 31/12/2014.
 */
public class AsyncToRun<typeToReturn> extends AsyncTask<Object, Void, typeToReturn> {
	private ToRun<typeToReturn> _main;
	private ToRun _post;

	@Override
	protected typeToReturn doInBackground(Object... params) {
		return _main.run(params);
	}

	@Override
	protected void onPostExecute(typeToReturn returnedValue) {
		super.onPostExecute(returnedValue);
		if(_post != null)
		_post.run(returnedValue);
	}

	// Setters  ----
	public AsyncToRun<typeToReturn> setMain(ToRun<typeToReturn> main) {
		_main = main;
		return this;
	}
	public AsyncToRun<typeToReturn> setPost(ToRun post) {
		_post = post;
		return this;
	}
}
