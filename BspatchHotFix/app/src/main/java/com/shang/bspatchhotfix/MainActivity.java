package com.shang.bspatchhotfix;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.os.Environment;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.shang.bspatchhotfix.bspatch.BsPatch;
import com.shang.bspatchhotfix.utils.ZipUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends Activity {

    private Button btn;
    private String apkfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = (Button)findViewById(R.id.test);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                patchDex();
            }
        });
        initPatch();
    }

    private void initPatch() {
        apkfile = getFilesDir().getAbsolutePath()+ File.separator+"bspatch";
        final ApplicationInfo ai = this.getApplicationInfo();
        new Thread(new Runnable() {
            @Override
            public void run() {
                //从data/app目录下获取到应用
                copyFile(ai.sourceDir,apkfile,"temp.apk");
                //将apk解压缩
                ZipUtils.unzip(apkfile+File.separator+"temp.apk");
                File file = new File(apkfile+File.separator+"temp.apk");
                if(file.exists()){
                    file.delete();
                }
            }
        }).start();
    }
    private String getSDPath(){
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(android.os.Environment.MEDIA_MOUNTED);  //判断sd卡是否存在
        if  (sdCardExist)
        {
            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
        }
        return sdDir.toString();
    }
    private void patchDex() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String olddex = apkfile + File.separator + "classes.dex";
                String newdex = getFilesDir() +File.separator+ "classes.dex";
                String patch = getSDPath()+File.separator+"classes.patch";
                int code = BsPatch.bspatch(olddex,newdex,patch);
                String src = getSDPath() + File.separator + "META-INF";
                String des = apkfile +File.separator + "META-INF";
                if(code == 0){
                    copyFile(newdex,apkfile,"classes.dex");
                    copyFile(src,des);
                    ZipUtils.zip(apkfile);
                }
            }
        }).start();

    }

    private void copyFile(String src, String des) {
        File srcfile = new File(src);
       // File desfile = new File(des);
        if(!srcfile.exists())
            return;
        File[] srcfiles = srcfile.listFiles();
        for(File file :srcfiles){
            if(file.isFile()){
                copyFile(file.getAbsolutePath(),des,file.getName());
            }
        }
    }

    private void copyFile(String publicSourceDir, String absolutePath,String filename) {
        FileInputStream inputStream = null;
        FileOutputStream outputStream = null;
        File dirfile = new File(absolutePath);
        File dexfile = new File(absolutePath,filename);
        if(!dirfile.exists()){
            dirfile.mkdirs();
        }
        if(dexfile.exists()){
            dexfile.delete();
        }
        try {
            inputStream = new FileInputStream(publicSourceDir);
            outputStream = new FileOutputStream(absolutePath+File.separator+filename);
            byte []buf = new byte[1024];
            int len = 0;
            //读一个数组大小，写一个数组大小方法。
            while((len = inputStream.read(buf)) != -1){
                outputStream.write(buf, 0, len);
            }

        } catch (Exception e) {
            System.out.println(e.toString());
        } finally {
            if (inputStream != null)
                try {
                    inputStream.close();
                } catch (Exception e2) {
                    throw new RuntimeException("关闭失败！");
                }
            if (outputStream != null)
                try {
                    outputStream.close();
                } catch (IOException e) {
                    throw new RuntimeException("关闭失败！");
                }
        }
    }
}
