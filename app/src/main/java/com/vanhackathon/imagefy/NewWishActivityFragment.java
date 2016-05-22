package com.vanhackathon.imagefy;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.facebook.login.LoginManager;
import com.vanhackathon.imagefy.service.AuthService;
import com.vanhackathon.imagefy.service.WishesService;
import com.vanhackathon.imagefy.service.data.auth.LoginResponse;
import com.vanhackathon.imagefy.service.data.auth.Wish;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A placeholder fragment containing a simple view.
 */
public class NewWishActivityFragment extends Fragment {
    private static final String TAG = "WISH";
    static final int REQUEST_TAKE_PHOTO = 111;
    String mCurrentPhotoPath;
    private ImageView image1;
    private View saveButton;

    public NewWishActivityFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_new_wish, container, false);

        Button button = (Button) rootView.findViewById(R.id.button_take_a_photo);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

        image1 = (ImageView) rootView.findViewById(R.id.new_wish_image_view_preview_1);
        if(mCurrentPhotoPath != null) {
            image1.setVisibility(View.VISIBLE);
            image1.setImageBitmap(BitmapFactory.decodeFile(mCurrentPhotoPath));
        }

        saveButton = rootView.findViewById(R.id.button_save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveButton.setEnabled(false);
                final Wish wish =  new Wish();
                wish.tags = "tags rodrigo";
                wish.brief = "brief rodrigo";
                wish.buget = "100";


                Bitmap original = BitmapFactory.decodeFile(mCurrentPhotoPath);
                int newW = (int) (original.getWidth() * 0.25);
                int newH = (int) (original.getHeight() * 0.25);
                Bitmap resized = Bitmap.createScaledBitmap(original, newW, newH, false);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                resized.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
                byte[] b = baos.toByteArray();

                wish.photo = Base64.encodeToString(b, Base64.DEFAULT);
//                Call<Wish> call = WishesService.getInstance(LocalLoginManager.loginToken(getContext())).imagefyWishesApi.add(wish);

                RequestBody photo = RequestBody.create(MediaType.parse("image/jpeg"), new File(mCurrentPhotoPath));
                Call<Wish> call = WishesService.getInstance(LocalLoginManager.loginToken(getContext())).imagefyWishesApi.uploadFile(photo, "1.00");

                call.enqueue(new Callback<Wish>() {
                    @Override
                    public void onResponse(Call<Wish> call, Response<Wish> response) {
                        Log.d(TAG, "response: " + response);
                    }

                    @Override
                    public void onFailure(Call<Wish> call, Throwable t) {
                        Log.d(TAG, "fail", t);
                        saveButton.setEnabled(true);
                    }
                });
            }
        });

        return rootView;
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == Activity.RESULT_OK) {
            image1.setVisibility(View.VISIBLE);
            image1.setImageBitmap(BitmapFactory.decodeFile(mCurrentPhotoPath));
        }
    }

    public void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getContext().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;

            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }
}
