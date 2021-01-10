package Web3j;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Event {
    private String name;
    private ArrayList<String> events;



    private String sha3String;

    public Event(String name, ArrayList<String> events) {
        this.name = name;
        this.events = events;
        sha3String = toSha3HashString();
    }



    @Override
    public String toString() {
        String txt = name;

        for (int i = 0; i < events.size(); i++) {
            txt += "Param " + i + ":";
            txt += events.get(i);
        }

        return txt;
    }

    public String toSha3HashString() {
        String txt = name;
        txt += "(";
        for (int i = 0; i < events.size(); i++) {

            txt += events.get(i);

            if (!(i == events.size() - 1)) {
                txt += ",";
            }
        }

        txt += ")";

        return Hash.sha3String(txt);
    }


    @Override
    public boolean equals(Object o) {
        boolean equal = false;

        if (o instanceof Event) {
            Event c = (Event) o;
            if (c.toSha3HashString().equals(toSha3HashString())
            ) {

                equal = true;

            }


        }

        return equal;
    }


    public String getName() {
        return name;
    }

    public ArrayList<String> getEvents() {
        return events;
    }

    public String getSha3String() {
        return sha3String;
    }


}