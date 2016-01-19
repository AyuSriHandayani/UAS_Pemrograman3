package com.my.diary;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.List;

public class AllDiary extends Activity {
	ListView list;
	JSONParser jParser = new JSONParser();
	ArrayList<Diary> daftar_diary = new ArrayList<Diary>();
	JSONArray daftarDiary = null;
	String url_read_diary = "http://192.168.1.116/AppMyDiary/get_all_diary.php";
	
	// JSON Node names, ini harus sesuai yang di API
	public static final String TAG_SUCCESS = "success";
	public static final String TAG_DIARY = "diary";
	public static final String TAG_ID = "id";
	public static final String TAG_Judul = "judul";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_all_diary);
		
		list = (ListView) findViewById(R.id.listView);
		//jalankan ReadMhsTask
		ReadDiary m= (ReadDiary) new ReadDiary().execute();
		list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int
			urutan, long id) {
			//dapatkan data id, nama, nim mahasiswa dan simpan dalam
			String ID = ((TextView)
			view.findViewById(R.id.idD)).getText().toString();
			String Judul = ((TextView)
			view.findViewById(R.id.judul)).getText().toString();
			//varible string tadi kita simpan dalam suatu Bundle, dan kita parse bundle tersebut bersama intent menuju kelas UpdateDeleteActivity
			Intent a = null;
			a = new Intent(AllDiary.this, TambahData.class);
			Bundle b = new Bundle();
			b.putString("ID", ID);
			b.putString("Judul", Judul);
			a.putExtras(b);
			startActivity(a);
			}
			});
	}

	
	class ReadDiary extends AsyncTask<String, Void, String>{
		 ProgressDialog pDialog;
		 
		 @Override
		 protected void onPreExecute() {
			 super.onPreExecute();
			 pDialog = new ProgressDialog(AllDiary.this);
			 pDialog.setMessage("Mohon menunggu..");
			 pDialog.setIndeterminate(true);
			 pDialog.setCancelable(true);
			 pDialog.show();
		 }
		 
		 @Override
		 protected String doInBackground(String... sText) {
			 String returnResult = getDiaryList(); //memanggil method getMhsList()
			 return returnResult;
		 }
		
		 @Override
		 protected void onPostExecute(String result) {
			 super.onPostExecute(result);
			 pDialog.dismiss();
			 if(result.equalsIgnoreCase("Exception Caught")){
				 
				 Toast.makeText(AllDiary.this, "Unable to connect to server,please check your internet connection!", Toast.LENGTH_LONG).show();
			 }
			 if(result.equalsIgnoreCase("no results")){
				 Toast.makeText(AllDiary.this, "Data empty", Toast.LENGTH_LONG).show();
			 }
			 else{
		     //Adapter menampilkan data mahasiswa ke dalam listView
				 list.setAdapter(new DiaryAdapter(AllDiary.this, daftar_diary));
			 }
		}
		 
		//method untuk memperoleh daftar mahasiswa dari JSON
		public String getDiaryList(){
			Diary tempDiary = new Diary();
			List<NameValuePair> parameter = new ArrayList<NameValuePair>();
			try {
				JSONObject json = jParser.makeHttpRequest(url_read_diary,"POST",parameter);
				int success = json.getInt(TAG_SUCCESS);
				if (success == 1) { //Ada record Data (SUCCESS = 1)
					//Getting Array of daftar_mhs
					daftarDiary = json.getJSONArray(TAG_DIARY);
					// looping through All daftar_mhs
					for (int i = 0; i < daftarDiary.length() ; i++){
						JSONObject c = daftarDiary.getJSONObject(i);
						tempDiary = new Diary();
						tempDiary.setidD(c.getString(TAG_ID));
						tempDiary.setjudul(c.getString(TAG_Judul));
					}
					return "OK";
				}
				else {
					//Tidak Ada Record Data (SUCCESS = 0)
					return "no results";
				}
			} catch (Exception e) {
				e.printStackTrace();
				return "Exception Caught";
			}
		}
	   }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.all_diary, menu);
		return true;
	}

}
