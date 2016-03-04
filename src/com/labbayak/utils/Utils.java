package com.labbayak.utils;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.labbayak.bean.MastersDataDetails;
import com.labbayak.bean.SpinnerBinder;

public class Utils {
	private static final String StartDate = null;

	public static boolean isOnline(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		}
		return false;
	}

	public static void hidestatusbar(Activity act) {
		act.requestWindowFeature(Window.FEATURE_NO_TITLE);
		act.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}

	public static void showAlert(Context CTX, String title, String MSG,
			final Boolean status) {
		AlertDialog.Builder alert = new AlertDialog.Builder(CTX);
		alert.setTitle(title);
		alert.setCancelable(false);
		alert.setMessage(MSG);
		alert.setPositiveButton(
				CTX.getResources().getString(android.R.string.ok),
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog1, int which) {
						dialog1.dismiss();
						if (status)
							System.exit(0);
					}
				});
		alert.setNegativeButton(
				CTX.getResources().getString(android.R.string.cancel),
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						arg0.dismiss();
					}
				});
		alert.show();
	}

	public static void toastMessage(String msg, Context context) {
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}

	public static void toastMessageLong(String msg, Context context) {
		final Toast toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
		toast.show();
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				toast.cancel();
			}
		}, 20000);
	}

	public static void hidesoftkeyboard(Activity mContext, IBinder iBinder) {
		InputMethodManager imm = (InputMethodManager) mContext
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(iBinder, 0);
	}

	public static void progressDialog(ProgressDialog pg, String msg) {
		pg.setMessage(msg);
		pg.setIndeterminate(true);
		pg.setCancelable(false);
		pg.show();
	}

	public static String getPresentdate(Context mContext) {
		String currentDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss",
				Locale.getDefault()).format(new Date());
		return currentDate;
	}

