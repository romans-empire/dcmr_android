package nl.oda.rotterdamradar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MailActivity extends Activity implements OnClickListener,
		OnItemSelectedListener {
	/** Called when the activity is first created. */
	EditText naamField, mailField, mobielField, berichtField;
	Spinner subject;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contact);

		naamField = (EditText) findViewById(R.id.editTekstNaam);
		mailField = (EditText) findViewById(R.id.editTextMail);
		mobielField = (EditText) findViewById(R.id.editTextMobiel);
		berichtField = (EditText) findViewById(R.id.editTextBericht);

		subject = (Spinner) findViewById(R.id.spinner);
		String subjects[] = new String[] { "Klacht", "Vraag", "Opmerking" };
		subject.setOnItemSelectedListener(this);
		ArrayAdapter<String> sa = new ArrayAdapter<String>(this,
				R.layout.spinner_item, subjects);
		sa.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		subject.setAdapter(sa);
		;

		final Button buttonSend = (Button) findViewById(R.id.buttonSend);
		buttonSend.setOnClickListener(this);
	}

	public void onClick(View v) {
		if (naamField.getText().toString().length() == 0) {
			naamField.setError("Vul uw naam in");
		} else if (mailField.getText().toString().length() == 0) {
			mailField.setError("Vul uw email in");
		} else if (mobielField.getText().toString().length() != 10) {
			mobielField.setError("Vul een geldig telefoonnummer in");
		} else if (berichtField.getText().toString().length() == 0) {
			berichtField.setError("Vul uw bericht in");
		} else if (subject.getSelectedItemPosition() == 0) {
			Toast.makeText(MailActivity.this, "Please select the Subject",
					Toast.LENGTH_SHORT).show();
		} else {
			String body = "Name : " + naamField.getText().toString()
					+ "<br>Mobile :" + mobielField.getText().toString()
					+ "<br>Email :" + mailField.getText().toString()
					+ "<br>Bericht :" + berichtField.getText().toString();

			Intent email = new Intent(Intent.ACTION_SEND);
			email.putExtra(Intent.EXTRA_EMAIL,
					new String[] { "richard-lindhout@hotmail.com" });
			email.putExtra(Intent.EXTRA_SUBJECT, subject.getSelectedItem()
					.toString());
			email.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(body));
			email.setType("message/rfc822");
			startActivityForResult(Intent.createChooser(email, "Richard"), 1);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		new AlertDialog.Builder(MailActivity.this)
				.setMessage("Your requested has been Accepted\nThank You")
				.setCancelable(false)
				.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				}).show();
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}
}