package tr.edu.khas.tatliavcisi;

import java.io.IOException;
import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.SQLException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SlidingDrawer;
import android.widget.Toast;
import android.app.AlertDialog;
import android.widget.ViewFlipper;

// Starting Activity
public class MainActivity extends Activity {

	// Variables - Deðiþkenler
	ViewFlipper flipper;
	ListView account;
	Boolean bc = false;
	final Context context = this;
	Button oynab, oynasag, oynasol, menub, menusol, menusag, yardimb,yardimsag, yardimsol;
	ImageView imgView;
	int i = 0;
	int flip_status = 0;
	Boolean click_status = false;
	baglantiAyarlari bagAyar=null;

	// Flipper Animation From Right - Saðdan Gelen Flipper Animasyonu
	private Animation inFromRightAnimation() {

		Animation inFromRight = new TranslateAnimation(

		Animation.RELATIVE_TO_PARENT, +1.0f, Animation.RELATIVE_TO_PARENT,
				0.0f,

				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f

		);

		inFromRight.setDuration(500);

		inFromRight.setInterpolator(new AccelerateInterpolator());

		return inFromRight;

	}

	// Flipper Animation To Left - Sola Giden Flipper Animasyonu
	private Animation outToLeftAnimation() {

		Animation outtoLeft = new TranslateAnimation(

		Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT,
				-1.0f,

				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f

		);

		outtoLeft.setDuration(500);

		outtoLeft.setInterpolator(new AccelerateInterpolator());

		return outtoLeft;

	}

	// Flipper Animation From Left - Soldan Gelen Flipper Animasyonu
	private Animation inFromLeftAnimation() {

		Animation inFromLeft = new TranslateAnimation(

		Animation.RELATIVE_TO_PARENT, -1.0f, Animation.RELATIVE_TO_PARENT,
				0.0f,

				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f

		);

		inFromLeft.setDuration(500);

		inFromLeft.setInterpolator(new AccelerateInterpolator());

		return inFromLeft;

	}

	// Flipper Animation To Right - Saða Giden Flipper Animasyonu
	private Animation outToRightAnimation() {

		Animation outtoRight = new TranslateAnimation(

		Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT,
				+1.0f,

				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f

		);

		outtoRight.setDuration(500);

		outtoRight.setInterpolator(new AccelerateInterpolator());

		return outtoRight;

	}

