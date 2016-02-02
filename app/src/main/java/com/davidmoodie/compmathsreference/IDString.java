package com.davidmoodie.compmathsreference;

/**
 * Created by cmcintyre on 02/02/2016.
 */
public class IDString {
    private int stringID;
    private String backingString;

    public IDString(int id, String string) {
        backingString = string;
        id = id;
    }

    public int getID() {
        return stringID;
    }

    public String toString() {
        return backingString;
    }
}
