package Web3j;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Event {
    private String name;
    private ArrayList<String> events;

    public Event(String name, ArrayList<String> events) {
        this.name = name;
        this.events = events;
    }


    public String getName() {
        return name;
    }

    public ArrayList<String> getEvents() {
        return events;
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

    public String toSha3HashString()
    {
        String txt = name;
        txt+= "(";
        for (int i = 0; i < events.size(); i++) {

            txt += events.get(i);

            if(!(i == events.size()-1))
            {
                txt+= ",";
            }
        }

        txt+= ")";

        return Hash.sha3String(txt);
    }

}
