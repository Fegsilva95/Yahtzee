package com.example.yahtzee;

import java.util.List;

public class ObjectValue {
    public ObjectValue(String name) {
        this.name = name;
        this.value = 0;
    }

    public ObjectValue(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    String name;
    Integer value;

    public String getName() {
        return name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public boolean verifyEmptyValues(List<ObjectValue> values){
        for (ObjectValue value : values) {
            if(value.getValue() == 0){
                return false;
            }
        }
        return true;
    }

    public void restartValues(List<ObjectValue> values){
        for(Integer i = 0; i < values.size(); i++) {
            values.get(i).setValue(0);
        }
    }

    private int verifyNumbers(List<ObjectValue> dices, Integer valueToCompare){
        int count = 0;

        for(ObjectValue dice : dices){
            if(dice.getValue() == valueToCompare){
                count = count + valueToCompare;
            }
        }
        return count;
    }

    public Integer verifyRules(List<ObjectValue> dices, Integer ruleNumber){
        Integer result = 0;

        switch (ruleNumber) {
            case 0:
                result = verifyNumbers(dices, 1);
                break;
            case 1:
                result = verifyNumbers(dices, 2);
                break;
            case 2:
                result = verifyNumbers(dices, 3);
                break;
            case 3:
                result = verifyNumbers(dices, 4);
                break;
            case 4:
                result = verifyNumbers(dices, 5);
                break;
            case 5:
                result = verifyNumbers(dices, 6);
                break;
            case 6:
                result = threeOfAKind(dices);
                break;
            case 7:
                result = fourOfAKind(dices);
                break;
            case 8:
                result = fullHouse(dices);
                break;
            case 9:
                result = smallStraight(dices);
                break;
            case 10:
                result = largeStraight(dices);
                break;
            case 11:
                result = yahtzee(dices);
                break;
            case 12:
                result = chance(dices);
                break;
            default:
                break;
        }
        return result;
    }

    public Integer threeOfAKind(List<ObjectValue> dices){
        return 1;
    }

    public Integer fourOfAKind(List<ObjectValue> dices){
        return 1;
    }

    public Integer fullHouse(List<ObjectValue> dices){
        return 1;
    }

    public Integer smallStraight(List<ObjectValue> dices){
        return 1;
    }

    public Integer largeStraight(List<ObjectValue> dices){
        return 1;
    }

    public Integer yahtzee(List<ObjectValue> dices){
        return 1;
    }

    public Integer chance(List<ObjectValue> dices){
        Integer result = 0;
        for (ObjectValue dice : dices){
            result += dice.getValue();
        }
        return result;
    }
}
