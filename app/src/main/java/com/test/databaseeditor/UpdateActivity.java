package com.test.databaseeditor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.test.databaseeditor.model.Students;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UpdateActivity extends AppCompatActivity {

    EditText id,name,age,rollNo,dept;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        age = findViewById(R.id.updateAge);
        id = findViewById(R.id.updateId);
        rollNo = findViewById(R.id.updateRollNo);
        dept = findViewById(R.id.updateDept);
        name = findViewById(R.id.updateName);
        submit = findViewById(R.id.submitData);

        Bundle data = getIntent().getExtras();
        
        age.setText(data.get("age").toString());
        id.setText(data.get("id").toString());
        name.setText(data.get("name").toString());
        dept.setText(data.get("dept").toString());
        rollNo.setText(data.get("rollno").toString());

        submit.setOnClickListener((view) -> {
            updateData();
        });

    }

    public void updateData(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        Log.d("DATA:", "Data Sent");
        StringRequest strRequest = new StringRequest(Request.Method.POST, Constants.UPDATE_URL,
                response -> {
                    try {
                        JSONArray jArray = new JSONArray(response);
                        for (int i = 0; i < jArray.length(); i++) {
                            JSONObject json_data = jArray.getJSONObject(i);
                            Log.d("Response:", json_data.toString());
                            Toast.makeText(UpdateActivity.this, json_data.toString(), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                },
                error -> Toast.makeText(this, error.toString(), Toast.LENGTH_LONG).show()) {
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params  = new HashMap<>();
                params.put("rollno",rollNo.getText().toString());
                params.put("age",age.getText().toString());
                params.put("name",name.getText().toString());
                params.put("dept",dept.getText().toString());
                return params;
            }
        };
        requestQueue.add(strRequest);
    }
}