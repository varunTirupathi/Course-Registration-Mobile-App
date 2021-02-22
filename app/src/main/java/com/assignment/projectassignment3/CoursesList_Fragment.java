package com.assignment.projectassignment3;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class CoursesList_Fragment extends Fragment implements CoursesViewAdapter.OnCourseListener {

    View v;
    private RecyclerView myrecyclerview;
    private List<Course> courses;
    private List<Course> regCourses;
    CourseDBHelper coursedbhelper;
    RegisterViewAdapter.onCourseLongClick onCourseLongClick;
    JSONObject response;

    public CoursesList_Fragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.courseslist_fragment, container, false);
        coursedbhelper = new CourseDBHelper(getContext());
        courses = coursedbhelper.getTotalAvailableCourses();
        myrecyclerview = (RecyclerView) v.findViewById(R.id.courseslist_recyclerview);
        final CoursesViewAdapter courseviewadapter = new CoursesViewAdapter(courses, this);
        myrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        myrecyclerview.setAdapter(courseviewadapter);

        Button registerBtn = (Button) v.findViewById(R.id.addCourseBtn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText courseId = (EditText) v.findViewById(R.id.courseIds);
                EditText courseName = (EditText) v.findViewById(R.id.courseName);
                EditText term = (EditText) v.findViewById(R.id.terms);
                EditText prerequsite = (EditText) v.findViewById(R.id.prerequsite);
                String p = "";
                AsyncHttpClient client = new AsyncHttpClient();

                courses = coursedbhelper.getallCoursesdata();
                int i = courses.size();
                i++;

                if (!TextUtils.isEmpty(courseName.getText()) && !TextUtils.isEmpty(courseId.getText())) {
                    if (!alreadyExist(courseId.getText().toString())) {
                        if (checktermcoursescount(term.getText().toString())) {
                            if (!isPrerequisite(prerequsite.getText().toString())) {
                                coursedbhelper.insertData(i, courseName.getText().toString(), courseId.getText().toString(), prerequsite.getText().toString(), term.getText().toString(), 0, "");
                                Toast toast = Toast.makeText(getContext(), "Course " + courseId.getText() + " registered successfully", Toast.LENGTH_SHORT);
                                toast.show();
                                try {
                                    JSONObject jsonParams = new JSONObject();
                                    jsonParams.put("student_id", "201906393");
                                    jsonParams.put("name", "Varun");
                                    jsonParams.put("email", "x2019ftu@stfx.ca");
                                    jsonParams.put("course", courseName.getText().toString());
                                    StringEntity entity = new StringEntity(jsonParams.toString());

                                    client.post(getContext(), "http://35.203.95.76:8809/register/", entity, "application/json", new JsonHttpResponseHandler() {
                                        @Override
                                        public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                                            super.onSuccess(statusCode, headers, response);
                                            System.out.println("Success response from server" + statusCode);
                                        }

                                        @Override
                                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                            super.onSuccess(statusCode, headers, response);
                                            System.out.println("Success response from server" + statusCode);
                                        }

                                        @Override
                                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                                            super.onFailure(statusCode, headers, responseString, throwable);
                                            //Toast.makeText(getContext(),"Failed to retrive data from server.",Toast.LENGTH_LONG).show();
                                        }

                                        @Override
                                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject response) {
                                            super.onFailure(statusCode, headers, throwable, response);
                                            System.out.println("response from server" + statusCode);
                                        }

                                        @Override
                                        public void onRetry(int retryNo) {
                                        }
                                    });
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            } else {
                                Toast toast = Toast.makeText(getContext(), "Course has prerequisite please register for prerequisite course first", Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        } else {
                            Toast toast = Toast.makeText(getContext(), "Cannot add more than 3 courses per term", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    } else {
                        Toast toast = Toast.makeText(getContext(), "Course already exists", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                } else {
                    Toast toast = Toast.makeText(getContext(), "Cannot add course without course details", Toast.LENGTH_SHORT);
                    toast.show();
                }
                regCourses = coursedbhelper.registeredCourses();
                RegisterViewAdapter coursesviewadapter = new RegisterViewAdapter(getContext(), regCourses, onCourseLongClick);
                coursesviewadapter.notifyItemInserted(i);
            }

        });
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCourseClick(int p) {

        Course course = courses.get(p);
        System.out.println(course.getCourseId());
        EditText courseId = (EditText) v.findViewById(R.id.courseIds);
        EditText coursename = (EditText) v.findViewById(R.id.courseName);
        EditText term = (EditText) v.findViewById(R.id.terms);
        EditText prerequisite = (EditText) v.findViewById(R.id.prerequsite);

        courseId.setText(course.getCourseId());
        coursename.setText(course.getCourseName());
        term.setText(course.getTerm());
        prerequisite.setText(course.getPrerequisite());
    }

    public boolean checktermcoursescount(String term) {

        List<Course> courses = new ArrayList<>();
        courses = coursedbhelper.registeredCourses();

        int firstTerm = 0, secondterm = 0;
        boolean flag = false;
        for (int i = 0; i < courses.size(); i++) {
            Course coursess = courses.get(i);

            if (term.equalsIgnoreCase(coursess.getTerm())) {
                firstTerm++;
            }
        }
        if (firstTerm < 3) {
            flag = true;
        }
        return flag;
    }

    // Checks the whether the course already registered or not?
    public boolean alreadyExist(String courseId) {

        List<Course> courses = new ArrayList<>();
        courses = coursedbhelper.registeredCourses();

        int firstTerm = 0, secondterm = 0;
        boolean flag = false;
        for (int i = 0; i < courses.size(); i++) {
            Course coursess = courses.get(i);

            if (courseId.equalsIgnoreCase(coursess.getCourseId())) {
                return true;
            }
        }
        return false;
    }

    // check the prerequsite validation
    public boolean isPrerequisite(String pre) {
        boolean flag = false;
        if (pre.equals("Not Available")) {
            return false;
        } else {
            List<Course> courses = new ArrayList<>();
            courses = coursedbhelper.registeredCourses();
            int firstTerm = 0, secondterm = 0;
            boolean a = false;
            boolean b = false;
            for (int i = 0; i < courses.size(); i++) {
                Course coursess = courses.get(i);

                if (pre.contains("and")) {
                    if (!a && pre.contains(coursess.getCourseId())) {
                        a = true;
                    } else if (pre.contains(coursess.getCourseId())) {
                        b = true;
                    }
                    if (a & b) {
                        flag = true;
                    }
                } else {
                    if (pre.contains(coursess.getCourseId())) {
                        flag = true;
                    } else if (pre.contains("or") && pre.contains(coursess.getCourseId())) {
                        flag = true;
                    }
                }
            }
        }
        if (!flag) {
            return true;
        } else {
            return false;
        }
    }

    public void toast(String message, int color) {
        Toast toast = Toast.makeText(getActivity(), message, Toast.LENGTH_LONG);
        TextView view = (TextView) toast.getView().findViewById(android.R.id.message);
        view.setTextColor(color);
        toast.show();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getFragmentManager().beginTransaction().detach(CoursesList_Fragment.this).attach(CoursesList_Fragment.this).commit();
        }
    }
}