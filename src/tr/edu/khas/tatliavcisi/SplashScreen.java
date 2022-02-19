package tr.edu.khas.tatliavcisi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.media.MediaPlayer;
 
public class SplashScreen extends Activity {
	
	MediaPlayer mp;
	
   /** Called when the activity is first created. */
   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.splash);
      
      // Create the sound
      mp = MediaPlayer.create(this,R.raw.intro);
     
      
      ProgressBar pg = (ProgressBar) findViewById(R.id.progressbar_default);
     
      //splash Screen Running
      Thread splashThread = new Thread() {
         @Override
         public void run() {
           
            mp.start();
            while(mp.isPlaying())
            {
            	
            }
           
               finish();
               Intent i = new Intent();
               i.setClassName("tr.edu.khas.tatliavcisi",
                              "tr.edu.khas.tatliavcisi.MainActivity");
               startActivity(i);
               mp.pause();
           
         }
      };
      splashThread.start();
   }
}