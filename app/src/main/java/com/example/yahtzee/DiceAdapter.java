package com.example.yahtzee;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DiceAdapter extends RecyclerView.Adapter<DiceAdapter.DiceViewHolder>{
    private List<ObjectValue> dices;
    private OnDiceCLickListener listener;

    public DiceAdapter(List<ObjectValue> dices, OnDiceCLickListener listener) {
        this.dices = dices;
        this.listener = listener;
    }

    @NonNull
    @Override
    public DiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View diceView = inflater.inflate(R.layout.dice_layout, parent, false);
        DiceViewHolder holder = new DiceViewHolder(diceView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull DiceViewHolder holder, int position) {
        ObjectValue dice = dices.get(position);
        holder.textViewDiceName.setText(dice.getName());
        holder.textViewDiceValue.setText(dice.getValue().toString());
    }

    @Override
    public int getItemCount() {
        return dices.size();
    }

    public class DiceViewHolder  extends RecyclerView.ViewHolder {
        private TextView textViewDiceName;
        private TextView textViewDiceValue;

        public DiceViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewDiceName = itemView.findViewById(R.id.textViewDiceName);
            textViewDiceValue = itemView.findViewById(R.id.textViewDiceValue);

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
