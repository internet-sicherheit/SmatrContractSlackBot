package Web3j.main;

public class Event {
    private String name;
    private String[] events;

    public Event(String name, String[] events)
    {
        this.name = name;
        this.events = events;
    }


    
    public String getName() {
        return name;
    }

    public String[] getEvents() {
        return events;
    }



}
