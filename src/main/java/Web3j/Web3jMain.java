package Web3j;

import com.slack.api.bolt.context.builtin.SlashCommandContext;
import org.web3j.abi.EventValues;
import org.web3j.crypto.Credentials;
import org.web3j.model.NumberContract;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.ChainId;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.StaticGasProvider;
import org.web3j.tx.response.PollingTransactionReceiptProcessor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigInteger;

public class Web3jMain {


    private Web3j web3j;
   private Credentials creds;
   private StaticGasProvider gasProvider;
 private NumberContract numberContract;

    public Web3jMain() throws Exception {




        //Provides a HttpService to local Ganache Blockchain and creates credentials from a private key from that blockchain
        web3j = Web3j.build(new HttpService("HTTP://127.0.0.1:7545"));
        creds = Credentials.create("44e746bdf08a465df59f94566643376d9f7a89128c6bac16562bb8da839b82a9");


        //A Gasprovider for later use with ethereum network (local)
        gasProvider = new StaticGasProvider(BigInteger.valueOf(1000), BigInteger.valueOf(1000000));

        loadContract();

        //NumberContractTests tests = new NumberContractTests(numberContract);
       // TransactionReceipt transactionReceipt = numberContract.storeNumber(BigInteger.valueOf(5)).send();

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
    private String loadContract() throws Exception {


        if ((NumberContract.load("0xd0189c47d6fd02f8b6735a22d6f7f678bebdc029", web3j, creds, gasProvider) != null)) {
            System.out.println("hello");
            numberContract = NumberContract.load("0xd0189c47d6fd02f8b6735a22d6f7f678bebdc029", web3j, creds, gasProvider);
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



}



