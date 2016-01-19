package com.my.diary;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainScreenActivity extends Activity {
	Button tambahdata;
	Button bacadata;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        addListenerOnButton();
    }

    public void addListenerOnButton(){
    	final Context context = this;
    	tambahdata = (Button) findViewById(R.id.tambah);
    	bacadata = (Button) findViewById(R.id.baca);
    	tambahdata.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				Intent i = new Intent(context, TambahData.class);
				startActivity(i);
			}
		});
    	bacadata.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				Intent a = new Intent(context, AllDiary.class);
				startActivity(a);
			}
		});
    	
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_screen, menu);
        return true;
    }
    
}