	// Activity Start Method - Aktiviteyi Çalýþtýrma Metodu
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		//Internet Connection Control - Ýnternet Kontrolü
		bagAyar=new baglantiAyarlari();
		registerReceiver(bagAyar, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

		// Starting walking cooker animation - Yürüyen ahçý animasyonu baþlar
		runAnimate();

		// Layout Components - Görüntü Bileþenleri

		flipper = (ViewFlipper) findViewById(R.id.flipper);

		// The Buttons which are used for sliding- drawer
		// Sliding Drawerýn açýlýp kapanmasý için kullanýlan butonlar
		final SlidingDrawer accounts = (SlidingDrawer) findViewById(R.id.slidingDrawer1);
		final SlidingDrawer editaccounts = (SlidingDrawer) findViewById(R.id.slidingDrawer2);
		final SlidingDrawer Slidemenu = (SlidingDrawer) findViewById(R.id.slidingDrawerMenu);
		final SlidingDrawer Slideyardim = (SlidingDrawer) findViewById(R.id.slidingDrawerYardim);

		final View handle = accounts.getHandle();
		final View handle2 = editaccounts.getHandle();
		final View Slidehandle = Slidemenu.getHandle();
		final View Slidehandle2 = Slideyardim.getHandle();

		// The Buttons which are used for moving Oyna Menu
		// Oyna Menüsünün çalýþmasý için kullanýlan butonlar
		oynab = (Button) findViewById(R.id.boyna);
		oynasag = (Button) findViewById(R.id.bsag);
		oynasol = (Button) findViewById(R.id.bsol);

		// The Buttons which are used for moving Menü Menu
		// Menü Menüsünün çalýþmasý için kullanýlan butonlar

		menub = (Button) findViewById(R.id.bmenu);
		menusol = (Button) findViewById(R.id.bmenusol);
		menusag = (Button) findViewById(R.id.bmenusag);

		// Yardim sag sol harektleri için xml le tanýmlýyoruz
		yardimb = (Button) findViewById(R.id.byardim);
		yardimsag = (Button) findViewById(R.id.byardimsag);
		yardimsol = (Button) findViewById(R.id.byardimsol);

		// Sliding Drawer Content
		// Sliding Drawer içinde kullanýlan bileþenler

		Button geridon = (Button) findViewById(R.id.geridon);
		final Button account1 = (Button) findViewById(R.id.account1);
		final Button account2 = (Button) findViewById(R.id.account2);
		final Button account3 = (Button) findViewById(R.id.account3);
		final Button account4 = (Button) findViewById(R.id.account4);
		final Button account5 = (Button) findViewById(R.id.account5);
		final Button edituser = (Button) findViewById(R.id.useredit);
		final Button editaccount1 = (Button) findViewById(R.id.editaccount1);
		final Button editaccount2 = (Button) findViewById(R.id.editaccount2);
		final Button editaccount3 = (Button) findViewById(R.id.editaccount3);
		final Button editaccount4 = (Button) findViewById(R.id.editaccount4);
		final Button editaccount5 = (Button) findViewById(R.id.editaccount5);
		Button editgeridon = (Button) findViewById(R.id.editgeridon);
		Button geridonMenu = (Button) findViewById(R.id.geridonMenu);
		Button geridonYardim = (Button) findViewById(R.id.geridonyardim);
		Button tatliavcisitanim = (Button) findViewById(R.id.tanimyardim);
		Button tatliavcisiicerik = (Button) findViewById(R.id.icerikyardim);
		Button tatliavcisikelimeler = (Button) findViewById(R.id.kelimeyardim);
		Button tatliavcisidereceler = (Button) findViewById(R.id.dereceyardim);
		Button tatliavcisipuanlama = (Button) findViewById(R.id.puanlamayardim);

		// Create new instance of DatabaseHelper class
		// DatabaseHelper sýnýfýndan yeni bir örnek yaratýlýr

		final DatabaseHelper myDbHelper = new DatabaseHelper(this);

		// Create database
		// Bu örnek üzerinden oluþan Database 'in yaratýlmasý / açýlmasý

		try {
			myDbHelper.createDataBase();

		} catch (IOException ioe) {
			throw new Error("Unable to create database");
		}

		// Open database - Veritabaný açýlýr
		try {
			myDbHelper.openDataBase();
		} catch (SQLException sqle) {
			throw sqle;
		}

		// Getting All Accounts
		// Tüm accountlarý çaðýrmak
		Cursor cursor = myDbHelper.getAllAccounts();
		startManagingCursor(cursor);

		String[] values = new String[5];
		int i = 0;

		while (cursor.moveToNext()) {
			values[i] = cursor.getString(cursor.getColumnIndex("name"));
			i++;
		}

		cursor.close();

		// Setting values to buttons
		// Butonlara o anki kullanýcý isimlerini vermek
		account1.setText(values[0]);
		account2.setText(values[1]);
		account3.setText(values[2]);
		account4.setText(values[3]);
		account5.setText(values[4]);

		editaccount1.setText(values[0]);
		editaccount2.setText(values[1]);
		editaccount3.setText(values[2]);
		editaccount4.setText(values[3]);
		editaccount5.setText(values[4]);

		// Closing the database - Veritabaný kapatýlýr
		myDbHelper.close();
		
		// Calling the "Boþ" string from string.xml
		final String string = getString(R.string.bos);

		// Check the edit user button for editting ; if all account buttons are
		// Boþ, edit user will be disable
		if (account1.getText().toString().compareTo(string) == 0
				&& account2.getText().toString().compareTo(string) == 0
				&& account3.getText().toString().compareTo(string) == 0
				&& account4.getText().toString().compareTo(string) == 0
				&& account5.getText().toString().compareTo(string) == 0) {
			edituser.setEnabled(false);
		}
		
		final String string2 = getString(R.string.oas);
		final String string3 = getString(R.string.oabb);
		final String string4 = getString(R.string.goa);
		final String string5 = getString(R.string.dialogButtonOK);
		final String string6 = getString(R.string.oa6kfo);
		final String string7 = getString(R.string.oabo);
		final String string9 = getString(R.string.bir);
		final String string10 = getString(R.string.iki);
		final String string11 = getString(R.string.uc);
		final String string12 = getString(R.string.dort);
		final String string13 = getString(R.string.bes);
		final String string14 = getString(R.string.tanimyardim);
		final String string15 = getString(R.string.tai);
		final String string16 = getString(R.string.tak);
		final String string17 = getString(R.string.tad);
		final String string18 = getString(R.string.tap);
		final String string19 = getString(R.string.useredit); 
		final String string20 = getString(R.string.od);
		final String string21 = getString(R.string.os);
		

		// Account1 process - Kullanýcý 1 iþlemleri
		account1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// Check the account value - Kullanýcý adý kontrolu
				if (account1.getText().toString().compareTo(string) == 0) {
					// adding new user
					final Dialog dialog = new Dialog(context);
					dialog.setContentView(R.layout.add_user_dialog);
					dialog.setTitle(string2);

					Button dialogButtonOK = (Button) dialog
							.findViewById(R.id.dialogButtonOK);
					Button dialogButtonBack = (Button) dialog
							.findViewById(R.id.dialogButtonBack);

					// When Tamam Button of Dialog Is Clicked
					dialogButtonOK.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							EditText adduseret = (EditText) dialog
									.findViewById(R.id.adduseret);

							// if length is 0 == empty
							if (adduseret.getText().length() == 0) {
								// Alert
								new AlertDialog.Builder(MainActivity.this)
										.setTitle(string4)
										.setMessage(
												string3)
										.setPositiveButton(
												string5,
												new DialogInterface.OnClickListener() {
													public void onClick(
															DialogInterface dialog,
															int which) {
													}
												}).show();

							}

							// if length is bigger than 6
							else if (adduseret.getText().length() > 6) {
								// Alert
								new AlertDialog.Builder(MainActivity.this)
										.setTitle(string4)
										.setMessage(
												string6)
										.setPositiveButton(
												string5,
												new DialogInterface.OnClickListener() {
													public void onClick(
															DialogInterface dialog,
															int which) {
													}
												}).show();

							}

							// if username is boþ
							else if (adduseret.getText().toString()
									.compareTo(string) == 0) {
								// Alert
								new AlertDialog.Builder(MainActivity.this)
										.setTitle(string4)
										.setMessage(string7)
										.setPositiveButton(
												string5,
												new DialogInterface.OnClickListener() {
													public void onClick(
															DialogInterface dialog,
															int which) {
													}
												}).show();

							}

							// If everything is okey
							else {
								myDbHelper.UpdateAccount(adduseret.getText()
										.toString(), string9);
								account1.setText(adduseret.getText().toString());
								editaccount1.setText(adduseret.getText()
										.toString());
								edituser.setEnabled(true);
								editaccount1.setEnabled(true);
								dialog.dismiss();
							}
						}
					});

					dialog.show();

					// When Geri Dön Button of Dialog Is Clicked
					dialogButtonBack.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {

							dialog.dismiss();
						}

					});

				} else {

					// Go to Dessert Page

					Intent intent = new Intent(getApplicationContext(),
							DessertsActivity.class);
					Bundle bundle = new Bundle();
					bundle.putString("account_id", string9);
					intent.putExtras(bundle);
					startActivity(intent);
				}

			}
		});

		// Account2 process - Kullanýcý 2 iþlemleri
		account2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// Check the account value - Kullanýcý adý kontrolu
				if (account2.getText().toString().compareTo(string) == 0) {
					// adding new user
					final Dialog dialog = new Dialog(context);
					dialog.setContentView(R.layout.add_user_dialog);
					dialog.setTitle(string2);

					Button dialogButtonOK = (Button) dialog
							.findViewById(R.id.dialogButtonOK);
					Button dialogButtonBack = (Button) dialog
							.findViewById(R.id.dialogButtonBack);

					// When Tamam Button of Dialog Is Clicked
					dialogButtonOK.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							EditText adduseret = (EditText) dialog
									.findViewById(R.id.adduseret);

							// if length is 0 == empty
							if (adduseret.getText().length() == 0) {
								// Alert
								new AlertDialog.Builder(MainActivity.this)
										.setTitle(string4)
										.setMessage(
												string3)
										.setPositiveButton(
												string5,
												new DialogInterface.OnClickListener() {
													public void onClick(
															DialogInterface dialog,
															int which) {

													}
												}).show();

							}

							// if length is bigger than 6
							else if (adduseret.getText().length() > 6) {
								// Alert
								new AlertDialog.Builder(MainActivity.this)
										.setTitle(string4)
										.setMessage(
												string6)
										.setPositiveButton(
												string5,
												new DialogInterface.OnClickListener() {
													public void onClick(
															DialogInterface dialog,
															int which) {

													}
												}).show();

							}

							// if username is boþ
							else if (adduseret.getText().toString()
									.compareTo(string) == 0) {
								// Alert
								new AlertDialog.Builder(MainActivity.this)
										.setTitle(string4)
										.setMessage(string7)
										.setPositiveButton(
												string5,
												new DialogInterface.OnClickListener() {
													public void onClick(
															DialogInterface dialog,
															int which) {

													}
												}).show();
							}

							// If everything is okey
							else {
								myDbHelper.UpdateAccount(adduseret.getText()
										.toString(), string10);
								account2.setText(adduseret.getText().toString());
								editaccount2.setText(adduseret.getText()
										.toString());
								edituser.setEnabled(true);
								editaccount2.setEnabled(true);
								dialog.dismiss();
							}
						}
					});

					dialog.show();

					// When Geri Dön Button of Dialog Is Clicked
					dialogButtonBack.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {

							dialog.dismiss();
						}

					});
				}

				else {

					// Go to Dessert Page

					Intent intent = new Intent(getApplicationContext(),
							DessertsActivity.class);
					Bundle bundle = new Bundle();
					bundle.putString("account_id", string10);
					intent.putExtras(bundle);
					startActivity(intent);
				}

			}
		});

		// Account3 process - Kullanýcý 3 iþlemleri
		account3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// Check the account value - Kullanýcý adý kontrolu
				if (account3.getText().toString().compareTo(string) == 0) {
					final Dialog dialog = new Dialog(context);
					dialog.setContentView(R.layout.add_user_dialog);
					dialog.setTitle(string2);

					Button dialogButtonOK = (Button) dialog
							.findViewById(R.id.dialogButtonOK);
					Button dialogButtonBack = (Button) dialog
							.findViewById(R.id.dialogButtonBack);

					// When Tamam Button of Dialog Is Clicked
					dialogButtonOK.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							EditText adduseret = (EditText) dialog
									.findViewById(R.id.adduseret);

							// if length is 0 == empty
							if (adduseret.getText().length() == 0) {
								// Alert
								new AlertDialog.Builder(MainActivity.this)
										.setTitle(string4)
										.setMessage(
												string3)
										.setPositiveButton(
												string5,
												new DialogInterface.OnClickListener() {
													public void onClick(
															DialogInterface dialog,
															int which) {

													}
												}).show();

							}

							// if length is bigger than 6
							else if (adduseret.getText().length() > 6) {
								// Alert
								new AlertDialog.Builder(MainActivity.this)
										.setTitle(string4)
										.setMessage(
												string6)
										.setPositiveButton(
												string5,
												new DialogInterface.OnClickListener() {
													public void onClick(
															DialogInterface dialog,
															int which) {

													}
												}).show();

							}

							// if username is boþ
							else if (adduseret.getText().toString()
									.compareTo(string) == 0) {
								// Alert
								new AlertDialog.Builder(MainActivity.this)
										.setTitle(string4)
										.setMessage(string7)
										.setPositiveButton(
												string5,
												new DialogInterface.OnClickListener() {
													public void onClick(
															DialogInterface dialog,
															int which) {

													}
												}).show();

							}

							// Everything is okey
							else {
								myDbHelper.UpdateAccount(adduseret.getText()
										.toString(), string11);
								account3.setText(adduseret.getText().toString());
								editaccount3.setText(adduseret.getText()
										.toString());
								edituser.setEnabled(true);
								editaccount3.setEnabled(true);
								dialog.dismiss();
							}
						}
					});

					dialog.show();

					// When Geri Dön Button of Dialog Is Clicked
					dialogButtonBack.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {

							dialog.dismiss();
						}

					});

				} else {

					// Go to Dessert Page
					Intent intent = new Intent(getApplicationContext(),
							DessertsActivity.class);
					Bundle bundle = new Bundle();
					bundle.putString("account_id", string11);
					intent.putExtras(bundle);
					startActivity(intent);

				}

			}
		});

		// Account4 process - Kullanýcý 4 iþlemleri
		account4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// Check the account value - Kullanýcý adý kontrolu
				if (account4.getText().toString().compareTo(string) == 0) {
					final Dialog dialog = new Dialog(context);
					dialog.setContentView(R.layout.add_user_dialog);
					dialog.setTitle(string2);

					Button dialogButtonOK = (Button) dialog
							.findViewById(R.id.dialogButtonOK);
					Button dialogButtonBack = (Button) dialog
							.findViewById(R.id.dialogButtonBack);

					// When Tamam Button of Dialog Is Clicked
					dialogButtonOK.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							EditText adduseret = (EditText) dialog
									.findViewById(R.id.adduseret);

							// if length is 0 == empty
							if (adduseret.getText().length() == 0) {
								// Alert
								new AlertDialog.Builder(MainActivity.this)
										.setTitle(string4)
										.setMessage(
												string3)
										.setPositiveButton(
												string5,
												new DialogInterface.OnClickListener() {
													public void onClick(
															DialogInterface dialog,
															int which) {

													}
												}).show();

							}

							// if length is bigger than 6
							else if (adduseret.getText().length() > 6) {
								// Alert
								new AlertDialog.Builder(MainActivity.this)
										.setTitle(string4)
										.setMessage(
												string6)
										.setPositiveButton(
												string5,
												new DialogInterface.OnClickListener() {
													public void onClick(
															DialogInterface dialog,
															int which) {

													}
												}).show();

							}

							// if username is boþ
							else if (adduseret.getText().toString()
									.compareTo(string) == 0) {
								// Alert
								new AlertDialog.Builder(MainActivity.this)
										.setTitle(string4)
										.setMessage(string7)
										.setPositiveButton(
												string5,
												new DialogInterface.OnClickListener() {
													public void onClick(
															DialogInterface dialog,
															int which) {

													}
												}).show();

							}

							// Everything is okey
							else {
								myDbHelper.UpdateAccount(adduseret.getText()
										.toString(), string12);
								account4.setText(adduseret.getText().toString());
								editaccount4.setText(adduseret.getText()
										.toString());
								edituser.setEnabled(true);
								editaccount4.setEnabled(true);
								dialog.dismiss();
							}
						}
					});

					dialog.show();

					// When Geri Dön Button of Dialog Is Clicked
					dialogButtonBack.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {

							dialog.dismiss();
						}

					});

				} else {
					// Go to Dessert Page
					Intent intent = new Intent(getApplicationContext(),
							DessertsActivity.class);
					Bundle bundle = new Bundle();
					bundle.putString("account_id", string12);
					intent.putExtras(bundle);
					startActivity(intent);
				}
			}
		});

		// Account5 process - Kullanýcý 5 iþlemleri
		account5.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// Check the account value - Kullanýcý adý kontrolu
				if (account5.getText().toString().compareTo(string) == 0) {
					final Dialog dialog = new Dialog(context);
					dialog.setContentView(R.layout.add_user_dialog);
					dialog.setTitle(string2);

					Button dialogButtonOK = (Button) dialog
							.findViewById(R.id.dialogButtonOK);
					Button dialogButtonBack = (Button) dialog
							.findViewById(R.id.dialogButtonBack);

					// When Tamam Button of Dialog Is Clicked
					dialogButtonOK.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							EditText adduseret = (EditText) dialog
									.findViewById(R.id.adduseret);

							// if length is 0 == empty
							if (adduseret.getText().length() == 0) {
								// Alert
								new AlertDialog.Builder(MainActivity.this)
										.setTitle(string4)
										.setMessage(
												string3)
										.setPositiveButton(
												string5,
												new DialogInterface.OnClickListener() {
													public void onClick(
															DialogInterface dialog,
															int which) {

													}
												}).show();

							}

							// if length is bigger than 6
							else if (adduseret.getText().length() > 6) {
								// Alert
								new AlertDialog.Builder(MainActivity.this)
										.setTitle(string4)
										.setMessage(
												string6)
										.setPositiveButton(
												string5,
												new DialogInterface.OnClickListener() {
													public void onClick(
															DialogInterface dialog,
															int which) {

													}
												}).show();

							}

							// if username is boþ
							else if (adduseret.getText().toString()
									.compareTo(string) == 0) {
								// Alert
								new AlertDialog.Builder(MainActivity.this)
										.setTitle(string4)
										.setMessage(string7)
										.setPositiveButton(
												string5,
												new DialogInterface.OnClickListener() {
													public void onClick(
															DialogInterface dialog,
															int which) {

													}
												}).show();

							}

							// Everything is okey
							else {
								myDbHelper.UpdateAccount(adduseret.getText()
										.toString(), string13);
								account5.setText(adduseret.getText().toString());
								editaccount5.setText(adduseret.getText()
										.toString());
								edituser.setEnabled(true);
								editaccount5.setEnabled(true);
								dialog.dismiss();
							}
						}
					});

					dialog.show();

					// When Geri Dön Button of Dialog Is Clicked
					dialogButtonBack.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							dialog.dismiss();
						}

					});

				} else {

					// Go to Dessert Page
					Intent intent = new Intent(getApplicationContext(),
							DessertsActivity.class);
					Bundle bundle = new Bundle();
					bundle.putString("account_id", string13);
					intent.putExtras(bundle);
					startActivity(intent);

				}

			}
		});

		// When Geri Dön Button Of Accounts SlidingDrawer Is Clicked
		geridon.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View w) {

				editaccounts.close();
				accounts.close();
			}

		});

		// When Geri Dön Button Of Edit Player SlidingDrawer Is Clicked
		editgeridon.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				editaccounts.setVisibility(4);
				accounts.setVisibility(0);
				editaccounts.close();
			}

		});

		// When Edit User Button Is Clicked
		edituser.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				accounts.setVisibility(4);
				editaccounts.setVisibility(0);
				handle2.performClick();
			}

		});

		// When Geri Dön Button of Menu Content Is Clicked
		geridonMenu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Slidemenu.close();

			}
		});

		// When Geri Dön Button of Yardým Content Is Clicked
		geridonYardim.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Slideyardim.close();

			}
		});

		// When Tatlý Avcýsý Button of Yardým Is Clicked
		tatliavcisitanim.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				final Dialog dialog = new Dialog(context);
				dialog.setContentView(R.layout.tatliavcisi_dialog);
				dialog.setTitle(string14);
				dialog.show();

				Button dialogButtongeridon = (Button) dialog
						.findViewById(R.id.geridontatliavcisi);
				dialogButtongeridon.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

						dialog.dismiss();

					}
				});

			}
		});

		// When Ýçerik Button of Yardým Is Clicked
		tatliavcisiicerik.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				final Dialog dialog = new Dialog(context);
				dialog.setContentView(R.layout.icerik_dialog);
				dialog.setTitle(string15);
				dialog.show();

				Button dialogButtongeridon = (Button) dialog
						.findViewById(R.id.geridonicerik);
				dialogButtongeridon.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

						dialog.dismiss();

					}
				});

			}
		});

		// When Kelimeler Button of Yardým Is Clicked
		tatliavcisikelimeler.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				final Dialog dialog = new Dialog(context);
				dialog.setContentView(R.layout.kelimeler_dialog);
				dialog.setTitle(string16);
				dialog.show();

				Button dialogButtongeridon = (Button) dialog
						.findViewById(R.id.geridonkelimeler);
				dialogButtongeridon.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

						dialog.dismiss();

					}
				});

			}
		});

		// When Dereceler Button of Yardým Is Clicked
		tatliavcisidereceler.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				final Dialog dialog = new Dialog(context);
				dialog.setContentView(R.layout.dereceler_dialog);
				dialog.setTitle(string17);
				dialog.show();

				Button dialogButtongeridon = (Button) dialog
						.findViewById(R.id.geridondereceler);
				dialogButtongeridon.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

						dialog.dismiss();

					}
				});

			}
		});

		// When Puanalama Button of Yardým Is Clicked
		tatliavcisipuanlama.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				final Dialog dialog = new Dialog(context);
				dialog.setContentView(R.layout.puanlama_dialog);
				dialog.setTitle(string18);
				dialog.show();

				Button dialogButtongeridon = (Button) dialog
						.findViewById(R.id.geridonpuanlama);
				dialogButtongeridon.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

						dialog.dismiss();

					}
				});

			}
		});

		// If EditAccount1 Button is Boþ, the button will be disable.
		if (editaccount1.getText().toString().compareTo(string) == 0) {
			editaccount1.setEnabled(false);
		}

		// If EditAccount2 Button is Boþ, the button will be disable.
		if (editaccount2.getText().toString().compareTo(string) == 0) {
			editaccount2.setEnabled(false);
		}

		// If EditAccount3 Button is Boþ, the button will be disable.
		if (editaccount3.getText().toString().compareTo(string) == 0) {
			editaccount3.setEnabled(false);
		}

		// If EditAccount4 Button is Boþ, the button will be disable.
		if (editaccount4.getText().toString().compareTo(string) == 0) {
			editaccount4.setEnabled(false);
		}

		// If EditAccount5 Button is Boþ, the button will be disable.
		if (editaccount5.getText().toString().compareTo(string) == 0) {
			editaccount5.setEnabled(false);
		}
		// When EditAccount1 Button of Oyuncu Düzenle Is Clicked
		editaccount1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				final Dialog dialog = new Dialog(context);
				dialog.setContentView(R.layout.edit_user_dialog);
				dialog.setTitle(string19);

				Button dialogButtonDuzenle = (Button) dialog
						.findViewById(R.id.dialogButtonDuzenle);
				Button dialogButtonEditBack = (Button) dialog
						.findViewById(R.id.dialogButtonEditBack);
				Button dialogButtonSil = (Button) dialog
						.findViewById(R.id.dialogButtonSil);
				final EditText edituseret = (EditText) dialog
						.findViewById(R.id.dialogEditUser);
				edituseret.setText(editaccount1.getText().toString());

				dialog.show();

				// If the user wants to change his/her username
				dialogButtonDuzenle.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

						if (edituseret.getText().length() > 6) {
							new AlertDialog.Builder(MainActivity.this)
									.setTitle(string4)
									.setMessage(
											string6)
									.setPositiveButton(
											string5,
											new DialogInterface.OnClickListener() {
												public void onClick(
														DialogInterface dialog,
														int which) {
												}
											}).show();

						}

						else {
							myDbHelper.UpdateAccount(edituseret.getText()
									.toString(), string9);
							editaccount1.setText(edituseret.getText()
									.toString());
							account1.setText(edituseret.getText().toString());
							Toast.makeText(getApplicationContext(),
									string20, Toast.LENGTH_SHORT)
									.show();
							dialog.dismiss();
						}

					}
				});

				// If the user wants to go back
				dialogButtonEditBack.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

						dialog.dismiss();

					}
				});

				// If the player wants to delete the user
				dialogButtonSil.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

						myDbHelper.DeleteAccount(string9);
						Toast.makeText(getApplicationContext(),
								string21, Toast.LENGTH_SHORT).show();
						dialog.dismiss();
						editaccount1.setText(string);
						account1.setText(string);
						editaccount1.setEnabled(false);
						edituser.setEnabled(false);
					}
				});

			}
		});

		// When EditAccount2 Button of Oyuncu Düzenle Is Clicked
		editaccount2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				final Dialog dialog = new Dialog(context);
				dialog.setContentView(R.layout.edit_user_dialog);
				dialog.setTitle(string19);

				Button dialogButtonDuzenle = (Button) dialog
						.findViewById(R.id.dialogButtonDuzenle);
				Button dialogButtonEditBack = (Button) dialog
						.findViewById(R.id.dialogButtonEditBack);
				Button dialogButtonSil = (Button) dialog
						.findViewById(R.id.dialogButtonSil);
				final EditText edituseret = (EditText) dialog
						.findViewById(R.id.dialogEditUser);
				edituseret.setText(editaccount2.getText().toString());

				dialog.show();

				// If the user wants to change his/her username
				dialogButtonDuzenle.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

						if (edituseret.getText().length() > 6) {
							new AlertDialog.Builder(MainActivity.this)
									.setTitle(string4)
									.setMessage(
											string6)
									.setPositiveButton(
											string5,
											new DialogInterface.OnClickListener() {
												public void onClick(
														DialogInterface dialog,
														int which) {
												}
											}).show();

						} else {
							myDbHelper.UpdateAccount(edituseret.getText()
									.toString(), string10);
							editaccount2.setText(edituseret.getText()
									.toString());
							account2.setText(edituseret.getText().toString());
							Toast.makeText(getApplicationContext(),
									string20, Toast.LENGTH_SHORT)
									.show();
							dialog.dismiss();
						}

					}
				});

				// If the user wants to go back
				dialogButtonEditBack.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

						dialog.dismiss();

					}
				});

				// If the player wants to delete the user
				dialogButtonSil.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

						myDbHelper.DeleteAccount(string10);
						Toast.makeText(getApplicationContext(),
								string21, Toast.LENGTH_SHORT).show();
						dialog.dismiss();
						editaccount2.setText(string);
						account2.setText(string);
						editaccount2.setEnabled(false);
						edituser.setEnabled(false);

					}
				});

			}
		});

		// When EditAccount3 Button of Oyuncu Düzenle Is Clicked
		editaccount3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				final Dialog dialog = new Dialog(context);
				dialog.setContentView(R.layout.edit_user_dialog);
				dialog.setTitle(string19);

				Button dialogButtonDuzenle = (Button) dialog
						.findViewById(R.id.dialogButtonDuzenle);
				Button dialogButtonEditBack = (Button) dialog
						.findViewById(R.id.dialogButtonEditBack);
				Button dialogButtonSil = (Button) dialog
						.findViewById(R.id.dialogButtonSil);
				final EditText edituseret = (EditText) dialog
						.findViewById(R.id.dialogEditUser);
				edituseret.setText(editaccount3.getText().toString());

				dialog.show();

				// If the user wants to change his/her username
				dialogButtonDuzenle.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

						if (edituseret.getText().length() > 6) {
							new AlertDialog.Builder(MainActivity.this)
									.setTitle(string4)
									.setMessage(
											string6)
									.setPositiveButton(
											string5,
											new DialogInterface.OnClickListener() {
												public void onClick(
														DialogInterface dialog,
														int which) {
												}
											}).show();

						} else {
							myDbHelper.UpdateAccount(edituseret.getText()
									.toString(), string11);
							editaccount3.setText(edituseret.getText()
									.toString());
							account3.setText(edituseret.getText().toString());
							Toast.makeText(getApplicationContext(),
									string20, Toast.LENGTH_SHORT)
									.show();
							dialog.dismiss();
						}

					}
				});

				// If the user wants to go back
				dialogButtonEditBack.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

						dialog.dismiss();

					}
				});

				// If the player wants to delete the user
				dialogButtonSil.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

						myDbHelper.DeleteAccount(string11);
						Toast.makeText(getApplicationContext(),
								string21, Toast.LENGTH_SHORT).show();
						dialog.dismiss();
						editaccount3.setText(string);
						account3.setText(string);
						editaccount3.setEnabled(false);
						edituser.setEnabled(false);

					}
				});

			}
		});

		// When EditAccount4 Button of Oyuncu Düzenle Is Clicked
		editaccount4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				final Dialog dialog = new Dialog(context);
				dialog.setContentView(R.layout.edit_user_dialog);
				dialog.setTitle(string19);

				Button dialogButtonDuzenle = (Button) dialog
						.findViewById(R.id.dialogButtonDuzenle);
				Button dialogButtonEditBack = (Button) dialog
						.findViewById(R.id.dialogButtonEditBack);
				Button dialogButtonSil = (Button) dialog
						.findViewById(R.id.dialogButtonSil);
				final EditText edituseret = (EditText) dialog
						.findViewById(R.id.dialogEditUser);
				edituseret.setText(editaccount4.getText().toString());

				dialog.show();

				// If the user wants to change his/her username
				dialogButtonDuzenle.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

						if (edituseret.getText().length() > 6) {
							new AlertDialog.Builder(MainActivity.this)
									.setTitle(string4)
									.setMessage(
											string6)
									.setPositiveButton(
											string5,
											new DialogInterface.OnClickListener() {
												public void onClick(
														DialogInterface dialog,
														int which) {
												}
											}).show();

						}

						else {
							myDbHelper.UpdateAccount(edituseret.getText()
									.toString(), string12);
							editaccount4.setText(edituseret.getText()
									.toString());
							account4.setText(edituseret.getText().toString());
							Toast.makeText(getApplicationContext(),
									string20, Toast.LENGTH_SHORT)
									.show();
							dialog.dismiss();
						}

					}
				});

				// If the user wants to go back
				dialogButtonEditBack.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

						dialog.dismiss();

					}
				});

				// If the player wants to delete the user
				dialogButtonSil.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

						myDbHelper.DeleteAccount(string12);
						Toast.makeText(getApplicationContext(),
								string21, Toast.LENGTH_SHORT).show();
						dialog.dismiss();
						editaccount4.setText(string);
						account4.setText(string);
						editaccount4.setEnabled(false);
						edituser.setEnabled(false);

					}
				});

			}
		});

		// When EditAccount5 Button of Oyuncu Düzenle Is Clicked
		editaccount5.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				final Dialog dialog = new Dialog(context);
				dialog.setContentView(R.layout.edit_user_dialog);
				dialog.setTitle(string19);

				Button dialogButtonDuzenle = (Button) dialog
						.findViewById(R.id.dialogButtonDuzenle);
				Button dialogButtonEditBack = (Button) dialog
						.findViewById(R.id.dialogButtonEditBack);
				Button dialogButtonSil = (Button) dialog
						.findViewById(R.id.dialogButtonSil);
				final EditText edituseret = (EditText) dialog
						.findViewById(R.id.dialogEditUser);
				edituseret.setText(editaccount5.getText().toString());

				dialog.show();

				// If the user wants to change his/her username
				dialogButtonDuzenle.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

						if (edituseret.getText().length() > 6) {
							new AlertDialog.Builder(MainActivity.this)
									.setTitle(string4)
									.setMessage(
											string6)
									.setPositiveButton(
											string5,
											new DialogInterface.OnClickListener() {
												public void onClick(
														DialogInterface dialog,
														int which) {
												}
											}).show();

						} else {
							myDbHelper.UpdateAccount(edituseret.getText()
									.toString(), string13);
							editaccount5.setText(edituseret.getText()
									.toString());
							account5.setText(edituseret.getText().toString());
							Toast.makeText(getApplicationContext(),
									string20, Toast.LENGTH_SHORT)
									.show();
							dialog.dismiss();
						}

					}
				});

				// If the user wants to go back
				dialogButtonEditBack.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

						dialog.dismiss();

					}
				});

				// If the player wants to delete the user
				dialogButtonSil.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

						myDbHelper.DeleteAccount(string13);
						Toast.makeText(getApplicationContext(),
								string21, Toast.LENGTH_SHORT).show();
						dialog.dismiss();
						editaccount5.setText(string);
						account5.setText(string);
						editaccount5.setEnabled(false);
						edituser.setEnabled(false);

					}
				});
			}
		});

		// Oyna Button is clicked
		oynab.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// Sliding Drawer is opened
				bc = handle.performClick();
			}
		});

		// Oyna sag button is clicked
		oynasag.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				flipper.setInAnimation(inFromRightAnimation());
				flipper.setOutAnimation(outToLeftAnimation());
				flipper.setDisplayedChild(1);
				flip_status = 1;
				click_status = true;
				runAnimate();

			}
		});

		// Oyna sol button is clicked
		oynasol.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				flipper.setInAnimation(inFromLeftAnimation());
				flipper.setOutAnimation(outToRightAnimation());
				flipper.setDisplayedChild(2);
				flip_status = 2;
				click_status = true;
				runAnimate();
			}
		});

		// Menu button is clicked
		menub.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// Sliding drawer is opened
				Slidehandle.performClick();

			}
		});

		// Menü sol button is clicked
		menusol.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				flipper.setInAnimation(inFromLeftAnimation());
				flipper.setOutAnimation(outToRightAnimation());
				flipper.setDisplayedChild(0);
				flip_status = 0;
				click_status = true;
				runAnimate();

			}
		});

		// Menü sað button is clicked
		menusag.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				flipper.setInAnimation(inFromRightAnimation());
				flipper.setOutAnimation(outToLeftAnimation());
				flipper.setDisplayedChild(2);
				flip_status = 2;
				click_status = true;
				runAnimate();
			}
		});

		// Yardým button is clicked
		yardimb.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// Sliding Drawer is opened
				Slidehandle2.performClick();
			}
		});

		// Yardým sað button is clicked
		yardimsag.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				flipper.setInAnimation(inFromRightAnimation());
				flipper.setOutAnimation(outToLeftAnimation());
				flipper.setDisplayedChild(0);
				flip_status = 0;
				click_status = true;
				runAnimate();
			}
		});

		// Yardým sol button is clicked
		yardimsol.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				flipper.setInAnimation(inFromLeftAnimation());
				flipper.setOutAnimation(outToRightAnimation());
				flipper.setDisplayedChild(1);
				flip_status = 1;
				click_status = true;
				runAnimate();
			}
		});

	}

	// Walking Cooker Animation - Yürüyen Ahçý Animasyonu
	public void runAnimate() {
		// Look at the flipper status and define the first image
		if (flip_status == 0)
			imgView = (ImageView) findViewById(R.id.anim1);
		else if (flip_status == 1)
			imgView = (ImageView) findViewById(R.id.anim2);
		else if (flip_status == 2)
			imgView = (ImageView) findViewById(R.id.anim3);

		// Speed of Cooker
		new CountDownTimer(50, 100) {

			int[] array = { R.drawable.giris_anim_01, R.drawable.giris_anim_02,
					R.drawable.giris_anim_03, R.drawable.giris_anim_04,
					R.drawable.giris_anim_05, R.drawable.giris_anim_06,
					R.drawable.giris_anim_07, R.drawable.giris_anim_08,
					R.drawable.giris_anim_09, R.drawable.giris_anim_10,
					R.drawable.giris_anim_11, R.drawable.giris_anim_12,
					R.drawable.giris_anim_13, R.drawable.giris_anim_14,
					R.drawable.giris_anim_15, R.drawable.giris_anim_16,
					R.drawable.giris_anim_17, R.drawable.giris_anim_18,
					R.drawable.giris_anim_19, R.drawable.giris_anim_20,
					R.drawable.giris_anim_21, R.drawable.giris_anim_22,
					R.drawable.giris_anim_23, R.drawable.giris_anim_24,
					R.drawable.giris_anim_25, R.drawable.giris_anim_26,
					R.drawable.giris_anim_27, R.drawable.giris_anim_28,
					R.drawable.giris_anim_29, R.drawable.giris_anim_30,
					R.drawable.giris_anim_31, R.drawable.giris_anim_32,
					R.drawable.giris_anim_33, R.drawable.giris_anim_34,
					R.drawable.giris_anim_35, R.drawable.giris_anim_36,
					R.drawable.giris_anim_37, R.drawable.giris_anim_38,
					R.drawable.giris_anim_39, R.drawable.giris_anim_40,
					R.drawable.giris_anim_41, R.drawable.giris_anim_42,
					R.drawable.giris_anim_43, R.drawable.giris_anim_44,
					R.drawable.giris_anim_45, R.drawable.giris_anim_46,
					R.drawable.giris_anim_47, R.drawable.giris_anim_48,
					R.drawable.giris_anim_49, R.drawable.giris_anim_50,
					R.drawable.giris_anim_51, R.drawable.giris_anim_52,
					R.drawable.giris_anim_53, R.drawable.giris_anim_54,
					R.drawable.giris_anim_55, R.drawable.giris_anim_56,
					R.drawable.giris_anim_57, R.drawable.giris_anim_58,
					R.drawable.giris_anim_59, R.drawable.giris_anim_60,
					R.drawable.giris_anim_61, R.drawable.giris_anim_62,
					R.drawable.giris_anim_63, R.drawable.giris_anim_64,
					R.drawable.giris_anim_65, R.drawable.giris_anim_66,
					R.drawable.giris_anim_67, R.drawable.giris_anim_68,
					R.drawable.giris_anim_69, R.drawable.giris_anim_70,
					R.drawable.giris_anim_71, R.drawable.giris_anim_72,
					R.drawable.giris_anim_73, R.drawable.giris_anim_74,
					R.drawable.giris_anim_75, R.drawable.giris_anim_76 };

			@Override
			public void onTick(long millisUntilFinished) {
			}

			@Override
			public void onFinish() {

				// It provides infinite animation.

				imgView.setImageDrawable(getResources().getDrawable(array[i]));
				i++;
				if (i == array.length)
					i = 0;

				if (click_status) {
					cancel();
					click_status = false;
				}

				else {
					start();
				}
			}
		}.start();

	}

	private class baglantiAyarlari extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			NetworkInfo nInfo = intent
					.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
			if (nInfo != null) {
				switch (nInfo.getState()) {
				case CONNECTED:
					break;
				case CONNECTING:
					
				case DISCONNECTED:
					new AlertDialog.Builder(MainActivity.this)
					.setTitle("UYARI !").setMessage("Ýnternet Ayarlarýnýzý Kontrol Ediniz!")
					.setPositiveButton("Tamam",
					new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
							}
					}).show();
					break;
				case DISCONNECTING:
					

				default:
					
				}
			}
		
	}

	}
	
	}
	