package com.example.yahtzee;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RuleAdapter extends RecyclerView.Adapter<RuleAdapter.DiceViewHolder>{
    private List<ObjectValue> dices;
    private DiceAdapter.OnDiceCLickListener listener;

    public RuleAdapter(List<ObjectValue> dices, DiceAdapter.OnDiceCLickListener listener) {
        this.dices = dices;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RuleAdapter.DiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View diceView = inflater.inflate(R.layout.rules_layout, parent, false);
        RuleAdapter.DiceViewHolder holder = new RuleAdapter.DiceViewHolder(diceView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RuleAdapter.DiceViewHolder holder, int position) {
        ObjectValue dice = dices.get(position);
        holder.textViewRuleName.setText(dice.getName());
        holder.textViewRuleValue.setText(dice.getValue().toString());
    }

    @Override
    public int getItemCount() {
        return dices.size();
    }

    public class DiceViewHolder  extends RecyclerView.ViewHolder {
        private TextView textViewRuleName;
        private TextView textViewRuleValue;

        public DiceViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewRuleName = itemView.findViewById(R.id.textViewRuleName);
            textViewRuleValue = itemView.findViewById(R.id.textViewRuleValue);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onDiceClick(v, getAdapterPosition());
                }
            });
        }
    }

    public interface OnDiceCLickListener {
        void onDiceClick(View source, int position);
    }
}
