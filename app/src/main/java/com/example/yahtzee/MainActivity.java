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
import android.widget.TextView;

import java.sql.SQLOutput;
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
    TextView totalPoints;
    TextView highestScoreValue;
    Integer highestScore = 0;

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
            add (new ObjectValue("Aces"));
            add (new ObjectValue("Twos"));
            add (new ObjectValue("Threes"));
            add (new ObjectValue("Fours"));
            add (new ObjectValue("Fives"));
            add (new ObjectValue("Sixes"));
            add (new ObjectValue("Three Of A Kind"));
            add (new ObjectValue("Four Of A Kind"));
            add (new ObjectValue("Full House"));
            add (new ObjectValue("Small Straight"));
            add (new ObjectValue("Large Straight"));
            add (new ObjectValue("Yahtzee"));
            add (new ObjectValue("Chance"));
        }};

        rulesRecyclerView = findViewById(R.id.pointsRecyclerView);
        LinearLayoutManager rulesLayoutManager = new LinearLayoutManager(this);
        rulesRecyclerView.setLayoutManager(rulesLayoutManager);

        RuleAdapter.OnRuleClickListener ruleListener = new RuleAdapter.OnRuleClickListener() {
            @Override
            public void onRuleClick(View source, int ruleNumber) {
                ObjectValue rule = rules.get(ruleNumber);
                if(!rule.verifyEmptyValues(dices)){
                    System.out.println("Empty Dices");
                }
                Integer result = rule.verifyRules(dices, ruleNumber);

                if(result != 0){
                    rule.setValue(result);
                    rule.restartValues(dices);
                    for (Integer i = 0; i <= dices.size(); i++) {
                        diceAdapter.notifyItemChanged(i);
                    }

                    totalPoints = findViewById(R.id.totalPointsValue);
                    totalPoints.setText(totalPointsValue().toString());
                } else {
                    System.out.println("Regra não aplicada");
                }

                ruleAdapter.notifyItemChanged(ruleNumber);
                result = 0;
            }
        };

        ruleAdapter = new RuleAdapter(rules, ruleListener);
        rulesRecyclerView.setAdapter(ruleAdapter);

        launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {}
                });
    }

    public Integer totalPointsValue(){
        Integer result = 0;
        for (ObjectValue rule : rules) {
            result += rule.getValue();
        }
        return result;
    }

    public void credits(View view){
        Intent intent = new Intent(MainActivity.this, CreditsActivity.class);
        launcher.launch(intent);
    }

    public void restart(View view){
        ObjectValue verify = new ObjectValue("");
        if(!verify.verifyEmptyValues(rules)){
            System.out.println("Alguma rule vazia");
        } else {
            verify.restartValues(rules);
            verify.restartValues(dices);
            for (Integer i = 0; i <= rules.size(); i++) {
                ruleAdapter.notifyItemChanged(i);
            }
            for (Integer i = 0; i <= dices.size(); i++) {
                diceAdapter.notifyItemChanged(i);
            }

            highestScoreValue = findViewById(R.id.highestScoreValue);
            Integer totalPValue = Integer.parseInt(totalPoints.getText().toString());
            System.out.println(totalPValue);
            if(totalPValue > highestScore){
                highestScoreValue.setText(totalPValue.toString());
            }
            totalPoints.setText("0");
        }
    }
}