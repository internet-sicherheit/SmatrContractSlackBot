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

    public StoredContract(String contractAddress) {
        this.contractAddress = contractAddress;
    }


    @Override
    public String toString() {
        String txt = "ContractAddress = " + contractAddress + "\nEvents:\n" ;

        for (int i = 0; i < events.size(); i++) {
            txt += events.get(i).toString() +"\n";
        }

        return txt;
    }


    public String getContractAddress() {
        return contractAddress;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }





}
