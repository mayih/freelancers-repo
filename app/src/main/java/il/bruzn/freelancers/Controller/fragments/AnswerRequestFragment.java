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
import android.widget.TextView;

import il.bruzn.freelancers.R;

/**
 * Created by Moshe on 22/01/15.
 */
public class AnswerRequestFragment extends DialogFragment {
	public static final String EXTRA_MESSAGE = "messageReceive";
	public static final int RESULT_ACCEPT = 44;
	public static final int RESULT_REFUSE = 45;

	private TextView _requestTextView;
	private String _message;

	public static AnswerRequestFragment newInstance(String message){
		Bundle args = new Bundle();
		args.putSerializable(EXTRA_MESSAGE, message);

		AnswerRequestFragment fragment = new AnswerRequestFragment();
		fragment.setArguments(args);

		return fragment;
	}

	private void sendResult(int resultCode)
	{
		if (getTargetFragment() == null)
			return;
		Intent i = new Intent();
		i.putExtra("view selected", getArguments().getLong("view selected"));
		getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);
	}

	@NonNull
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {


		_message = (String)getArguments().getSerializable(EXTRA_MESSAGE);

		View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_answer_request, null);
		_requestTextView = (TextView) v.findViewById(R.id.dialog_answer_request_textView);
		_requestTextView.setText(_message);

		return new AlertDialog.Builder(getActivity())
				.setView(v)
				.setTitle("Request")
				.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						sendResult(RESULT_ACCEPT);
					}
				})
				.setNeutralButton("Refuse", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						sendResult(RESULT_REFUSE);
					}
				})
				.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						sendResult(Activity.RESULT_CANCELED);
					}
				})
				.create();
	}
}