package com.allever.coderhouse.modules.image.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.allever.coderhouse.R;
import com.allever.coderhouse.utils.CommonUtil;
import com.allever.coderhouse.utils.Constants;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by allever on 17-6-3.
 */

public class ShowImageFragment extends Fragment {

    private static final String TAG = "ShowImageFragment";

    private String url;
    private String description;

    private TextView tv_description;
    private ImageView iv_picture;

    private ImageView iv_download;

    public ShowImageFragment(){

    }

    @SuppressLint("ValidFragment")
    public ShowImageFragment(String url, String description){
        this.url = url;
        this.description = description;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.show_image_fragment_layout,container,false);

        iv_picture = (ImageView)view.findViewById(R.id.id_image_fragment_iv);
        tv_description = (TextView)view.findViewById(R.id.id_image_fragment_tv_description);

        Glide.with(getActivity()).load(url).into(iv_picture);

        iv_download = (ImageView)view.findViewById(R.id.id_image_fragment_iv_download);

        iv_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = new File(Constants.IMAGE_PATH + "/coderHouse/" + CommonUtil.getFileNameFromUrl(url));
                if (file.exists()){
                    Toast.makeText(getActivity(),"已保存",Toast.LENGTH_LONG).show();
                    return;
                }

                //6.0权限管理
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                    //getImageDaily();
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                }

                CommonUtil.downloadImage(url, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        //Log.d(TAG, "onResponse: url = " + url);
                        String fileName = (url.substring(url.lastIndexOf("/"))).split("/")[1];
                        InputStream inputStream = response.body().byteStream();

                        String dirPath = Environment.getExternalStorageDirectory() + "/coderHouse/";
                        File dirFile = new File(dirPath);
                        if(!dirFile.exists()){
                            dirFile.mkdir();
                        }
                        File file = new File(dirPath,fileName);
                        Log.d(TAG, "onResponse: dir = " + dirPath);
                        Log.d(TAG, "onResponse: fileName = " + fileName);
                        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        byte[] b = new byte[1024];
                        int len;
                        while ((len = bufferedInputStream.read(b)) > 0 ){
                            baos.write(b,0,len);
                        }
                        FileOutputStream fos = new FileOutputStream(file);
                        fos.write(baos.toByteArray());
                        fos.close();
                        bufferedInputStream.close();
                        inputStream.close();
                        response.body().close();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getActivity(),"以保存至:" + dirPath + fileName,Toast.LENGTH_LONG).show();
                            }
                        });

                    }
                });
            }
        });

        tv_description.setText(description);

        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED){
                    //getImageDaily();
                    Toast.makeText(getActivity(), "无法保存,请重新授权", Toast.LENGTH_SHORT).show();
                    //finish();
                }else {
                    //showBackground();
                }
                break;
        }
    }
}
