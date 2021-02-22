package com.assignment.projectassignment3;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RegisterViewAdapter extends RecyclerView.Adapter<RegisterViewAdapter.RegisteredViewHolder> {

    private Context context;
    List<Course> courses;
    onCourseLongClick onCourseLongClick;

    public RegisterViewAdapter(Context context, List<Course> courses, onCourseLongClick onCourseLongClick) {
        this.context = context;
        this.courses = courses;
        this.onCourseLongClick = onCourseLongClick;
    }

    @NonNull
    @Override
    public RegisteredViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(context).inflate(R.layout.course, parent, false);
        RegisteredViewHolder viewholder = new RegisteredViewHolder(v, onCourseLongClick);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull final RegisteredViewHolder courseholder, final int position) {
        courseholder.coursename.setText(courses.get(position).getCourseName());
        courseholder.courseID.setText(courses.get(position).getCourseId());
        courseholder.pre_requisite.setText(courses.get(position).getPrerequisite());
        courseholder.term.setText(courses.get(position).getTerm());
        courseholder.coursedbhelper = new CourseDBHelper(context);
        courseholder.layout.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View view) {
                courseholder.coursedbhelper.deleteCourse(courses.get(position).getUniqueColumnId());
                toast("Course " + courseholder.courseID.getText() + " dropped Successfully, Please refresh the tab", Color.GREEN);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    public static class RegisteredViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {

        private TextView coursename;
        private TextView courseID;
        private TextView pre_requisite;
        private TextView term;
        private LinearLayout layout;
        CourseDBHelper coursedbhelper;
        RecyclerView recyclerView;
        onCourseLongClick onCourseLongClick;


        public RegisteredViewHolder(View view, onCourseLongClick onCourseLongClick) {
            super(view);
            coursename = (TextView) view.findViewById(R.id.name_course);
            courseID = (TextView) view.findViewById(R.id.courseid);
            pre_requisite = (TextView) view.findViewById(R.id.pre_requisite);
            term = (TextView) view.findViewById(R.id.term);
            layout = (LinearLayout) view.findViewById(R.id.courseslist);
            recyclerView = (RecyclerView) view.findViewById(R.id.registeredcourseslist_recyclerview);
            this.onCourseLongClick = onCourseLongClick;
            view.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View view) {
            onCourseLongClick.onCourseLongClick(getAdapterPosition());
            return false;
        }
    }

    public interface onCourseLongClick {
        void onCourseLongClick(int position);
    }

    public void toast(String message, int color) {
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
        TextView view = (TextView) toast.getView().findViewById(android.R.id.message);
        view.setTextColor(color);
        toast.show();
    }
}