
package com.example.andoirdlabs;



        import android.Manifest;
        import android.content.Intent;
        import android.content.pm.PackageManager;
        import android.graphics.Bitmap;
        import android.os.Bundle;
        import android.provider.MediaStore;
        import android.support.v4.content.ContextCompat;
        import android.support.v7.app.AppCompatActivity;


        import android.util.Log;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ImageButton;



public class ProfileActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    ImageButton mImageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_profile);

        Intent fromPrevious = getIntent();
        String previousTyped = fromPrevious.getStringExtra("typed");

        EditText enterText = (EditText)findViewById(R.id.Editxt2);
        enterText.setText(previousTyped);

        mImageButton=findViewById(R.id.btn_image);
        mImageButton.setOnClickListener( b -> {


            //create an intent to send data back to FirstActivity:


            dispatchTakePictureIntent();

        });

        Button nextButton = (Button)findViewById(R.id.button2);
        nextButton.setOnClickListener( c -> {

            //Give directions to go from this page, to SecondActivity
            Intent nextPage = new Intent(ProfileActivity.this,ChatRoomActivity.class);

            //Now make the transition:
            startActivity(nextPage);
        });
        Button tool=(Button)findViewById(R.id.buttonTool);
        tool.setOnClickListener(d->{
            Intent gototool = new Intent(ProfileActivity.this,TestToolbar.class);
            startActivity(gototool);
        });


    }
    private void dispatchTakePictureIntent(){

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
        setResult(RESULT_OK, takePictureIntent);

    }
    // This will start Android’s Activity that is responsible for the MediaStore.ACTION_IMAGE_CAPTURE intent.
    // Since you are using startActivityForResult( ), the activity will call your onActivityResult(int request, int result, Intent data ) when finished.
    // If you clicked the checkbox button on the camera activity, then result will be Activity.RESULT_OK,
    // and the data parameter will be an intent that has the picture saved under the name “data”:

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");;
            mImageButton.setImageBitmap(imageBitmap);


        }
    }
    public static final String ACTIVITY_NAME = "PROFILE_ACTIVITY";
    @Override
    protected void onPause() {
        super.onPause();
        Log.e(ACTIVITY_NAME, "In function:" + "onPause");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(ACTIVITY_NAME, "In function:" + "onDestroy");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(ACTIVITY_NAME, "In function:" + "onStop");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(ACTIVITY_NAME, "In function:" + "onResume");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(ACTIVITY_NAME, "In function:" + "onStart");
    }




}

