package tr.edu.khas.tatliavcisi;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.BaseRequestListener;
import com.facebook.android.Facebook;
import com.facebook.android.SessionStore;
import com.twitter.android.OAUTH;

public class TestPost extends Activity{
	
	//Variables -- Deðiþkenler
	private Facebook mFacebook;
	private CheckBox mFacebookCb;
	private ProgressDialog mProgress;
	String dessert_name;
	Long skor;
	String account_id;
	
	private Handler mRunOnUi = new Handler();
	
	// The Informations which are needed
	private static final String APP_ID = "135619813238760";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.post);
		
		// Getting dessert name and account id from test connect activity
		 Bundle extra = getIntent().getExtras();
	     dessert_name = extra.getString("dessert_name");
	     account_id = extra.getString("account_id");
		
		
		final EditText reviewEdit = (EditText) findViewById(R.id.revieew);
		mFacebookCb				  = (CheckBox) findViewById(R.id.cb_facebook);
		
		mProgress	= new ProgressDialog(this);
		
		mFacebook 	= new Facebook(APP_ID);
		
		SessionStore.restore(mFacebook, this);

		if (mFacebook.isSessionValid()) {
			mFacebookCb.setChecked(true);
				
			String name = SessionStore.getName(this);
			name		= (name.equals("")) ? "Bilinmeyen" : name;
				
			mFacebookCb.setText(" " + name + " ");
		}
		
		((Button) findViewById(R.id.button1)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String review = reviewEdit.getText().toString();
				
				if (review.equals("")) return;
			
				if (mFacebookCb.isChecked()) postToFacebook(review);
			}
		});
	}
	
	// Post to Facebook
	private void postToFacebook(String review) {	
		mProgress.setMessage("Posting ...");
		mProgress.show();
		
		AsyncFacebookRunner mAsyncFbRunner = new AsyncFacebookRunner(mFacebook);
		
		DatabaseHelper myDbHelper = new DatabaseHelper(this);
		if(dessert_name.compareTo("Kemalpasa") == 0)
		{
			skor = myDbHelper.GetKemalpasaScore(Integer.parseInt(account_id));
		}
		
		else if(dessert_name.compareTo("Kazandibi") == 0)
		{
			skor = myDbHelper.GetKazandibiScore(Integer.parseInt(account_id));
		}
		
		else if(dessert_name.compareTo("Pismaniye") == 0)
		{
			skor = myDbHelper.GetPismaniyeScore(Integer.parseInt(account_id));
		}
		
		else if(dessert_name.compareTo("Muhallebi") == 0)
		{
			skor = myDbHelper.GetMuhallebiScore(Integer.parseInt(account_id));
		}
		
		Long total_score = myDbHelper.GetTotalScore(Integer.parseInt(account_id));
		
		Bundle params = new Bundle();
    		
		// The informations which will be sended / posted
		params.putString("message", review);
		params.putString("name", "Tatli Avcisi");
		params.putString("description", "Tatli Avcýsýnda yeni bir skor elde ettim: " + dessert_name +" skorum " + skor + " oldu ve toplamda " + total_score +" puan elde ettim.Ya sen ne durumdasin?");
		params.putString("picture", "http://farm8.staticflickr.com/7104/7152745745_a096cd9a4f_m.jpg");
		
		mAsyncFbRunner.request("me/feed", params, "POST", new WallPostListener());
		myDbHelper.close();
		
	}

	private final class WallPostListener extends BaseRequestListener {
        public void onComplete(final String response) {
        	mRunOnUi.post(new Runnable() {
        		@Override
        		public void run() {
        			mProgress.cancel();
        			
        			Toast.makeText(TestPost.this, "Facebook'ta paylaþýldý!", Toast.LENGTH_SHORT).show();
        			
        			if(dessert_name.compareTo("Kemalpasa")==0)
        			{
        				// Back to Kemalpasa Activity
        				Intent intent = new Intent(getApplicationContext(), Kemalpasa.class);
        				Bundle bundle = new Bundle();
        				bundle.putString("account_id",account_id);
        				bundle.putString("dessert_name",dessert_name);
        				intent.putExtras(bundle);
        				startActivity(intent);
        				finish();
        			}
        			
        			else if(dessert_name.compareTo("Kazandibi")==0)
        			{
        				// Back to Kazandibi Activity
        				Intent intent = new Intent(getApplicationContext(), Kazandibi.class);
        				Bundle bundle = new Bundle();
        				bundle.putString("account_id",account_id);
        				bundle.putString("dessert_name",dessert_name);
        				intent.putExtras(bundle);
        				startActivity(intent);
        				finish();
        			}
        			
        			else if(dessert_name.compareTo("Pismaniye")==0)
        			{
        				// Back to Pismaniye Activity
        				Intent intent = new Intent(getApplicationContext(), Pismaniye.class);
        				Bundle bundle = new Bundle();
        				bundle.putString("account_id",account_id);
        				bundle.putString("dessert_name",dessert_name);
        				intent.putExtras(bundle);
        				startActivity(intent);
        				finish();
        			}
        			
        			else if(dessert_name.compareTo("Muhallebi")==0)
        			{
        				// Back to Muhallebi Activity
        				Intent intent = new Intent(getApplicationContext(), Muhallebi.class);
        				Bundle bundle = new Bundle();
        				bundle.putString("account_id",account_id);
        				bundle.putString("dessert_name",dessert_name);
        				intent.putExtras(bundle);
        				startActivity(intent);
        				finish();
        			}
        		}
        	});
        }
    }
}