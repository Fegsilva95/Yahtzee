package com.example.yahtzee;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    RecyclerView dicesRecyclerView;
    RecyclerView rulesRecyclerView;
    ActivityResultLauncher<Intent> launcher;
    List<ObjectValue> dices;
    List<ObjectValue> rules;
    DiceAdapter diceAdapter;
    RuleAdapter ruleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dices = new ArrayList<ObjectValue>(){{
            add (new ObjectValue("Dice 1"));
            add (new ObjectValue("Dice 2"));
            add (new ObjectValue("Dice 3"));
            add (new ObjectValue("Dice 4"));
            add (new ObjectValue("Dice 5"));
            add (new ObjectValue("Dice 6"));
        }};

        dicesRecyclerView = findViewById(R.id.diceRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        dicesRecyclerView.setLayoutManager(layoutManager);

        DiceAdapter.OnDiceCLickListener listener = new DiceAdapter.OnDiceCLickListener() {
            @Override
            public void onDiceClick(View source, int diceNumber) {
                ObjectValue dice = dices.get(diceNumber);
                Integer randomNumber = 0;
                while (randomNumber == 0){
                    randomNumber = new Random().nextInt(7);
                }
                dice.setValue(randomNumber);
                diceAdapter.notifyItemChanged(diceNumber);
            }
        };

        diceAdapter = new DiceAdapter(dices, listener);
        dicesRecyclerView.setAdapter(diceAdapter);

        rules = new ArrayList<ObjectValue>(){{
            add (new ObjectValue("Rule 1"));
            add (new ObjectValue("Rule 2"));
            add (new ObjectValue("Rule 3"));
            add (new ObjectValue("Rule 4"));
            add (new ObjectValue("Rule 5"));
            add (new ObjectValue("Rule 6"));
        }};

        rulesRecyclerView = findViewById(R.id.pointsRecyclerView);
        LinearLayoutManager rulesLayoutManager = new LinearLayoutManager(this);
        rulesRecyclerView.setLayoutManager(rulesLayoutManager);

        RuleAdapter.OnRuleClickListener ruleListener = new RuleAdapter.OnRuleClickListener() {
            @Override
            public void onRuleClick(View source, int ruleNumber) {
                ObjectValue rule = rules.get(ruleNumber);
                rule.setValue(0);
                ruleAdapter.notifyItemChanged(ruleNumber);
            }
        };

        ruleAdapter = new RuleAdapter(rules, ruleListener);
        rulesRecyclerView.setAdapter(ruleAdapter);

        launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {

                    }
                });
    }

    public void credits(View view){
        Intent intent = new Intent(MainActivity.this, CreditsActivity.class);
        launcher.launch(intent);
    }
}