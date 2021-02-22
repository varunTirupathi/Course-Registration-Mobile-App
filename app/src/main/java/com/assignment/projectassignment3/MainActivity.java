package com.assignment.projectassignment3;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    CourseDBHelper coursedbhelper;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    ArrayList<Course> courses;
    ArrayList<Course> regiC;
    AsyncHttpClient client;
    private CoursesViewAdapter.OnCourseListener mCourseListner;
    private RecyclerView myrecyclerview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        coursedbhelper = new CourseDBHelper(this);
        courses = new ArrayList<Course>();
        regiC = new ArrayList<Course>();
        client = new AsyncHttpClient();
        mCourseListner = new CoursesViewAdapter.OnCourseListener() {
            @Override
            public void onCourseClick(int p) {
                try {
                    Course course = courses.get(p);
                    EditText courseId = (EditText) findViewById(R.id.courseIds);
                    EditText coursename = (EditText) findViewById(R.id.courseName);
                    EditText term = (EditText) findViewById(R.id.terms);
                    EditText prerequisite = (EditText) findViewById(R.id.prerequsite);

                    courseId.setText(course.getCourseId());
                    coursename.setText(course.getCourseName());
                    term.setText(course.getTerm());
                    prerequisite.setText(course.getPrerequisite());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        };
        client.get("http://35.203.95.76:8809/course-details/", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);

                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = (JSONObject) response.getJSONObject(i);

                        String pre = "";
                        String condition = "";
                        if (jsonObject.getString("condition").equals("1")) {
                            condition = " or ";
                        } else if (jsonObject.getString("condition").equals("2")) {
                            condition = " and ";
                        }
                        if (!TextUtils.isEmpty(jsonObject.getString("prerequitiesOne"))) {
                            pre = pre + jsonObject.getString("prerequitiesOne").toUpperCase();
                        }
                        if (!TextUtils.isEmpty(jsonObject.getString("prerequitiesTwo"))) {
                            pre = pre + condition + jsonObject.getString("prerequitiesTwo").toUpperCase();
                        }
                        int isReg = 1;
                        if (jsonObject.getBoolean("seletionStatus")) {
                            isReg = 0;
                        }
                        if (pre.equals("")) {
                            pre = "Not Available";
                        }
                        courses.add(new Course(i, jsonObject.getString("coursename"), jsonObject.getString("courseNumber"), pre, jsonObject.getString("term"), jsonObject.getString("imageUrl")));
                        coursedbhelper.insertData(i, jsonObject.getString("coursename"), jsonObject.getString("courseNumber"), pre, jsonObject.getString("term"), isReg, jsonObject.getString("imageUrl"));
                        if (jsonObject.getString("courseNumber").toUpperCase().equals("CS161") || jsonObject.getString("courseNumber").toUpperCase().equals("CS162") || jsonObject.getString("courseNumber").toUpperCase().equals("CS255") || jsonObject.getString("courseNumber").toUpperCase().equals("CS263")) {
                            regiC.add(new Course(i, jsonObject.getString("coursename"), jsonObject.getString("courseNumber"), pre, jsonObject.getString("term"), jsonObject.getString("imageUrl")));
                        }
                    }
                    myrecyclerview = (RecyclerView) findViewById(R.id.courseslist_recyclerview);
                    final CoursesViewAdapter courseviewadapter = new CoursesViewAdapter(courses, mCourseListner);
                    myrecyclerview.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    myrecyclerview.setAdapter(courseviewadapter);
                    for (int k = 0; k < regiC.size(); k++) {
                        int j = courses.size() + k + 1;
                        coursedbhelper.insertData(j, regiC.get(k).getCourseName(), regiC.get(k).getCourseId(), regiC.get(k).getPrerequisite(), regiC.get(k).getTerm(), 0, regiC.get(k).getImageUrl());

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                System.out.println("Success response from server" + statusCode);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Toast.makeText(MainActivity.this, "Failed to retrive data from server.", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject response) {
                super.onFailure(statusCode, headers, throwable, response);
                Toast.makeText(MainActivity.this, "Failed to retrive data from server.", Toast.LENGTH_LONG).show();
                System.out.println("Success response from server" + statusCode);
            }

            @Override
            public void onRetry(int retryNo) {
                System.out.println("retryNo::" + retryNo);
            }
        });
        tabLayout = (TabLayout) findViewById(R.id.firstab);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), 3);
        // add fragments here

        adapter.AddFragment(new CoursesList_Fragment(), "Available Courses");
        adapter.AddFragment(new RegisteredCourses_Fragment(), "Registered courses");

        RegisteredCourses_Fragment reg = new RegisteredCourses_Fragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.mainid, reg, "firstfragment");
        ft.commit();

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Fragment fragment = adapter.getFragment(position);
                if (position == 1 && fragment != null) {
                    fragment.onResume();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

}