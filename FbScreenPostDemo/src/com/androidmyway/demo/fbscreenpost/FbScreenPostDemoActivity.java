package com.androidmyway.demo.fbscreenpost;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.AsyncFacebookRunner.RequestListener;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;

public class FbScreenPostDemoActivity extends Activity {
	//Creating Facebook object 
	Facebook facebook = new Facebook("333119860104217");
	//Facebook facebook = new Facebook("Your facebook app id");
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    
    public void onFbButtonClick(View v){
		
    	//You need to call facebookLogout method first to finish any on going session of fb.
    	
		facebookLogout();
		
		facebook.authorize(this,new String[]{"publish_stream"}, new Facebook.DialogListener() {
          
            public void onFacebookError(FacebookError e) {
                // TODO Auto-generated method stub
                facebookLogout();
            }
          
            public void onError(DialogError e) {
                // TODO Auto-generated method stub
                facebookLogout();
            }

            public void onComplete(Bundle values) {
                // TODO Auto-generated method stub
                try {
                	
                	//Need to create a object of your root layout 
                	
                	RelativeLayout v = (RelativeLayout) findViewById(R.id.rootLayout);
                    
                    v.setDrawingCacheEnabled(true);
                    v.buildDrawingCache();
                   
                    byte[] data = null;

                    Bitmap bi = v.getDrawingCache();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bi.compress(Bitmap.CompressFormat.JPEG,50, baos);
                    data = baos.toByteArray();
                    Bundle parameters = new Bundle();
                    parameters.putString(Facebook.TOKEN, facebook.getAccessToken());
                                     
                    parameters.putString("method", "photos.upload");
                    parameters.putByteArray("picture", data);
                   
                   
                    AsyncFacebookRunner mAsyncRunner = new AsyncFacebookRunner(facebook);
                    mAsyncRunner.request(null, parameters, "POST", new RequestListener() {
                       
                        public void onMalformedURLException(MalformedURLException e, Object state) {
                            // TODO Auto-generated method stub
                           
                        }
                      
                        public void onIOException(IOException e, Object state) {
                            // TODO Auto-generated method stub
                           
                        }
                      
                        public void onFileNotFoundException(FileNotFoundException e, Object state) {
                            // TODO Auto-generated method stub
                           
                        }
                      
                        public void onFacebookError(FacebookError e, Object state) {
                            // TODO Auto-generated method stub
                           
                        }
                      
                        public void onComplete(String response, Object state) {
                            // TODO Auto-generated method stub
                           
                        }
                    }, null);
                   
                } catch (Exception e) {
                    // TODO: handle exception
                }

                facebookLogout();
            }

          
            public void onCancel() {
                // TODO Auto-generated method stub
                facebookLogout();
            }
        });
		
	}
	
	void facebookLogout(){
        try {
            facebook.logout(this);
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }
    
}