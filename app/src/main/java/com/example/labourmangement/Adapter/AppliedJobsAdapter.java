package com.example.labourmangement.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.labourmangement.Contractor.JobApplyDetails;
import com.example.labourmangement.Labour.JobDetails;
import com.example.labourmangement.R;
import com.example.labourmangement.model.AppliedJobsModel;
import com.example.labourmangement.model.JobModel;

import java.util.List;

import static com.android.volley.VolleyLog.TAG;

public class AppliedJobsAdapter extends RecyclerView.Adapter<AppliedJobsAdapter.ViewHolder> {

    private Context context;
    private List<AppliedJobsModel> appliedjob;

    public  interface OnItemClickListener{
        void onClick(View view);

        void onItemClick(int position);
    }
    public AppliedJobsAdapter(Context context, List appliedjob) {
        this.context = context;
        this.appliedjob = appliedjob;
    }

    @Override
    public AppliedJobsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_job_list, parent, false);
        AppliedJobsAdapter.ViewHolder viewHolder = new AppliedJobsAdapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AppliedJobsAdapter.ViewHolder holder, int position) {
        holder.itemView.setTag(appliedjob.get(position));

        AppliedJobsModel pu = appliedjob.get(position);

        Log.d("job id","40 "+pu.getJob_id());
        // holder.job_id.setText(pu.getJob_id());
        holder.jobtitle.setText(pu.getJob_title());
        holder.job_details.setText(pu.getJob_details());
        holder.job_wages.setText(pu.getJob_wages());
        holder.job_area.setText(pu.getJob_area());
        holder.job_id.setText(pu.getJob_id());
        holder.appliedby.setText(pu.getApplied_by());
        holder.applieddate.setText(pu.getApplied_date());

        holder.jobtitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: Product Name: " + appliedjob.get(position));
                Intent intent = new Intent(context, JobApplyDetails.class);
                intent.putExtra("job_title", pu.getJob_title());
                intent.putExtra("job_details", pu.getJob_details());
                intent.putExtra("job_wages",pu.getJob_wages());
                intent.putExtra("job_area",pu.getJob_area());
                intent.putExtra("job_id",pu.getJob_id());
                intent.putExtra("applied_by",pu.getApplied_by());
                intent.putExtra("applied_date",pu.getApplied_date());

                context.startActivity(intent);
            }
        });
        holder.job_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: Product Name: " + appliedjob.get(position));
                Intent intent = new Intent(context, JobApplyDetails.class);
                intent.putExtra("job_title", pu.getJob_title());
                intent.putExtra("job_details", pu.getJob_details());
                intent.putExtra("job_wages",pu.getJob_wages());
                intent.putExtra("job_area",pu.getJob_area());
                intent.putExtra("job_id",pu.getJob_id());
                intent.putExtra("applied_by",pu.getApplied_by());
                intent.putExtra("applied_date",pu.getApplied_date());
                context.startActivity(intent);
            }
        });
        holder.job_wages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: Product Name: " + appliedjob.get(position));
                Intent intent = new Intent(context, JobApplyDetails.class);
                intent.putExtra("job_title", pu.getJob_title());
                intent.putExtra("job_details", pu.getJob_details());
                intent.putExtra("job_wages",pu.getJob_wages());
                intent.putExtra("job_area",pu.getJob_area());
                intent.putExtra("job_id",pu.getJob_id());
                intent.putExtra("applied_by",pu.getApplied_by());
                intent.putExtra("applied_date",pu.getApplied_date());
                context.startActivity(intent);
            }
        });

        holder.job_area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: Product Name: " + appliedjob.get(position));
                Intent intent = new Intent(context, JobApplyDetails.class);
                intent.putExtra("job_title", pu.getJob_title());
                intent.putExtra("job_details", pu.getJob_details());
                intent.putExtra("job_wages",pu.getJob_wages());
                intent.putExtra("job_area",pu.getJob_area());
                intent.putExtra("job_id",pu.getJob_id());
                intent.putExtra("applied_by",pu.getApplied_by());
                intent.putExtra("applied_date",pu.getApplied_date());
                context.startActivity(intent);
            }
        });
        holder.appliedby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: Product Name: " + appliedjob.get(position));
                Intent intent = new Intent(context, JobApplyDetails.class);
                intent.putExtra("job_title", pu.getJob_title());
                intent.putExtra("job_details", pu.getJob_details());
                intent.putExtra("job_wages",pu.getJob_wages());
                intent.putExtra("job_area",pu.getJob_area());
                intent.putExtra("job_id",pu.getJob_id());
                intent.putExtra("applied_by",pu.getApplied_by());
                intent.putExtra("applied_date",pu.getApplied_date());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return appliedjob.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView jobtitle;
        public TextView job_details;
        public TextView job_wages;
        public  TextView job_area;
        public  TextView job_id;
        public  TextView appliedby;
        public  TextView applieddate;

        public ViewHolder(View itemView) {
            super(itemView);

            jobtitle = (TextView) itemView.findViewById(R.id.job_title);
            job_details = (TextView) itemView.findViewById(R.id.job_details);
            job_wages = (TextView) itemView.findViewById(R.id.job_wages);
            job_area = (TextView) itemView.findViewById(R.id.job_area);
            job_id = (TextView) itemView.findViewById(R.id.job_id);
            appliedby = (TextView) itemView.findViewById(R.id.applied_by);
            applieddate = (TextView) itemView.findViewById(R.id.applieddate);

        }
    }
}
