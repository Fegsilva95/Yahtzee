package com.example.yahtzee;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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
    SharedPreferences sharedPreferences;
    String DB = "YAHTZEE_DATABASE";
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences(DB, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        totalPoints = findViewById(R.id.totalPointsValue);

        dices = new ArrayList<ObjectValue>(){{
            add (new ObjectValue("Dice 1"));
            add (new ObjectValue("Dice 2"));
            add (new ObjectValue("Dice 3"));
            add (new ObjectValue("Dice 4"));
            add (new ObjectValue("Dice 5"));
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
                    Integer total = sharedPreferences.getInt("totalPoints", 0);
                    total += result;
                    editor.putInt("totalPoints", total);
                    editor.apply();

                    setRuleValue(ruleNumber, result);
                    rule.setValue(result);
                    ruleAdapter.notifyItemChanged(ruleNumber);

                    rule.restartValues(dices);
                    for (Integer i = 0; i <= dices.size(); i++) {
                        diceAdapter.notifyItemChanged(i);
                    }

                    totalPoints.setText(totalPointsValue().toString());

                } else {
                    System.out.println("Regra não aplicada");
                }
            }
        };

        ruleAdapter = new RuleAdapter(rules, ruleListener);
        getRulesValue();
        for (Integer i = 0; i < rules.size(); i++) {
            ruleAdapter.notifyItemChanged(i);
        };
        rulesRecyclerView.setAdapter(ruleAdapter);

        launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {}
                });

        highestScoreValue = findViewById(R.id.highestScoreValue);
        Integer highest = sharedPreferences.getInt("highestScore", 0);
        highestScoreValue.setText(highest.toString());

        totalPoints.setText(totalPointsValue().toString());
    }

    public Integer totalPointsValue(){
        return sharedPreferences.getInt("totalPoints", 0);
    }

    public void setRuleValue(Integer ruleNumber, Integer value){
        editor.putInt(ruleNumber.toString(), value);
    }

    public Integer getRuleValue(Integer ruleNumber){
        Integer ruleValue = sharedPreferences.getInt(ruleNumber.toString(), 0);
        ruleAdapter.notifyItemChanged(ruleNumber);
        return ruleValue;
    }

    public void getRulesValue(){
        for (Integer i = 0; i < rules.size(); i++){
            Integer ruleValue = sharedPreferences.getInt(i.toString(), 0);
            rules.get(i).setValue(ruleValue);
        }
    }

    public void restartRulesValue(){
        for (Integer i = 0; i < rules.size(); i++){
            editor.putInt(i.toString(), 0);
        }
    }

    public void credits(View view){
        Intent intent = new Intent(MainActivity.this, CreditsActivity.class);
        launcher.launch(intent);
    }

    public void restart(View view){
        ObjectValue verify = new ObjectValue("");
        if(!verify.verifyEmptyValues(rules)){
            Toast.makeText(this, "There is some empty rule" , Toast.LENGTH_SHORT).show();
        } else {
            restartRulesValue();
            verify.restartValues(rules);
            verify.restartValues(dices);
            for (Integer i = 0; i <= rules.size(); i++) {
                ruleAdapter.notifyItemChanged(i);
            }
            for (Integer i = 0; i <= dices.size(); i++) {
                diceAdapter.notifyItemChanged(i);
            }

            highestScore = sharedPreferences.getInt("highestScore", 0);
            Integer totalPointsValue = totalPointsValue();
            if(totalPointsValue > highestScore){
                highestScoreValue = findViewById(R.id.highestScoreValue);
                highestScoreValue.setText(totalPointsValue.toString());
                editor.putInt("highestScore", totalPointsValue);
                editor.apply();
            }
            totalPoints.setText("0");
            editor.putInt("totalPoints", 0);
            editor.apply();
        }
    }
}