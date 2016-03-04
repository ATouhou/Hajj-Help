package com.labbayak.etab;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.labbayak.R;
import com.labbayak.bean.UploadresultModel;
import com.labbayak.utils.ApiConstants;
import com.labbayak.utils.SharedPreferenceUtil;
import com.labbayak.utils.Trace;
import com.labbayak.utils.Utils;

public class UserRegistration_Activity extends Activity {

	EditText edituname, editemail, editpassportnum, editmobilenum, editcity,
			editprofession, editpassword, editconfirmpassword;

	private Spinner genderspinner, nationalityspinneer, CountrySpinner,
			stateSpinner;

	String UserName, EmailID, PassportNumber, GenderID, NationalityName,
			MobileNumber, CountryName, StateName, City, Password, UserType,
			Profesion;

	RequestQueue queue;
	String Url, WUrl, reg;

	String photoid, ImageBase;
	ImageView piligrimphoto;
	String Username;
	int Isvolunteer = 0;
	CheckBox volunteercheckbox;
	private UploadresultModel model;

	ArrayList<UploadresultModel> result;

	boolean processClick = true;

	String imgBase = null;
	LinearLayout btn_cancel, btn_save_details, volunteerLayout;
	private UserRegistration_Activity mContext = null;
	protected String TAG = "PiligrimRegistration_Activity";

	private byte[] bArray;
	private String base64;
	private int imageviewPos = 0;
	private ImageView imgview = null;

