package tr.edu.khas.tatliavcisi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
 
public class DessertsActivity extends Activity {
 
	//Variables
	private ListView Dessert_List;
	String account_ids ;
	String kemalpasa_counts ;
	String muhallebi_counts;
	String pismaniye_counts;
	String kazandibi_counts;
	
	
   //Start the activity
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dessert_list);
        
        // Get the values from activity change
        
        Bundle extra = getIntent().getExtras();
        account_ids = extra.getString("account_id");
        
        Bundle extra2 = getIntent().getExtras();
        kemalpasa_counts = extra2.getString("kemalpasa_count");
        
        Bundle extra3 = getIntent().getExtras();
        muhallebi_counts = extra3.getString("muhallebi_count");
        
        Bundle extra4 = getIntent().getExtras();
        pismaniye_counts = extra4.getString("pismaniye_count");
        
        Bundle extra5 = getIntent().getExtras();
        kazandibi_counts = extra5.getString("kazandibi_count");
        
        //Create new instance of DatabaseHelper class
        final DatabaseHelper myDbHelper = new DatabaseHelper(this);
        
        //Create database 
        try {
        		myDbHelper.createDataBase();
        	
        } catch (IOException ioe) {
        	throw new Error("Unable to create database");
        }
      
        //Open database
        try {
 			myDbHelper.openDataBase();
        }catch(SQLException sqle){
 		throw sqle;
        }
 	
    final int account_id = Integer.parseInt(account_ids);
 	
    // Getting all Dessert names from database
 	Cursor cursor = myDbHelper.getAllDesserts(account_id);
 	startManagingCursor(cursor);
 	
    String [] values = new String [4] ;
    int i= 0;
 	
 	 while(cursor.moveToNext())
     {
     	values[i] = cursor.getString(cursor.getColumnIndex("name"));
     	i++;
     }
     
     cursor.close();
     
     	// Writing the information to screen
     	TextView total_dessert = (TextView) findViewById(R.id.total_dessert);
     	total_dessert.setText("Tatlý:" + Long.toString(myDbHelper.GetCountWords()));
     	
     	long complete_result = (100 * myDbHelper.GetCountFindWords(account_id)) / myDbHelper.GetCountWords();
     	TextView completed = (TextView) findViewById(R.id.complete);
     	completed.setText("Bulunan: %" + Long.toString(complete_result));
     	
     	TextView total_score = (TextView) findViewById(R.id.total_score);
     	total_score.setText("Puan:" + Long.toString(myDbHelper.GetTotalScore(account_id)));
     	
     	Button dessertback = (Button) findViewById(R.id.dessertback);
     	
     	//If the back button is used
     	dessertback.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
			finish();
			}});
        
        Dessert_List = (ListView) findViewById(R.id.dessertlist);
        Dessert_List.setOnItemClickListener(new OnItemClickListener() {

        	//It determines that which dessert page will be opened
        	@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long id) {
				Bundle bundle = new Bundle();

				if (id == 0) {
					// Go to Kemalpaþa Page
					Intent intent = new Intent(getApplicationContext(),Kemalpasa.class);
					bundle = new Bundle();
					bundle.putString("account_id",Integer.toString(account_id));
					intent.putExtras(bundle);
					startActivity(intent);
					finish();
				} 
				
				else if (id == 1) {
					// Go to Muhallebi Page
					Intent intent = new Intent(getApplicationContext(),Muhallebi.class);
					bundle = new Bundle();
					bundle.putString("account_id", Integer.toString(account_id));
					intent.putExtras(bundle);
					startActivity(intent);
					finish();
					
				}
				
				else if (id == 2) {
					//Go to Piþmaniye Page
					Intent intent = new Intent(getApplicationContext(),Pismaniye.class);
					bundle = new Bundle();
					bundle.putString("account_id", Integer.toString(account_id));
					intent.putExtras(bundle);
					startActivity(intent);
					finish();
				}
				
				else if (id == 3) {
					//Go to Kazandibi Page
					Intent intent = new Intent(getApplicationContext(),Kazandibi.class);
					bundle = new Bundle();
					bundle.putString("account_id", Integer.toString(account_id));
					intent.putExtras(bundle);
					startActivity(intent);
					finish();
				}
			}
		});
        
        // Putting the rating bar and the scores
        ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map;
 
        // Adding Kemalpaþa Information
        map = new HashMap<String, String>(); 
        map.put("title", values[0]);
        
        map.put("description",Long.toString(myDbHelper.GetCountFindKemalpasaWords(account_id))  + "/" + Long.toString(myDbHelper.GetCountKemalpasa()));
       
        if(kemalpasa_counts != null)
        {
        	if ( Integer.parseInt(kemalpasa_counts)>=0 && Integer.parseInt(kemalpasa_counts)<=6)
        	{ 
        		map.put("img", String.valueOf(R.drawable.ratingbar1));
        	}
        
        	else if ( Integer.parseInt(kemalpasa_counts)>=7 && Integer.parseInt(kemalpasa_counts)<=13)
        	{
        		map.put("img", String.valueOf(R.drawable.ratingbar2));	
        	} 
        
        	else if ( Integer.parseInt(kemalpasa_counts)>=14 && Integer.parseInt(kemalpasa_counts)<=20)
        	{
        		map.put("img", String.valueOf(R.drawable.ratingbar3));	
        	} 
        
        	else if ( Integer.parseInt(kemalpasa_counts)>=21 && Integer.parseInt(kemalpasa_counts)<=27)
        	{
        		map.put("img", String.valueOf(R.drawable.ratingbar4));	
        	} 
        	
        	else if ( Integer.parseInt(kemalpasa_counts)>=28 && Integer.parseInt(kemalpasa_counts)<=34)
        	{
        		map.put("img", String.valueOf(R.drawable.ratingbar5));	
        	} 
        
        	else if ( Integer.parseInt(kemalpasa_counts)>=35 && Integer.parseInt(kemalpasa_counts)<=41)
        	{
        		map.put("img", String.valueOf(R.drawable.ratingbar6));	
        	} 
        
        	else if ( Integer.parseInt(kemalpasa_counts)>=42 && Integer.parseInt(kemalpasa_counts)<=48)
        	{
        		map.put("img", String.valueOf(R.drawable.ratingbar7));	
        	} 
        
        	else if ( Integer.parseInt(kemalpasa_counts)>=49 && Integer.parseInt(kemalpasa_counts)<=55)
        	{
        		map.put("img", String.valueOf(R.drawable.ratingbar8));	
        	} 
        
        	else if ( Integer.parseInt(kemalpasa_counts)>=56 && Integer.parseInt(kemalpasa_counts)<=62)
        	{
        		map.put("img", String.valueOf(R.drawable.ratingbar9));	
        	} 
        
        else if ( Integer.parseInt(kemalpasa_counts)>=63 && Integer.parseInt(kemalpasa_counts)<=72)
        {
        	map.put("img", String.valueOf(R.drawable.ratingbar10));	
        } 
        
        else if ( Integer.parseInt(kemalpasa_counts) == 73)
        {
        	map.put("img", String.valueOf(R.drawable.ratingbar11));	
        } 
           
        }
        
        else
        {
        
        if ( myDbHelper.GetCountFindKemalpasaWords(account_id)>=0 && myDbHelper.GetCountFindKemalpasaWords(account_id)<=6)
        { 
        	map.put("img", String.valueOf(R.drawable.ratingbar1));
        }
        
       else if ( myDbHelper.GetCountFindKemalpasaWords(account_id)>=7 && myDbHelper.GetCountFindKemalpasaWords(account_id)<=13)
        {
        	map.put("img", String.valueOf(R.drawable.ratingbar2));	
        } 
        
        else if ( myDbHelper.GetCountFindKemalpasaWords(account_id)>=14 && myDbHelper.GetCountFindKemalpasaWords(account_id)<=20)
        {
        	map.put("img", String.valueOf(R.drawable.ratingbar3));	
        } 
        
        else if ( myDbHelper.GetCountFindKemalpasaWords(account_id)>=21 && myDbHelper.GetCountFindKemalpasaWords(account_id)<=27)
        {
        	map.put("img", String.valueOf(R.drawable.ratingbar4));	
        } 
        
        else if ( myDbHelper.GetCountFindKemalpasaWords(account_id)>=28 && myDbHelper.GetCountFindKemalpasaWords(account_id)<=34)
        {
        	map.put("img", String.valueOf(R.drawable.ratingbar5));	
        } 
        
        else if ( myDbHelper.GetCountFindKemalpasaWords(account_id)>=35 && myDbHelper.GetCountFindKemalpasaWords(account_id)<=41)
        {
        	map.put("img", String.valueOf(R.drawable.ratingbar6));	
        } 
        
        else if ( myDbHelper.GetCountFindKemalpasaWords(account_id)>=42 && myDbHelper.GetCountFindKemalpasaWords(account_id)<=48)
        {
        	map.put("img", String.valueOf(R.drawable.ratingbar7));	
        } 
        
        else if ( myDbHelper.GetCountFindKemalpasaWords(account_id)>=49 && myDbHelper.GetCountFindKemalpasaWords(account_id)<=55)
        {
        	map.put("img", String.valueOf(R.drawable.ratingbar8));	
        } 
        
        else if ( myDbHelper.GetCountFindKemalpasaWords(account_id)>=56 && myDbHelper.GetCountFindKemalpasaWords(account_id)<=62)
        {
        	map.put("img", String.valueOf(R.drawable.ratingbar9));	
        } 
        
        else if ( myDbHelper.GetCountFindKemalpasaWords(account_id)>=63 && myDbHelper.GetCountFindKemalpasaWords(account_id)<=72)
        {
        	map.put("img", String.valueOf(R.drawable.ratingbar10));	
        } 
        
        else if ( myDbHelper.GetCountFindKemalpasaWords(account_id) == 73)
        {
        	map.put("img", String.valueOf(R.drawable.ratingbar11));	
        } 
           
        }
        map.put("dessert_score","Puan:"+ Long.toString(myDbHelper.GetKemalpasaScore(account_id)));
        listItem.add(map);
 
        
        // Adding Muhallebi Information
        map = new HashMap<String, String>();
        map.put("title", values[1]);
        
        map.put("description", Long.toString(myDbHelper.GetCountFindMuhallebiWords(account_id)) + "/" + Long.toString(myDbHelper.GetCountMuhallebi()));
       
        if(muhallebi_counts != null)
        {
        if ( Integer.parseInt(muhallebi_counts)>=0 && Integer.parseInt(muhallebi_counts)<=6)
        { 
        	map.put("img", String.valueOf(R.drawable.ratingbar1));
        }
        
       else if ( Integer.parseInt(muhallebi_counts)>=7 && Integer.parseInt(muhallebi_counts)<=13)
        {
        	map.put("img", String.valueOf(R.drawable.ratingbar2));	
        } 
        
        else if ( Integer.parseInt(muhallebi_counts)>=14 && Integer.parseInt(muhallebi_counts)<=20)
        {
        	map.put("img", String.valueOf(R.drawable.ratingbar3));	
        } 
        
        else if ( Integer.parseInt(muhallebi_counts)>=21 && Integer.parseInt(muhallebi_counts)<=27)
        {
        	map.put("img", String.valueOf(R.drawable.ratingbar4));	
        } 
        
        else if ( Integer.parseInt(muhallebi_counts)>=28 && Integer.parseInt(muhallebi_counts)<=34)
        {
        	map.put("img", String.valueOf(R.drawable.ratingbar5));	
        } 
        
        else if ( Integer.parseInt(muhallebi_counts)>=35 && Integer.parseInt(muhallebi_counts)<=41)
        {
        	map.put("img", String.valueOf(R.drawable.ratingbar6));	
        } 
        
        else if ( Integer.parseInt(muhallebi_counts)>=42 && Integer.parseInt(muhallebi_counts)<=48)
        {
        	map.put("img", String.valueOf(R.drawable.ratingbar7));	
        } 
        
        else if ( Integer.parseInt(muhallebi_counts)>=49 && Integer.parseInt(muhallebi_counts)<=55)
        {
        	map.put("img", String.valueOf(R.drawable.ratingbar8));	
        } 
        
        else if ( Integer.parseInt(muhallebi_counts)>=56 && Integer.parseInt(muhallebi_counts)<=62)
        {
        	map.put("img", String.valueOf(R.drawable.ratingbar9));	
        } 
        
        else if ( Integer.parseInt(muhallebi_counts)>=63 && Integer.parseInt(muhallebi_counts)<=72)
        {
        	map.put("img", String.valueOf(R.drawable.ratingbar10));	
        } 
        
        else if ( Integer.parseInt(muhallebi_counts) == 73)
        {
        	map.put("img", String.valueOf(R.drawable.ratingbar11));	
        } 
           
        }
        
        else
        {
        
        
        if(myDbHelper.GetCountFindMuhallebiWords(account_id) >= 0 && myDbHelper.GetCountFindMuhallebiWords(account_id) <= 7)
        {
        	map.put("img", String.valueOf(R.drawable.ratingbar1));
        }
        
        else if(myDbHelper.GetCountFindMuhallebiWords(account_id) >= 8 && myDbHelper.GetCountFindMuhallebiWords(account_id) <= 14)
        {
        	map.put("img", String.valueOf(R.drawable.ratingbar2));
        } 
        
        else if(myDbHelper.GetCountFindMuhallebiWords(account_id) >= 15 && myDbHelper.GetCountFindMuhallebiWords(account_id) <= 22)
        {
        	map.put("img", String.valueOf(R.drawable.ratingbar3));
        } 
        
        else if(myDbHelper.GetCountFindMuhallebiWords(account_id) >= 23 && myDbHelper.GetCountFindMuhallebiWords(account_id) <= 30)
        {
        	map.put("img", String.valueOf(R.drawable.ratingbar4));
        } 
        
        else if(myDbHelper.GetCountFindMuhallebiWords(account_id) >= 31 && myDbHelper.GetCountFindMuhallebiWords(account_id) <= 38)
        {
        	map.put("img", String.valueOf(R.drawable.ratingbar5));
        } 
        
        else if(myDbHelper.GetCountFindMuhallebiWords(account_id) >= 39 && myDbHelper.GetCountFindMuhallebiWords(account_id) <= 46)
        {
        	map.put("img", String.valueOf(R.drawable.ratingbar6));
        } 
        
        else if(myDbHelper.GetCountFindMuhallebiWords(account_id) >= 47 && myDbHelper.GetCountFindMuhallebiWords(account_id) <= 54)
        {
        	map.put("img", String.valueOf(R.drawable.ratingbar7));
        } 
        
        else if(myDbHelper.GetCountFindMuhallebiWords(account_id) >= 55 && myDbHelper.GetCountFindMuhallebiWords(account_id) <= 62)
        {
        	map.put("img", String.valueOf(R.drawable.ratingbar8));
        }
        
        else if(myDbHelper.GetCountFindMuhallebiWords(account_id) >= 63 && myDbHelper.GetCountFindMuhallebiWords(account_id) <= 70)
        {
        	map.put("img", String.valueOf(R.drawable.ratingbar9));
        } 
        
        else if(myDbHelper.GetCountFindMuhallebiWords(account_id) >= 71 && myDbHelper.GetCountFindMuhallebiWords(account_id) <= 78)
        {
        	map.put("img", String.valueOf(R.drawable.ratingbar10));
        }
        
        else if(myDbHelper.GetCountFindMuhallebiWords(account_id) == 79)
        {
        	map.put("img", String.valueOf(R.drawable.ratingbar11));      	
        } 
        
        }
        
        map.put("dessert_score","Puan:"+ Long.toString(myDbHelper.GetMuhallebiScore(account_id)));
        listItem.add(map);
 
        
        // Adding Piþmaniye Information
        map = new HashMap<String, String>();
        map.put("title", values[2]);
        
        map.put("description",Long.toString(myDbHelper.GetCountFindPismaniyeWords(account_id))  + "/" + Long.toString(myDbHelper.GetCountPismaniye()));
        
        if(pismaniye_counts != null)
        {
        if ( Integer.parseInt(pismaniye_counts)>=0 && Integer.parseInt(pismaniye_counts)<=6)
        { 
        	map.put("img", String.valueOf(R.drawable.ratingbar1));
        }
        
       else if ( Integer.parseInt(pismaniye_counts)>=7 && Integer.parseInt(pismaniye_counts)<=13)
        {
        	map.put("img", String.valueOf(R.drawable.ratingbar2));	
        } 
        
        else if ( Integer.parseInt(pismaniye_counts)>=14 && Integer.parseInt(pismaniye_counts)<=20)
        {
        	map.put("img", String.valueOf(R.drawable.ratingbar3));	
        } 
        
        else if ( Integer.parseInt(pismaniye_counts)>=21 && Integer.parseInt(pismaniye_counts)<=27)
        {
        	map.put("img", String.valueOf(R.drawable.ratingbar4));	
        } 
        
        else if ( Integer.parseInt(pismaniye_counts)>=28 && Integer.parseInt(pismaniye_counts)<=34)
        {
        	map.put("img", String.valueOf(R.drawable.ratingbar5));	
        } 
        
        else if ( Integer.parseInt(pismaniye_counts)>=35 && Integer.parseInt(pismaniye_counts)<=41)
        {
        	map.put("img", String.valueOf(R.drawable.ratingbar6));	
        } 
        
        else if ( Integer.parseInt(pismaniye_counts)>=42 && Integer.parseInt(pismaniye_counts)<=48)
        {
        	map.put("img", String.valueOf(R.drawable.ratingbar7));	
        } 
        
        else if ( Integer.parseInt(pismaniye_counts)>=49 && Integer.parseInt(pismaniye_counts)<=55)
        {
        	map.put("img", String.valueOf(R.drawable.ratingbar8));	
        } 
        
        else if ( Integer.parseInt(pismaniye_counts)>=56 && Integer.parseInt(pismaniye_counts)<=62)
        {
        	map.put("img", String.valueOf(R.drawable.ratingbar9));	
        } 
        
        else if ( Integer.parseInt(pismaniye_counts)>=63 && Integer.parseInt(pismaniye_counts)<=72)
        {
        	map.put("img", String.valueOf(R.drawable.ratingbar10));	
        } 
        
        else if ( Integer.parseInt(pismaniye_counts) == 73)
        {
        	map.put("img", String.valueOf(R.drawable.ratingbar11));	
        } 
           
        }
        
        else
        {
        
        
        if ( myDbHelper.GetCountFindPismaniyeWords(account_id)>=0 && myDbHelper.GetCountFindPismaniyeWords(account_id) <=4)
        {
        map.put("img", String.valueOf(R.drawable.ratingbar1));
        }
        
        else if ( myDbHelper.GetCountFindPismaniyeWords(account_id)>=5 && myDbHelper.GetCountFindPismaniyeWords(account_id) <=9)
        {
        map.put("img", String.valueOf(R.drawable.ratingbar2));
        } 
        
        else if ( myDbHelper.GetCountFindPismaniyeWords(account_id)>=10 && myDbHelper.GetCountFindPismaniyeWords(account_id) <=14)
        {
        map.put("img", String.valueOf(R.drawable.ratingbar3));
        } 
        
        else if ( myDbHelper.GetCountFindPismaniyeWords(account_id)>=15 && myDbHelper.GetCountFindPismaniyeWords(account_id) <=19)
        {
        map.put("img", String.valueOf(R.drawable.ratingbar4));
        } 
        
        else if ( myDbHelper.GetCountFindPismaniyeWords(account_id)>=20 && myDbHelper.GetCountFindPismaniyeWords(account_id) <=24)
        {
        map.put("img", String.valueOf(R.drawable.ratingbar5));
        }
        
        else if ( myDbHelper.GetCountFindPismaniyeWords(account_id)>=25 && myDbHelper.GetCountFindPismaniyeWords(account_id) <=29)
        {
        map.put("img", String.valueOf(R.drawable.ratingbar6));
        }
        
        else if ( myDbHelper.GetCountFindPismaniyeWords(account_id)>=30 && myDbHelper.GetCountFindPismaniyeWords(account_id) <=34)
        {
        map.put("img", String.valueOf(R.drawable.ratingbar7));
        }  
        
        else if ( myDbHelper.GetCountFindPismaniyeWords(account_id)>=35 && myDbHelper.GetCountFindPismaniyeWords(account_id) <=39)
        {
        map.put("img", String.valueOf(R.drawable.ratingbar8));
        } 
        
        else if ( myDbHelper.GetCountFindPismaniyeWords(account_id)>=40 && myDbHelper.GetCountFindPismaniyeWords(account_id) <=44)
        {
        map.put("img", String.valueOf(R.drawable.ratingbar9));
        } 
        
        else if ( myDbHelper.GetCountFindPismaniyeWords(account_id)>=45 && myDbHelper.GetCountFindPismaniyeWords(account_id) <=51)
        {
        map.put("img", String.valueOf(R.drawable.ratingbar10));
        } 
        
        else if ( myDbHelper.GetCountFindPismaniyeWords(account_id)>=52 )
        {
        map.put("img", String.valueOf(R.drawable.ratingbar11));
        } 
        }
        
        
        map.put("dessert_score","Puan:"+ Long.toString(myDbHelper.GetPismaniyeScore(account_id)));
        listItem.add(map);
 
        // Adding Kazandibi Information
        map = new HashMap<String, String>();
        map.put("title", values[3]);
        
        map.put("description",Long.toString(myDbHelper.GetCountFindKazandibiWords(account_id))  + "/" + Long.toString(myDbHelper.GetCountKazandibi()));
        
        if(kazandibi_counts != null)
        {
        if ( Integer.parseInt(kazandibi_counts)>=0 && Integer.parseInt(kazandibi_counts)<=6)
        { 
        	map.put("img", String.valueOf(R.drawable.ratingbar1));
        }
        
       else if ( Integer.parseInt(kazandibi_counts)>=7 && Integer.parseInt(kazandibi_counts)<=13)
        {
        	map.put("img", String.valueOf(R.drawable.ratingbar2));	
        } 
        
        else if ( Integer.parseInt(kazandibi_counts)>=14 && Integer.parseInt(kazandibi_counts)<=20)
        {
        	map.put("img", String.valueOf(R.drawable.ratingbar3));	
        } 
        
        else if ( Integer.parseInt(kazandibi_counts)>=21 && Integer.parseInt(kazandibi_counts)<=27)
        {
        	map.put("img", String.valueOf(R.drawable.ratingbar4));	
        } 
        
        else if ( Integer.parseInt(kazandibi_counts)>=28 && Integer.parseInt(kazandibi_counts)<=34)
        {
        	map.put("img", String.valueOf(R.drawable.ratingbar5));	
        } 
        
        else if ( Integer.parseInt(kazandibi_counts)>=35 && Integer.parseInt(kazandibi_counts)<=41)
        {
        	map.put("img", String.valueOf(R.drawable.ratingbar6));	
        } 
        
        else if ( Integer.parseInt(kazandibi_counts)>=42 && Integer.parseInt(kazandibi_counts)<=48)
        {
        	map.put("img", String.valueOf(R.drawable.ratingbar7));	
        } 
        
        else if ( Integer.parseInt(kazandibi_counts)>=49 && Integer.parseInt(kazandibi_counts)<=55)
        {
        	map.put("img", String.valueOf(R.drawable.ratingbar8));	
        } 
        
        else if ( Integer.parseInt(kazandibi_counts)>=56 && Integer.parseInt(kazandibi_counts)<=62)
        {
        	map.put("img", String.valueOf(R.drawable.ratingbar9));	
        } 
        
        else if ( Integer.parseInt(kazandibi_counts)>=63 && Integer.parseInt(kazandibi_counts)<=72)
        {
        	map.put("img", String.valueOf(R.drawable.ratingbar10));	
        } 
        
        else if ( Integer.parseInt(kazandibi_counts) == 73)
        {
        	map.put("img", String.valueOf(R.drawable.ratingbar11));	
        } 
           
        }
        
        else
        {
        if( myDbHelper.GetCountFindKazandibiWords(account_id)>=0 && myDbHelper.GetCountFindKazandibiWords(account_id)<=5)
        {
        map.put("img", String.valueOf(R.drawable.ratingbar1));
        }
        
       else if( myDbHelper.GetCountFindKazandibiWords(account_id)>=6 && myDbHelper.GetCountFindKazandibiWords(account_id)<=11)
        {
        map.put("img", String.valueOf(R.drawable.ratingbar2));
        } 
        
        else if( myDbHelper.GetCountFindKazandibiWords(account_id)>=12 && myDbHelper.GetCountFindKazandibiWords(account_id)<=17)
        {
        map.put("img", String.valueOf(R.drawable.ratingbar3));
        }
        
        else if( myDbHelper.GetCountFindKazandibiWords(account_id)>=18 && myDbHelper.GetCountFindKazandibiWords(account_id)<=23)
        {
        map.put("img", String.valueOf(R.drawable.ratingbar4));
        } 
        
        else if( myDbHelper.GetCountFindKazandibiWords(account_id)>=24 && myDbHelper.GetCountFindKazandibiWords(account_id)<=29)
        {
        map.put("img", String.valueOf(R.drawable.ratingbar5));
        } 
        
        else if( myDbHelper.GetCountFindKazandibiWords(account_id)>=30 && myDbHelper.GetCountFindKazandibiWords(account_id)<=35)
        {
        map.put("img", String.valueOf(R.drawable.ratingbar6));
        } 
        
        else if( myDbHelper.GetCountFindKazandibiWords(account_id)>=36 && myDbHelper.GetCountFindKazandibiWords(account_id)<=41)
        {
        map.put("img", String.valueOf(R.drawable.ratingbar7));
        } 
        
        else if( myDbHelper.GetCountFindKazandibiWords(account_id)>=42 && myDbHelper.GetCountFindKazandibiWords(account_id)<=47)
        {
        map.put("img", String.valueOf(R.drawable.ratingbar8));
        } 
        
        else if( myDbHelper.GetCountFindKazandibiWords(account_id)>=48 && myDbHelper.GetCountFindKazandibiWords(account_id)<=53)
        {
        map.put("img", String.valueOf(R.drawable.ratingbar9));
        } 
        
        else if( myDbHelper.GetCountFindKazandibiWords(account_id)>=54 && myDbHelper.GetCountFindKazandibiWords(account_id)<=60)
        {
        map.put("img", String.valueOf(R.drawable.ratingbar10));
        } 
        
        else if( myDbHelper.GetCountFindKazandibiWords(account_id) == 61)
        {
        map.put("img", String.valueOf(R.drawable.ratingbar11));
        } 
        
        }
        map.put("dessert_score","Puan:"+ Long.toString(myDbHelper.GetKazandibiScore(account_id)));
        listItem.add(map);
 
        
        SimpleAdapter mSchedule = new SimpleAdapter (this.getBaseContext(), listItem, R.layout.desserts,
               new String[] { "title","description","img","dessert_score"}, new int[] { R.id.title, R.id.description,R.id.img,R.id.dessert_score});
 
        
        Dessert_List.setAdapter(mSchedule);
        
        myDbHelper.close();
 
    }
}