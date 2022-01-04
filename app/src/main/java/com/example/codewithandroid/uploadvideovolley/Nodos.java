//package com.example.codewithandroid.uploadvideovolley;
////package com.example.flutter_nodos.activitys;
//
//import android.app.Activity;
//import android.hardware.Camera;
//import android.media.MediaRecorder;
//import android.net.Uri;
//import android.os.Bundle;
//import android.os.Environment;
//import android.util.Log;
//import android.view.KeyEvent;
//import android.view.SurfaceHolder;
//import android.view.SurfaceView;
//
//import com.android.volley.AuthFailureError;
//import com.android.volley.NetworkResponse;
//import com.android.volley.Request;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
////import com.android.volley.toolbox.Volley;
//import com.example.flutter_nodos.volley.VolleyMultiPartRequest;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.ByteArrayOutputStream;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import android.content.Context;
//
//import com.example.flutter_nodos.R;
//import com.example.flutter_nodos.utils.Values;
//
//
//public class VideoActivity extends Activity implements SurfaceHolder.Callback /*implements SurfaceHolder.Callback*/ {
//    private static final String TAG = "Recorder";
//    public static SurfaceView mSurfaceView;
//    public static SurfaceHolder mSurfaceHolder;
//    public static Camera mCamera;
//    private MediaRecorder mMediaRecorder;
//    public static boolean mPreviewRunning;
//    Context Gcontext;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_video);
//
//        mSurfaceView = (SurfaceView) findViewById(R.id.surfaceView);
//        mSurfaceHolder = mSurfaceView.getHolder();
//        mSurfaceHolder.addCallback(this);
//        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
//        mSurfaceHolder.setKeepScreenOn(true);
//
////        Button prueba = (Button) findViewById(R.id.BotonDePruebas);
////        prueba.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
//
//        EnVideo = true;
//        new comenzarVideo().start();
//
////            }
////        });
//
//        //LockService.TakeVideo();
//
////        finish();
//    }
//
//    int TiempoDeEspera = 1*10000;
//    boolean EnVideo = false;
//
//    public class comenzarVideo extends Thread {
//        public void run() {
//            Log.e("Activity", "Entra en el Thread VA");
//            while (EnVideo) {
//                EnVideo = false;
//                Log.e("Activity","Empieza el click VA");
//                try {
//                    mCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT);
//                    mCamera.setDisplayOrientation(90);
//
//                    Camera.Parameters params = mCamera.getParameters();
//                    mCamera.setParameters(params);
//                    Camera.Parameters p = mCamera.getParameters();
//
//                    final List<Camera.Size> listSize = p.getSupportedPreviewSizes();
//                    Camera.Size mPreviewSize = listSize.get(2);
//                    Log.e(TAG, "use: width = " + mPreviewSize.width
//                            + " height = " + mPreviewSize.height);
//                    p.setPreviewSize(mPreviewSize.width, mPreviewSize.height);
//                    //p.setPreviewFormat(PixelFormat.YCbCr_420_SP);
//                    mCamera.setParameters(p);
//
//                    try {
//                        Log.e(TAG, "mSurfaceHolder " + mSurfaceHolder);
//                        mCamera.setPreviewDisplay(mSurfaceHolder);
//                        mCamera.startPreview();
//                    }
//                    catch (RuntimeException eTime) {
//                        Log.e(TAG, "RuntimeException " + eTime.getMessage(), eTime);
//                    }
//                    Log.e(TAG, "mServiceCamera unlock");
//                    mCamera.unlock();
//
//                    mMediaRecorder = new MediaRecorder();
//                    mMediaRecorder.setCamera(mCamera);
//                    mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
//                    mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
//                    mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
//                    mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
//                    mMediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.DEFAULT);
//                    mMediaRecorder.setOrientationHint(270);
//                    Log.e("video","Antes de");
//                    mMediaRecorder.setOutputFile(Environment.getExternalStorageDirectory() + "/Download/videoRemoto.mp4");
//                    String namePath = Values.varVideoRutaVideo = Environment.getExternalStorageDirectory() + "/Download/videoRemoto.mp4";
//                    //Envio de video a la funcion del servicio.
//                    uploadPDF(namePath);
//
//                    Date horaActual = new Date();
//
//                    String hora = "video"+((horaActual.getYear() + 1900) + "" + (horaActual.getMonth() + 1))
//                            + "" + horaActual.getDate() + "" + horaActual.getHours() + "" + horaActual.getMinutes()
//                            + "" + horaActual.getSeconds();
//
//                    //Values.varVideoNombreVideo = hora+".mp4";
//                    Log.e("video","Despues de");
//                    //Log.e("Directorio del video al grabar", Values.varVideoRutaVideo);
//                    mMediaRecorder.setVideoFrameRate(30);
//                    //mMediaRecorder.setVideoSize(mPreviewSize.width, mPreviewSize.height);
//                    mMediaRecorder.setPreviewDisplay(mSurfaceHolder.getSurface());
//
//                    mMediaRecorder.prepare();
//                    mMediaRecorder.start();
//
//                    Thread.sleep(5*1000);
//
//                    try {
//                        mCamera.reconnect();
//                    } catch (IOException e) {
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//                    }
//                    mMediaRecorder.stop();
//                    mMediaRecorder.reset();
//
//                    mCamera.stopPreview();
//                    mMediaRecorder.release();
//
//                    mCamera.release();
//                    mCamera = null;
//
//                } catch (Exception e) {
//                    Log.e("VideoActivity","try startService(intent)");
//                }
//                Log.e("Activity","Termina el click VA");;
//            }
//            Log.e("Tracking", "Afuera del while VA");
//            finish();
//            //LockService.uploadRemoteVideo();
//        }
//    }
//
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        Log.e(TAG, "key Event: " + keyCode);
//        if (keyCode == KeyEvent.KEYCODE_BACK)
//            return true;
//
//        return super.onKeyDown(keyCode, event);
//    }
//
//    @Override
//    public void surfaceCreated(SurfaceHolder surfaceHolder) {
//
//    }
//
//    @Override
//    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
//
//    }
//
//    @Override
//    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
//
//    }
//
//    private void uploadPDF(String fileName){
//
//        Uri myUri = Uri.parse(fileName);
//        String nameVideo = "Video";
//
//        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, "http://34.75.19.230/api/videoEvent",
//                new Response.Listener<NetworkResponse>(){
//                    @Override
//                    public void onResponse(NetworkResponse response) {
//                        try {
//                            JSONObject obj = new JSONObject(new String(response.data));
//                            Log.i("Info", "mensaje de satisfaccion");
//                            Log.i("Info",obj.toString());
//
//                        } catch ( JSONException e) {
//                            e.printStackTrace();
//                            Log.i("Info", "mensaje de error del servicio");
//                        }
//                    }
//                },new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.i("Info", "mensaje de Error grande");
//                Log.i("Error",error.toString() );
//            }
//        }){
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("terminal_id", "173");
////                params.put("img", tags);
//                return params;
//            }
//
//
//            protected Map<String, VolleyMultiPartRequest.DataPart> getByteData() {
//                Map<String, VolleyMultiPartRequest.DataPart> params = new HashMap<>();
//                long imagename = System.currentTimeMillis();
//                //params.put("terminal_id",new DataPart("terminal_id","termnial_id","165"));
//                params.put("img", new VolleyMultiPartRequest.DataPart(nameVideo + ".mp4", getBytes(myUri)));
//                return params;
//            }
//
//        };
//        Volley.newRequestQueue(Gcontext).add(volleyMultipartRequest);
//
//    }
//
//    public byte[] getBytes(InputStream inputStream) throws IOException {
//        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
//        int bufferSize = 1024;
//        byte[] buffer = new byte[bufferSize];
//
//        int len = 0;
//        while ((len = inputStream.read(buffer)) != -1) {
//            byteBuffer.write(buffer, 0, len);
//        }
//        return byteBuffer.toByteArray();
//    }
//
//}