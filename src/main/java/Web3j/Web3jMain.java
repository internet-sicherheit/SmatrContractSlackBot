package Web3j;


import com.slack.api.methods.SlackApiException;
import com.slack.api.webhook.WebhookResponse;
import org.web3j.crypto.Credentials;
import org.web3j.model.old.NumberContract;
import org.web3j.protocol.Web3j;

import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.StaticGasProvider;

import java.io.IOException;

import java.math.BigInteger;

import java.util.ArrayList;

public class Web3jMain {


    private Web3j web3j;
    private Credentials creds;
    private StaticGasProvider gasProvider;
    private NumberContract numberContract;


    ContractManager contractManager;


    public Web3jMain() throws Exception {


        //Provides a HttpService to local Ganache Blockchain and creates credentials from a private key from that blockchain
        web3j = Web3j.build(new HttpService("HTTP://127.0.0.1:7545"));
        creds = Credentials.create("44e746bdf08a465df59f94566643376d9f7a89128c6bac16562bb8da839b82a9");


        //A Gasprovider for later use with ethereum network (local)
        gasProvider = new StaticGasProvider(BigInteger.valueOf(1000), BigInteger.valueOf(1000000));

        contractManager = new ContractManager();


        //For faster testing
        // loadContract("0x89da503E68803B69833dfB0e6F18E96470430897");
        // storeNewContractFromSlack("0x89da503E68803B69833dfB0e6F18E96470430897 , newNumberStored uint256, calledRequestNumberFunction");


    }


    public String listenToEventX(String eventname) throws IOException, SlackApiException {

        StoredContract activeContract = contractManager.getActiveContract();
        Event eventX = null;
        boolean eventExists = false;
        boolean multipleEventsFound = false;

        for (int i = 0; i < activeContract.getEvents().size(); i++) {


            if (activeContract.getEvents().get(i).getName().equals(eventname)) {


                if (!eventExists) {

                    eventExists = true;
                    eventX = activeContract.getEvents().get(i);
                } else
                    multipleEventsFound = true;
            }
        }

        if (multipleEventsFound) {
            return "specify parameters";
        } else if (!eventExists) {
            return "no such event";

        } else if (eventExists) {

            EthFilter filter = new EthFilter(DefaultBlockParameterName.LATEST, DefaultBlockParameterName.LATEST, numberContract.getContractAddress().substring(2));


            System.out.println(eventX.getSha3String());

            filter.addSingleTopic(eventX.getSha3String());
            web3j.ethLogFlowable(filter).subscribe(log -> printLog(log));


            return "Sucessfully added listener to the event " + eventname;
        }

        return "???";
    }


    //loads a contract onto the server via contractAddress
    private String loadContract(String contractAddress) throws Exception {


        if ((NumberContract.load(contractAddress, web3j, creds, gasProvider) != null)) {
            System.out.println("Loaded Contract");
            numberContract = NumberContract.load(contractAddress, web3j, creds, gasProvider);
            return "Contract loaded successfully";
        } else {
            System.out.println("Please deploy the contract first or check your given address");
            return "Please deploy the contract first or check your given address";
        }


    }


    public NumberContract getNumberContract() {
        return numberContract;
    }


    public Web3j getWeb3j() {
        return web3j;
    }


    public boolean compareEventHashWithTopics(String eventHash) {

        return true;
    }


    public String eventNameToSha3Hash(String eventname) {
        return Hash.sha3String(eventname);
    }

/*This function needs a string in this way:
    ContractAddress,Event1 param1 param2 ..., Event2 param1 param2, ...

*/

    public void storeNewContractFromSlack(String contractInformation) throws Exception {
        String parts[] = contractInformation.trim().split(",", 2);

        String contractAddress = parts[0];


        System.out.println(parts.length);
        if(parts.length>1) {
          ArrayList<Event>  events = eventsToArrayList(parts[1]);

            contractManager.storeContract(new StoredContract(contractAddress, events));

            System.out.println(contractManager.getContract(contractAddress).getEvents());

        }else
        {
            contractManager.storeContract(new StoredContract(contractAddress));
        }

       switchActiveContract(contractAddress);


    }

    public String switchActiveContract(String contractAddress) throws Exception {
        loadContract(contractAddress);
        return contractManager.switchCurrentlyLoadedContract(contractAddress);
    }


    /*Takes the complete string with all events(and parameters), every event is seperated by a comma and every parameters(of an event) is seperated by a space
    First Seperates all events and stores them into an array. The code goes through the events and seperates and seperates them into a string array [Eventname,param1,param2,...]
    The first Stringvalue is the Eventname and gets stored seperatly while all the parameters gets stored into an arraylist. With Eventname and the paramaters a new Event is stored.

     */


    public ArrayList<Event> eventsToArrayList(String eventsString) {


        ArrayList<Event> events = new ArrayList<>();

        //Creates Array with all Events
        String[] allEventStrings = eventsString.trim().split(",");

        for (int i = 0; i < allEventStrings.length; i++) {

            //Creates Array that contains every parameter of the current event
            String[] singleEventString = allEventStrings[i].trim().split("\\s+");


            String eventName = singleEventString[0];
            ArrayList<String> eventParameters = new ArrayList<>();

            for (int j = 1; j < singleEventString.length; j++) {
                eventParameters.add(singleEventString[j]);
            }
            events.add(new Event(eventName, eventParameters));
        }


        return events;
    }


    private void printLog(Log log) {


        System.out.println(log.getData() + " /n" + log.getTopics());
        System.out.println(log);
        System.out.println();


    }


    //getters
    public ContractManager getContractManager() {
        return contractManager;
    }


}



