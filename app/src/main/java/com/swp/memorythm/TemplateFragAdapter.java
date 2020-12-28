package com.swp.memorythm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TemplateFragAdapter extends RecyclerView.Adapter<TemplateFragAdapter.TemplateViewHolder> implements ItemTouchHelperListener {

    Context templateContext;
    ArrayList<Template> dataTemplate = new ArrayList<>();
    public boolean isArray = false;

    public TemplateFragAdapter() { }

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

        if(isArray){
            holder.itemView.setClickable(false);
            holder.templateNum.setVisibility(View.INVISIBLE);

        }else{
            holder.itemView.setClickable(true);
            holder.templateNum.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return (null != dataTemplate ? dataTemplate.size():0);
    }

    public void setItems(ArrayList<Template> itemList){
        dataTemplate = itemList;
        notifyDataSetChanged();
    }

    @Override
    public boolean onItemMove(int from_position, int to_position) {
        Template template = dataTemplate.get(from_position);
        dataTemplate.remove(from_position);
        dataTemplate.add(to_position,template);
        notifyItemMoved(from_position,to_position);

        return true;
    }

    @Override
    public void onItemSwipe(int position) {

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
    public void setArray(boolean array){
        isArray = array;
    }
}