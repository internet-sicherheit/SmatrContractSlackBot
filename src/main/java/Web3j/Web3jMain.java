package Web3j;

import com.slack.api.bolt.context.builtin.SlashCommandContext;
import org.web3j.crypto.Credentials;
import org.web3j.model.old.NumberContract;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.StaticGasProvider;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
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


        //NumberContractTests tests = new NumberContractTests(numberContract);
        // TransactionReceipt transactionReceipt = numberContract.storeNumber(BigInteger.valueOf(5)).send();

    }

    private void setEventNames() {


    }


    public void listenToEventX(String eventname, SlashCommandContext ctx) {
        numberContract.newNumberEventFlowable(DefaultBlockParameterName.LATEST, DefaultBlockParameterName.LATEST).subscribe(event -> {

            //Event emit notice
            //return event.number?
            ctx.say("" + event.number);
        });
    }

    public void callMethod(String name) {

        Method method = null;
        try {


            method = numberContract.getClass().getMethod("new" + "name" + "flowable", DefaultBlockParameter.class, DefaultBlockParameter.class);
        } catch (SecurityException e) {
            System.out.println(e);
        } catch (NoSuchMethodException e) {
            System.out.println(e);

        }

        try {
            method.invoke(numberContract, DefaultBlockParameterName.LATEST, DefaultBlockParameterName.LATEST);
        } catch (IllegalArgumentException e) {
            System.out.println(e);
        } catch (IllegalAccessException e) {
            System.out.println(e);
        } catch (InvocationTargetException e) {
            System.out.println(e);
        }

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


    public void storeNewContractFromSlack(String contractInformation) {

        String parts[] = contractInformation.split(" ", 2);

        String contractAddress = parts[0];

        ArrayList<Event> events = eventsToArrayList(parts[1]);

        contractManager.storeContract(new StoredContract(contractAddress, events));

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



    public ContractManager getContractManager()
    {
        return contractManager;
    }



}



