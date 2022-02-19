package tr.edu.khas.tatliavcisi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.media.MediaPlayer;

public class Kazandibi extends Activity implements SensorListener,Runnable {

	//Variables -- Değişkenler
	private SensorManager sensorMgr;
	private long lastUpdate = -1;
	private float x, y, z;
	private float last_x, last_y, last_z;
	private static final int SHAKE_THRESHOLD = 800;
	Button b1, b2, b3, b4, b5, b6, b7, b8, b9, sil, hint,cevap,leave,facebook,twitter;
	TextView kelime;
	AnimationDrawable a;
	LinearLayout ll ;
	String account_ids ;
	LinearLayout slidingText;
	Animation mAnimation;
	int id=0;
	Button b=null;
	Button c=null;
	Cursor cr ;
	Button addAsSt[];
	Gallery gallery;
	Handler handler;
	private int PicPosition;
	String [] Text;
	Boolean leave_status = false ;
	ImageView imgView;
	int i=0;
	MediaPlayer correct;
	MediaPlayer wrong;
	int k = 0;
	String[] ar ;
	String s;
	ArrayList al = new ArrayList(); 
	ArrayList<String> word_al = new ArrayList();
	Boolean word_al_sta = false;
	int j;
	TextView t;
	Boolean new_word = true;
	Boolean b1_sta = true;
	Boolean b2_sta = true;
	Boolean b3_sta = true;
	Boolean b4_sta = true;
	Boolean b5_sta = true;
	Boolean b6_sta = true;
	Boolean b7_sta = true;
	Boolean b8_sta = true;
	Boolean b9_sta = true;
	Boolean check = false;
	final Context context = this;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.kazandibi);
		
		
		//Starting the animation
		runAnimate();
		
		// Cevap Button is false,firstly
		cevap = (Button)findViewById(R.id.bcevap);
		cevap.setEnabled(false);
		
		//Wrong and correct sounds
		 correct = MediaPlayer.create(this,R.raw.correct);
		 wrong = MediaPlayer.create(this,R.raw.wrong);
		 
		
		//getting account_id from other activity
		Bundle extra = getIntent().getExtras();
		account_ids = extra.getString("account_id");
		final int account_id = Integer.parseInt(account_ids);
		
		//Gallery - Sliding Texts
	    gallery=(Gallery)findViewById(R.id.gallery);
	    
	    final DatabaseHelper myDbHelper = new DatabaseHelper(this);
        id = (int)myDbHelper.GetCountFindKazandibiWords(account_id);
        
        Text= new String[id+5];
        int j=0;
        
        cr = myDbHelper.GetFindedWordsKazandibi(account_id);
        while(cr.moveToNext()){	
        	
        	Text[j]="\t\t"+(cr.getString(cr.getColumnIndex("wordname")))+"\t\t";
        	j++;	
        }
        
        for(int i= Text.length-1 ; i >= Text.length - 5 ; i--)
        {
        	Text[i]="\t\t\t\t\t";	
        }
        
        ArrayAdapter arr=new ArrayAdapter(this,android.R.layout.simple_gallery_item, Text);
        gallery.setAdapter(arr);
 
       
        handler= new Handler();
        run();
        
        cr.close();
        myDbHelper.close();
        
        // If the Click the gallery item
        gallery.setOnItemClickListener(new OnItemClickListener() {
  	      public void onItemClick(AdapterView<?> parent, View v,int position, long id) {
  	    	  
  	    	String word = (String) gallery.getItemAtPosition(position);
  	    	
  	       if(word.compareTo("\t\t\t\t\t") != 0 )
  	       {  
  	    	   
  	        	final DatabaseHelper myDbHelper = new DatabaseHelper(getApplicationContext());
  	        	String meaning = myDbHelper.GetWordMeaning(word,account_id);
  	    
  	        	new AlertDialog.Builder(Kazandibi.this)
				.setTitle("Kelimenin Anlamı:").setMessage(word.toUpperCase() + " : " + meaning)
				.setPositiveButton("Tamam",
				new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
						}
				}).show(); 
  	        	
  	        	cr.close();
  	        	myDbHelper.close();
  	      }
  	    	  
  	      }});
	    
        //Call Getting components
			implement();
			
		// Start Motion Detection
		sensorMgr = (SensorManager) getSystemService(SENSOR_SERVICE);
		boolean accelSupported = sensorMgr.registerListener(this,
				SensorManager.SENSOR_ACCELEROMETER,
				SensorManager.SENSOR_DELAY_GAME);
		if (!accelSupported) {
			// on accelerometer on this device
			sensorMgr.unregisterListener(this,
					SensorManager.SENSOR_ACCELEROMETER);
		}
	}
	
	
	//Getting components
	private void implement() {
		
		kelime = (TextView) findViewById(R.id.editText1);
		sil = (Button) findViewById(R.id.hintordelete);
		hint = (Button) findViewById(R.id.Hint);
		
		
		// Go to Facebook Page
		facebook = (Button)findViewById(R.id.facebook);
		facebook.setOnClickListener(new OnClickListener(){

			
			public void onClick(View arg0) {
				Intent intent = new Intent(getApplicationContext(),TestConnect.class);
				
				Bundle bundle = new Bundle();
				bundle.putString("account_id",account_ids);
				bundle.putString("dessert_name","Kazandibi");
				intent.putExtras(bundle);
				startActivity(intent);
				
			}});
		
		// Go to Twitter Page
		twitter = (Button)findViewById(R.id.twitter);
		twitter.setOnClickListener(new OnClickListener(){

			
			public void onClick(View arg0) {
				Intent intent = new Intent(getApplicationContext(),Twitter.class);
				Bundle bundle = new Bundle();
				bundle.putString("account_id",account_ids);
				bundle.putString("dessert_name","Kazandibi");
				intent.putExtras(bundle);
				startActivity(intent);
				finish();
				
			}});
			
			
		sil.setVisibility(View.INVISIBLE);	
		hint.setVisibility(View.VISIBLE);
		
		
		// B1 Button Processes
		b1 = (Button) findViewById(R.id.b1);
		b1.setOnClickListener(new OnClickListener() {

			
			public void onClick(View arg0) {
				
				// Eğer ilk olarak bu harfe basıldıysa
				if (kelime.length() == 0) {
					kelime.setText(b1.getText().toString());
				
					sil.setVisibility(View.VISIBLE);	
					hint.setVisibility(View.INVISIBLE);	
					b1.setEnabled(false);
				
				} 
				
				//Daha önce bu harfe basıldıysa
				else {
					kelime.setText(kelime.getText() +b1.getText().toString());
					b1.setEnabled(false);
				}
					
					if(kelime.length()<4 ){
						
						cevap.setEnabled(false); }

					
					// Maximum 9 harf yazılabilir
					else if(kelime.length()==9){
						b1.setEnabled(false);
						b2.setEnabled(false);
						b3.setEnabled(false);
						b4.setEnabled(false);
						b5.setEnabled(false);
						b6.setEnabled(false);
						b7.setEnabled(false);
						b8.setEnabled(false);
						b9.setEnabled(false);
					} 
					
					else
					{
					    cevap.setEnabled(true);
					}
					
					// Enable- Disable Check
					al.add(b1.getId());
					word_al.add(b1.getText().toString());
					b1_sta = false;
						
			}});

		// B2 Button Processes
		b2 = (Button) findViewById(R.id.b2);
		b2.setOnClickListener(new OnClickListener() {

			
			public void onClick(View arg0) {
				
				// Eğer ilk olarak bu harfe basıldıysa
				if (kelime.length() == 0) {
					kelime.setText(b2.getText().toString());
					
					sil.setVisibility(View.VISIBLE);	
					hint.setVisibility(View.INVISIBLE);	
					b2.setEnabled(false);
					
				} 
				
				//Daha önce bu harfe basıldıysa
				else {
					kelime.setText(kelime.getText() + b2.getText().toString());
					b2.setEnabled(false);
				}
					
					if(kelime.length()<4 ){
						
						cevap.setEnabled(false);

					}
					// Maximum 9 harf yazılabilir
					else if(kelime.length()==9){
						b1.setEnabled(false);
						b2.setEnabled(false);
						b3.setEnabled(false);
						b4.setEnabled(false);
						b5.setEnabled(false);
						b6.setEnabled(false);
						b7.setEnabled(false);
						b8.setEnabled(false);
						b9.setEnabled(false);
					} 
					
					else
					{
						cevap.setEnabled(true);
					}
					
					// Enable- Disable Check
					al.add(b2.getId());
					word_al.add(b2.getText().toString());
					b2_sta = false ;
				
			}});

		// B3 Button Processes
		b3 = (Button) findViewById(R.id.b3);
		b3.setOnClickListener(new OnClickListener() {

			
			public void onClick(View arg0) {
				
				// Eğer ilk olarak bu harfe basıldıysa
				if (kelime.length() == 0) {
					kelime.setText(b3.getText().toString());
					
					sil.setVisibility(View.VISIBLE);	
					hint.setVisibility(View.INVISIBLE);	
					
					b3.setEnabled(false);
				
				} 
				
				//Daha önce bu harfe basıldıysa
				else {
					kelime.setText(kelime.getText().toString() + b3.getText().toString());
					b3.setEnabled(false);
					
				}
					
					if(kelime.length()<4 ){
						
						cevap.setEnabled(false);

					}
					
					// Maximum 9 harf yazılabilir
					else if(kelime.length()==9){
						b1.setEnabled(false);
						b2.setEnabled(false);
						b3.setEnabled(false);
						b4.setEnabled(false);
						b5.setEnabled(false);
						b6.setEnabled(false);
						b7.setEnabled(false);
						b8.setEnabled(false);
						b9.setEnabled(false);
					} 
					
					else
					{
						cevap.setEnabled(true);
					}
				
					// Enable- Disable Check
					al.add(b3.getId());
					word_al.add(b3.getText().toString());
					b3_sta = false ;
					
			}});
		
		// B4 Button Processes
		b4 = (Button) findViewById(R.id.b4);
		b4.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				
				// Eğer ilk olarak bu harfe basıldıysa
				if (kelime.length() == 0) {
					kelime.setText(b4.getText().toString());
					
					sil.setVisibility(View.VISIBLE);	
					hint.setVisibility(View.INVISIBLE);	
					
					b4.setEnabled(false);
				}
				
				//Daha önce bu harfe basıldıysa
				 else {
					kelime.setText(kelime.getText().toString() + b4.getText().toString());
					b4.setEnabled(false);
				 }
					
					if(kelime.length()<4 ){
						
						cevap.setEnabled(false);

					}
					// Maximum 9 harf yazılabilir
					else if(kelime.length()==9){
						b1.setEnabled(false);
						b2.setEnabled(false);
						b3.setEnabled(false);
						b4.setEnabled(false);
						b5.setEnabled(false);
						b6.setEnabled(false);
						b7.setEnabled(false);
						b8.setEnabled(false);
						b9.setEnabled(false);
					} 
					
					else
					{
						cevap.setEnabled(true);
					}
					
					// Enable- Disable Check
					al.add(b4.getId());
					word_al.add(b4.getText().toString());
					b4_sta=false;
				
				
			}});

		// B5 Button Processes
		b5 = (Button) findViewById(R.id.b5);
		b5.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				
				// Eğer ilk olarak bu harfe basıldıysa
				if (kelime.length() == 0) {
					kelime.setText(b5.getText().toString());
				
					sil.setVisibility(View.VISIBLE);	
					hint.setVisibility(View.INVISIBLE);	
					
					b5.setEnabled(false);
				} 
				
				//Daha önce bu harfe basıldıysa
				else {
					kelime.setText(kelime.getText().toString() + b5.getText().toString());
					b5.setEnabled(false);
				}
					
					if(kelime.length()<4 ){
						
						cevap.setEnabled(false);

					}
					
					// Maximum 9 harf yazılabilir
					else if(kelime.length()==9){
						b1.setEnabled(false);
						b2.setEnabled(false);
						b3.setEnabled(false);
						b4.setEnabled(false);
						b5.setEnabled(false);
						b6.setEnabled(false);
						b7.setEnabled(false);
						b8.setEnabled(false);
						b9.setEnabled(false);
					} 
					
					else
					{
						cevap.setEnabled(true);
					}
					
					// Enable- Disable Check
					al.add(b5.getId());
					word_al.add(b5.getText().toString());
					b5_sta=false;
				
			}});

		// B6 Button Processes
		b6 = (Button) findViewById(R.id.b6);
		b6.setOnClickListener(new OnClickListener() {

			
			public void onClick(View arg0) {
				
				// Eğer ilk olarak bu harfe basıldıysa
				if (kelime.length() == 0) {
					kelime.setText(b6.getText().toString());
					
					sil.setVisibility(View.VISIBLE);	
					hint.setVisibility(View.INVISIBLE);	
					b6.setEnabled(false);
				} 
				
				//Daha önce bu harfe basıldıysa
				else {
					kelime.setText(kelime.getText().toString() + b6.getText().toString());
					b6.setEnabled(false);
				}
					
					if(kelime.length()<4 ){
						
						cevap.setEnabled(false);

					}
				
					// Maximum 9 harf yazılabilir
					else if(kelime.length()==9){
						b1.setEnabled(false);
						b2.setEnabled(false);
						b3.setEnabled(false);
						b4.setEnabled(false);
						b5.setEnabled(false);
						b6.setEnabled(false);
						b7.setEnabled(false);
						b8.setEnabled(false);
						b9.setEnabled(false);
					} 
					
					else
					{
						cevap.setEnabled(true);
					}
					
					// Enable- Disable Check
					al.add(b6.getId());
					word_al.add(b6.getText().toString());
					b6_sta = false ;
				
			}});

		// B7 Button Processes
		b7 = (Button) findViewById(R.id.b7);
		b7.setOnClickListener(new OnClickListener() {

			
			public void onClick(View arg0) {
				
				// Eğer ilk olarak bu harfe basıldıysa
				if (kelime.length() == 0) {
					kelime.setText(b7.getText().toString());
					
					sil.setVisibility(View.VISIBLE);	
					hint.setVisibility(View.INVISIBLE);	
					b7.setEnabled(false);
				} 
				
				//Daha önce bu harfe basıldıysa
				else {
					kelime.setText(kelime.getText().toString() + b7.getText().toString());
					b7.setEnabled(false);
				}
					
					if(kelime.length()<4 ){
						
						cevap.setEnabled(false);

					}
					
					// Maximum 9 harf yazılabilir
					else if(kelime.length()==9){
						b1.setEnabled(false);
						b2.setEnabled(false);
						b3.setEnabled(false);
						b4.setEnabled(false);
						b5.setEnabled(false);
						b6.setEnabled(false);
						b7.setEnabled(false);
						b8.setEnabled(false);
						b9.setEnabled(false);
					} 
					
					else
					{
						cevap.setEnabled(true);
					}
				
					// Enable- Disable Check
					al.add(b7.getId());
					word_al.add(b7.getText().toString());
					b7_sta = false ;
				
			}});
		
		// B8 Button Processes
		b8 = (Button) findViewById(R.id.b8);
		b8.setOnClickListener(new OnClickListener() {

			
			public void onClick(View arg0) {
				
				// Eğer ilk olarak bu harfe basıldıysa
				if (kelime.length() == 0) {
					kelime.setText(b8.getText().toString());
					
					sil.setVisibility(View.VISIBLE);	
					hint.setVisibility(View.INVISIBLE);	
					b8.setEnabled(false);
				} 
				
				//Daha önce bu harfe basıldıysa
				else {
					kelime.setText(kelime.getText().toString() + b8.getText().toString());
					b8.setEnabled(false);
				}
					
					if(kelime.length()<4 ){
						
						cevap.setEnabled(false);

					}
					
					// Maximum 9 harf yazılabilir
					else if(kelime.length()==9){
						b1.setEnabled(false);
						b2.setEnabled(false);
						b3.setEnabled(false);
						b4.setEnabled(false);
						b5.setEnabled(false);
						b6.setEnabled(false);
						b7.setEnabled(false);
						b8.setEnabled(false);
						b9.setEnabled(false);
					} 
					
					else
					{
						cevap.setEnabled(true);
					}
				
					// Enable- Disable Check
					al.add(b8.getId());
					word_al.add(b8.getText().toString());
					b8_sta = false ;
				
			}});

		// B9 Button Processes
		b9 = (Button) findViewById(R.id.b9);
		b9.setOnClickListener(new OnClickListener() {

			
			public void onClick(View arg0) {
				
				// Eğer ilk olarak bu harfe basıldıysa
				if (kelime.length() == 0) {
					kelime.setText(b9.getText().toString());
					
					sil.setVisibility(View.VISIBLE);	
					hint.setVisibility(View.INVISIBLE);	
					b9.setEnabled(false);
				} 
				
				//Daha önce bu harfe basıldıysa
				else {
					kelime.setText(kelime.getText().toString() + b9.getText().toString());
					b9.setEnabled(false);
				}
					
					if(kelime.length()<4 ){
						
						cevap.setEnabled(false);

					}
					
					// Maximum 9 harf yazılabilir
					else if(kelime.length()==9){
						b1.setEnabled(false);
						b2.setEnabled(false);
						b3.setEnabled(false);
						b4.setEnabled(false);
						b5.setEnabled(false);
						b6.setEnabled(false);
						b7.setEnabled(false);
						b8.setEnabled(false);
						b9.setEnabled(false);
					} 
					
					else
					{
						cevap.setEnabled(true);
					}
					
					// Enable- Disable Check
					al.add(b9.getId());
					word_al.add(b9.getText().toString());
					b9_sta = false ;
				
			}});

		
		// Sil Button Processes
		sil.setOnClickListener(new OnClickListener() {

			
			public void onClick(View arg0) {
				
				String kelimeler = kelime.getText().toString();
				String silinmiskelime = kelimeler;
			
				// Just delete last letter
				if( silinmiskelime.length() > 0 )
				{
					silinmiskelime = kelimeler.substring(0,kelimeler.length() - 1);
					kelime.setText(silinmiskelime);
				}
				
				// Check the length of the word
				if(kelime.length() < 9){
					cevap.setEnabled(true);
				}
				
				if(kelime.length()< 4 ){
					
					cevap.setEnabled(false);
				}
				
				if( silinmiskelime.length() == 0 )
				{
					hint.setVisibility(View.VISIBLE);	
					sil.setVisibility(View.INVISIBLE);	
				}
				
				
				// If the shake exists, the situation of the buttons - Enable Disable Issues
			if(word_al_sta.equals(false)){
				
				if(al.size() != 0)
				{
					if(((Object)b1.getId()).equals(al.get(al.size()-1)))
					{
					   b1.setEnabled(true);
					   b1_sta = true;
					   
					}	
					
					else if(((Object)b2.getId()).equals(al.get(al.size()-1)))
					{
					   b2.setEnabled(true);
					   b2_sta = true;
					   
					}	
					
					else if(((Object)b3.getId()).equals(al.get(al.size()-1)))
					{
					   b3.setEnabled(true);
					   b3_sta = true;
					  
					}	
					
					else if(((Object)b4.getId()).equals(al.get(al.size()-1)))
					{
					   b4.setEnabled(true);
					   b4_sta = true;
					  
					}	
					
					else if(((Object)b5.getId()).equals(al.get(al.size()-1)))
					{
					   b5.setEnabled(true);
					   b5_sta = true;
					  
					}	
					
					else if(((Object)b6.getId()).equals(al.get(al.size()-1)))
					{
						
					   b6.setEnabled(true);
					   b6_sta = true;
					  
					}	
					
					else if(((Object)b7.getId()).equals(al.get(al.size()-1)))
					{
					   b7.setEnabled(true);
					   b7_sta = true;
					  
					}	
					
					else if(((Object)b8.getId()).equals(al.get(al.size()-1)))
					{
					   b8.setEnabled(true);
					   b8_sta = true;
					  
					}	
					
					else if(((Object)b9.getId()).equals(al.get(al.size()-1)))
					{
					   b9.setEnabled(true);
					   b9_sta = true;
					  
					}	
					
					word_al.remove(word_al.size()-1);
					al.remove(al.size()-1);
					
				}
				}

				
			//If shake does not exist - enable disable Issues
			else
				{
					if(b1.getText().toString().compareTo(word_al.get(word_al.size()-1)) == 0 && (b1_sta.equals(false))){
					   b1.setEnabled(true);
					   b1_sta = true;
					}	
					
					else if(b2.getText().toString().compareTo(word_al.get(word_al.size()-1)) == 0 && (b2_sta.equals(false)))
					{
					   b2.setEnabled(true);
					   b2_sta = true;
					   
					}	
					
					else if(b3.getText().toString().compareTo(word_al.get(word_al.size()-1)) == 0 && (b3_sta.equals(false)))
					{
					   b3.setEnabled(true);
					   b3_sta = true;
					}	
					
					else if(b4.getText().toString().compareTo(word_al.get(word_al.size()-1)) == 0 && (b4_sta.equals(false)))
					{
					   b4.setEnabled(true);
					   b4_sta = true;
					  
					}	
					
					else if(b5.getText().toString().compareTo(word_al.get(word_al.size()-1)) == 0 && (b5_sta.equals(false)))
					{
					   b5.setEnabled(true);
					   b5_sta = true;
					}	
					
					else if(b6.getText().toString().compareTo(word_al.get(word_al.size()-1)) == 0 && (b6_sta.equals(false)))
					{
					   b6.setEnabled(true);
					   b6_sta = true;
					}	
					
					else if(b7.getText().toString().compareTo(word_al.get(word_al.size()-1)) == 0 && (b7_sta.equals(false)))
					{
					   b7.setEnabled(true);
					   b7_sta = true;
					}	
					
					else if(b8.getText().toString().compareTo(word_al.get(word_al.size()-1)) == 0 && (b8_sta.equals(false)))
					{
					   b8.setEnabled(true);
					   b8_sta = true;
					}	
					
					else if(b9.getText().toString().compareTo(word_al.get(word_al.size()-1)) == 0 && (b9_sta.equals(false)))
					{
					   b9.setEnabled(true);
					   b9_sta = true;
					}
					
					word_al.remove(word_al.size()-1);
					al.remove(al.size()-1);
					
				}
				
			}});
		
		
		//Hint Button Processes
		hint.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {

				// New word hint
				if(new_word )
				{
					//getting hint words
					 final int account_id = Integer.parseInt(account_ids);
					 DatabaseHelper hintHelper = new DatabaseHelper(context);
					 Cursor crr = hintHelper.GetNotFindedWords(account_id, 4);
					 int h=0;
					 
					 ar = new String[crr.getCount()];
					
				        while(crr.moveToNext()){	
				        	
				        	ar[h]=crr.getString(crr.getColumnIndex("wordname"));
				        	h++;	
				        }
				        
				    crr.close();
				    hintHelper.close();
					
					final Random r = new Random();
					
					t = (TextView) findViewById(R.id.editText1);
					k=0;

					j = r.nextInt(ar.length);	
					
					final Dialog dialog = new Dialog(getApplicationContext());
					dialog.setContentView(R.layout.hint_dialog);
					dialog.setTitle("Uyarı!");

					Button dialogButtonOK = (Button) dialog.findViewById(R.id.dialogButtonOK);
					Button dialogButtonBack = (Button) dialog.findViewById(R.id.dialogButtonBack);
					
					dialog.show();
					
					dialogButtonOK.setOnClickListener(new OnClickListener()	{

						@Override
						public void onClick(View arg0) {
							dialog.dismiss();
						}
						
					});
					
					dialogButtonBack.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {

							
							return;
						
						}

					});
					
				}
				
				// Button Enable - Disable Issues
					al.clear();
					word_al.clear();
					b1_sta = true;
					b2_sta = true;
					b3_sta = true;
					b4_sta = true;
					b5_sta = true;
					b6_sta = true;
					b7_sta = true;
					b8_sta = true;
					b9_sta = true;
					
					b1.setEnabled(true);
					b2.setEnabled(true);
					b3.setEnabled(true);
					b4.setEnabled(true);
					b5.setEnabled(true);
					b6.setEnabled(true);
					b7.setEnabled(true);
					b8.setEnabled(true);
					b9.setEnabled(true);
					
					k++;
					s = ar[j].toString();
					
					//getting the meaning of the word
					final DatabaseHelper myDbHelper = new DatabaseHelper(getApplicationContext());
					String meaning = myDbHelper.GetWordMeaning(s);
					
					// Scoring Update
					int account_id =Integer.parseInt(account_ids);
					int kazandibi_puan = (int) myDbHelper.GetKazandibiScore(account_id) - 5 ;
		 	    	int total_puan = (int) myDbHelper.GetTotalScore(account_id) - 5;
		    		myDbHelper.UpdateKazandibiScore(Integer.toString(kazandibi_puan), Integer.toString(account_id));
		         	myDbHelper.UpdateTotalScore(Integer.toString(total_puan), Integer.toString(account_id));
					
					myDbHelper.close();
					
					
					// Hint Steps
				 if (s.length() > 0) {
	
						
						if (k == s.length() - (s.length()-1) &&  k<=s.length()) {
							if(s.length() == 4){
								new AlertDialog.Builder(Kazandibi.this)
								.setTitle("İp Ucu:").setMessage(s.substring(0, s.length()- (s.length() - 1))+"??? : " + meaning)
								.setPositiveButton("Tamam",
								new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int which) {
										}
								}).show(); 
								
							}else if(s.length()==5){
								new AlertDialog.Builder(Kazandibi.this)
								.setTitle("İp ucu:").setMessage(s.substring(0, s.length()- (s.length() - 1))+"???? : " + meaning)
								.setPositiveButton("Tamam",
								new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int which) {
										}
								}).show(); 
	
							}else if(s.length()==6){
								new AlertDialog.Builder(Kazandibi.this)
								.setTitle("İp Ucu:").setMessage(s.substring(0, s.length()- (s.length() - 1))+"????? : "+ meaning)
								.setPositiveButton("Tamam",
								new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int which) {
										}
								}).show(); 
	
	
							}else if(s.length()==7){
								new AlertDialog.Builder(Kazandibi.this)
								.setTitle("İp Ucu:").setMessage(s.substring(0, s.length()- (s.length() - 1))+"????? : "+ meaning)
								.setPositiveButton("Tamam",
								new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int which) {
										}
								}).show(); 
	
	
							}else if(s.length()==8){
								new AlertDialog.Builder(Kazandibi.this)
								.setTitle("İp Ucu:").setMessage(s.substring(0, s.length()- (s.length() - 1))+"??????? : "+ meaning)
								.setPositiveButton("Tamam",
								new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int which) {
										}
								}).show(); 
	
	
							}else if(s.length()==9){
								new AlertDialog.Builder(Kazandibi.this)
								.setTitle("İp Ucu:").setMessage(s.substring(0, s.length()- (s.length() - 1))+"???????? : "+ meaning)
								.setPositiveButton("Tamam",
								new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int which) {
										}
								}).show(); 	
							
						}
							
							new_word = false; 
	
						} 
						
						else if (k == s.length() - (s.length() - 2) && k<=s.length()) {
							
								if(s.length() == 4){
									new AlertDialog.Builder(Kazandibi.this)
									.setTitle("İp Ucu:").setMessage(s.substring(0, s.length()- (s.length() - 2))+"?? : " + meaning)
									.setPositiveButton("Tamam",
									new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int which) {
											}
									}).show(); 
	
								}else if(s.length()==5){
									new AlertDialog.Builder(Kazandibi.this)
									.setTitle("İp Ucu:").setMessage(s.substring(0, s.length()- (s.length() - 2))+"??? : " + meaning)
									.setPositiveButton("Tamam",
									new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int which) {
											}
									}).show(); 
									
	
								}else if(s.length()==6){
									new AlertDialog.Builder(Kazandibi.this)
									.setTitle("İp Ucu:").setMessage(s.substring(0, s.length()- (s.length() - 2))+"???? : " + meaning)
									.setPositiveButton("Tamam",
									new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int which) {
											}
									}).show(); 
									
	
								}else if(s.length()==7){
									new AlertDialog.Builder(Kazandibi.this)
									.setTitle("İp Ucu:").setMessage(s.substring(0, s.length()- (s.length() - 2))+"????? : " + meaning)
									.setPositiveButton("Tamam",
									new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int which) {
											}
									}).show(); 
									
	
								}else if(s.length()==8){
									new AlertDialog.Builder(Kazandibi.this)
									.setTitle("İp Ucu:").setMessage(s.substring(0, s.length()- (s.length() - 2))+"?????? : " + meaning)
									.setPositiveButton("Tamam",
									new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int which) {
											}
									}).show();
									
	
								}else if(s.length()==9){
									new AlertDialog.Builder(Kazandibi.this)
									.setTitle("İp Ucu:").setMessage(s.substring(0, s.length()- (s.length() - 2))+"??????? : " + meaning)
									.setPositiveButton("Tamam",
									new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int which) {
											}
									}).show();
									
							}
								
								new_word = false; 
	
						} else if (k == s.length() - (s.length() - 3) &&  k<=s.length()) {
							if(s.length() == 4){
								new AlertDialog.Builder(Kazandibi.this)
								.setTitle("İp Ucu:").setMessage(s.substring(0, s.length()- (s.length() - 3))+"? : " + meaning)
								.setPositiveButton("Tamam",
								new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int which) {
										}
								}).show();
								
								new_word = false; 
							}else if(s.length()==5){
								new AlertDialog.Builder(Kazandibi.this)
								.setTitle("İp Ucu:").setMessage(s.substring(0, s.length()- (s.length() - 3))+"?? : " + meaning)
								.setPositiveButton("Tamam",
								new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int which) {
										}
								}).show();
	
							}else if(s.length()==6){
								new AlertDialog.Builder(Kazandibi.this)
								.setTitle("İp Ucu:").setMessage(s.substring(0, s.length()- (s.length() - 3))+"??? : " + meaning)
								.setPositiveButton("Tamam",
								new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int which) {
										}
								}).show();
								
	
							}else if(s.length()==7){
								new AlertDialog.Builder(Kazandibi.this)
								.setTitle("İp Ucu:").setMessage(s.substring(0, s.length()- (s.length() - 3))+"???? : " + meaning)
								.setPositiveButton("Tamam",
								new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int which) {
										}
								}).show();
								
	
							}else if(s.length()==8){
								new AlertDialog.Builder(Kazandibi.this)
								.setTitle("İp Ucu:").setMessage(s.substring(0, s.length()- (s.length() - 3))+"????? : " + meaning)
								.setPositiveButton("Tamam",
								new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int which) {
										}
								}).show();
	
							}else if(s.length()==9){
								new AlertDialog.Builder(Kazandibi.this)
								.setTitle("İp Ucu:").setMessage(s.substring(0, s.length()- (s.length() - 3))+"?????? : " + meaning)
								.setPositiveButton("Tamam",
								new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int which) {
										}
								}).show();
								
							}
							
							new_word = false; 
							
						}else if (k == s.length() - (s.length() - 4)&&  k<=s.length()) {
							if(s.length()==4){
								new AlertDialog.Builder(Kazandibi.this)
								.setTitle("İp Ucu:").setMessage(s.substring(0, s.length()- (s.length() - 4)) + " : " + meaning)
								.setPositiveButton("Tamam",
								new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int which) {
										}
								}).show();
								
								DatabasedeAra(s.substring(0, s.length()- (s.length() - 4)));
								new_word = true;
							}
								else if(s.length()==5){
									new AlertDialog.Builder(Kazandibi.this)
									.setTitle("İp Ucu:").setMessage(s.substring(0, s.length()- (s.length() - 4))+"? : " + meaning)
									.setPositiveButton("Tamam",
									new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int which) {
											}
									}).show();
									
								new_word = false;
	
							}else if(s.length()==6){
								new AlertDialog.Builder(Kazandibi.this)
								.setTitle("İp Ucu:").setMessage(s.substring(0, s.length()- (s.length() - 4))+"?? : " + meaning)
								.setPositiveButton("Tamam",
								new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int which) {
										}
								}).show();
								
							}else if(s.length()==7){
								new AlertDialog.Builder(Kazandibi.this)
								.setTitle("İp Ucu:").setMessage(s.substring(0, s.length()- (s.length() - 4))+"??? : " + meaning)
								.setPositiveButton("Tamam",
								new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int which) {
										}
								}).show();
							
							}else if(s.length()==8){
								new AlertDialog.Builder(Kazandibi.this)
								.setTitle("İp Ucu:").setMessage(s.substring(0, s.length()- (s.length() - 4))+"???? : " + meaning)
								.setPositiveButton("Tamam",
								new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int which) {
										}
								}).show();
								
							}else if(s.length()==9){
								
								new AlertDialog.Builder(Kazandibi.this)
								.setTitle("İp Ucu:").setMessage(s.substring(0, s.length()- (s.length() - 4))+"????? : " + meaning)
								.setPositiveButton("Tamam",
								new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int which) {
										}
								}).show();
								
							}
						}else if (k == s.length() - (s.length() - 5)&&  k<=s.length()) {
							
							if(s.length()==5){
								new AlertDialog.Builder(Kazandibi.this)
								.setTitle("İp Ucu:").setMessage(s.substring(0, s.length()- (s.length() - 5)) + " : " + meaning)
								.setPositiveButton("Tamam",
								new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int which) {
										}
								}).show();
								
								DatabasedeAra(s.substring(0, s.length()- (s.length() - 5)));
								new_word = true;
							}
	
							else if(s.length()==6){
								
								new AlertDialog.Builder(Kazandibi.this)
								.setTitle("İp Ucu:").setMessage(s.substring(0, s.length()- (s.length() - 5))+"? : " + meaning)
								.setPositiveButton("Tamam",
								new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int which) {
										}
								}).show();
								
								new_word = false; 
	
							}else if(s.length()==7){
								
								new AlertDialog.Builder(Kazandibi.this)
								.setTitle("İp Ucu:").setMessage(s.substring(0, s.length()- (s.length() - 5))+"?? : " + meaning)
								.setPositiveButton("Tamam",
								new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int which) {
										}
								}).show();
								
							}else if(s.length()==8){
								new AlertDialog.Builder(Kazandibi.this)
								.setTitle("İp Ucu:").setMessage(s.substring(0, s.length()- (s.length() - 5))+"??? : " + meaning)
								.setPositiveButton("Tamam",
								new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int which) {
										}
								}).show();
	
							}else if(s.length()==9){
								new AlertDialog.Builder(Kazandibi.this)
								.setTitle("İp Ucu:").setMessage(s.substring(0, s.length()- (s.length() - 5))+"???? : " + meaning)
								.setPositiveButton("Tamam",
								new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int which) {
										}
								}).show();
	
							}
						}else if (k == s.length() - (s.length() - 6)&&  k<=s.length()) {
							if(s.length()==6){
								new AlertDialog.Builder(Kazandibi.this)
								.setTitle("İp Ucu:").setMessage(s.substring(0, s.length()- (s.length() -6)) + " : " + meaning)
								.setPositiveButton("Tamam",
								new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int which) {
										}
								}).show();
	
								DatabasedeAra(s.substring(0, s.length()- (s.length() -6)));
								new_word = true;
	
							}else if(s.length()==7){
								new AlertDialog.Builder(Kazandibi.this)
								.setTitle("İp Ucu:").setMessage(s.substring(0, s.length()- (s.length() - 6))+"? : " + meaning)
								.setPositiveButton("Tamam",
								new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int which) {
										}
								}).show();
								
								new_word = false; 
	
							}else if(s.length()==8){
								new AlertDialog.Builder(Kazandibi.this)
								.setTitle("İp Ucu:").setMessage(s.substring(0, s.length()- (s.length() - 6))+"?? : " + meaning)
								.setPositiveButton("Tamam",
								new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int which) {
										}
								}).show();
								
	
							}else if(s.length()==9){
								new AlertDialog.Builder(Kazandibi.this)
								.setTitle("İp Ucu:").setMessage(s.substring(0, s.length()- (s.length() - 6))+"??? : " + meaning)
								.setPositiveButton("Tamam",
								new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int which) {
										}
								}).show();
								
							}
						}else if (k == s.length() - (s.length() - 7) &&  k<=s.length()) {
							if(s.length()==7){
								new AlertDialog.Builder(Kazandibi.this)
								.setTitle("Ä°p Ucu:").setMessage(s.substring(0, s.length()- (s.length() - 7)) + " : "+ meaning)
								.setPositiveButton("Tamam",
								new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int which) {
										}
								}).show();
								
								DatabasedeAra(s.substring(0, s.length()- (s.length() - 7)));
								new_word = true;
	
							}else if(s.length()==8){
								new AlertDialog.Builder(Kazandibi.this)
								.setTitle("Ä°p Ucu:").setMessage(s.substring(0, s.length()- (s.length() - 7))+"? : "+ meaning)
								.setPositiveButton("Tamam",
								new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int which) {
										}
								}).show();
								
								new_word = false; 
	
							}else if(s.length()==9){
								new AlertDialog.Builder(Kazandibi.this)
								.setTitle("Ä°p Ucu:").setMessage(s.substring(0, s.length()- (s.length() - 7))+"?? : "+ meaning)
								.setPositiveButton("Tamam",
								new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int which) {
										}
								}).show();
								
							}
						}else if (k == s.length() - (s.length() - 8) &&  k<=s.length()) {
							if(s.length()==8){
								new AlertDialog.Builder(Kazandibi.this)
								.setTitle("Ä°p Ucu:").setMessage(s.substring(0, s.length()- (s.length() - 8)) + " : "+ meaning)
								.setPositiveButton("Tamam",
								new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int which) {
										}
								}).show();
								
								DatabasedeAra(s.substring(0, s.length()- (s.length() - 8)));
								new_word = true;
	
							}else if(s.length()==9){
								new AlertDialog.Builder(Kazandibi.this)
								.setTitle("Ä°p Ucu:").setMessage(s.substring(0, s.length()- (s.length() - 8))+"? : "+ meaning)
								.setPositiveButton("Tamam",
								new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int which) {
										}
								}).show();
								
								new_word = false; 
								
							}
						}else if (k == s.length() - (s.length() - 9) &&  k<=s.length()) {
							if(s.length()==9){
								new AlertDialog.Builder(Kazandibi.this)
								.setTitle("Ä°p Ucu:").setMessage(s.substring(0, s.length()- (s.length() - 9)) + " : "+ meaning)
								.setPositiveButton("Tamam",
								new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int which) {
										}
								}).show();
								
								DatabasedeAra(s.substring(0, s.length()- (s.length() - 9)));
								new_word = true;
							}
						}
				 	}
			}});
		
		//Cevap Button Processes
		cevap.setOnClickListener(new OnClickListener() {

			
			public void onClick(View arg0) {
				String cevapk= kelime.getText().toString();
				DatabasedeAra(cevapk);
			}});
		
		//Leave Button Processes
		leave = (Button)findViewById(R.id.leave);
		leave.setOnClickListener(new OnClickListener() {

			
			public void onClick(View arg0) {
				
				leave_status = true;
				
				final int account_id = Integer.parseInt(account_ids);
				
				Intent intent = new Intent(getApplicationContext(),DessertsActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("account_id", Long.toString(account_id));
				bundle.putString("kazandibi_count", Long.toString(getCount()));
				bundle.putString("muhallebi_count", null);
				bundle.putString("kemalpasa_count", null);
				bundle.putString("pismaniye_count", null);
				intent.putExtras(bundle);
				startActivity(intent);	
				
				finish();
				
			}});
			
	}
	
	//Sensor pause
	protected void onPause() {
		if (sensorMgr != null) {
			sensorMgr.unregisterListener(this,
					SensorManager.SENSOR_ACCELEROMETER);
			sensorMgr = null;
		}
		super.onPause();
	}

	public void onAccuracyChanged(int arg0, int arg1) {
	}
	
	// getting the number of words
	private int getCount()
	{
		int count=0;
		final DatabaseHelper myDbHelper = new DatabaseHelper(this);
		int account_id =Integer.parseInt(account_ids);
		count = (int) myDbHelper.GetCountFindKazandibiWords(account_id);
		myDbHelper.close();
		return count;
	}
	
	
	//Sensor Issue
	public void onSensorChanged(int sensor, float[] values) {
		
		if (sensor == SensorManager.SENSOR_ACCELEROMETER) {
			long curTime = System.currentTimeMillis();
			
			// only allow one update every 100ms.
			if ((curTime - lastUpdate) > 100) {
				long diffTime = (curTime - lastUpdate);
				lastUpdate = curTime;

				x = values[SensorManager.DATA_X];
				y = values[SensorManager.DATA_Y];
				

				float speed = Math.abs(x + y - last_x - last_y)
						/ diffTime * 10000;

				if (speed > SHAKE_THRESHOLD) {

					String[] a = { b1.getText().toString(),
							b2.getText().toString(), b3.getText().toString(),
							b4.getText().toString(), b5.getText().toString(),
							b6.getText().toString(), b7.getText().toString(),
							b8.getText().toString(), b9.getText().toString() };

					Collections.shuffle(Arrays.asList(a));
					
					//Buttons Enable-Disable Issues
					b1.setEnabled(true);
					b2.setEnabled(true);
					b3.setEnabled(true);
					b4.setEnabled(true);
					b5.setEnabled(true);
					b6.setEnabled(true);
					b7.setEnabled(true);
					b8.setEnabled(true);
					b9.setEnabled(true);

					b1.setText(a[0]);
					b2.setText(a[1]);
					b3.setText(a[2]);
					b4.setText(a[3]);
					b5.setText(a[4]);
					b6.setText(a[5]);
					b7.setText(a[6]);
					b8.setText(a[7]);
					b9.setText(a[8]);
					
					b1_sta = true;
					b2_sta = true;
					b3_sta = true;
					b4_sta = true;
					b5_sta = true;
					b6_sta = true;
					b7_sta = true;
					b8_sta = true;
					b9_sta = true;
					
					word_al_sta = true;
					
					for(int i=0 ; i<word_al.size() ; i++)
					{
						if(b1.getText().toString().compareTo(word_al.get(i)) == 0 && (b1_sta.equals(true)))
						{
						   b1.setEnabled(false);
						   b1_sta = false;
						   
						}	
						
						else if(b2.getText().toString().compareTo(word_al.get(i)) == 0 && (b2_sta.equals(true)))
						{
						   b2.setEnabled(false);
						   b2_sta =false;
						   
						}	
						
						else if(b3.getText().toString().compareTo(word_al.get(i)) == 0 && (b3_sta.equals(true)))
						{
						   b3.setEnabled(false);
						   b3_sta = false;
						  
						}	
						
						else if(b4.getText().toString().compareTo(word_al.get(i)) == 0 && (b4_sta.equals(true)))
						{
						   b4.setEnabled(false);
						   b4_sta = false;
						  
						}	
						
						else if(b5.getText().toString().compareTo(word_al.get(i)) == 0 && (b5_sta.equals(true)))
						{
						   b5.setEnabled(false);
						   b5_sta = false;
						  
						}	
						
						else if(b6.getText().toString().compareTo(word_al.get(i)) == 0 && (b6_sta.equals(true)))
						{
						   b6.setEnabled(false);
						   b6_sta = false;
						  
						}	
						
						else if(b7.getText().toString().compareTo(word_al.get(i)) == 0 && (b7_sta.equals(true)))
						{
						   b7.setEnabled(false);
						   b7_sta = false;
						  
						}	
						
						else if(b8.getText().toString().compareTo(word_al.get(i)) == 0 && (b8_sta.equals(true)))
						{
						   b8.setEnabled(false);
						   b8_sta = false;
						  
						}	
						
						else if(b9.getText().toString().compareTo(word_al.get(i)) == 0 && (b9_sta.equals(true)))
						{
						   b9.setEnabled(false);
						   b9_sta = false;
						  
						}	
					}

				}
				last_x = x;
				last_y = y;
				
			}
		}
	}
	
	
	// Checking the word is correct or not - adding to database or not
	private void DatabasedeAra(String cevapk) {
    	//Create new instance of DatabaseHelper class
        
        final DatabaseHelper myDbHelper = new DatabaseHelper(this);
        
    	Boolean sonuc1 = false;
    	Boolean sonuc2 = false ;

    	int account_id =Integer.parseInt(account_ids);

    	myDbHelper.close();
    	
    	myDbHelper.openDataBase();
 
    	Cursor c1 = myDbHelper.GetFindedWordsKazandibi(account_id);
    	
    	// Look at the words which are found
    	
    	while(c1.moveToNext())
    	{
    		
    		if(cevapk.compareToIgnoreCase(c1.getString(c1.getColumnIndex("wordname"))) == 0)
    		{
    			wrong.start();
    			new AlertDialog.Builder(Kazandibi.this)
				.setTitle("UYARI !").setMessage(c1.getString(c1.getColumnIndex("wordname")) + "  Bu Kelime Zaten BulunmuÅŸtur..")
				.setPositiveButton("Tamam",
				new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
						}
				}).show();
    			sonuc1 = true;
    			if(c1.getString(c1.getColumnIndex("wordname")).equals(s)){
    				new_word=true;
    			}else {
    				new_word=false;
    			}
    			kelime.setText("");
    			hint.setVisibility(View.VISIBLE);	
				sil.setVisibility(View.INVISIBLE);	
				cevap.setEnabled(false);
				
				b1.setEnabled(true);
				b2.setEnabled(true);
				b3.setEnabled(true);
				b4.setEnabled(true);
				b5.setEnabled(true);
				b6.setEnabled(true);
				b7.setEnabled(true);
				b8.setEnabled(true);
				b9.setEnabled(true);
				
				b1_sta = true;
				b2_sta = true;
				b3_sta = true;
				b4_sta = true;
				b5_sta = true;
				b6_sta = true;
				b7_sta = true;
				b8_sta = true;
				b9_sta = true;
				
				al.clear();
				word_al.clear();
				
    			break;
    		}
    		
    	}
    	
    	myDbHelper.close();
    	
    	myDbHelper.openDataBase();
    	
    	Cursor c2 = myDbHelper.GetKazandibiWords();
    	
    	
    	// Look at the all words
    	if(sonuc1 == false)
    	{
    		

    	while(c2.moveToNext()){	
    		
    	if(cevapk.compareToIgnoreCase(c2.getString(c2.getColumnIndex("wordname"))) == 0){
    		correct.start();
    		sonuc2 = true;
    		kelime.setText("");
    		hint.setVisibility(View.VISIBLE);	
			sil.setVisibility(View.INVISIBLE);	
			cevap.setEnabled(false);
			
			b1.setEnabled(true);
			b2.setEnabled(true);
			b3.setEnabled(true);
			b4.setEnabled(true);
			b5.setEnabled(true);
			b6.setEnabled(true);
			b7.setEnabled(true);
			b8.setEnabled(true);
			b9.setEnabled(true);
			
			b1_sta = true;
			b2_sta = true;
			b3_sta = true;
			b4_sta = true;
			b5_sta = true;
			b6_sta = true;
			b7_sta = true;
			b8_sta = true;
			b9_sta = true;
			
			al.clear();
			word_al.clear();
			
			myDbHelper.InsertFindedWordKazandibi(account_id,cevapk); 

			if(cevapk.equals(s)){
    			new_word=true;
    		}else {
    			new_word=false;
    		}
			
	        id = (int)myDbHelper.GetCountFindKazandibiWords(account_id);
	        
	        Text= new String[id+5];
	        int j=0;
	        
	        cr = myDbHelper.GetFindedWordsKazandibi(account_id);
	        while(cr.moveToNext()){	
	        	
	        	Text[j]="\t\t"+(cr.getString(cr.getColumnIndex("wordname")))+"\t\t";
	        	j++;	
	        }
	        
	        for(int i= Text.length-1 ; i >= Text.length - 5 ; i--)
	        {
	        	Text[i]="\t\t\t\t\t";	
	        }
	        
	        ArrayAdapter arr=new ArrayAdapter(this,android.R.layout.simple_gallery_item, Text);
	        gallery.setAdapter(arr);
	 
	       
	        handler= new Handler();
	        run();
    		
    		
    		int kazandibi_puan = (int) myDbHelper.GetKazandibiScore(account_id) + ((cevapk.length() * 5 )) ;
    		int total_puan = (int) myDbHelper.GetTotalScore(account_id) + ((cevapk.length() * 5 ));
    		myDbHelper.UpdateKazandibiScore(Integer.toString(kazandibi_puan), Integer.toString(account_id));
         	myDbHelper.UpdateTotalScore(Integer.toString(total_puan), Integer.toString(account_id));
    		break;
    	}	
    	
    	}
    	
    	}
    	
    	// If the word is wrong
    	if(sonuc1 == false && sonuc2 == false)
    	{
    		wrong.start();
    	}
    	
    	c1.close();
    	c2.close();
    	myDbHelper.close();
    	
    
}

	
	// Infinite Sliding Gallery
	public void run() {
			if( leave_status )
				return;
		
            PicPosition = gallery.getSelectedItemPosition() + 1 ;
            if (PicPosition == Text.length) 
            {
            	PicPosition=0;
                gallery.setSelection(PicPosition);
            }
            else
            {
            	gallery.setSelection(PicPosition);//move to the next gallery element.
            	
            }
            handler.postDelayed(this, 2500); //execute every second
}
	
	// Running the Animation
	public void runAnimate()
	{
		
		imgView = (ImageView) findViewById ( R.id.kazandibi_anim);
        
        new CountDownTimer(75,150) {
        	
        	int[] array  = {R.drawable.kazandibi1,R.drawable.kazandibi2,R.drawable.kazandibi3,
        					R.drawable.kazandibi4,R.drawable.kazandibi5,R.drawable.kazandibi6,
        					R.drawable.kazandibi7,R.drawable.kazandibi8};

                        
                        public void onTick(long millisUntilFinished) {}

                        
                        public void onFinish() {
                           
							imgView.setImageDrawable(getResources().getDrawable(array[i]));
                            i++;
                            if(i== array.length) 
                            	i=0;
                            		start();	
                           
                        }
                    }.start();
	}
	
		
	}
	
