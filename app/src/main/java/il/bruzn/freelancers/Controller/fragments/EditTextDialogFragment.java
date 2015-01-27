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
import android.widget.RatingBar;

import il.bruzn.freelancers.R;

/**
 * Created by Moshe on 08/01/15.
 */
public class EditTextDialogFragment extends DialogFragment {
	public static final String EXTRA_TITLE = "Title";
	public static final String EXTRA_MESSAGE = "messageSent";
	public static final String EXTRA_LEVEL = "level";

	private String _message;
	private EditText _messageEditText;
	private RatingBar _ratingBar;

	public static EditTextDialogFragment newInstance(String title){
		Bundle args = new Bundle();
		args.putSerializable(EXTRA_TITLE, title);

		EditTextDialogFragment fragment = new EditTextDialogFragment();
		fragment.setArguments(args);

		return fragment;
	}
	private void sendResult(int resultCode)
	{
		if (getTargetFragment() == null)
		return;
		Intent i = new Intent();
		i.putExtra(EXTRA_MESSAGE, _message);

		if (getTargetFragment().getClass() == RequestSentFragment.class){
			double level = _ratingBar.getRating();
			i.putExtra(EXTRA_LEVEL, level);
		}

		getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);
	}

	@NonNull
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		String title = (String)getArguments().getSerializable(EXTRA_TITLE);
		View v = null;

		if (getTargetFragment().getClass() == ProfileFragment.class) {
			v = getActivity().getLayoutInflater().inflate(R.layout.dialog_request, null);
			_messageEditText = (EditText)v.findViewById(R.id.dialog_request_editText);
		}else if (getTargetFragment().getClass() == RequestSentFragment.class){
			v = getActivity().getLayoutInflater().inflate(R.layout.dialog_opinion, null);
			_messageEditText = (EditText)v.findViewById(R.id.dialog_opinion_editText);
			_ratingBar = (RatingBar)v.findViewById(R.id.dialog_opinion_ratingbar);
			_ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
				@Override
				public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
					if (rating == 0){
						rating = 0.5f;
						ratingBar.setRating(0.5f);
					}
				}
			});
		}

		return new AlertDialog.Builder(getActivity())
				.setView(v)
				.setTitle(title)
				.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						_message = _messageEditText.getText().toString();
						if (!_message.isEmpty() || getTargetFragment().getClass() == RequestSentFragment.class ){
							sendResult(Activity.RESULT_OK);
						}else {
							sendResult(Activity.RESULT_CANCELED);
						}
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
