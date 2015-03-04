package camera;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.esgi.cap.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.ImageView;

public abstract class PictureManager extends Activity{
	
	final private static int CAPTURE_IMAGE = 2;
	static final String FILEFOLDER = "/CAP/";
	static String mCurrentPhotoPath;
	static ImageView mImageView;
	static String filePathName;
	
	public static void dispatchTakePictureIntent(Context ctx) {
		Activity ctxActivity = (Activity) ctx;
		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		if (takePictureIntent.resolveActivity(ctx.getPackageManager()) != null) {
			File photoFile = null;
			try {
				photoFile = createImageFile();
			} catch (IOException ex) {}
			if (photoFile != null) {
				takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
				Intent mediaScanIntent = new Intent( Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
				mediaScanIntent.setData(Uri.fromFile(photoFile));
				ctxActivity.sendBroadcast(mediaScanIntent);
				ctxActivity.startActivityForResult(takePictureIntent, CAPTURE_IMAGE);

			}
		}
	}
	
	private static File createImageFile() throws IOException {
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		String imageFileName = "CAP_" + timeStamp + "_";

		File folder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)+FILEFOLDER);
		if (!folder.exists()) {
			folder.mkdir();
		}

		File storageDir = Environment.getExternalStoragePublicDirectory(
				Environment.DIRECTORY_DCIM+FILEFOLDER);
		File image = File.createTempFile(
				imageFileName,  
				".jpg",         
				storageDir      
				);

		mCurrentPhotoPath = "file:" + image.getAbsolutePath();
		return image;
	}
	
	public static ImageView createPicture(Context ctx, Intent data){
		Bundle extras = data.getExtras();
		Bitmap imageBitmap = (Bitmap) extras.get("data");
		mImageView.setImageBitmap(imageBitmap);
		return mImageView;
	}
	
	public static String loadPict(Context ctx, Intent data){
		Activity ctxActivity = (Activity) ctx;
		Uri selectedImage = data.getData();
		filePathName = getRealPathFromURI(ctx, selectedImage);
		String[] filePathColumn = { MediaStore.Images.Media.DATA };

		Cursor cursor = ctx.getContentResolver().query(selectedImage,
				filePathColumn, null, null, null);
		cursor.moveToFirst();

		int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
		String picturePath = cursor.getString(columnIndex);
		cursor.close();

		ImageView imageView = (ImageView) ctxActivity.findViewById(R.id.pictureController);
		imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
		return filePathName;
	}
	
	public static String getRealPathFromURI(Context context, Uri contentUri) {
		Cursor cursor = null;
		try { 
			String[] proj = { MediaStore.Images.Media.DATA };
			cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
			int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			return cursor.getString(column_index);
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
	}
}