//	public static void selectProfilePic(final Activity mContext) {
//		final CharSequence[] items = mContext.getResources().getStringArray(
//				R.array.selectPic_array);
//		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
//		builder.setTitle(mContext.getString(R.string.addphoto));
//		builder.setItems(items, new DialogInterface.OnClickListener() {
//			@Override
//			public void onClick(DialogInterface dialog, int item) {
//				if (items[item].equals(mContext.getString(R.string.takePhoto))) {
//					Intent intent = new Intent(
//							android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//					File f = new File(android.os.Environment
//							.getExternalStorageDirectory(), "temp.jpg");
//					intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
//					mContext.startActivityForResult(intent, 1);
//				}/*
//				 * else if
//				 * (items[item].equals(mContext.getString(R.string.chooseFromGallery
//				 * ))) { Intent intent = new Intent();
//				 * intent.setType("image/*");
//				 * intent.setAction(Intent.ACTION_GET_CONTENT); // Intent intent
//				 * = new
//				 * Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images
//				 * .Media.EXTERNAL_CONTENT_URI);
//				 * mContext.startActivityForResult(intent, 2); }
//				 */
//				else if (items[item].equals(mContext.getString(R.string.cancel))) {
//					dialog.dismiss();// testing
//				}
//			}
//		});
//		builder.show();
//	}
//
//	public static void selectGalleryPic(final Activity mContext) {
//		final CharSequence[] items = mContext.getResources().getStringArray(
//				R.array.selectgalleryPic_array);
//		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
//		builder.setTitle(mContext.getString(R.string.addphoto));
//		builder.setItems(items, new DialogInterface.OnClickListener() {
//			@Override
//			public void onClick(DialogInterface dialog, int item) {
//				if (items[item].equals(mContext.getString(R.string.takePhoto))) {
//					Intent intent = new Intent(
//							android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//					File f = new File(android.os.Environment
//							.getExternalStorageDirectory(), "temp.jpg");
//					intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
//					mContext.startActivityForResult(intent, 1);
//
//				} else if (items[item].equals(mContext
//						.getString(R.string.chooseFromGallery))) {
//					Intent intent = new Intent();
//					intent.setType("image/*");
//					intent.setAction(Intent.ACTION_GET_CONTENT);
//					Intent intent1 = new Intent(
//							Intent.ACTION_PICK,
//							android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//					File f = new File(android.os.Environment
//							.getExternalStorageDirectory(), "temp.jpg");
//					intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
//					mContext.startActivityForResult(intent1, 2);
//				}
//
//				else if (items[item].equals(mContext.getString(R.string.cancel))) {
//					dialog.dismiss();
//				}
//			}
//		});
//		builder.show();
//	}

	public static void selectImage(final Activity mContext) {

		final CharSequence[] options = { "Take Photo", "Choose from Gallery",
				"Cancel" };

		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		builder.setTitle("Add Photo!");
		builder.setItems(options, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int item) {
				if (options[item].equals("Take Photo")) {
					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					File f = new File(android.os.Environment
							.getExternalStorageDirectory(), "temp.jpg");
					intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
					mContext.startActivityForResult(intent, 1);
				} else if (options[item].equals("Choose from Gallery")) {
					Intent intent = new Intent(
							Intent.ACTION_PICK,
							android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
					mContext.startActivityForResult(intent, 2);

				} else if (options[item].equals("Cancel")) {
					dialog.dismiss();
				}
			}
		});
		builder.show();
	}

	public static void selectUploadImage(final Activity mContext) {

		final CharSequence[] options = { "Take Photo", "Choose from Gallery",
				"Cancel" };

		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		builder.setTitle("Add Photo!");
		builder.setItems(options, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int item) {
				if (options[item].equals("Take Photo")) {
					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					File f = new File(android.os.Environment
							.getExternalStorageDirectory(), "temp.jpg");
					intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
					mContext.startActivityForResult(intent, 3);
				} else if (options[item].equals("Choose from Gallery")) {
					Intent intent = new Intent(
							Intent.ACTION_PICK,
							android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
					mContext.startActivityForResult(intent, 4);

				} else if (options[item].equals("Cancel")) {
					dialog.dismiss();
				}
			}
		});
		builder.show();
	}

	public static String decodeImageToBase64(String path) {
		Bitmap bitmap;
		BitmapFactory.Options options = null;
		options = new BitmapFactory.Options();
		options.inSampleSize = 1;

		bitmap = BitmapFactory.decodeFile(path, options);

		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		// Must compress the Image to reduce image size to make upload easy
		bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);

		byte[] byte_arr = stream.toByteArray();

		// Encode Image to String
		String encodedString = Base64.encodeToString(byte_arr, 0);

		return encodedString;
	}

	public static Bitmap getBitmapFromStream(InputStream input) {

		Bitmap myBitmap = BitmapFactory.decodeStream(input);
		return myBitmap;

	}

	public static byte[] decodeBitmapToByteArray(Bitmap bitmap) {

		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		// Must compress the Image to reduce image size to make upload easy
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
		byte[] byte_arr = stream.toByteArray();
		return byte_arr;
	}

	public static String decodeBitmapToBase64(Bitmap bitmap) {
		try {

			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			// Must compress the Image to reduce image size to make upload easy
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);

			byte[] byte_arr = stream.toByteArray();

			// Encode Image to String
			String encodedString = Base64.encodeToString(byte_arr, 0);
			return encodedString;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public static String decodeFileToBase64(String path) {
		Bitmap bitmap;
		BitmapFactory.Options o = new BitmapFactory.Options();
		o.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, o);
		// The new size we want to scale to
		final int REQUIRED_SIZE = 1024;
		// Find the correct scale value. It should be the power of 2.
		int width_tmp = o.outWidth, height_tmp = o.outHeight;
		int scale = 1;
		while (true) {
			if (width_tmp < REQUIRED_SIZE && height_tmp < REQUIRED_SIZE)
				break;
			width_tmp /= 2;
			height_tmp /= 2;
			scale *= 2;
		}
		BitmapFactory.Options o2 = new BitmapFactory.Options();
		o2.inSampleSize = scale;
		bitmap = BitmapFactory.decodeFile(path, o2);
		// Converting image to bytes..
		BitmapFactory.Options bfo;
		ByteArrayOutputStream bao;
		bfo = new BitmapFactory.Options();
		bfo.inSampleSize = 2;
		bao = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bao);
		byte[] ba = bao.toByteArray();
		String image_str = Base64.encodeToString(ba, Base64.DEFAULT);
		return image_str;
	}

	public static Bitmap decodeBase64StringToBitmap(String strBase64) {

		byte[] decodedString = Base64.decode(strBase64, Base64.DEFAULT);
		Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0,
				decodedString.length);

		return decodedByte;
	}

	// Displaying Gps Enable dialog
