package com.example.codewithandroid.uploadvideovolley;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Button btn;
    private TextView tv;
    private String upload_URL = "Add your Api Here";
    private RequestQueue rQueue;
    private ArrayList<HashMap<String, String>> arraylist;
    Uri contentURI;
    String url;
    URL myURL;
    VideoView videoshow;
    MediaController mediaController;

    String spath;

    String usertype;
    int userid;

    Button submit;


    //testting

    private Button btn1;
    private VideoView videoView;
    private static final String VIDEO_DIRECTORY = "/lnitv";
    private int GALLERY = 1, CAMERA = 2;
    EditText title, videoDecription;


    //testing



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        title = findViewById(R.id.videotitle);
        videoDecription = findViewById(R.id.videoDecription);
        submit =findViewById(R.id.submit);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String title1 = title.getText().toString();

                String VideoDect = videoDecription.getText().toString();

                Toast.makeText(MainActivity.this,title1,Toast.LENGTH_LONG).show();

                Toast.makeText(MainActivity.this,VideoDect,Toast.LENGTH_LONG).show();

                String displayName = String.valueOf(Calendar.getInstance().getTimeInMillis()+".mp4");

                if (title1.isEmpty()){
                    title.setError("Please Enter Video Title");
                    title.requestFocus();
                }else if (VideoDect.isEmpty()){
                    videoDecription.setError("Please Enter Video Description");
                    videoDecription.requestFocus();
                }else {

                    uploadPDF(displayName,contentURI);

                }
            }
        });


        /*btn = findViewById(R.id.btn);*/
        /* tv = findViewById(R.id.tv);*/
        //testing
        videoView = findViewById(R.id.videoshow);

        //videoView = (VideoView) findViewById(R.id.vv);


        btn1 = (Button) findViewById(R.id.btn1);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            // Get the Uri of the selected file
            Uri uri = data.getData();
            String uriString = uri.toString();
            File myFile = new File(uriString);

            contentURI = data.getData();
//testing

            String[] mediaColumns = {MediaStore.Video.Media.SIZE};
            Cursor cursor = MainActivity.this.getContentResolver().query(contentURI,mediaColumns,null,null,null);
            cursor.moveToFirst();
            int sizeColInd =cursor.getColumnIndex(mediaColumns[0]);
            double fileSize =cursor.getDouble(sizeColInd);
            fileSize = fileSize/1024.0;
            cursor.close();
            Toast.makeText(MainActivity.this,"filesize"+fileSize,Toast.LENGTH_LONG).show();

            //tset


            Log.d("result",""+resultCode);
            super.onActivityResult(requestCode, resultCode, data);
            if (resultCode == this.RESULT_CANCELED) {
                Log.d("what","cancle");
                return;
            }
            if (requestCode == GALLERY) {
                Log.d("what","gale");
                if (data != null) {

                    String selectedVideoPath = getPath(contentURI);
                    Log.d("path",selectedVideoPath);
                    saveVideoToInternalStorage(selectedVideoPath);
                    videoView.setVideoURI(contentURI);
                    videoView.requestFocus();
                    videoView.start();
                    MediaController mc= new MediaController(MainActivity.this);
                    videoView.setMediaController(mc);

                }

            } else if (requestCode == CAMERA) {
                //Uri contentURI = data.getData();
                String recordedVideoPath = getPath(contentURI);
                Log.d("frrr",recordedVideoPath);
                saveVideoToInternalStorage(recordedVideoPath);
                videoView.setVideoURI(contentURI);
                videoView.requestFocus();
                videoView.start();
                MediaController mc = new MediaController(MainActivity.this);
                videoView.setMediaController(mc);
            }


            //test


            //implement video show activity //

            /*videoshow.setVideoURI(uri);
            videoshow.requestFocus();
            videoshow.start();
            MediaController mc= new MediaController(UploadVideo.this);
            videoshow.setMediaController(mc);*/


            Log.d("uri", String.valueOf(uri));


            spath = myFile.getAbsolutePath();

            Log.d("uripath", spath);
            /*long lenth = spath.length();
            lenth = (lenth /1024);

            Toast.makeText(UploadVideo.this,"Video Size"+lenth+"KB",Toast.LENGTH_LONG).show();

            Log.d("filelenth", String.valueOf(lenth));*/


            //uploadPDF(displayName,uri);
        }

        super.onActivityResult(requestCode, resultCode, data);

    }

    private void uploadPDF(final String pdfname, Uri pdffile){

        InputStream iStream = null;
        try {

            iStream = getContentResolver().openInputStream(pdffile);
            final byte[] inputData = getBytes(iStream);

            VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, "",
                    new Response.Listener<NetworkResponse>() {
                        @Override
                        public void onResponse(NetworkResponse response) {

                            Log.d("ressssssoo",new String(response.data));
                            rQueue.getCache().clear();
                            try {
                                JSONObject jsonObject = new JSONObject(new String(response.data));
                                Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }) {

                /*
                 * If you want to add more parameters with the image
                 * you can do it here
                 * here we have only one parameter with the image
                 * which is tags
                 * */
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("terminal_id", "36");  //add string parameters
                    return params;
                }
                /*
                 * pass files using below method
                 * */
                @Override
                protected Map<String, DataPart> getByteData() {
                    Map<String, DataPart> params = new HashMap<>();
                    params.put("video", new DataPart(pdfname ,inputData));
                    return params;
                }
            };


            volleyMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(
                    0,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)

            );

            volleyMultipartRequest.setRetryPolicy(new RetryPolicy() {
                @Override
                public int getCurrentTimeout() {
                    return 50000;
                }

                @Override
                public int getCurrentRetryCount() {
                    return 50000;
                }

                @Override
                public void retry(VolleyError error) throws VolleyError {

                }
            });
            rQueue = Volley.newRequestQueue(MainActivity.this);
            rQueue.add(volleyMultipartRequest);



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }


    //testting  //

    private void showPictureDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select video from gallery",
                "Record video from camera" };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                chooseVideoFromGallary();
                                break;
                            case 1:
                                takeVideoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void chooseVideoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takeVideoFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    private void saveVideoToInternalStorage (String filePath) {

        File newfile;

        try {

            File currentFile = new File(filePath);
            File wallpaperDirectory = new File(Environment.getExternalStorageDirectory() + VIDEO_DIRECTORY);
            newfile = new File(wallpaperDirectory, Calendar.getInstance().getTimeInMillis() + ".mp4");

            if (!wallpaperDirectory.exists()) {
                wallpaperDirectory.mkdirs();
            }

            if(currentFile.exists()){

                InputStream in = new FileInputStream(currentFile);
                OutputStream out = new FileOutputStream(newfile);

                // Copy the bits from instream to outstream
                byte[] buf = new byte[1024];
                int len;

                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                in.close();
                out.close();
                Log.v("vii", "Video file saved successfully.");
            }else{
                Log.v("vii", "Video saving failed. Source file missing.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Video.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            // HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
            // THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else
            return null;
    }







}
