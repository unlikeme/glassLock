package com.nemus.apps.glasslock;

import android.content.Context;
import android.util.Log;

import com.nemus.apps.glasslock.ui.LayerView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class DownLoader {

    private final static String TAG = "DownLoader";
    private final static int BUF_SIZE = 4096;
    
    private Object mObj;
    private ArrayList<Item> mList = new ArrayList<Item>();
    private File mDir;
    private DownloadThread mThread;
    
    private class Item{
        private Object obj;
        private String url;
        private String out;
        private Item(Object o, String u){
            obj = o;
            url = u;
            out = new File(url).getName();
            Log.d(TAG, "url : "+url+"   out : "+out);
        }
    }

    
    public DownLoader(Context ctx, Object obj){
//        mDir = new File("/sdcard");
        mDir = ctx.getFilesDir();
        mObj = obj;
        mList.clear();
    }
    
    public void addItem(Object obj, String url){
        mList.add(new Item(obj, url));
    }
    
    public void start(){
        mThread = new DownloadThread();
        mThread.start();
    }
    
    public void cancel(){
        if(mThread != null){
            mThread.interrupt();
        }
    }
    
    private void clearAll(){
        for(Item item : mList){
            File f = new File(mDir, item.out);
            if(f.exists()){
                f.delete();
            }
        }        
    }

    
    
    private class DownloadThread extends Thread{
        private boolean isStop;
        
        @Override
        public void interrupt() {
            super.interrupt();
        }
        
        @Override
        public void run() {
            
            boolean isErr = false;
            
            for(Item item : mList){
                
                if(isStop){
                    break;
                }
                InputStream is = null; 
                OutputStream os = null;
                
                try {
                    is = new URL(item.url).openStream();
                    File f = new File(mDir, item.out);
                    os = new FileOutputStream(f);
                    
                    byte[] bytes = new byte[BUF_SIZE];
                    for(;;) {
                        if(isStop){
                            break;
                        }
                        int count = is.read(bytes, 0, BUF_SIZE);
                        if (count == -1)
                            break;
                        os.write(bytes, 0, count);
                    }
                    is.close();
                    os.close();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    isErr = true;
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    isErr = true;
                } catch (IOException e) {
                    e.printStackTrace();
                    isErr = true;
                }
                if(isErr) break;

                ((LayerView)(item.obj)).succeeded(); // 아이템 하나 다운로드 완료
            }

            if( isStop ){
                clearAll();
                ((LayerView)mObj).canceled(); // 취소.
            }else{
                if(isErr){
                    clearAll();
                    ((LayerView)mObj).failed(); // 다운로드 실패
                }else{
                    ((LayerView)mObj).completed(); // 다운로드 성공
                }
            }
        }
    }
    
}




