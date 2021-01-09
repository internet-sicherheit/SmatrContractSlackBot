package Web3j;

import Web3j.Event;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class StoredContract {


    private String contractAddress;
    private ArrayList<Event> events;

    public StoredContract(String contractAddress, ArrayList<Event> events) {

        this.contractAddress = contractAddress;
        this.events = events;
    }


    public String getContractAddress() {
        return contractAddress;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }





}
