package com.test.databaseeditor.model;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.test.databaseeditor.Constants;
import com.test.databaseeditor.R;
import com.test.databaseeditor.UpdateActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import java.util.HashMap;

import java.util.Map;

@SuppressLint("NotifyDataSetChanged")


public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentItemHolder>  {
    final Activity activity;
    final ArrayList<Students> students;

    public StudentAdapter(Activity activity, ArrayList<Students> students) {
        this.activity = activity;
        this.students = students;

    }

    @NonNull
    @Override
        public StudentItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.student_layout, parent, false);
        return new StudentItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentAdapter.StudentItemHolder holderParent, int position) {
        final Students student = students.get(position);
         holderParent.age.setText(student.getAge());
         holderParent.id.setText(student.getId());
         holderParent.name.setText(student.getName());
         holderParent.dept.setText(student.getDept());
         holderParent.rollno.setText(student.getRollno());
         holderParent.click.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent intent = new Intent(activity, UpdateActivity.class);
                 intent.putExtra("rollno",student.getRollno());
                 intent.putExtra("age",student.getAge());
                 intent.putExtra("id",student.getId());
                 intent.putExtra("dept",student.getDept());
                 intent.putExtra("name",student.getName());
                 activity.startActivity(intent);
             }
         });
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    static class StudentItemHolder extends RecyclerView.ViewHolder {

        TextView id;
        TextView name;
        TextView age;
        TextView dept;
        TextView rollno;

        RelativeLayout click;

        public StudentItemHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            age = itemView.findViewById(R.id.age);
            dept = itemView.findViewById(R.id.dept);
            rollno = itemView.findViewById(R.id.rollno);
            id = itemView.findViewById(R.id.id);
            click = itemView.findViewById(R.id.click);
        }
    }

    public void fetchData(){
        students.clear();
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        Log.d("DATA:", "Data Sent");
        StringRequest strRequest = new StringRequest(Request.Method.GET, Constants.BASE_URL,
                response -> {
                    try {
                        JSONArray jArray = new JSONArray(response);
                        for (int i = 0; i < jArray.length(); i++) {
                            JSONObject json_data = jArray.getJSONObject(i);
                            Students student = new Students();
                            student.setAge(json_data.getString("age"));
                            student.setId(json_data.getString("id"));
                            student.setRollno(json_data.getString("rollno"));
                            student.setName(json_data.getString("name"));
                            student.setDept(json_data.getString("dept"));
                            students.add(student);
                            Log.d("Response:", student.toString());
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                },
                error -> Toast.makeText(activity, error.toString(), Toast.LENGTH_LONG).show()) {
            @Override
            protected Map<String, String> getParams() {
                return null;
            }
        };
        Log.d("new list", students.toString());
        requestQueue.add(strRequest);
        notifyDataSetChanged();
    }

    public void fetchOne(String val){
        students.clear();
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        Log.d("DATA:", "Data Sent");
        StringRequest strRequest = new StringRequest(Request.Method.POST, Constants.SELECT_URL,
                response -> {
                    try {
                        JSONArray jArray = new JSONArray(response);
                        for (int i = 0; i < jArray.length(); i++) {
                            JSONObject json_data = jArray.getJSONObject(i);
                            Students student = new Students();
                            student.setAge(json_data.getString("age"));
                            student.setId(json_data.getString("id"));
                            student.setRollno(json_data.getString("rollno"));
                            student.setName(json_data.getString("name"));
                            student.setDept(json_data.getString("dept"));
                            students.add(student);
                            Log.d("Response:", student.toString());
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                },
                error -> Toast.makeText(activity, error.toString(), Toast.LENGTH_LONG).show()) {
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params  = new HashMap<>();
                params.put("id",val);
                return params;
            }
        };
        Log.d("new list", students.toString());
        requestQueue.add(strRequest);
        notifyDataSetChanged();
    }


    }