	// private LayoutInflater li = null;
	private int pos = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.userregistration_layout);
		mContext = this;
		initComponents();

		String[] Gender = { "Select", "Male", "Female", "Others" };
		ArrayAdapter<String> stringArrayAdapter1 = new ArrayAdapter<String>(
				this, android.R.layout.simple_spinner_dropdown_item, Gender);
		genderspinner.setAdapter(stringArrayAdapter1);

		String[] Nationality = { "Select", "Indian" ,"Other" };
		ArrayAdapter<String> stringArrayAdapter3 = new ArrayAdapter<String>(
				this, android.R.layout.simple_spinner_dropdown_item,
				Nationality);
		nationalityspinneer.setAdapter(stringArrayAdapter3);

		btn_cancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(mContext, MainActivity.class);

				startActivity(intent);

			}
		});

		btn_save_details.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				btn_save_details.setClickable(false);
				if (processClick) {

					int EmailID = 0;
					if (editemail.getText().toString().length() == 0
							|| (checkEmail(editemail.getText().toString()) == true)) {

						EmailID = 1;
					} else {
						EmailID = 0;

					}

					imgBase = SharedPreferenceUtil.getStringFromImageRef_SP(
							mContext, "Base" + imageviewPos, "");
					if (Utils.isOnline(mContext)) {
						SharedPreferenceUtil.saveIntInSP(mContext,
								ApiConstants.CHECKPOS, 0);

						if (edituname.getText().toString().length() > 0) {

							if (EmailID == 1) {
								if (editpassportnum.getText().toString()
										.length() > 0) {
									if (nationalityspinneer
											.getSelectedItemPosition() > 0) {
										if (editmobilenum.getText().toString()
												.length() > 0) {

											if (editpassword.getText()
													.toString().length() > 0) {
												if (editconfirmpassword
														.getText().toString()
														.length() > 0) {

													checkUser();

												} else {

													Utils.toastMessage(
															getResources()
																	.getString(
																			R.string.toastcpassword),
															mContext);
													btn_save_details
															.setClickable(true);
												}
											} else {

												Utils.toastMessage(
														getResources()
																.getString(
																		R.string.toastpassword),
														mContext);
												btn_save_details
														.setClickable(true);
											}

										} else {
											Utils.toastMessage(
													getResources()
															.getString(
																	R.string.toastmobileno),
													mContext);
											btn_save_details.setClickable(true);
										}

									} else {
										Utils.toastMessage(
												getResources()
														.getString(
																R.string.toastnationality),
												mContext);
										btn_save_details.setClickable(true);
									}

								} else {
									Utils.toastMessage(getResources()
											.getString(R.string.toastpassport),
											mContext);
									btn_save_details.setClickable(true);
								}

							} else {

								Utils.toastMessage(
										getResources().getString(
												R.string.toastemail), mContext);
								btn_save_details.setClickable(true);
							}
						} else {

							Utils.toastMessage(
									getResources().getString(
											R.string.toastusername), mContext);
							btn_save_details.setClickable(true);
						}

					} else {

						Utils.toastMessage(
								getResources().getString(
										R.string.connecttoInternet), mContext);

						btn_save_details.setClickable(true);
					}

				}
			}

			private void checkUser() {
				// TODO Auto-generated method stub

				// if (rcount == 0) {
				// if (processClick) {
				// if(farmerphoto.getVisibility() > 0){}

				imgBase = SharedPreferenceUtil.getStringFromImageRef_SP(
						mContext, "Base" + imageviewPos, "");

				String Name = edituname.getText().toString();
				String EmailID = editemail.getText().toString();

				String PassportNumber = editpassportnum.getText().toString();
				String MobileNumber = editmobilenum.getText().toString();

				String Password = editpassword.getText().toString();
				String ConfirmPassword = editconfirmpassword.getText()
						.toString();
				String City = editcity.getText().toString();
				String Profesion = editprofession.getText().toString();
				String UserType = "pilgrim";

				final JSONObject pilligrimProfile = new JSONObject();

				try {

					pilligrimProfile.put("UserName", Name);
					pilligrimProfile.put("EmailID", EmailID);
					pilligrimProfile.put("PassportNumber", PassportNumber);
					pilligrimProfile.put("GenderID", "M");
					pilligrimProfile.put("NationalityName", "Indian");
					pilligrimProfile.put("MobileNumber", MobileNumber);
					pilligrimProfile.put("CountryName", "India");
					pilligrimProfile.put("StateName", "Telangana");
					pilligrimProfile.put("City", City);
					pilligrimProfile.put("Password", Password);
					pilligrimProfile.put("ImageBase", imgBase);
					pilligrimProfile.put("IsVolunteer", Isvolunteer);
					pilligrimProfile.put("UserType", UserType);
					pilligrimProfile.put("Profesion", Profesion);
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				// String data= pilligrimProfile.toString();

				Url = "http://labbayak.begoniainfosys.in/api/signup?fields="
						+ pilligrimProfile;

				StringRequest stringRequest = new StringRequest(
						Request.Method.GET, Url,
						new Response.Listener<String>() {
							@Override
							public void onResponse(String response) {

								String resultstr = response.toString();
								Toast.makeText(getApplicationContext(),
										response, Toast.LENGTH_LONG).show();

							}
						}, new Response.ErrorListener() {
							@Override
							public void onErrorResponse(VolleyError error) {

								// Error handling
								 System.out.println("Something went wrong!");

							}
						});

				// Add the request to the queue
				Volley.newRequestQueue(mContext).add(stringRequest);

				// Toast.makeText(getApplicationContext(), stringRequest,
				// Toast.LENGTH_LONG).show();

			}
		});

		volunteercheckbox.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				if (volunteercheckbox.isChecked()) {

					Isvolunteer = 1;
				} else {

					Isvolunteer = 0;
				}

			}

		});
	}

	private void initComponents() {
		btn_save_details = (LinearLayout) findViewById(R.id.btn_save_details);
		btn_cancel = (LinearLayout) findViewById(R.id.btn_cancel);
		volunteerLayout = (LinearLayout) findViewById(R.id.volunteerLayout);

		editemail = (EditText) findViewById(R.id.editemail);
		editpassportnum = (EditText) findViewById(R.id.editpassportnum);
		editmobilenum = (EditText) findViewById(R.id.editmobilenum);
		editcity = (EditText) findViewById(R.id.editcity);
		editpassword = (EditText) findViewById(R.id.editpassword);
		editconfirmpassword = (EditText) findViewById(R.id.editconfirmpassword);
		editprofession = (EditText) findViewById(R.id.editprofession);

		edituname = (EditText) findViewById(R.id.edituname);
		genderspinner = (Spinner) findViewById(R.id.genderspinner);
		nationalityspinneer = (Spinner) findViewById(R.id.nationalityspinneer);
		CountrySpinner = (Spinner) findViewById(R.id.CountrySpinner);
		stateSpinner = (Spinner) findViewById(R.id.stateSpinner);

		volunteercheckbox = (CheckBox) findViewById(R.id.volunteercheckbox);

		imgview = (ImageView) findViewById(R.id.piligrimphoto);

		int position = 0;
		imgview.setTag(position);

		imgview.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				int position = (Integer) v.getTag();
				SharedPreferenceUtil.saveIntInSP(mContext, ApiConstants.POS,
						position);
				try {
					Utils.selectImage(mContext);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});

		// Clear Shared preferences of Imagepreferences
		SharedPreferenceUtil.clearimagepref(mContext);

	}

	private boolean checkEmail(String EmailID) {

		Pattern EMAIL_ADDRESS_PATTERN = Pattern
				.compile("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" + "\\@"
						+ "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" + "(" + "\\."
						+ "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+");

		return EMAIL_ADDRESS_PATTERN.matcher(EmailID).matches();

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Bitmap bitmap;
		try {
			final int THUMBNAIL_HEIGHT = 400;
			final int THUMBNAIL_WIDTH = 800;
			imageviewPos = SharedPreferenceUtil
					.getIntFromSP(mContext, "Pos", 0);
			if (resultCode == RESULT_OK) {
				if (requestCode == 1) {
					File f = new File(Environment.getExternalStorageDirectory()
							.toString());
					for (File temp : f.listFiles()) {
						if (temp.getName().equals("temp.jpg")) {
							f = temp;
							break;
						}
					}
					try {
						BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
						bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
								bitmapOptions);

						Float width = new Float(bitmap.getWidth());
						Float height = new Float(bitmap.getHeight());
						Float ratio = width / height;
						bitmap = Bitmap.createScaledBitmap(bitmap,
								(int) (THUMBNAIL_HEIGHT * ratio),
								THUMBNAIL_HEIGHT, false);
						int padding = (THUMBNAIL_WIDTH - bitmap.getWidth()) / 2;

						// Getting child of inflated view

						imgview.setImageBitmap(bitmap);

						// Saving Image Byte array in preferences
						ByteArrayOutputStream bos = new ByteArrayOutputStream();
						bitmap.compress(Bitmap.CompressFormat.PNG, 10, bos);
						bArray = bos.toByteArray();
						SharedPreferenceUtil.saveByteArray(mContext, "Byte"
								+ imageviewPos, bArray);

						// JOptionPane.showMessageDialog(null,sampleidTxt);

						String path = android.os.Environment
								.getExternalStorageDirectory() + File.separator;
						f.delete();
						OutputStream outFile = null;
						File file = new File(path, String.valueOf(System
								.currentTimeMillis()) + ".jpg");
						try {
							outFile = new FileOutputStream(file);
							bitmap.compress(Bitmap.CompressFormat.PNG, 100,
									outFile);
							outFile.flush();
							outFile.close();
							base64 = Utils.decodeFileToBase64(file
									.getAbsolutePath());
							if (base64 != null) {
								SharedPreferenceUtil
										.saveStringInImageRef_SP(mContext,
												"Base" + imageviewPos, base64);
							}
							Trace.d("Test", "Test ..Base 64.." + base64);
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						} catch (Exception e) {
							e.printStackTrace();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else if (requestCode == 2) {

					Uri selectedImage = data.getData();
					String[] filePath = { MediaStore.Images.Media.DATA };
					Cursor c = getContentResolver().query(selectedImage,
							filePath, null, null, null);
					c.moveToFirst();
					int columnIndex = c.getColumnIndex(filePath[0]);
					String picturePath = c.getString(columnIndex);
					c.close();
					Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));

					Float width = new Float(thumbnail.getWidth());
					Float height = new Float(thumbnail.getHeight());
					Float ratio = width / height;

					thumbnail = Bitmap.createScaledBitmap(thumbnail,
							(int) (THUMBNAIL_HEIGHT * ratio), THUMBNAIL_HEIGHT,
							false);

					Log.w("path of image from gallery......******************.........",
							picturePath + "");

					try {
						imgview.setImageBitmap(thumbnail);
						base64 = Utils.decodeFileToBase64(picturePath);
						if (base64 != null) {
							SharedPreferenceUtil.saveStringInImageRef_SP(
									mContext, "Base" + imageviewPos, base64);
						}
					} catch (Exception ex) {
						ex.printStackTrace();
					}

				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
