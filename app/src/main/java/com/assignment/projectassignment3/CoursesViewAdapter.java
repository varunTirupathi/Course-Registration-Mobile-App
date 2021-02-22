package com.assignment.projectassignment3;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class CoursesViewAdapter extends RecyclerView.Adapter<CoursesViewAdapter.MyViewHolder> {

    Context context;
    List<Course> courses;
    CourseDBHelper coursedbhelper;
    private OnCourseListener mCourseListner;

    public CoursesViewAdapter(List<Course> courses, OnCourseListener mCourseListner) {
        this.courses = courses;
        this.mCourseListner = mCourseListner;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.course, parent, false);
        MyViewHolder viewholder = new MyViewHolder(v, mCourseListner, viewType);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder courseholder, final int position) {
        courseholder.coursename.setText(courses.get(position).getCourseName());
        courseholder.courseID.setText(courses.get(position).getCourseId());
        courseholder.pre_requisite.setText(courses.get(position).getPrerequisite());
        courseholder.term.setText(courses.get(position).getTerm());
        if (!TextUtils.isEmpty(courses.get(position).getImageUrl())) {
            Picasso.get().load(courses.get(position).getImageUrl()).placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background).resize(90, 70).centerCrop().into(courseholder.im);
        }
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView coursename;
        private TextView courseID;
        private TextView pre_requisite;
        private TextView term;
        private LinearLayout layout;
        private EditText courseid;
        private ImageView im;
        OnCourseListener onCourseListener;

        public MyViewHolder(View view, OnCourseListener onCourseListener, int position) {
            super(view);
            coursename = (TextView) view.findViewById(R.id.name_course);
            courseID = (TextView) view.findViewById(R.id.courseid);
            pre_requisite = (TextView) view.findViewById(R.id.pre_requisite);
            term = (TextView) view.findViewById(R.id.term);
            courseid = (EditText) view.findViewById(R.id.courseIds);
            layout = (LinearLayout) view.findViewById(R.id.courseslist);
            im = (ImageView) view.findViewById(R.id.courseImage);
            this.onCourseListener = onCourseListener;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onCourseListener.onCourseClick(getAdapterPosition());
        }
    }

    public interface OnCourseListener {
        void onCourseClick(int position);
    }
}