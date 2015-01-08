package il.bruzn.freelancers.Controller.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.EditText;

import il.bruzn.freelancers.R;

/**
 * Created by Moshe on 08/01/15.
 */
public class RequestEditTextFragment extends DialogFragment {
	public static final String EXTRA_IS_REQUEST_IN_PROGRESS = "il.bruzn.freelancers.Controller.fragments.request";
	private boolean _isRequestSend;
	private String _message;
	private EditText _messageEditText;

	private void sendResult(int resultCode)
	{
		if (getTargetFragment() == null)
		return;
		Intent i = new Intent().putExtra(EXTRA_IS_REQUEST_IN_PROGRESS, _isRequestSend);
		getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);
	}

	@NonNull
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_request, null);
		_messageEditText = (EditText)v.findViewById(R.id.dialog_request_editText);


		return new AlertDialog.Builder(getActivity())
				.setView(v)
				.setTitle("Request")
				.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						_message = _messageEditText.getText().toString();

						if (!_message.isEmpty()) {
							_isRequestSend = true;
						} else {
							_isRequestSend = false;
						}
						sendResult(Activity.RESULT_OK);
					}
				})
				.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						_isRequestSend = false;
						sendResult(Activity.RESULT_OK);
					}
				})
				.create();
	}
}
