package tr.edu.khas.tatliavcisi;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper  extends SQLiteOpenHelper{
	
	// Database path and name
    private static String DB_PATH = "/data/data/tr.edu.khas.tatliavcisi/databases/";
    private static String DB_NAME = "TatliAvcisi.db";
    
    // Variables for using database
    private SQLiteDatabase myDataBase; 
    private final Context myContext;
   
    //Constructor Method
    public DatabaseHelper(Context context) {
 
    	super(context, DB_NAME, null, 1);
        this.myContext = context;
    }	
 
 
  //Creates a empty database on the system and rewrites it with your own database.
    public void createDataBase() throws IOException{
 
    	boolean dbExist = checkDataBase();
 
    	if(dbExist){
    		//do nothing - database already exist
    	}else{
 
    		//By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
        	this.getReadableDatabase();
 
        	try {
    			copyDataBase();
 
    		} catch (IOException e) {
 
        		throw new Error("Error copying database");
        	}
    	}
 
    }
 
      //Check if the database already exist to avoid re-copying the file each time you open the application.
      //return true if it exists, false if it doesn't
     
    private boolean checkDataBase(){
 
    	SQLiteDatabase checkDB = null;
 
    	try{
    		String myPath = DB_PATH + DB_NAME;
    		checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
 
    	}catch(SQLiteException e){
 
    		//database does't exist yet.
    	}
 
    	if(checkDB != null){
 
    		checkDB.close();
 
    	}
 
    	return checkDB != null ? true : false;
    }
 
    
    //Copies your database from your local assets-folder to the just created empty database in the
    //system folder, from where it can be accessed and handled.
    //This is done by transfering bytestream.
    
    private void copyDataBase() throws IOException{
 
    	//Open your local db as the input stream
    	InputStream myInput = myContext.getAssets().open(DB_NAME);
 
    	// Path to the just created empty db
    	String outFileName = DB_PATH + DB_NAME;
 
    	//Open the empty db as the output stream
    	OutputStream myOutput = new FileOutputStream(outFileName);
 
    	//transfer bytes from the inputfile to the outputfile
    	byte[] buffer = new byte[1024];
    	int length;
    	while ((length = myInput.read(buffer))>0){
    		myOutput.write(buffer, 0, length);
    	}
 
    	//Close the streams
    	myOutput.flush();
    	myOutput.close();
    	myInput.close();
 
    }
 
    // opening database
    public void openDataBase() throws SQLException{
 
    	//Open the database
        String myPath = DB_PATH + DB_NAME;
    	myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    }
 
    //closing Database
    @Override
	public synchronized void close() {
 
    	    if(myDataBase != null)
    		    myDataBase.close();
 
    	    super.close();
 
	}
 
    // Creating database - not used because of external database
	@Override
	public void onCreate(SQLiteDatabase db) {
 
	}
 
	// Upgrade database - not used
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			
	}
 
	// Getting All Accounts Name
	Cursor getAllAccounts()
	  {
	   this.close();
	   this.openDataBase();
	   SQLiteDatabase db=this.getReadableDatabase();
	   Cursor cur= db.query("accounts", new String[] {"name"},null, null, null, null, null);
	   return cur;
	  }
	
	// Getting All Desserts Name
	Cursor getAllDesserts(int account_id)
	{
		this.close();
		this.openDataBase();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cur = db.query("desserts", new String[] {"name"}, null,null,null,null,null);
		return cur;	
	}
	
	// Updating Account Name
	public void UpdateAccount(String newName,String Id)
	{
		this.close();
		this.openDataBase();
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues updateAccount = new ContentValues();
		updateAccount.put("name",newName);
		
		try{
			db.update("accounts", updateAccount, "_id=?", new String[] {Id});
		}
		catch(Exception e){
			Log.e("update",e.getMessage());
		}
		finally {
			
			this.close();
			db.close();
		}
			
	}
	
	// Updating Kemalpasa Score
	public void UpdateKemalpasaScore(String puan,String Id)
	{
		this.close();
		this.openDataBase();
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues updateScore = new ContentValues();
		updateScore.put("kemalpasa_score", puan);
		
		try{
			db.update("scoring", updateScore, "account_id=?", new String[] {Id});
		}
		catch(Exception e){
			Log.e("update",e.getMessage());
		}
		finally {
			
			this.close();
			db.close();
		}
		
	}
	
	// Updating Muhallebi Score
	public void UpdateMuhallebiScore(String puan,String Id)
	{
		this.close();
		this.openDataBase();
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues updateScore = new ContentValues();
		updateScore.put("muhallebi_score", puan);
		
		try{
			db.update("scoring", updateScore, "account_id=?", new String[] {Id});
		}
		catch(Exception e){
			Log.e("update",e.getMessage());
		}
		finally {
			
			this.close();
			db.close();
		}
	}
	
	// Updating Piþmaniye Score
	public void UpdatePismaniyeScore(String puan,String Id)
	{
		this.close();
		this.openDataBase();
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues updateScore = new ContentValues();
		updateScore.put("pismaniye_score", puan);
		
		try{
			db.update("scoring", updateScore, "account_id=?", new String[] {Id});
		}
		catch(Exception e){
			Log.e("update",e.getMessage());
		}
		finally {
			
			this.close();
			db.close();
		}
	}
	
	// Updating Kazandibi Score
	public void UpdateKazandibiScore(String puan,String Id)
	{
		this.close();
		this.openDataBase();
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues updateScore = new ContentValues();
		updateScore.put("kazandibi_score", puan);
		
		try{
			db.update("scoring", updateScore, "account_id=?", new String[] {Id});
		}
		catch(Exception e){
			Log.e("update",e.getMessage());
		}
		finally {
			
			this.close();
			db.close();
		}
		
	}
	
	// Updating Total Score
	public void UpdateTotalScore(String puan, String Id)
	{
		this.close();
		this.openDataBase();
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues updateScore = new ContentValues();
		updateScore.put("total_score", puan);
		
		try{
			db.update("scoring", updateScore, "account_id=?", new String[] {Id});
		}
		catch(Exception e){
			Log.e("update",e.getMessage());
		}
		finally {
			
			this.close();
			db.close();
		}
	}
	
	
	//Deleting Account
	public void DeleteAccount(String Id)
	{
		this.close();
		this.openDataBase();
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues updateAccount = new ContentValues();
		updateAccount.put("name","Boþ");
		ContentValues updateScore = new ContentValues();
		updateScore.put("kemalpasa_score",0);
		updateScore.put("kazandibi_score",0);
		updateScore.put("pismaniye_score",0);
		updateScore.put("muhallebi_score",0);
		updateScore.put("total_score",0);
		
		try{
			 db.update("accounts",updateAccount,"_id=?",new String[] {Id});
			 db.update("scoring",updateScore,"account_id=?",new String[] {Id});
			 
			 if(Integer.parseInt(Id) == 1)
				 db.delete("find_words_ac1",null,null);
			 else if(Integer.parseInt(Id) == 2)
				 db.delete("find_words_ac2",null,null);
			 else if(Integer.parseInt(Id) == 3)
				 db.delete("find_words_ac3",null,null);
			 else if(Integer.parseInt(Id) == 4)
				 db.delete("find_words_ac4",null,null);
			 else if(Integer.parseInt(Id) == 5)
				 db.delete("find_words_ac5",null,null);
		}
		
		catch(Exception e)
		{
			Log.e("delete",e.getMessage());
		}
		
		finally{
			
			this.close();
			db.close();
		}
	}
	
	
	// Total Word
	public long GetCountWords()
	{
		this.close();
		this.openDataBase();
		long count_result = 0;
		SQLiteDatabase db = this.getReadableDatabase();
		count_result = db.compileStatement("SELECT COUNT(*) FROM WORDS").simpleQueryForLong();
		this.close();
		return count_result;
	}
	
	
	// Total Finded Words About Account
	public long GetCountFindWords(int account_id)
	{
		this.close();
		this.openDataBase();
		long count_result=0;
		SQLiteDatabase db = this.getReadableDatabase();
		if(account_id == 1)
			count_result= db.compileStatement("SELECT COUNT(*) FROM FIND_WORDS_AC1").simpleQueryForLong();
		else if(account_id == 2)
			count_result= db.compileStatement("SELECT COUNT(*) FROM FIND_WORDS_AC2 ").simpleQueryForLong();
		else if(account_id == 3)
			count_result= db.compileStatement("SELECT COUNT(*) FROM FIND_WORDS_AC3 ").simpleQueryForLong();
		else if(account_id == 4)
			count_result= db.compileStatement("SELECT COUNT(*) FROM FIND_WORDS_AC4 " ).simpleQueryForLong();
		else if(account_id == 5)
			count_result= db.compileStatement("SELECT COUNT(*) FROM FIND_WORDS_AC5 ").simpleQueryForLong();
		return count_result;		
	}
	
	// Finded Kemalpasa Words
	public Cursor GetFindedWordsKemalpasa ( int account_id)
	{
		this.close();
		this.openDataBase();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cr = null;
		if(account_id == 1)
			cr= db.rawQuery("SELECT WORDNAME FROM FIND_WORDS_AC1 WHERE WORD_ID>0 AND WORD_ID<74",null);
		else if(account_id == 2)
			cr= db.rawQuery("SELECT WORDNAME FROM FIND_WORDS_AC2 WHERE WORD_ID>0 AND WORD_ID<74",null);
		else if(account_id == 3)
			cr= db.rawQuery("SELECT WORDNAME FROM FIND_WORDS_AC3 WHERE WORD_ID>0 AND WORD_ID<74",null);
		else if(account_id == 4)
			cr= db.rawQuery("SELECT WORDNAME FROM FIND_WORDS_AC4 WHERE WORD_ID>0 AND WORD_ID<74",null);
		else if(account_id == 5)
			cr= db.rawQuery("SELECT WORDNAME FROM FIND_WORDS_AC5 WHERE WORD_ID>0 AND WORD_ID<74",null);
		return cr;
	}
	
	// Finded Muhallebi Words
	public Cursor GetFindedWordsMuhallebi ( int account_id)
	{
		this.close();
		this.openDataBase();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cr = null;
		if(account_id == 1)
			cr= db.rawQuery("SELECT WORDNAME FROM FIND_WORDS_AC1 WHERE WORD_ID>=74 AND WORD_ID<=152",null);
		else if(account_id == 2)
			cr= db.rawQuery("SELECT WORDNAME FROM FIND_WORDS_AC2 WHERE WORD_ID>=74 AND WORD_ID<=152",null);
		else if(account_id == 3)
			cr= db.rawQuery("SELECT WORDNAME FROM FIND_WORDS_AC3 WHERE WORD_ID>=74 AND WORD_ID<=152",null);
		else if(account_id == 4)
			cr= db.rawQuery("SELECT WORDNAME FROM FIND_WORDS_AC4 WHERE WORD_ID>=74 AND WORD_ID<=152",null);
		else if(account_id == 5)
			cr= db.rawQuery("SELECT WORDNAME FROM FIND_WORDS_AC5 WHERE WORD_ID>=74 AND WORD_ID<=152",null);
		return cr;
	}
	
	// Finded Pismaniye Words
	public Cursor GetFindedWordsPismaniye ( int account_id)
	{
		this.close();
		this.openDataBase();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cr = null;
		if(account_id == 1)
			cr= db.rawQuery("SELECT WORDNAME FROM FIND_WORDS_AC1 WHERE WORD_ID>=153 AND WORD_ID<=204", null);
		else if(account_id == 2)
			cr= db.rawQuery("SELECT WORDNAME FROM FIND_WORDS_AC2 WHERE WORD_ID>=153 AND WORD_ID<=204", null);
		else if(account_id == 3)
			cr= db.rawQuery("SELECT WORDNAME FROM FIND_WORDS_AC3 WHERE WORD_ID>=153 AND WORD_ID<=204", null);
		else if(account_id == 4)
			cr= db.rawQuery("SELECT WORDNAME FROM FIND_WORDS_AC4 WHERE WORD_ID>=153 AND WORD_ID<=204", null);
		else if(account_id == 5)
			cr= db.rawQuery("SELECT WORDNAME FROM FIND_WORDS_AC5 WHERE WORD_ID>=153 AND WORD_ID<=204", null);
		return cr;
	}
	
	// Finded Kazandibi Words
	public Cursor GetFindedWordsKazandibi( int account_id)
	{
		this.close();
		this.openDataBase();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cr = null;
		if(account_id == 1)
			cr= db.rawQuery("SELECT WORDNAME FROM FIND_WORDS_AC1 WHERE WORD_ID>=205 AND WORD_ID<=265",null);
		else if(account_id == 2)
			cr= db.rawQuery("SELECT WORDNAME FROM FIND_WORDS_AC2 WHERE WORD_ID>=205 AND WORD_ID<=265",null);
		else if(account_id == 3)
			cr= db.rawQuery("SELECT WORDNAME FROM FIND_WORDS_AC3 WHERE WORD_ID>=205 AND WORD_ID<=265",null);
		else if(account_id == 4)
			cr= db.rawQuery("SELECT WORDNAME FROM FIND_WORDS_AC4 WHERE WORD_ID>=205 AND WORD_ID<=265",null);
		else if(account_id == 5)
			cr= db.rawQuery("SELECT WORDNAME FROM FIND_WORDS_AC5 WHERE WORD_ID>=205 AND WORD_ID<=265",null);
		return cr;
	}
	
	// Finded Kemalpasa Words Count
	public long GetCountFindKemalpasaWords(int account_id)
	{
		this.close();
		this.openDataBase();
		long count_result=0;
		SQLiteDatabase db = this.getReadableDatabase();
		if(account_id == 1)
			count_result= db.compileStatement("SELECT COUNT(*) FROM FIND_WORDS_AC1 WHERE WORD_ID>0 AND WORD_ID<74").simpleQueryForLong();
		else if(account_id == 2)
			count_result= db.compileStatement("SELECT COUNT(*) FROM FIND_WORDS_AC2 WHERE WORD_ID>0 AND WORD_ID<74").simpleQueryForLong();
		else if(account_id == 3)
			count_result= db.compileStatement("SELECT COUNT(*) FROM FIND_WORDS_AC3 WHERE WORD_ID>0 AND WORD_ID<74").simpleQueryForLong();
		else if(account_id == 4)
			count_result= db.compileStatement("SELECT COUNT(*) FROM FIND_WORDS_AC4 WHERE WORD_ID>0 AND WORD_ID<74").simpleQueryForLong();
		else if(account_id == 5)
			count_result= db.compileStatement("SELECT COUNT(*) FROM FIND_WORDS_AC5 WHERE WORD_ID>0 AND WORD_ID<74").simpleQueryForLong();
		return count_result;		
	}
	
	// Finded Muhallebi Words Count
	public long GetCountFindMuhallebiWords(int account_id)
	{
		this.close();
		this.openDataBase();
		long count_result=0;
		SQLiteDatabase db = this.getReadableDatabase();
		if(account_id == 1)
			count_result= db.compileStatement("SELECT COUNT(*) FROM FIND_WORDS_AC1 WHERE WORD_ID>=74 AND WORD_ID<=152").simpleQueryForLong();
		else if(account_id == 2)
			count_result= db.compileStatement("SELECT COUNT(*) FROM FIND_WORDS_AC2 WHERE WORD_ID>=74 AND WORD_ID<=152").simpleQueryForLong();
		else if(account_id == 3)
			count_result= db.compileStatement("SELECT COUNT(*) FROM FIND_WORDS_AC3 WHERE WORD_ID>=74 AND WORD_ID<=152").simpleQueryForLong();
		else if(account_id == 4)
			count_result= db.compileStatement("SELECT COUNT(*) FROM FIND_WORDS_AC4 WHERE WORD_ID>=74 AND WORD_ID<=152").simpleQueryForLong();
		else if(account_id == 5)
			count_result= db.compileStatement("SELECT COUNT(*) FROM FIND_WORDS_AC5 WHERE WORD_ID>=74 AND WORD_ID<=152").simpleQueryForLong();
		return count_result;		
	}
		
	// Finded Pismaniye Words Count
	public long GetCountFindPismaniyeWords(int account_id)
	{
		this.close();
		this.openDataBase();
		long count_result=0;
		SQLiteDatabase db = this.getReadableDatabase();
		if(account_id == 1)
			count_result= db.compileStatement("SELECT COUNT(*) FROM FIND_WORDS_AC1 WHERE WORD_ID>=153 AND WORD_ID<=204").simpleQueryForLong();
		else if(account_id == 2)
			count_result= db.compileStatement("SELECT COUNT(*) FROM FIND_WORDS_AC2 WHERE WORD_ID>=153 AND WORD_ID<=204").simpleQueryForLong();
		else if(account_id == 3)
			count_result= db.compileStatement("SELECT COUNT(*) FROM FIND_WORDS_AC3 WHERE WORD_ID>=153 AND WORD_ID<=204").simpleQueryForLong();
		else if(account_id == 4)
			count_result= db.compileStatement("SELECT COUNT(*) FROM FIND_WORDS_AC4 WHERE WORD_ID>=153 AND WORD_ID<=204").simpleQueryForLong();
		else if(account_id == 5)
			count_result= db.compileStatement("SELECT COUNT(*) FROM FIND_WORDS_AC5 WHERE WORD_ID>=153 AND WORD_ID<=204").simpleQueryForLong();
		return count_result;		
	}	
	
	// Finded Kazandibi Words Count
	public long GetCountFindKazandibiWords(int account_id)
	{
		this.close();
		this.openDataBase();
		long count_result=0;
		SQLiteDatabase db = this.getReadableDatabase();
		if(account_id == 1)
			count_result= db.compileStatement("SELECT COUNT(*) FROM FIND_WORDS_AC1 WHERE WORD_ID>=205 AND WORD_ID<=265").simpleQueryForLong();
		else if(account_id == 2)
			count_result= db.compileStatement("SELECT COUNT(*) FROM FIND_WORDS_AC2 WHERE WORD_ID>=205 AND WORD_ID<=265").simpleQueryForLong();
		else if(account_id == 3)
			count_result= db.compileStatement("SELECT COUNT(*) FROM FIND_WORDS_AC3 WHERE WORD_ID>=205 AND WORD_ID<=265").simpleQueryForLong();
		else if(account_id == 4)
			count_result= db.compileStatement("SELECT COUNT(*) FROM FIND_WORDS_AC4 WHERE WORD_ID>=205 AND WORD_ID<=265").simpleQueryForLong();
		else if(account_id == 5)
			count_result= db.compileStatement("SELECT COUNT(*) FROM FIND_WORDS_AC5 WHERE WORD_ID>=205 AND WORD_ID<=265").simpleQueryForLong();
		return count_result;		
		}	
		
	
	// Adding found kemalpasa words
	public void InsertFindedWordKemalpasa ( int account_id , String word)
	{
		this.close();
		this.openDataBase();
		int son= 0;
		String meaning="";
		String word_id="";
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		
		if(account_id == 1)
			{	son = (int) db.compileStatement("SELECT COUNT(*) FROM FIND_WORDS_AC1").simpleQueryForLong();
				son = son + 1;
			}
		else if(account_id == 2)
		{	son = (int) db.compileStatement("SELECT COUNT(*) FROM FIND_WORDS_AC2").simpleQueryForLong();
			son = son + 1;
		}
		else if(account_id == 3)
		{	son = (int) db.compileStatement("SELECT COUNT(*) FROM FIND_WORDS_AC3").simpleQueryForLong();
			son = son + 1;
		}
		else if(account_id == 4)
		{	son = (int) db.compileStatement("SELECT COUNT(*) FROM FIND_WORDS_AC4").simpleQueryForLong();
			son = son + 1;
		}
		else if(account_id == 5)
		{	son = (int) db.compileStatement("SELECT COUNT(*) FROM FIND_WORDS_AC5").simpleQueryForLong();
			son = son + 1;
		}
		Cursor cr= db.rawQuery("SELECT MEANING FROM WORDS WHERE WORDNAME=? AND _ID>0 AND _ID<74", new String[] {word});
		Cursor cr2 = db.rawQuery("SELECT _ID FROM WORDS WHERE WORDNAME=? AND _ID>0 AND _ID<74", new String[] {word});
		
		while(cr.moveToNext())
		{
			meaning = cr.getString(cr.getColumnIndex("meaning"));
		}
		
		while(cr2.moveToNext())
		{
			word_id = cr2.getString(cr2.getColumnIndex("_id"));
		}
		
		contentValues.put("_ID", Integer.toString(son));
		contentValues.put("WORDNAME", word);
		contentValues.put("MEANING", meaning);
		contentValues.put("WORD_ID", word_id);
		
		ContentValues updateFind = new ContentValues();
	
		if(account_id == 1)
			{
				db.insert("FIND_WORDS_AC1", null, contentValues);
				updateFind.put("AC1_Find",1);
			}
		else if(account_id == 2)
		{
			db.insert("FIND_WORDS_AC2", null, contentValues);
			updateFind.put("AC2_Find",1);
		}
		else if(account_id == 3)
		{
			db.insert("FIND_WORDS_AC3", null, contentValues);
			updateFind.put("AC3_Find",1);
		}
		else if(account_id == 4)
		{
			db.insert("FIND_WORDS_AC4", null, contentValues);
			updateFind.put("AC4_Find",1);
		}
		else if(account_id == 5)
		{
			db.insert("FIND_WORDS_AC5", null, contentValues);
			updateFind.put("AC5_Find",1);
		}
		cr.close();
		cr2.close();
		db.close();
		
		SQLiteDatabase dbFind = this.getWritableDatabase();
		dbFind.update("words",updateFind,"wordname=?",new String[] {word});
		dbFind.close();
			
	}
	
	// Adding found muhallebi words
	public void InsertFindedWordMuhallebi ( int account_id , String word)
	{
		this.close();
		this.openDataBase();
		int son= 0;
		String meaning="";
		String word_id="";
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		
		if(account_id == 1)
		{	son = (int) db.compileStatement("SELECT COUNT(*) FROM FIND_WORDS_AC1").simpleQueryForLong();
			son = son + 1;
		}
		else if(account_id == 2)
		{	son = (int) db.compileStatement("SELECT COUNT(*) FROM FIND_WORDS_AC2").simpleQueryForLong();
			son = son + 1;
		}
		else if(account_id == 3)
		{	son = (int) db.compileStatement("SELECT COUNT(*) FROM FIND_WORDS_AC3").simpleQueryForLong();
			son = son + 1;
		}
		else if(account_id == 4)
		{	son = (int) db.compileStatement("SELECT COUNT(*) FROM FIND_WORDS_AC4").simpleQueryForLong();
			son = son + 1;
		}
		else if(account_id == 5)
		{	son = (int) db.compileStatement("SELECT COUNT(*) FROM FIND_WORDS_AC5").simpleQueryForLong();
			son = son + 1;
		}
		Cursor cr= db.rawQuery("SELECT MEANING FROM WORDS WHERE WORDNAME=? AND _ID>73 AND _ID<153", new String[] {word});
		Cursor cr2 = db.rawQuery("SELECT _ID FROM WORDS WHERE WORDNAME=? AND _ID>73 AND _ID<153", new String[] {word});
		
		while(cr.moveToNext())
		{
			meaning = cr.getString(cr.getColumnIndex("meaning"));
		}
		
		while(cr2.moveToNext())
		{
			word_id = cr2.getString(cr2.getColumnIndex("_id"));
		}
		
		contentValues.put("_ID", Integer.toString(son));
		contentValues.put("WORDNAME", word);
		contentValues.put("MEANING", meaning);
		contentValues.put("WORD_ID", word_id);
		
		
		ContentValues updateFind = new ContentValues();
		
		if(account_id == 1)
			{
				db.insert("FIND_WORDS_AC1", null, contentValues);
				updateFind.put("AC1_Find",1);
			}
		else if(account_id == 2)
		{
			db.insert("FIND_WORDS_AC2", null, contentValues);
			updateFind.put("AC2_Find",1);
		}
		else if(account_id == 3)
		{
			db.insert("FIND_WORDS_AC3", null, contentValues);
			updateFind.put("AC3_Find",1);
		}
		else if(account_id == 4)
		{
			db.insert("FIND_WORDS_AC4", null, contentValues);
			updateFind.put("AC4_Find",1);
		}
		else if(account_id == 5)
		{
			db.insert("FIND_WORDS_AC5", null, contentValues);
			updateFind.put("AC5_Find",1);
		}
		cr.close();
		cr2.close();
		db.close();
		
		SQLiteDatabase dbFind = this.getWritableDatabase();
		dbFind.update("words",updateFind,"wordname=?",new String[] {word});
		dbFind.close();
		
	}
	
	// Adding found pismaniye words
	public void InsertFindedWordPismaniye ( int account_id , String word)
	{
		this.close();
		this.openDataBase();
		int son= 0;
		String meaning="";
		String word_id="";
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		
		if(account_id == 1)
		{	son = (int) db.compileStatement("SELECT COUNT(*) FROM FIND_WORDS_AC1").simpleQueryForLong();
			son = son + 1;
		}
		else if(account_id == 2)
		{	son = (int) db.compileStatement("SELECT COUNT(*) FROM FIND_WORDS_AC2").simpleQueryForLong();
			son = son + 1;
		}
		else if(account_id == 3)
		{	son = (int) db.compileStatement("SELECT COUNT(*) FROM FIND_WORDS_AC3").simpleQueryForLong();
			son = son + 1;
		}
		else if(account_id == 4)
		{	son = (int) db.compileStatement("SELECT COUNT(*) FROM FIND_WORDS_AC4").simpleQueryForLong();
			son = son + 1;
		}
		else if(account_id == 5)
		{	son = (int) db.compileStatement("SELECT COUNT(*) FROM FIND_WORDS_AC5").simpleQueryForLong();
			son = son + 1;
		}
		
		Cursor cr= db.rawQuery("SELECT MEANING FROM WORDS WHERE WORDNAME=? AND _ID>152 AND _ID<205", new String[] {word});
		Cursor cr2 = db.rawQuery("SELECT _ID FROM WORDS WHERE WORDNAME=? AND _ID>152 AND _ID<205", new String[] {word});
		
		while(cr.moveToNext())
		{
			meaning = cr.getString(cr.getColumnIndex("meaning"));
		}
		
		while(cr2.moveToNext())
		{
			word_id = cr2.getString(cr2.getColumnIndex("_id"));
		}
		
		contentValues.put("_ID", Integer.toString(son));
		contentValues.put("WORDNAME", word);
		contentValues.put("MEANING", meaning);
		contentValues.put("WORD_ID", word_id);
		
		
		ContentValues updateFind = new ContentValues();
		
		if(account_id == 1)
			{
				db.insert("FIND_WORDS_AC1", null, contentValues);
				updateFind.put("AC1_Find",1);
			}
		else if(account_id == 2)
		{
			db.insert("FIND_WORDS_AC2", null, contentValues);
			updateFind.put("AC2_Find",1);
		}
		else if(account_id == 3)
		{
			db.insert("FIND_WORDS_AC3", null, contentValues);
			updateFind.put("AC3_Find",1);
		}
		else if(account_id == 4)
		{
			db.insert("FIND_WORDS_AC4", null, contentValues);
			updateFind.put("AC4_Find",1);
		}
		else if(account_id == 5)
		{
			db.insert("FIND_WORDS_AC5", null, contentValues);
			updateFind.put("AC5_Find",1);
		}
		cr.close();
		cr2.close();
		db.close();
		
		SQLiteDatabase dbFind = this.getWritableDatabase();
		dbFind.update("words",updateFind,"wordname=?",new String[] {word});
		dbFind.close();
	}
	
	// Adding found kazandibi words
	public void InsertFindedWordKazandibi ( int account_id , String word)
	{
		this.close();
		this.openDataBase();
		int son= 0;
		String meaning="";
		String word_id="";
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		
		if(account_id == 1)
		{	son = (int) db.compileStatement("SELECT COUNT(*) FROM FIND_WORDS_AC1").simpleQueryForLong();
			son = son + 1;
		}
		else if(account_id == 2)
		{	son = (int) db.compileStatement("SELECT COUNT(*) FROM FIND_WORDS_AC2").simpleQueryForLong();
			son = son + 1;
		}
		else if(account_id == 3)
		{	son = (int) db.compileStatement("SELECT COUNT(*) FROM FIND_WORDS_AC3").simpleQueryForLong();
			son = son + 1;
		}
		else if(account_id == 4)
		{	son = (int) db.compileStatement("SELECT COUNT(*) FROM FIND_WORDS_AC4").simpleQueryForLong();
			son = son + 1;
		}
		else if(account_id == 5)
		{	son = (int) db.compileStatement("SELECT COUNT(*) FROM FIND_WORDS_AC5").simpleQueryForLong();
			son = son + 1;
		}
		Cursor cr= db.rawQuery("SELECT MEANING FROM WORDS WHERE WORDNAME=? AND _ID>204 AND _ID<266", new String[] {word});
		Cursor cr2 = db.rawQuery("SELECT _ID FROM WORDS WHERE WORDNAME=? AND _ID>204 AND _ID<266", new String[] {word});
	
		while(cr.moveToNext())
		{
			meaning = cr.getString(cr.getColumnIndex("meaning"));
		}
		
		while(cr2.moveToNext())
		{
			word_id = cr2.getString(cr2.getColumnIndex("_id"));
		}
		
		contentValues.put("_ID", Integer.toString(son));
		contentValues.put("WORDNAME", word);
		contentValues.put("MEANING", meaning);
		contentValues.put("WORD_ID", word_id);
		
		ContentValues updateFind = new ContentValues();
		
		if(account_id == 1)
			{
				db.insert("FIND_WORDS_AC1", null, contentValues);
				updateFind.put("AC1_Find",1);
			}
		else if(account_id == 2)
		{
			db.insert("FIND_WORDS_AC2", null, contentValues);
			updateFind.put("AC2_Find",1);
		}
		else if(account_id == 3)
		{
			db.insert("FIND_WORDS_AC3", null, contentValues);
			updateFind.put("AC3_Find",1);
		}
		else if(account_id == 4)
		{
			db.insert("FIND_WORDS_AC4", null, contentValues);
			updateFind.put("AC4_Find",1);
		}
		else if(account_id == 5)
		{
			db.insert("FIND_WORDS_AC5", null, contentValues);
			updateFind.put("AC5_Find",1);
		}
		cr.close();
		cr2.close();
		db.close();
		
		SQLiteDatabase dbFind = this.getWritableDatabase();
		dbFind.update("words",updateFind,"wordname=?",new String[] {word});
		dbFind.close();
		
	}
	
	//Getting Kemalpasa Words
	public Cursor GetKemalpasaWords()
	{
		this.close();
		this.openDataBase();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cr= db.rawQuery("SELECT WORDNAME FROM WORDS WHERE _ID>=0 AND _ID<=73",null);
		return cr;	
	}
	
	//Getting Muhallebi Words
	public Cursor GetMuhallebiWords()
	{
		this.close();
		this.openDataBase();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cr= db.rawQuery("SELECT WORDNAME FROM WORDS WHERE _ID>=74 AND _ID<=152",null);
		return cr;	
	}
	
	//Getting Pismaniye Words
	public Cursor GetPismaniyeWords()
	{
		this.close();
		this.openDataBase();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cr= db.rawQuery("SELECT WORDNAME FROM WORDS WHERE _ID>=153 AND _ID<=204",null);
		return cr;	
	}
	
	//Getting Kazandibi Words
	public Cursor GetKazandibiWords()
	{
		this.close();
		this.openDataBase();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cr= db.rawQuery("SELECT WORDNAME FROM WORDS WHERE _ID>=205 AND _ID<=265",null);
		return cr;	
	}
	
	// Getting Total Score for specific account
	public long GetTotalScore ( int account_id )
	{
		this.close();
		this.openDataBase();
		SQLiteDatabase db = this.getReadableDatabase();
		long count_result = db.compileStatement("SELECT TOTAL_SCORE FROM SCORING WHERE ACCOUNT_ID=" + Integer.toString(account_id)).simpleQueryForLong();
		return count_result;
		
	}
	
	// Getting Kemalpasa Score for specific account
	public long GetKemalpasaScore(int account_id)
	{
		this.close();
		this.openDataBase();
		SQLiteDatabase db = this.getReadableDatabase();
		long count_result = db.compileStatement("SELECT KEMALPASA_SCORE FROM SCORING WHERE ACCOUNT_ID=" + Integer.toString(account_id)).simpleQueryForLong();
		return count_result;	
	}
	
	// Getting Muhallebi Score for specific account
	public long GetMuhallebiScore(int account_id)
	{
		this.close();
		this.openDataBase();
		SQLiteDatabase db = this.getReadableDatabase();
		long count_result = db.compileStatement("SELECT MUHALLEBI_SCORE FROM SCORING WHERE ACCOUNT_ID=" + Integer.toString(account_id)).simpleQueryForLong();
		return count_result;	
	}
	
	// Getting Pismaniye Score for specific account
	public long GetPismaniyeScore(int account_id)
	{
		this.close();
		this.openDataBase();
		SQLiteDatabase db = this.getReadableDatabase();
		long count_result = db.compileStatement("SELECT PISMANIYE_SCORE FROM SCORING WHERE ACCOUNT_ID=" + Integer.toString(account_id)).simpleQueryForLong();
		return count_result;	
	}
	
	// Getting Kazandibi Score for specific account
	public long GetKazandibiScore(int account_id)
	{
		this.close();
		this.openDataBase();
		SQLiteDatabase db = this.getReadableDatabase();
		long count_result = db.compileStatement("SELECT KAZANDIBI_SCORE FROM SCORING WHERE ACCOUNT_ID=" + Integer.toString(account_id)).simpleQueryForLong();
		return count_result;	
	}
	
	// Getting Kemalpasa Number
	public long GetCountKemalpasa()
	{
		this.close();
		this.openDataBase();
		long count_result = 0;
		SQLiteDatabase db = this.getReadableDatabase();
		count_result = db.compileStatement("SELECT COUNT(*) FROM WORDS WHERE DESSERT_ID=1").simpleQueryForLong();
		return count_result;	
	}
	
	// Getting Muhallebi Number
	public long GetCountMuhallebi()
	{
		this.close();
		this.openDataBase();
		long count_result = 0;
		SQLiteDatabase db = this.getReadableDatabase();
		count_result = db.compileStatement("SELECT COUNT(*) FROM WORDS WHERE DESSERT_ID=2").simpleQueryForLong();
		return count_result;	
	}
	
	// Getting Pismaniye Number
	public long GetCountPismaniye()
	{
		this.close();
		this.openDataBase();
		long count_result = 0;
		SQLiteDatabase db = this.getReadableDatabase();
		count_result = db.compileStatement("SELECT COUNT(*) FROM WORDS WHERE DESSERT_ID=3").simpleQueryForLong();
		return count_result;	
	}
	
	// Getting Kazandibi Number
	public long GetCountKazandibi()
	{
		this.close();
		this.openDataBase();
		long count_result = 0;
		SQLiteDatabase db = this.getReadableDatabase();
		count_result = db.compileStatement("SELECT COUNT(*) FROM WORDS WHERE DESSERT_ID=4").simpleQueryForLong();
		return count_result;	
	}
	
	// Getting The Word Meaning
	public String GetWordMeaning(String wordname)
	{
		this.close();
		this.openDataBase();
		String meaning = "";
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cr = db.rawQuery("SELECT MEANING FROM WORDS WHERE WORDNAME=?", new String[]{wordname});
		while(cr.moveToNext())
		{
			meaning = cr.getString(cr.getColumnIndex("meaning")) ;
		}
		cr.close();
		db.close();
		
		return meaning ;
	}
	
	// Getting The Word Meaning
	public String GetWordMeaning(String wordname,int account_id)
	{
		this.close();
		this.openDataBase();
		String meaning = "";
		wordname = wordname.trim();
		Cursor cr = null;
		SQLiteDatabase db = this.getReadableDatabase();
		if( account_id == 1)
			cr= db.rawQuery("SELECT MEANING FROM FIND_WORDS_AC1 WHERE WORDNAME=?",new String[]{wordname});
		
		if( account_id == 2)
			cr= db.rawQuery("SELECT MEANING FROM FIND_WORDS_AC2 WHERE WORDNAME=?",new String[]{wordname});
		
		if( account_id == 3)
			cr= db.rawQuery("SELECT MEANING FROM FIND_WORDS_AC3 WHERE WORDNAME=?",new String[]{wordname});
		
		if( account_id == 4)
			cr= db.rawQuery("SELECT MEANING FROM FIND_WORDS_AC4 WHERE WORDNAME=?",new String[]{wordname});
		
		if( account_id == 5)
			cr= db.rawQuery("SELECT MEANING FROM FIND_WORDS_AC5 WHERE WORDNAME=?",new String[]{wordname});
		
	
		while(cr.moveToNext())
		{
			meaning = cr.getString(cr.getColumnIndex("meaning")) ;
		}
	   
		cr.close();
		db.close();
		
		return meaning ;	
	}
	
	//Getting the words which are not found
	public Cursor GetNotFindedWords (int account_id , int dessert_id)
	{
		this.close();
		this.openDataBase();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cr= null;
		if(account_id == 1)
			 cr= db.rawQuery("SELECT WORDNAME FROM WORDS WHERE AC1_FIND=0 AND DESSERT_ID=?", new String[]{Integer.toString(dessert_id)});
		else if(account_id == 2)
			 cr= db.rawQuery("SELECT WORDNAME FROM WORDS WHERE AC2_FIND=0 AND DESSERT_ID=?", new String[]{Integer.toString(dessert_id)});
		else if(account_id == 3)
			 cr= db.rawQuery("SELECT WORDNAME FROM WORDS WHERE AC3_FIND=0 AND DESSERT_ID=?", new String[]{Integer.toString(dessert_id)});
		else if(account_id == 4)
			 cr= db.rawQuery("SELECT WORDNAME FROM WORDS WHERE AC4_FIND=0 AND DESSERT_ID=?", new String[]{Integer.toString(dessert_id)});
		else if(account_id == 5)
			 cr= db.rawQuery("SELECT WORDNAME FROM WORDS WHERE AC5_FIND=0 AND DESSERT_ID=?", new String[]{Integer.toString(dessert_id)});
		return cr;
	}	
}
