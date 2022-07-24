package com.example.yahtzee;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RuleAdapter extends RecyclerView.Adapter<RuleAdapter.RuleViewHolder>{
    private List<ObjectValue> rules;
    private RuleAdapter.OnRuleClickListener listener;

    public RuleAdapter(List<ObjectValue> rules, RuleAdapter.OnRuleClickListener listener) {
        this.rules = rules;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RuleAdapter.RuleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View ruleView = inflater.inflate(R.layout.rules_layout, parent, false);
        RuleViewHolder holder = new RuleViewHolder(ruleView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RuleAdapter.RuleViewHolder holder, int position) {
        ObjectValue rule = rules.get(position);
        holder.textViewRuleName.setText(rule.getName());
        holder.textViewRuleValue.setText(rule.getValue().toString());
    }

    @Override
    public int getItemCount() {
        return rules.size();
    }

    public class RuleViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewRuleName;
        private TextView textViewRuleValue;

        public RuleViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewRuleName = itemView.findViewById(R.id.textViewRuleName);
            textViewRuleValue = itemView.findViewById(R.id.textViewRuleValue);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onRuleClick(v, getAdapterPosition());
                }
            });
        }
    }

    public interface OnRuleClickListener {
        void onRuleClick(View source, int position);
    }
}
