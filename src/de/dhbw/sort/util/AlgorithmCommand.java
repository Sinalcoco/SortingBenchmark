package de.dhbw.sort.util;
//TODO Make Obsolete
public class AlgorithmCommand {
    Action type;
    int firstValue;
    int secondValue;
    Direction direction;
    public AlgorithmCommand(Action theType, int theFirstValue, int theSecondValue) {
        this(theType, theFirstValue, theSecondValue, Direction.IN_MAIN);
    }
    AlgorithmCommand(Action theType, int theFirstValue, int theSecondValue, Direction theDirection) {
        type = theType;
        firstValue = theFirstValue;
        secondValue = theSecondValue;
        direction = theDirection;
    }

    public Action type() {
        return type;
    }

    public int first() {
        return firstValue;
    }

    public int second() {
        return secondValue;
    }

    public Direction direction() {
        return direction;
    }

    public enum Action {
        MOVE, COMPARE, SWAP;
    }

    //TODO kompatibility with multiple Screens
    enum Direction {
        IN_MAIN, IN_OUTPUT, MAIN_TO_OUTPUT, OUTPUT_TO_MAIN
    }
}