//	public static void showGpsEnabledialog(final Activity mContext) {
//		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
//		builder.setTitle(mContext.getResources().getString(
//				R.string.enableGpstitle));
//		builder.setCancelable(false);
//		builder.setMessage(mContext.getResources().getString(
//				R.string.enableGpsMessage));
//		builder.setPositiveButton(
//				mContext.getResources().getString(android.R.string.ok),
//				new DialogInterface.OnClickListener() {
//					public void onClick(DialogInterface dialog1, int which) {
//						dialog1.dismiss();
//						Intent in = new Intent(
//								Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//						mContext.startActivity(in);
//					}
//				});
//		builder.setNegativeButton(
//				mContext.getResources().getString(android.R.string.cancel),
//				new DialogInterface.OnClickListener() {
//					@Override
//					public void onClick(DialogInterface arg0, int arg1) {
//						arg0.dismiss();
//					}
//				});
//		Dialog d = builder.create();
//		d.show();
//	}

	public static void writetoFile(Context mContext, String imagebase) {

		try {
			String filename = "VASImageBase64.txt";
			File myfile = new File(Environment.getExternalStorageDirectory(),
					filename);
			if (!myfile.exists()) {
				myfile.createNewFile();
			}
			FileOutputStream fos;
			byte[] data = imagebase.getBytes();
			try {
				fos = new FileOutputStream(myfile);
				fos.write(data);
				fos.flush();
				fos.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getPresenttime(Activity mContext) {
		String currentTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS",
				Locale.getDefault()).format(new Date());
		return currentTime;
	}

	public static String getSampleCollectedTime(Activity mContext) {
		String currentTime = new SimpleDateFormat("yyyyMMdd-hhmmss",
				Locale.getDefault()).format(new Date());
		return currentTime;
	}

	public static String getSampleCollectedTimeNoSecs(Activity mContext) {
		String currentTime = new SimpleDateFormat("yyyyMMdd-hhmm",
				Locale.getDefault()).format(new Date());
		return currentTime;
	}

	public static void CopyStream(InputStream is, OutputStream os) {
		final int buffer_size = 1024;
		try {
			byte[] bytes = new byte[buffer_size];
			for (;;) {
				int count = is.read(bytes, 0, buffer_size);
				if (count == -1)
					break;
				os.write(bytes, 0, count);
			}
		} catch (Exception ex) {
		}
	}

	public static int getSpinnerPositionBymultiString(ArrayList<String> ary,
			String cstring) {
		int jc = 0;
		for (int j = 0; j < ary.size(); j++) {

			if ((ary.get(j).trim()).equalsIgnoreCase(cstring.trim())) {
				jc = j;
				break;
			}

		}

		return jc;
	}

	public static int[] intarrytoArray(List<Integer> src) {
		int[] res = new int[src.size()];
		for (int i = 0; i < src.size(); i++) {
			res[i] = src.get(i);
		}
		return res;
	}

	public static int getSpinnerPositionByString(String[] ary, String cstring) {
		int jc = 0;
		for (int j = 0; j < ary.length; j++) {

			if ((ary[j]).equals(cstring)) {
				jc = j;
				break;
			}

		}

		return jc;
	}

	public static int getSpinnerPositionByArrayAdapter(
			ArrayAdapter<String> ary, String cstring) {
		int jc = 0;
		for (int j = 0; j < ary.getCount(); j++) {

			if ((ary.getItem(j)).equalsIgnoreCase(cstring)) {
				jc = j;
				break;
			}

		}

		return jc;
	}

	public static int getSpinnerPositionByStringSpinner(Spinner spinner,
			String cstring) {
		int jc = 0;
		SpinnerBinder spinBind;
		for (int j = 0; j < spinner.getCount(); j++) {

			spinBind = (SpinnerBinder) spinner.getItemAtPosition(j);
			if ((spinBind.name).equals(cstring)) {
				jc = j;
				break;
			}

		}

		return jc;
	}

	public static int getArraySpinnerPositionByStringSpinner(
			ArrayList<SpinnerBinder> spinner, String cstring) {
		int jc = 0;

		for (int j = 0; j < spinner.size(); j++) {

			// spinBind = (SpinnerBinder) spinner.getItemAtPosition(j);
			if ((spinner.get(j).name).equals(cstring)) {
				jc = (int) spinner.get(j).id;
				break;
			}

		}

		return jc;
	}

	public static int getSpinnerPositionByID(Spinner spinner, int myId) {

		int index = 0;
		SpinnerBinder spinBind;
		// String myS = selDi.name;
		for (int i = 0; i < spinner.getCount(); i++) {
			spinBind = (SpinnerBinder) spinner.getItemAtPosition(i);

			if (spinBind.id == myId) {
				index = i;
				break;
			}
		}
		return index;
	}

//	public static int getSpinnerPositionByFarmerObjID(Spinner spinner, String myId) {
//
//		int index = 0;
//		FarmerDetails spinBind;
//		// String myS = selDi.name;
//		for (int i = 0; i < spinner.getCount(); i++) {
//			try
//			{
//			spinBind = (FarmerDetails) spinner.getItemAtPosition(i);
//
//			if (String.valueOf(spinBind.getFarmerID()).equalsIgnoreCase(myId)) {
//				index = i;
//				break;
//			}
//			}catch(Exception ex)
//			{
//				ex.printStackTrace();
//			}
//		}
//		return index;
//	}

//	public static int getSpinnerPositionByFarmObjID(Spinner spinner, String myId) {
//
//		int index = 0;
//		FarmDetails spinBind;
//		// String myS = selDi.name;
//		for (int i = 0; i < spinner.getCount(); i++) {
//			try
//			{
//			spinBind = (FarmDetails) spinner.getItemAtPosition(i);
//
//			if (String.valueOf(spinBind.getFarmID()).equalsIgnoreCase(myId)) {
//				index = i;
//				break;
//			}
//			}catch(Exception ex)
//			{
//				ex.printStackTrace();
//			}
//		}
//		return index;
//	}

	
	public static int getSpinnerMasterPositionByID(ArrayList<MastersDataDetails> spinner, int myId) {

		int index = 0;
		MastersDataDetails spinBind;
		// String myS = selDi.name;
		for (int i = 0; i < spinner.size(); i++) {
			spinBind = spinner.get(i);

			if (spinBind.MastersID.equals(myId)) {
				index = i;
				break;
			}
		}
		return index;
	}
	public static void exportDB() {

		try {
			// File sd = Environment.getExternalStorageDirectory();
			File sd = new File(Environment.getExternalStorageDirectory()
					+ "/VAS_Content");
			if (!sd.exists()) {
				sd.mkdirs();
			}
			File data = Environment.getDataDirectory();

			if (sd.canWrite()) {
				String currentDBPath = "//data//" + "com.vas.home"
						+ "//databases//" + "vasdatabase.db";
				String backupDBPath = "/" + "vasdatabase.db";
				File currentDB = new File(data, currentDBPath);
				File backupDB = new File(sd, backupDBPath);

				FileChannel src = new FileInputStream(currentDB).getChannel();
				FileChannel dst = new FileOutputStream(backupDB).getChannel();
				dst.transferFrom(src, 0, src.size());
				src.close();
				dst.close();
				/*
				 * Toast.makeText(getBaseContext(), backupDB.toString(),
				 * Toast.LENGTH_LONG).show();
				 */

			}
		} catch (Exception e) {

			/*
			 * Toast.makeText(getBaseContext(), e.toString(), Toast.LENGTH_LONG)
			 * .show();
			 */

		}
	}

	public static String encodeFileTobase64(String filepath) {
		String encodedString = null;

		try {

			File file = new File(filepath);

			FileInputStream fis = new FileInputStream(file);
			// System.out.println(file.exists() + "!!");
			// InputStream in = resource.openStream();
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			byte[] buf = new byte[1024];
			try {
				for (int readNum; (readNum = fis.read(buf)) != -1;) {
					bos.write(buf, 0, readNum); // no doubt here is 0
					// Writes len bytes from the specified byte array starting
					// at offset off to this byte array output stream.
					System.out.println("read " + readNum + " bytes,");
				}
			} catch (IOException ex) {
				// Logger.getLogger(genJpeg.class.getName()).log(Level.SEVERE,
				// null, ex);
			}
			byte[] bytes = bos.toByteArray();

			encodedString = Base64.encodeToString(bytes, 0);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return encodedString;
	}
	
	public static String getRadioButtonString(String[] arrlistactStats, String[] arrlistRbutnames, int selecdval) {
		String mRadioSelcname = null;
		String ms = null;
		for (int i = 0; i < arrlistactStats.length; i++) {
			ms = arrlistRbutnames[i];
			if ((ms.trim()).equalsIgnoreCase(arrlistactStats[selecdval - 1].trim())) {
				mRadioSelcname = ms.trim();
				//break;
				return mRadioSelcname;
			}
		}

		return mRadioSelcname;
	}
	

	public static String getSelectedRadioButton(String[] arrlistactStats, String[] arrlistRbutnames, String selecdname) {
		int mRadioSelcId = 0;
		for (int i = 0; i < arrlistactStats.length; i++) {
			if (arrlistactStats[i].trim().equalsIgnoreCase(selecdname.trim())) {
				mRadioSelcId = i+1;
			///	break;
			}
		}

		return String.valueOf(mRadioSelcId);
	}

	public static String changeDateDDMMYYYY(String sdate)
	{
		DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		DateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");

		DateFormat formatter2 = new SimpleDateFormat("MM/dd/yyyy");

		String StartDate = "";

		try {

			StartDate = formatter.format(
					(java.util.Date) formatter1.parse(sdate))
					.toString();// txtstartdate.getText().toString();

			// txtenddate.getText().toString();// endDate;
		} catch (ParseException e1) {

			try {

				StartDate = formatter.format(
						(java.util.Date) formatter2.parse(sdate)).toString();// txtstartdate.getText().toString();

				/*
				 * Date dadt = (java.util.Date)
				 * formatter2.parse(txtstartdate.getText() .toString());
				 */

				// txtenddate.getText().toString();// endDate;
			} catch (ParseException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();

			}

			// TODO Auto-generated catch block
			e1.printStackTrace();

		}
		
		return StartDate;
	}
	
	public static String changeDateMMDDYYYY(String sdate)
	{
		DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		DateFormat formatter1 = new SimpleDateFormat("MM-dd-yyyy");

		DateFormat formatter2 = new SimpleDateFormat("MM/dd/yyyy");

		String StartDate = "";

		try {

			StartDate = formatter1.format(
					(java.util.Date) formatter.parse(sdate))
					.toString();// txtstartdate.getText().toString();

			// txtenddate.getText().toString();// endDate;
		} catch (ParseException e1) {

//			try {
//
//				StartDate = formatter.format(
//						(java.util.Date) formatter2.parse(sdate)).toString();// txtstartdate.getText().toString();

				/*
				 * Date dadt = (java.util.Date)
				 * formatter2.parse(txtstartdate.getText() .toString());
				 */

				// txtenddate.getText().toString();// endDate;
//			} catch (ParseException e2) {
//				// TODO Auto-generated catch block
//				e2.printStackTrace();
//
//			}

			// TODO Auto-generated catch block
			e1.printStackTrace();

		}
		
		return StartDate;
	}

//	public Date String changeDateDDMMYYYY(String sdate)
//	{
//		DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
//		//DateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
//
//		DateFormat formatter2 = new SimpleDateFormat("MM/dd/yyyy");
//
//		String StartDate = "";
//
//		try {
//
//			StartDate = formatter.format(
//					(java.util.Date) formatter.parse(sdate))
//					.toString();// txtstartdate.getText().toString();
//
//			// txtenddate.getText().toString();// endDate;
//		} catch (ParseException e1) {
//
//			try {
//
//				StartDate = formatter.format(
//						(java.util.Date) formatter2.parse(sdate)).toString();// txtstartdate.getText().toString();
//
//				/*
//				 * Date dadt = (java.util.Date)
//				 * formatter2.parse(txtstartdate.getText() .toString());
//				 */
//
//				// txtenddate.getText().toString();// endDate;
//			} catch (ParseException e2) {
//				// TODO Auto-generated catch block
//				e2.printStackTrace();
//
//			}
//
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//
//		}
//		
//		return StartDate;
//	}
//	
//	
	public static String changeDateYYYYMMDD(String sdate)
	{
		DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		DateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");

		DateFormat formatter2 = new SimpleDateFormat("MM/dd/yyyy");

		String StartDate = "";

		try {

			StartDate = formatter1.format(
					(java.util.Date) formatter.parse(sdate))
					.toString();// txtstartdate.getText().toString();

			// txtenddate.getText().toString();// endDate;
		} catch (ParseException e1) {

			try {

				StartDate = formatter1.format(
						(java.util.Date) formatter2.parse(sdate)).toString();// txtstartdate.getText().toString();

				/*
				 * Date dadt = (java.util.Date)
				 * formatter2.parse(txtstartdate.getText() .toString());
				 */

				// txtenddate.getText().toString();// endDate;
			} catch (ParseException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();

			}

			// TODO Auto-generated catch block
			e1.printStackTrace();

		}
		
		return StartDate;
	}
	
	public static Date changeDateYYYYMMDDdate(String sdate)
	{
		DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		DateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");

		DateFormat formatter2 = new SimpleDateFormat("MM/dd/yyyy");

		
		Date StartDate = null;
		try {

			
			StartDate =(java.util.Date) formatter1.parse(formatter1.format((java.util.Date) formatter.parse(sdate)));	
			
			// txtenddate.getText().toString();// endDate;
		} catch (ParseException e1) {

			try {
				StartDate =(java.util.Date) formatter1.parse(formatter1.format(
						(java.util.Date) formatter2.parse(sdate)));	

// txtstartdate.getText().toString();

				/*
				 * Date dadt = (java.util.Date)
				 * formatter2.parse(txtstartdate.getText() .toString());
				 */

				// txtenddate.getText().toString();// endDate;
			} catch (ParseException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();

			}

			// TODO Auto-generated catch block
			e1.printStackTrace();

		}
		
		return StartDate;
	}
	public static Date changeDateYYYYMMDDtodate(String sdate)
	{
		//DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		DateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat formatter_2 = new SimpleDateFormat("MM/dd/yyyy");
		DateFormat formatter2 = new SimpleDateFormat("yyyy/MM/dd");

		
		Date StartDate = null;
		try {

			
			StartDate =(java.util.Date) formatter1.parse(sdate);	
			
			// txtenddate.getText().toString();// endDate;
		} catch (ParseException e1) {

			try {
				StartDate =(java.util.Date) formatter1.parse(formatter1.format(
						(java.util.Date) formatter_2.parse(sdate)));	

// txtstartdate.getText().toString();

				/*
				 * Date dadt = (java.util.Date)
				 * formatter2.parse(txtstartdate.getText() .toString());
				 */

				// txtenddate.getText().toString();// endDate;
			} catch (ParseException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();

			}

			// TODO Auto-generated catch block
			e1.printStackTrace();

		}
		
		return StartDate;
	}


}

