package Web3j;
import org.bouncycastle.crypto.digests.KeccakDigest;
import org.bouncycastle.jcajce.provider.digest.Keccak;
import com.slack.api.bolt.context.builtin.SlashCommandContext;
import jnr.ffi.Address;
import org.bouncycastle.jcajce.provider.digest.SHA3;
import org.bouncycastle.util.encoders.Hex;
import org.web3j.crypto.Credentials;
import org.web3j.model.old.NumberContract;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.StaticGasProvider;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class Web3jMain {


    private Web3j web3j;
   private Credentials creds;
   private StaticGasProvider gasProvider;
 private NumberContract numberContract;

 private ArrayList<String> alleEvents = new ArrayList<>();

    private String contractAddressFromSlack;

    public Web3jMain() throws Exception {




        eventToTopicHash("newNumberStored(uint256)");




        //Provides a HttpService to local Ganache Blockchain and creates credentials from a private key from that blockchain
        web3j = Web3j.build(new HttpService("HTTP://127.0.0.1:7545"));
        creds = Credentials.create("44e746bdf08a465df59f94566643376d9f7a89128c6bac16562bb8da839b82a9");


        //A Gasprovider for later use with ethereum network (local)
        gasProvider = new StaticGasProvider(BigInteger.valueOf(1000), BigInteger.valueOf(1000000));


        String contractAddress = "0x1D6947DC1e4e1c4c9B47EB090Aaa07a978A730dE";


        loadContract(contractAddress);



        //NumberContractTests tests = new NumberContractTests(numberContract);
       // TransactionReceipt transactionReceipt = numberContract.storeNumber(BigInteger.valueOf(5)).send();

    }

    private void setEventNames() {


    }


    public void listenToEventX(String eventname, SlashCommandContext ctx)
    {
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




    //if the contract isn't deployed you need to deploy the contract (e.g. via bot command DeployContract) and specify the address here
    // NumberContract.load( address...
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

public void eventToTopicHash(String name) throws NoSuchAlgorithmException {

    System.out.println(Hash.sha3String(name));

}

public boolean compareEventHashWithTopics(String eventHash)
{

    return true;
}


public void setContractAddressFromSlack(String address){

        contractAddressFromSlack = address;

    }


    public void addEvents(String[] events)
    {
        for (int i = 0; i < events.length; i++) {
            alleEvents.add(events[i]);
        }


    }

    public String getContractAddressFromSlack() {
        return contractAddressFromSlack;
    }

}



