package tr.edu.khas.tatliavcisi;

import org.json.JSONObject;
import org.json.JSONTokener;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;
import com.facebook.android.SessionStore;
import com.facebook.android.Facebook.DialogListener;

public class TestConnect extends Activity {
	
	//Variables - Deðiþkenler
	private Facebook mFacebook;
	private CheckBox mFacebookBtn;
	private ProgressDialog mProgress;
	
	String dessert_name;
	String account_ids;
	
	// Informations which are needed
	private static final String[] PERMISSIONS = new String[] {"publish_stream", "read_stream", "offline_access"};
	
	private static final String APP_ID = "135619813238760";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.check);
        
        //Getting the dessert name and account id from other layouts
        Bundle extra = getIntent().getExtras();
        dessert_name = extra.getString("dessert_name");
	    account_ids = extra.getString("account_id");
		
        
        mFacebookBtn	= (CheckBox) findViewById(R.id.cb_facebook);
        
        mProgress		= new ProgressDialog(this);
        mFacebook		= new Facebook(APP_ID);
        
        SessionStore.restore(mFacebook, this);
        
        // Session is okey or not 
        if (mFacebook.isSessionValid()) {
			mFacebookBtn.setChecked(true);
			
			String name = SessionStore.getName(this);
			name		= (name.equals("")) ? "Bilinmeyen" : name;
			
			mFacebookBtn.setText("  Facebook (" + name + ")");

			mFacebookBtn.setTextColor(Color.WHITE);
		}
        
        mFacebookBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onFacebookClick();
			}
		});
        
        ((Button) findViewById(R.id.button1)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				// Go to Test Post Page
				Intent intent = new Intent(getApplicationContext(),TestPost.class);
				Bundle bundle = new Bundle();
				bundle.putString("account_id",account_ids);
				bundle.putString("dessert_name",dessert_name);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
    }
    
    private void onFacebookClick() {
		if (mFacebook.isSessionValid()) {
			
			// fb log out
			final AlertDialog.Builder builder = new AlertDialog.Builder(this);
			
			builder.setMessage("Þu anki Facebook baðlantýsýný sonlandýrmak istiyor musunuz?")
			       .setCancelable(false)
			       .setPositiveButton("Evet", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			        	   fbLogout();
			           }
			       })
			       .setNegativeButton("Hayýr", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			                dialog.cancel();
			                
			                mFacebookBtn.setChecked(true);
			           }
			       });
			
			final AlertDialog alert = builder.create();
			
			alert.show();
		} else {
			mFacebookBtn.setChecked(false);
			
			mFacebook.authorize(this, PERMISSIONS, -1, new FbLoginDialogListener());
		}
	}
    
    // Facebook Login Status
    private final class FbLoginDialogListener implements DialogListener {
        public void onComplete(Bundle values) {
            SessionStore.save(mFacebook, TestConnect.this);
           
            mFacebookBtn.setText("  (No Name)");
            mFacebookBtn.setChecked(true);
			mFacebookBtn.setTextColor(Color.WHITE);
			 
            getFbName();
        }

        public void onFacebookError(FacebookError error) {
           Toast.makeText(TestConnect.this, "Facebook Baðlantý Hatasý", Toast.LENGTH_SHORT).show();
           
           mFacebookBtn.setChecked(false);
        }
        
        public void onError(DialogError error) {
        	Toast.makeText(TestConnect.this, "Facebook Baðlantý Hatasý", Toast.LENGTH_SHORT).show(); 
        	
        	mFacebookBtn.setChecked(false);
        }

        public void onCancel() {
        	mFacebookBtn.setChecked(false);
        }
    }
    
	private void getFbName() {
		mProgress.setMessage("Sonlandýrýlýyor...");
		mProgress.show();
		
		new Thread() {
			@Override
			public void run() {
		        String name = "";
		        int what = 1;
		        
		        try {
		        	String me = mFacebook.request("me");
		        	
		        	JSONObject jsonObj = (JSONObject) new JSONTokener(me).nextValue();
		        	name = jsonObj.getString("name");
		        	what = 0;
		        } catch (Exception ex) {
		        	ex.printStackTrace();
		        }
		        
		        mFbHandler.sendMessage(mFbHandler.obtainMessage(what, name));
			}
		}.start();
	}
	
	//Facebook Log Out Method
	private void fbLogout() {
		mProgress.setMessage("Facebook'tan çýkýlýyor...");
		mProgress.show();
			
		new Thread() {
			@Override
			public void run() {
				SessionStore.clear(TestConnect.this);
		        	   
				int what = 1;
					
		        try {
		        	mFacebook.logout(TestConnect.this);
		        		 
		        	what = 0;
		        } catch (Exception ex) {
		        	ex.printStackTrace();
		        }
		        	
		        mHandler.sendMessage(mHandler.obtainMessage(what));
			}
		}.start();
	}
	
	private Handler mFbHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			mProgress.dismiss();
			
			if (msg.what == 0) {
				String username = (String) msg.obj;
		        username = (username.equals("")) ? "Ýsim yok:" : username;
		            
		        SessionStore.saveName(username, TestConnect.this);
		        
		        mFacebookBtn.setText("  " + username + "");
		        
		      
		        Toast.makeText(TestConnect.this, "Facebook'a þu kiþi olarak baðlanýldý:" + username, Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(TestConnect.this, "Facebook'a baðlanýldý.", Toast.LENGTH_SHORT).show();
			}
		}
	};
	
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			mProgress.dismiss();
			
			if (msg.what == 1) {
				Toast.makeText(TestConnect.this, "Facebook Çýkýþ Hatasý", Toast.LENGTH_SHORT).show();
			} else {
				mFacebookBtn.setChecked(false);
	        	mFacebookBtn.setText("  Facebook (Baðlý Deðil)");
	        	mFacebookBtn.setTextColor(Color.GRAY);
	        	   
				Toast.makeText(TestConnect.this, "Facebook'tan çýkýldý.", Toast.LENGTH_SHORT).show();
			}
		}
	};
}