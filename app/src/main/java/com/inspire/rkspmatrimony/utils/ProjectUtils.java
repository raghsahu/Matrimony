package com.inspire.rkspmatrimony.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.format.DateUtils;
import android.text.format.Time;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TimePicker;
import android.widget.Toast;


import com.inspire.rkspmatrimony.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by varun on 27/2/17.
 */
public class ProjectUtils {

    public static final String TAG = "ProjectUtility";
    private static AlertDialog dialog;
    private static Toast toast;
    private static ProgressDialog mProgressDialog;
    private static final String VERSION_UNAVAILABLE = "N/A";

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    //For Changing Status Bar Color if Device is above 5.0(Lollipop)
    public static void changeStatusBarColor(Activity activity) {

        Window window = activity.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(activity.getResources().getColor(R.color.black));
        }
    }


    public static void changeStatusBarColorNew(Activity activity, int color) {
        Window window = activity.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(activity.getResources().getColor(color));
        }
    }


    //For Progress Dialog
    public static ProgressDialog getProgressDialog(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(context.getResources().getString(R.string.please_wait));
        return progressDialog;
    }

    //For Long Period Toast Message
    public static void showToast(Context context, String message) {
        if (message == null) {
            return;
        }
        if (toast == null && context != null) {
            toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
        }
        if (toast != null) {
            toast.setText(message);
            toast.show();
        }
    }

    public static boolean containsOnlyNumbers(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i)))
                return false;
        }
        return true;
    }

    // For Alert Dialog in App
    public static Dialog createDialog(Context context, int titleId, int messageId,
                                      DialogInterface.OnClickListener positiveButtonListener,
                                      DialogInterface.OnClickListener negativeButtonListener) {
        Builder builder = new Builder(context);
        builder.setTitle(titleId);
        builder.setMessage(messageId);
        builder.setPositiveButton(R.string.ok, positiveButtonListener);
        builder.setNegativeButton(R.string.cancel, negativeButtonListener);

        return builder.create();
    }

    // For Alert Dialog on Custom View in App
    public static Dialog createDialog(Context context, int titleId, int messageId, View view,
                                      DialogInterface.OnClickListener positiveClickListener,
                                      DialogInterface.OnClickListener negativeClickListener) {

        Builder builder = new Builder(context);
        builder.setTitle(titleId);
        builder.setMessage(messageId);
        builder.setView(view);
        builder.setPositiveButton(R.string.ok, positiveClickListener);
        builder.setNegativeButton(R.string.cancel, negativeClickListener);

        return builder.create();
    }

    public static void showDialog(Context context, String title, String msg,
                                  DialogInterface.OnClickListener OK, boolean isCancelable) {

        if (title == null)
            title = context.getResources().getString(R.string.app_name);

        if (OK == null)
            OK = new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    hideDialog();
                }
            };

        if (dialog == null) {
            Builder builder = new Builder(context);
            builder.setTitle(title);
            builder.setMessage(msg);
            builder.setPositiveButton("OK", OK);
            dialog = builder.create();
            dialog.setCancelable(isCancelable);
        }

        try {
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Static method to show the dialog with custom message on it
     *
     * @param context      Context of the activity where to show the dialog
     * @param title        Title to be shown either custom or application name
     * @param msg          Custom message to be shown on dialog
     * @param OK           Overridden click listener for OK button in dialog
     * @param cancel       Overridden click listener for cancel button in dialog
     * @param isCancelable : Sets whether this dialog is cancelable with the BACK key.
     */
    public static void showDialog(Context context, String title, String msg,
                                  DialogInterface.OnClickListener OK,
                                  DialogInterface.OnClickListener cancel, boolean isCancelable) {

        if (title == null)
            title = context.getResources().getString(R.string.app_name);

        if (OK == null)
            OK = new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface paramDialogInterface,
                                    int paramInt) {
                    hideDialog();
                }
            };

        if (cancel == null)
            cancel = new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface paramDialogInterface,
                                    int paramInt) {
                    hideDialog();
                }
            };

        if (dialog == null) {
            Builder builder = new Builder(context);
            builder.setTitle(title);
            builder.setMessage(msg);
            builder.setPositiveButton("OK", OK);
            builder.setNegativeButton("Cancel", cancel);
            dialog = builder.create();
            dialog.setCancelable(isCancelable);
        }

        try {
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Static method to show the progress dialog.
     *
     * @param context      : Context of the activity where to show the dialog
     * @param isCancelable : Sets whether this dialog is cancelable with the BACK key.
     * @param message      : Message to be shwon on the progress dialog.
     * @return Object of progress dialog.
     */
    public static Dialog showProgressDialog(Context context,
                                            boolean isCancelable, String message) {

        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMessage(message);
        mProgressDialog.show();
        mProgressDialog.setCancelable(isCancelable);
        return mProgressDialog;
    }

    /**
     * Static method to pause the progress dialog.
     */
    public static void pauseProgressDialog() {
        try {
            if (mProgressDialog != null) {
                mProgressDialog.cancel();
                mProgressDialog.dismiss();
                mProgressDialog = null;
            }
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Static method to cancel the Dialog.
     */
    public static void cancelDialog() {

        try {
            if (dialog != null) {
                dialog.cancel();
                dialog.dismiss();
                dialog = null;
            }
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Static method to hide the dialog if visible
     */
    public static void hideDialog() {

        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog.cancel();
            dialog = null;
        }
    }

    /**
     * This method will create alert dialog
     *
     * @param context  Context of calling class
     * @param title    Title of the dialog to be shown
     * @param msg      Msg of the dialog to be shown
     * @param btnText  array of button texts
     * @param listener
     */
    public static void showAlertDialog(Context context, String title,
                                       String msg, String btnText,
                                       DialogInterface.OnClickListener listener) {

        if (listener == null)
            listener = new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface paramDialogInterface,
                                    int paramInt) {
                    paramDialogInterface.dismiss();
                    paramDialogInterface.dismiss();
                }
            };

        Builder builder = new Builder(context);
        builder.getContext().getResources().getColor(R.color.black);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setPositiveButton(btnText, listener);
        dialog = builder.create();
        dialog.setCancelable(false);
        try {
            dialog.show();
        } catch (Exception e) {
        }

    }

    public static void showAlertDialogWithCancel(Context context, String title,
                                                 String msg, String btnText,
                                                 DialogInterface.OnClickListener listener, String cancelTxt, DialogInterface.OnClickListener cancelListenr) {

        if (listener == null)
            listener = new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface paramDialogInterface,
                                    int paramInt) {
                    paramDialogInterface.dismiss();
                }
            };
        if (cancelListenr == null) {
            cancelListenr = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            };
        }

        Builder builder = new Builder(context);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setIcon(context.getResources().getDrawable(R.mipmap.ic_launcher));
        builder.setPositiveButton(btnText, listener);
        builder.setNegativeButton(cancelTxt, cancelListenr);
        dialog = builder.create();
        dialog.setCancelable(false);
        try {
            dialog.show();
        } catch (Exception e) {
        }

    }


    public static AlertDialog showCustomtDialog(Context context,
                                                String title, String msg, String[] btnText, int layout_id,
                                                DialogInterface.OnClickListener listener) {
        if (listener == null)
            listener = new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface paramDialogInterface,
                                    int paramInt) {
                    paramDialogInterface.dismiss();
                }
            };
        LayoutInflater factory = LayoutInflater.from(context);
        final View textEntryView = factory.inflate(layout_id,
                null);
        Builder builder = new Builder(context);
        builder.setTitle(title);
        // builder.setResponseMessage(msg);
        // builder.setView(mEmail_forgot);

        builder.setPositiveButton(btnText[0], listener);
        if (btnText.length != 1) {
            builder.setNegativeButton(btnText[1], listener);
        }
        dialog = builder.create();
        dialog.setCancelable(false);
        dialog.setView(textEntryView, 10, 10, 10, 10);
        dialog.show();
        return dialog;

    }


    /**
     * Checks the validation of email address.
     * Takes pattern and checks the text entered is valid email address or not.
     *
     * @param email : email in string format
     * @return True if email address is correct.
     */
    public static boolean isEmailValid(String email) {
        String expression = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches()) {
            return true;
        } else if (email.equals("")) {
            return false;
        }
        return false;
    }


    /**
     * Method checks if the given phone number is valid or not.
     *
     * @param number : Phone number is to be checked.
     * @return True if the number is valid.
     * False if number is not valid.
     */
    public static boolean isPhoneNumberValid(String number) {

        //String regexStr = "^([0-9\\(\\)\\/\\+ \\-]*)$";
        String regexStr = "^((0)|(91)|(00)|[7-9]){1}[0-9]{3,14}$";

        if (number.length() < 10 || number.length() > 11 || number.matches(regexStr) == false) {
            //	Log.d("tag", "Number is not valid");
            return false;
        }

        return true;
    }

    public static boolean IsPasswordValidation(String password) {
        if (password.length() < 6 || password.length() >= 13) {
            return false;
        }
        return true;
    }


    public static boolean isPasswordValid(String number) {

        //String regexStr = "^([0-9\\(\\)\\/\\+ \\-]*)$";
        String regexStr = " (?!^[0-9]*$)(?!^[a-zA-Z]*$)^([a-zA-Z0-9]{8,20})$";

        if (number.length() < 5 || number.length() > 13 /*|| number.matches(regexStr) == false*/) {
            //	Log.d("tag", "Number is not valid");
            return false;
        }

        return true;
    }

    public static long currentTimeInMillis() {
        Time time = new Time();
        time.setToNow();
        return time.toMillis(false);
    }

    /**
     * Get  EditText value.
     */
    public static String getEditTextValue(EditText text) {
        return text.getText().toString().trim();
    }

    /**
     * Checks if any text box is null or not.
     *
     * @param text : Text view for which validation is to be checked.
     * @return True if not null.
     */
    public static boolean isEditTextFilled(EditText text) {
        if (text.getText() != null && text.getText().toString().trim().length() > 0) {
            return true;
        } else {
            return false;
        }
    }


    public static boolean isAddharValidate(String str) {
        Pattern pattern = Pattern.compile("\\d{12}");
        Matcher matcher = pattern.matcher(str);
        if (matcher.matches()) {
            if (VerhoeffAlgorithm.validateVerhoeff(str)) {
                return true;

            } else {
                return false;
            }
        } else if (str.equals("")) {
            return false;
        }
        return false;
    }


    public static boolean isPasswordLengthCorrect(EditText text) {
        if (text.getText() != null && text.getText().toString().trim().length() >= 8) {
            return true;
        } else {
            return false;
        }
    }


    public static void InternetAlertDialog(final Activity mContext, String title, String msg) {
        Builder alertDialog = new Builder(mContext);

        //Setting Dialog Title
        alertDialog.setTitle(title);

        //Setting Dialog Message
        alertDialog.setMessage(msg);

        //On Pressing Setting button
        alertDialog.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        mContext.finish();


                    }
                });
        alertDialog.show();
    }


    public static boolean isMyServiceRunning(Class<?> serviceClass, Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public static int getAppVersion(Context ctx) {
        try {
            PackageInfo packageInfo = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    public static float convertPixelsToDp(float px, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }

    public static float getDpi(Activity activity) {
        float dp = 0;
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        if (metrics.density == 3.0) {
            dp = 1;
        }
        return dp;
    }

    public static void putBitmapInDiskCache(int blobId, Bitmap avatar, Context mContext) {
        // Create a path pointing to the system-recommended cache dir for the app, with sub-dir named
        // thumbnails
        File cacheDir = new File(mContext.getCacheDir(), "thumbnails-nudgebuddies");
        if (!cacheDir.exists())
            cacheDir.mkdir();
        // Create a path in that dir for a file, named by the default hash of the url

        File cacheFile = new File(cacheDir, "" + blobId);
        try {
            // Create a file at the file path, and open it for writing obtaining the output stream
            cacheFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(cacheFile);
            // Write the bitmap to the output stream (and thus the file) in PNG format (lossless compression)
            avatar.compress(Bitmap.CompressFormat.PNG, 100, fos);
            // Flush and close the output stream
            fos.flush();
            fos.close();
            avatar.recycle();
        } catch (Exception e) {
            // Log anything that might go wrong with IO to file
            Log.e("IMAGE CACHE", "Error when saving image to cache. ", e);
        }
    }

    public static Bitmap getBitmapFromDiskCache(int blobId, Context mContext) {
        // Create a path pointing to the system-recommended cache dir for the app, with sub-dir named
        // thumbnails
        Bitmap avatar = null;
        File cacheDir = new File(mContext.getCacheDir(), "thumbnails-nudgebuddies");
        // Create a path in that dir for a file, named by the default hash of the url

        File cacheFile = new File(cacheDir, "" + blobId);
        try {
            if (cacheFile.exists()) {

                FileInputStream fis = new FileInputStream(cacheFile);
                // Read a bitmap from the file (which presumable contains bitmap in PNG format, since
                // that's how files are created)
                avatar = BitmapFactory.decodeStream(fis);
                // Write the bitmap to the output stream (and thus the file) in PNG format (lossless compression)
            }
            // Create a file at the file path, and open it for writing obtaining the output stream
            // Flush and close the output stream
        } catch (Exception e) {
            // Log anything that might go wrong with IO to file
            Log.e("IMAGE CACHE", "Error when saving image to cache. ", e);
        }
        return avatar;
    }

    public static void saveImage(Bitmap finalBitmap, int fileName) {

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/nudgebuddies/avatar");
        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = fileName + ".jpg";
        File file = new File(myDir, fname);
        if (file.exists()) file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Bitmap getBitmapFromExternalStorage(int fileName) {

        Bitmap bitmap = null;
        String root = Environment.getExternalStorageDirectory().toString();
        File f = new File(root + "/nudgebuddies/avatar/" + fileName + ".jpg");
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        try {
            bitmap = BitmapFactory.decodeStream(new FileInputStream(f), null, options);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    public static String getImagePathExternalStorage(String fileName) {


       /* String fileName= listFile[i];
        fileName = fileName.replace(':', '/');
        fileName = fileName.replace('/', '_');*/

        String bitmapPath = null;
        String root = Environment.getExternalStorageDirectory().toString();
        File f = new File(root + "/nudgebuddies/avatar/" + fileName + ".jpg");
        String loadURL = "file://" + Environment.getExternalStorageDirectory() + "/nudgebuddies/avatar/" + fileName;
        bitmapPath = f.getAbsolutePath();
        return loadURL;
    }

    public static String getInitials(String str) {
        String userInitials = "";
        if (str.length() > 1) {
            String[] array = str.split(" ");
            if (array.length == 1) {
                String firstLatter = String.valueOf(array[0].charAt(0)).toUpperCase();
                String secLatter = String.valueOf(array[0].charAt(1)).toUpperCase();
                userInitials = firstLatter + secLatter;
            } else if (array.length == 2) {
                String firstLatter = String.valueOf(array[0].charAt(0)).toUpperCase();
                String secLatter = String.valueOf(array[1].charAt(0)).toUpperCase();
                userInitials = firstLatter + secLatter;
            }
        } else if (str.length() == 1) {
            userInitials = str;
        }
        return userInitials;
    }

    public static int getCameraPhotoOrientation(Context context, Uri imageUri, String imagePath) {
        int rotate = 0;
        try {
            context.getContentResolver().notifyChange(imageUri, null);
            File imageFile = new File(imagePath);
            ExifInterface exif = new ExifInterface(
                    imageFile.getAbsolutePath());
            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
            }


            Log.v(TAG, "Exif orientation: " + orientation);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rotate;
    }

    public static void setBadge(Context context, int count) {
        String launcherClassName = getLauncherClassName(context);
        if (launcherClassName == null) {
            return;
        }
        Intent intent = new Intent("android.intent.action.BADGE_COUNT_UPDATE");
        intent.putExtra("badge_count", count);
        intent.putExtra("badge_count_package_name", context.getPackageName());
        intent.putExtra("badge_count_class_name", launcherClassName);
        context.sendBroadcast(intent);
    }

    public static String getLauncherClassName(Context context) {

        PackageManager pm = context.getPackageManager();

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> resolveInfos = pm.queryIntentActivities(intent, 0);
        for (ResolveInfo resolveInfo : resolveInfos) {
            String pkgName = resolveInfo.activityInfo.applicationInfo.packageName;
            if (pkgName.equalsIgnoreCase(context.getPackageName())) {
                String className = resolveInfo.activityInfo.name;
                return className;
            }
        }
        return null;
    }

    public static void soundPlayer(Context mContext, int resourceId) {
        MediaPlayer player = MediaPlayer.create(mContext, resourceId);
        if (player.isPlaying()) {
            player.stop();
        } else {
            player.start();
        }
    }

    public static boolean hasFroyo() {
        // Can use static final constants like FROYO, declared in later versions
        // of the OS since they are inlined at compile time. This is guaranteed behavior.
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO;
    }

    public static boolean hasGingerbread() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;
    }

    public static boolean hasHoneycomb() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    }

    public static boolean hasHoneycombMR1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1;
    }

    public static boolean hasJellyBean() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
    }

    public static boolean hasICS() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
    }

    public static boolean hasKitKat() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

    public static String getVersionName(Context context) {
        // Get app version
        PackageManager pm = context.getPackageManager();
        String packageName = context.getPackageName();
        String versionName;
        try {
            PackageInfo info = pm.getPackageInfo(packageName, 0);
            versionName = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            versionName = VERSION_UNAVAILABLE;
        }
        return versionName;
    }

    public static String calculateAge(String strDate) {

        int years = 0;
        int months = 0;
        int days = 0;

        try {
            long timeInMillis = Long.parseLong(strDate);
            Date birthDate = new Date(timeInMillis);


            //create calendar object for birth day
            Calendar birthDay = Calendar.getInstance();
            birthDay.setTimeInMillis(birthDate.getTime());
            //create calendar object for current day
            long currentTime = System.currentTimeMillis();
            Calendar now = Calendar.getInstance();
            now.setTimeInMillis(currentTime);
            //Get difference between years
            years = now.get(Calendar.YEAR) - birthDay.get(Calendar.YEAR);
            int currMonth = now.get(Calendar.MONTH) + 1;
            int birthMonth = birthDay.get(Calendar.MONTH) + 1;
            //Get difference between months
            months = currMonth - birthMonth;
            //if month difference is in negative then reduce years by one and calculate the number of months.
            if (months < 0) {
                years--;
                months = 12 - birthMonth + currMonth;
                if (now.get(Calendar.DATE) < birthDay.get(Calendar.DATE))
                    months--;
            } else if (months == 0 && now.get(Calendar.DATE) < birthDay.get(Calendar.DATE)) {
                years--;
                months = 11;
            }
            //Calculate the days
            if (now.get(Calendar.DATE) > birthDay.get(Calendar.DATE))
                days = now.get(Calendar.DATE) - birthDay.get(Calendar.DATE);
            else if (now.get(Calendar.DATE) < birthDay.get(Calendar.DATE)) {
                int today = now.get(Calendar.DAY_OF_MONTH);
                now.add(Calendar.MONTH, -1);
                days = now.getActualMaximum(Calendar.DAY_OF_MONTH) - birthDay.get(Calendar.DAY_OF_MONTH) + today;
            } else {
                days = 0;
                if (months == 12) {
                    years++;
                    months = 0;
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        //Create new Age object
        return years + " years " + months + " months " + days + " days";
    }

    public static boolean hasPermissionInManifest(Activity activity, int requestCode, String permissionName) {
        if (ContextCompat.checkSelfPermission(activity,
                permissionName)
                != PackageManager.PERMISSION_GRANTED) {
            // No explanation needed, we can request the permission.
            ActivityCompat.requestPermissions(activity,
                    new String[]{permissionName},
                    requestCode);
        } else {
            return true;
        }
        return false;
    }

    public static Date getDate(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.DATE, day);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, year);
        return calendar.getTime();
    }

    public static String getTimeStamp(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.DATE, day);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, year);
        return "" + calendar.getTimeInMillis();
    }

    public static String getFormattedDate(String strDate) {


        String formattedDate = "";
        try {
            long timeInMillis = Long.parseLong(strDate);
            Date date = new Date(timeInMillis);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

            formattedDate = formatter.format(strDate);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return formattedDate;
    }

    public static String getAge(int year, int month, int day) {
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }

        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();

        return ageS;
    }

    public static String getFormattedTime(String strTime) {


        String formattedTime = "";
        try {
            long timeInMillis = Long.parseLong(strTime);
            Date date = new Date(timeInMillis);
            SimpleDateFormat formatter = new SimpleDateFormat("hh:mm");

            formattedTime = formatter.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return formattedTime;
    }


    public static int getIntMonth(String date) {

        String dateArr[] = date.split("/");
        int month = Integer.parseInt(dateArr[1]);

        return month;
    }

    public static long getIntDay(String strDate) {

        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(Long.parseLong(strDate));

        int day = c.get(Calendar.DAY_OF_MONTH);

        return day;
    }

    public static String getStrMonth(String strDate) {

        Date date = new Date(Long.parseLong(strDate));

        String month = null;
        try {
            //  date = df.parse(strDate);
            Format formatter = new SimpleDateFormat("MMM yyyy");
            month = formatter.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return month;
    }


    public static String getStringMonth(String strDate) {

        Date date = new Date(Long.parseLong(strDate));

        String month = null;
        try {
            //  date = df.parse(strDate);
            Format formatter = new SimpleDateFormat("MMM");
            month = formatter.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return month;
    }

    public static String getFormatedDate(String strDate) {

        Date date = new Date(Long.parseLong(strDate));
        String formattedDate = null;
        SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy");
        try {
//            Date date = formatter.parse(date);
            formattedDate = formatter.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return formattedDate;
    }


    public static String getConvertedTime(String strTime) {

        String convertedTime = null;

        String timeArr[] = strTime.split(":");
        int hour = Integer.parseInt(timeArr[0]);

        if (hour < 12) {
            convertedTime = strTime + " AM";
        } else {
            convertedTime = strTime + " PM";
        }

        return convertedTime;
    }

    public static ArrayList<Integer> getMonthLst() {
        ArrayList<Integer> months = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            months.add(i);
        }
        return months;
    }


    public static ArrayList<String> getPetTypeList() {
        ArrayList<String> petTypes = new ArrayList<String>();
        petTypes.add("INDOOR");
        petTypes.add("OUTDOOR");
        petTypes.add("BOTH");

        return petTypes;
    }

    public static ArrayList<String> getPetTrainedTypesList() {
        ArrayList<String> trainedTypes = new ArrayList<String>();
        trainedTypes.add("Basic");
        trainedTypes.add("Obedience");
        trainedTypes.add("Professional");
        trainedTypes.add("None");

        return trainedTypes;
    }

    public static ArrayList<String> getPetLifeStyleList() {
        ArrayList<String> lifeStyleTypes = new ArrayList<String>();
        lifeStyleTypes.add("Very Active");
        lifeStyleTypes.add("Active");
        lifeStyleTypes.add("Moderately Active");
        lifeStyleTypes.add("Sedentary");
        lifeStyleTypes.add("Lazy");

        return lifeStyleTypes;
    }

    public static ArrayList<String> getPetWeightList() {
        ArrayList<String> weightUnits = new ArrayList<String>();
        weightUnits = new ArrayList<String>();
        weightUnits.add("KG");
        weightUnits.add("LBS");
        weightUnits.add("STONE");

        return weightUnits;
    }


    public static ArrayList<String> getPetHeightList() {
        ArrayList<String> heightUnits = new ArrayList<String>();
        heightUnits = new ArrayList<String>();
        heightUnits.add("CM");

        return heightUnits;
    }

    public static int getItemPos(List<String> arrayList, String category) {

        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).equalsIgnoreCase(category)) {
                return i;
            }
        }

        return 0;
    }

    public static String firstDayOfMonthFormatted() {
        String formattedDate = "";
        Calendar calendar = Calendar.getInstance();


        while (calendar.get(Calendar.DATE) > 1) {
            calendar.add(Calendar.DATE, -1); // Substract 1 day until first day of month.
        }
        long firstDayOfMonthTimestamp = calendar.getTimeInMillis();

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
        try {
            formattedDate = formatter.format(calendar.getTime());
            Log.e("<--First Day of Month :", formattedDate);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return formattedDate;
    }

    public static String firstDayOfMonth() {
        String formattedDate = "";
        Calendar calendar = Calendar.getInstance();


        while (calendar.get(Calendar.DATE) > 1) {
            calendar.add(Calendar.DATE, -1); // Substract 1 day until first day of month.
        }
        long firstDayOfMonthTimestamp = calendar.getTimeInMillis();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            formattedDate = formatter.format(calendar.getTime());
            Log.e("<--First Day of Month :", formattedDate);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return formattedDate;
    }

    public static String lastDayOfMonth() {
        String formattedDate = "";
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            formattedDate = formatter.format(calendar.getTime());
            Log.e("<--Last Day of Month :", formattedDate);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return formattedDate;
    }

    public static String lastDayOfMonthFormatted() {
        String formattedDate = "";
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
        try {
            formattedDate = formatter.format(calendar.getTime());
            Log.e("<--Last Day of Month :", formattedDate);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return formattedDate;
    }

    public static String firstDayOfWeekFormatted() {
        String formattedDate = "";
        Calendar calendar = Calendar.getInstance();
        while (calendar.get(Calendar.DAY_OF_WEEK) > calendar.getFirstDayOfWeek()) {
            calendar.add(Calendar.DATE, -1); // Substract 1 day until first day of week.
        }
        //long firstDayOfWeekTimestamp = calendar.getTimeInMillis();

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
        try {
            formattedDate = formatter.format(calendar.getTime());
            Log.e("<--First Day of Week:", formattedDate);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return formattedDate;
    }

    public static String firstDayOfWeek() {
        String formattedDate = "";
        Calendar calendar = Calendar.getInstance();
        while (calendar.get(Calendar.DAY_OF_WEEK) > calendar.getFirstDayOfWeek()) {
            calendar.add(Calendar.DATE, -1); // Substract 1 day until first day of week.
        }
        //long firstDayOfWeekTimestamp = calendar.getTimeInMillis();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            formattedDate = formatter.format(calendar.getTime());
            Log.e("<--First Day of Week:", formattedDate);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return formattedDate;
    }

    public static String lastDayOfWeek() {
        String formattedDate = "";
        Calendar calendar = Calendar.getInstance();
        while (calendar.get(Calendar.DAY_OF_WEEK) > calendar.getFirstDayOfWeek()) {
            calendar.add(Calendar.DATE, -1); // Substract 1 day until first day of week.
        }
        //long firstDayOfWeekTimestamp = calendar.getTimeInMillis();
        calendar.add(Calendar.DATE, 6);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            formattedDate = formatter.format(calendar.getTime());
            Log.e("<--last Day of Week :", formattedDate);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return formattedDate;
    }

    public static String lastDayOfWeekFormatted() {
        String formattedDate = "";
        Calendar calendar = Calendar.getInstance();
        while (calendar.get(Calendar.DAY_OF_WEEK) > calendar.getFirstDayOfWeek()) {
            calendar.add(Calendar.DATE, -1); // Substract 1 day until first day of week.
        }
        //long firstDayOfWeekTimestamp = calendar.getTimeInMillis();
        calendar.add(Calendar.DATE, 6);
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
        try {
            formattedDate = formatter.format(calendar.getTime());
            Log.e("<--last Day of Week :", formattedDate);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return formattedDate;
    }

    @SuppressLint("SimpleDateFormat")
    public static String getTodaysTime(long timestamp) {
        Timestamp tStamp = new Timestamp(timestamp);
        SimpleDateFormat simpleDateFormat;
        simpleDateFormat = new SimpleDateFormat("hh:mm a");
        return simpleDateFormat.format(tStamp);
    }

    @SuppressLint("SimpleDateFormat")
    public static String convertTimestampToDate(long timestamp) {
        Timestamp tStamp = new Timestamp(timestamp);
        SimpleDateFormat simpleDateFormat;
        if (DateUtils.isToday(timestamp)) {
            simpleDateFormat = new SimpleDateFormat("hh:mm a");
            return "Today " + simpleDateFormat.format(tStamp);
        } else {
            //simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
            simpleDateFormat = new SimpleDateFormat("dd MMM yyyy hh:mm a");
            return simpleDateFormat.format(tStamp);
        }
    }

    @SuppressLint("SimpleDateFormat")
    public static String convertTimestampToDate1(long timestamp) {
        Timestamp tStamp = new Timestamp(timestamp);
        SimpleDateFormat simpleDateFormat;
        if (DateUtils.isToday(timestamp)) {
            simpleDateFormat = new SimpleDateFormat("dd MMM yy");
            return /*"Today " +*/ simpleDateFormat.format(tStamp);
        } else {
            //simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
            simpleDateFormat = new SimpleDateFormat("dd MMM yy");
            return simpleDateFormat.format(tStamp);
        }
    }

    public static long correctTimestamp(long timestampInMessage) {
        long correctedTimestamp = timestampInMessage;

        if (String.valueOf(timestampInMessage).length() < 13) {
            int difference = 13 - String.valueOf(timestampInMessage).length(), i;
            String differenceValue = "1";
            for (i = 0; i < difference; i++) {
                differenceValue += "0";
            }
            correctedTimestamp = (timestampInMessage * Integer.parseInt(differenceValue))
                    + (System.currentTimeMillis() % (Integer.parseInt(differenceValue)));
        }
        return correctedTimestamp;
    }

    public static String convertTimestampToTime(long timestamp) {
        Timestamp tStamp = new Timestamp(timestamp);
        SimpleDateFormat simpleDateFormat;
        if (DateUtils.isToday(timestamp)) {
            simpleDateFormat = new SimpleDateFormat("hh:mm a");
            return simpleDateFormat.format(tStamp);
        } else {
            simpleDateFormat = new SimpleDateFormat("hh:mm a");
            return simpleDateFormat.format(tStamp);
        }
    }

    public static boolean validateAadharNumber(String aadharNumber) {
        Pattern aadharPattern = Pattern.compile("\\d{12}");
        boolean isValidAadhar = aadharPattern.matcher(aadharNumber).matches();
        if (isValidAadhar) {
            isValidAadhar = VerhoeffAlgorithm.validateVerhoeff(aadharNumber);
        }
        return isValidAadhar;
    }


    public static class CustomTimePickerDialog extends TimePickerDialog {


        private TimePicker mTimePicker;
        private final OnTimeSetListener mTimeSetListener;

        public CustomTimePickerDialog(Context context, OnTimeSetListener listener,
                                      int hourOfDay, int minute, boolean is24HourView) {
            super(context, TimePickerDialog.THEME_HOLO_LIGHT, null, hourOfDay,
                    minute, is24HourView);
            mTimeSetListener = listener;

        }

        @Override
        public void updateTime(int hourOfDay, int minuteOfHour) {
            mTimePicker.setCurrentHour(hourOfDay);
            mTimePicker.setCurrentMinute(minuteOfHour);
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case BUTTON_POSITIVE:
                    if (mTimeSetListener != null) {
                        mTimeSetListener.onTimeSet(mTimePicker, mTimePicker.getCurrentHour(),
                                mTimePicker.getCurrentMinute());
                    }
                    break;
                case BUTTON_NEGATIVE:
                    cancel();
                    break;
            }
        }

        @Override
        public void onAttachedToWindow() {
            super.onAttachedToWindow();
            try {
                Class<?> classForid = Class.forName("com.android.internal.R$id");
                Field timePickerField = classForid.getField("timePicker");
                mTimePicker = (TimePicker) findViewById(timePickerField.getInt(null));
                Field field = classForid.getField("minute");

                NumberPicker minuteSpinner = (NumberPicker) mTimePicker
                        .findViewById(field.getInt(null));
                minuteSpinner.setMinValue(0);
                minuteSpinner.setMaxValue((60) - 1);
                List<String> displayedValues = new ArrayList<>();
                for (int i = 0; i < 60; i++) {
                    displayedValues.add(String.format("%02d", i));
                }
                minuteSpinner.setDisplayedValues(displayedValues
                        .toArray(new String[displayedValues.size()]));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static String changeDateFormate(String time) {//2018-05-08 05:57:07
        String inputPattern = "yyyy-MM-dd HH:mm:ss";
        String outputPattern = "dd MMM yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }  public static String changeDateFormateDOB(String time) {//2018-05-08 05:57:07
        String inputPattern = "yyyy-MM-dd";
        String outputPattern = "dd MMM yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

}