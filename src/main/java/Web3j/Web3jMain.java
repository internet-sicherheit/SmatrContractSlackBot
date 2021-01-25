package Web3j;


import com.slack.api.methods.SlackApiException;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;

import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Contract;
import org.web3j.tx.gas.StaticGasProvider;

import java.io.IOException;

import java.math.BigInteger;

import java.util.ArrayList;

public class Web3jMain {


    private Web3j web3j;
    private Credentials creds;
    private StaticGasProvider gasProvider;
    private Contract contract;


    ContractManager contractManager;


    public Web3jMain() throws Exception {


        //Provides a HttpService to local Ganache Blockchain and creates credentials from a private key from that blockchain
        web3j = Web3j.build(new HttpService("https://core.bloxberg.org"));


        System.out.println( web3j.web3ClientVersion().send().getWeb3ClientVersion());

        contractManager = new ContractManager();




        //iscc 0x4945d63B509e137b0293Bd958cf97B61996c0fB9

        //For faster testing
        // loadContract("0x89da503E68803B69833dfB0e6F18E96470430897");
         storeNewContractFromSlack("0x4945d63B509e137b0293Bd958cf97B61996c0fB9, ISCC address bytes bytes");
         listenToEventX("ISCC");


    }


    public String listenToEventX(String eventname) throws IOException, SlackApiException {

        StoredContract activeContract = contractManager.getActiveContract();
        Event eventX = null;
        boolean eventExists = false;
        boolean multipleEventsFound = false;


        for (int i = 0; i < activeContract.getEvents().size(); i++) {


            System.out.println("ListenToEventX: Active Contract Event:" + i + " " + activeContract.getEvents().get(i).getName() + " " + "Gegebenes Event: " +  eventname);

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

            EthFilter filter = new EthFilter(DefaultBlockParameterName.LATEST, DefaultBlockParameterName.LATEST, contractManager.getActiveContract().getContractAddress());
            //EthFilter filter = new EthFilter(DefaultBlockParameterName.LATEST, DefaultBlockParameterName.LATEST, contractManager.getActiveContract().getContractAddress().substring(2));


            System.out.println("Keccak string : " + eventX.getSha3String());

           filter.addSingleTopic(eventX.getSha3String());
            web3j.ethLogFlowable(filter).subscribe(log -> printLog(log));


            return "Sucessfully added listener to the following event: " + eventname;
        }

        return "???";
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


    //TODO: Komma vergessen
    public void storeNewContractFromSlack(String contractInformation) throws Exception {


        String parts[] = contractInformation.trim().split(",", 2);

        String contractAddress = parts[0];

       // for (int i = 0; i < parts.length; i++) {
       //     System.out.println(parts[i]);
//}

        if (parts.length > 1) {
            ArrayList<Event> events = eventsToArrayList(parts[1]);

            contractManager.storeContract(new StoredContract(contractAddress, events));



        } else {
            contractManager.storeContract(new StoredContract(contractAddress));
        }

        // switchActiveContract(contractAddress);


    }

    public String switchActiveContract(String contractAddress) throws Exception {
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
            System.out.println(allEventStrings[i]);

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


        System.out.println("helo");
        String eventName = "noEvent";

        for (int i = 0; i < contractManager.getActiveContract().getEvents().size(); i++) {

            if(contractManager.getActiveContract().getEvents().get(i).getSha3String().equals(log.getTopics().get(0))){
    eventName = contractManager.getActiveContract().getEvents().get(i).getName();


            }

        }

        SlackMessage slackMessage = SlackMessage.builder()
                .username("Contract-Bot")
                .text("New Event notification: " + eventName+"\nData: " + log.getData())
                .icon_emoji(":twice:")
                .build();
        SlackUtils.sendMessage(slackMessage);



    }


    //getters
    public ContractManager getContractManager() {
        return contractManager;
    }


}



