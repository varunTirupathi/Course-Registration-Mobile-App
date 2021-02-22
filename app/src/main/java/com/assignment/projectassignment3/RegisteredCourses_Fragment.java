package com.assignment.projectassignment3;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RegisteredCourses_Fragment extends Fragment {
    View v;
    private RecyclerView myrecyclerview;
    private List<Course> courses;
    RegisterViewAdapter.onCourseLongClick onCourseLongClick;

    CourseDBHelper coursedbhelper;

    public RegisteredCourses_Fragment() {
    }

    @Override
    public void onResume() {
        super.onResume();
        coursedbhelper = new CourseDBHelper(getContext());
        courses = coursedbhelper.registeredCourses();
        myrecyclerview = (RecyclerView) v.findViewById(R.id.registeredcourseslist_recyclerview);
        RegisterViewAdapter coursesviewadapter = new RegisterViewAdapter(getContext(), courses, onCourseLongClick);
        myrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        myrecyclerview.setAdapter(coursesviewadapter);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.registeredcourseslist_fragment, container, false);
        coursedbhelper = new CourseDBHelper(getContext());
        courses = coursedbhelper.registeredCourses();
        myrecyclerview = (RecyclerView) v.findViewById(R.id.registeredcourseslist_recyclerview);
        RegisterViewAdapter coursesviewadapter = new RegisterViewAdapter(getContext(), courses, onCourseLongClick);
        myrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        myrecyclerview.setAdapter(coursesviewadapter);
        return v;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getFragmentManager().beginTransaction().detach(RegisteredCourses_Fragment.this).attach(RegisteredCourses_Fragment.this).commit();
        }
    }
}