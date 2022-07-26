package com.example.yahtzee;

import java.util.HashSet;
import java.util.List;
import java.util.stream.*;

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
                result = sumDices(dices);
                break;
            default:
                break;
        }
        return result;
    }

    public Integer threeOfAKind(List<ObjectValue> dices){
       return threeOfKind(dices) ? sumDices(dices) : 0;
    }

    public Integer fourOfAKind(List<ObjectValue> dices){
        return fourOfKind(dices) ? sumDices(dices) : 0;
    }

    public Integer fullHouse(List<ObjectValue> dices){
        return _fullHouse(dices) ? 25 : 0;
    }

    public Integer smallStraight(List<ObjectValue> dices){
        return _smallStraight(dices) ? 30 : 0;
    }

    public Integer largeStraight(List<ObjectValue> dices){
        return _largeStraight(dices) ? 40 : 0;
    }

    public Integer yahtzee(List<ObjectValue> dices){
        Integer dice1 = dices.get(0).getValue();
        Integer dice2 = dices.get(1).getValue();
        Integer dice3 = dices.get(2).getValue();
        Integer dice4 = dices.get(3).getValue();
        Integer dice5 = dices.get(4).getValue();
        boolean result = dice1 == dice2 && dice1 == dice3 && dice1 == dice4 && dice1 == dice5;
        return result ? 50 : 0;
    }

    public Integer sumDices(List<ObjectValue> dices){
        Integer result = 0;
        for (ObjectValue dice : dices){
            result += dice.getValue();
        }
        return result;
    }

    public boolean threeOfKind(List<ObjectValue> dices){
        Integer dice1 = dices.get(0).getValue();
        Integer dice2 = dices.get(1).getValue();
        Integer dice3 = dices.get(2).getValue();
        Integer dice4 = dices.get(3).getValue();
        Integer dice5 = dices.get(4).getValue();

        if(dice1 == dice2 && dice1 == dice3 || dice1 == dice2 && dice1 == dice4 || dice1 == dice2 && dice1 == dice5
        || dice1 == dice3 && dice1 == dice4 || dice1 == dice3 && dice1 == dice5 || dice1 == dice4 && dice1 == dice5
        || dice2 == dice3 && dice2 == dice4 || dice2 == dice3 && dice2 == dice5 || dice3 == dice4 && dice3 == dice5)
        {
            return true;
        }
        return false;
    }

    public boolean fourOfKind(List<ObjectValue> dices){
        Integer dice1 = dices.get(0).getValue();
        Integer dice2 = dices.get(1).getValue();
        Integer dice3 = dices.get(2).getValue();
        Integer dice4 = dices.get(3).getValue();
        Integer dice5 = dices.get(4).getValue();

        if(dice1 == dice2 && dice1 == dice3 && dice1 == dice4
        || dice1 == dice5 && dice1 == dice3 && dice1 == dice4
        || dice1 == dice2 && dice1 == dice3 && dice1 == dice5
        || dice2 == dice5 && dice2 == dice3 && dice2 == dice4)
        {
            return true;
        }
        return false;
    }

    public boolean _smallStraight(List<ObjectValue> dices){
        Integer dice1 = dices.get(0).getValue();
        Integer dice2 = dices.get(1).getValue();
        Integer dice3 = dices.get(2).getValue();
        Integer dice4 = dices.get(3).getValue();
        Integer dice5 = dices.get(4).getValue();

        if(((dice1 == 1 || dice2 == 1 || dice3 == 1 || dice4 == 1 || dice5 == 1 ) &&
        (dice1 == 2 || dice2 == 2 || dice3 == 2 || dice4 == 2 || dice5 == 2 ) &&
        (dice1 == 3 || dice2 == 3 || dice3 == 3 || dice4 == 3 || dice5 == 3 ) &&
        (dice1 == 4 || dice2 == 4 || dice3 == 4 || dice4 == 4 || dice5 == 4 )) ||
        ((dice1 == 2 || dice2 == 2 || dice3 == 2 || dice4 == 2 || dice5 == 2 ) &&
        (dice1 == 3 || dice2 == 3 || dice3 == 3 || dice4 == 3 || dice5 == 3 ) &&
        (dice1 == 4 || dice2 == 4 || dice3 == 4 || dice4 == 4 || dice5 == 4 ) &&
        (dice1 == 5 || dice2 == 5 || dice3 == 5 || dice4 == 5 || dice5 == 5 )) ||
        ((dice1 == 3 || dice2 == 3 || dice3 == 3 || dice4 == 3 || dice5 == 3 ) &&
        (dice1 == 4 || dice2 == 4 || dice3 == 4 || dice4 == 4 || dice5 == 4 ) &&
        (dice1 == 5 || dice2 == 5 || dice3 == 5 || dice4 == 5 || dice5 == 5 ) &&
        (dice1 == 6 || dice2 == 6 || dice3 == 6 || dice4 == 6 || dice5 == 6 )))
        {
            return true;
        }
        return false;
    }

    public boolean _largeStraight(List<ObjectValue> dices){
        Integer dice1 = dices.get(0).getValue();
        Integer dice2 = dices.get(1).getValue();
        Integer dice3 = dices.get(2).getValue();
        Integer dice4 = dices.get(3).getValue();
        Integer dice5 = dices.get(4).getValue();

        if(((dice1 == 1 || dice2 == 1 || dice3 == 1 || dice4 == 1 || dice5 == 1 ) &&
        (dice1 == 2 || dice2 == 2 || dice3 == 2 || dice4 == 2 || dice5 == 2 ) &&
        (dice1 == 3 || dice2 == 3 || dice3 == 3 || dice4 == 3 || dice5 == 3 ) &&
        (dice1 == 4 || dice2 == 4 || dice3 == 4 || dice4 == 4 || dice5 == 4 ) &&
        (dice1 == 5 || dice2 == 5 || dice3 == 5 || dice4 == 5 || dice5 == 5 )) ||
        ((dice1 == 2 || dice2 == 2 || dice3 == 2 || dice4 == 2 || dice5 == 2 ) &&
        (dice1 == 3 || dice2 == 3 || dice3 == 3 || dice4 == 3 || dice5 == 3 ) &&
        (dice1 == 4 || dice2 == 4 || dice3 == 4 || dice4 == 4 || dice5 == 4 ) &&
        (dice1 == 5 || dice2 == 5 || dice3 == 5 || dice4 == 5 || dice5 == 5 ) &&
        (dice1 == 6 || dice2 == 6 || dice3 == 6 || dice4 == 6 || dice5 == 6 )))
        {
            return true;
        }
        return false;
    }

    public boolean _fullHouse(List<ObjectValue> dices){
        Integer dice1 = dices.get(0).getValue();
        Integer dice2 = dices.get(1).getValue();
        Integer dice3 = dices.get(2).getValue();
        Integer dice4 = dices.get(3).getValue();
        Integer dice5 = dices.get(4).getValue();

        if(dice1 == dice2 && dice3 == dice4  && dice3 == dice5 ||
        dice1 == dice3 && dice2 == dice4  && dice2 == dice5 ||
        dice1 == dice4 && dice3 == dice2  && dice3 == dice5 ||
        dice1 == dice5 && dice3 == dice4  && dice3 == dice2 ||
        dice2 == dice3 && dice4 == dice5  && dice1 == dice5 ||
        dice2 == dice4 && dice1 == dice5  && dice1 == dice3 ||
        dice2 == dice5 && dice1 == dice4  && dice1 == dice3 ||
        dice3 == dice4 && dice1 == dice2  && dice1 == dice5 ||
        dice3 == dice5 && dice1 == dice2  && dice1 == dice4 ||
        dice4 == dice5 && dice1 == dice2  && dice1 == dice3)
        {
            return true;
        }
        return false;
    }
}
