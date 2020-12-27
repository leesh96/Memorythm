package com.swp.memorythm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TemplateFragAdapter extends RecyclerView.Adapter<TemplateFragAdapter.TemplateViewHolder> {

    Context templateContext;
    ArrayList<Template> dataTemplate;

    public TemplateFragAdapter(Context templateContext, ArrayList<Template> dataTemplate) {
        this.templateContext = templateContext;
        this.dataTemplate = dataTemplate;
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @NonNull
    @Override
    public TemplateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(templateContext).inflate(R.layout.item_template_view, parent, false);
        TemplateViewHolder tvHolder = new TemplateViewHolder(v);
        return tvHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull TemplateViewHolder holder, int position) {
        holder.templateTitle.setText(dataTemplate.get(position).getTitle());
        holder.templateNum.setText(dataTemplate.get(position).getNum());

    }

    @Override
    public int getItemCount() {
        return (null != dataTemplate ? dataTemplate.size():0);
    }

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public static class TemplateViewHolder extends RecyclerView.ViewHolder{
        TextView templateTitle, templateNum;
        public TemplateViewHolder(@NonNull View itemView) {
            super(itemView);
            this.templateTitle = (TextView)itemView.findViewById(R.id.templateName);
            this.templateNum = (TextView)itemView.findViewById(R.id.templateNum);
        }
    }
}
