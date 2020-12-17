package TestingStuff;

import org.web3j.crypto.Credentials;
import org.web3j.model.NumberContract2;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.StaticGasProvider;

import java.math.BigInteger;

public class Web3jMain2 {


    private Web3j web3j;
    private Credentials creds;
    private StaticGasProvider gasProvider;
    private NumberContract2 numberContract2;




    public void Web3jMain2() throws Exception {

        //Provides a HttpService to local Ganache Blockchain and creates credentials from a private key from that blockchain
        web3j = Web3j.build(new HttpService("HTTP://127.0.0.1:7545"));
        creds = Credentials.create("0x820Dbe91D4Ee217B518B7BE2CD7c30ea47e5ee1E");


        //A Gasprovider for later use with ethereum network (local)
        gasProvider = new StaticGasProvider(BigInteger.valueOf(1000), BigInteger.valueOf(1000000));



        String loadMessage = loadContract("0x820Dbe91D4Ee217B518B7BE2CD7c30ea47e5ee1E");
        System.out.println(loadMessage);

        listentoallevents();







    }



//    public void listenToEventX(String eventname, SlashCommandContext ctx)
//    {
//        numberContract2.newNumberEventFlowable(DefaultBlockParameterName.LATEST, DefaultBlockParameterName.LATEST).subscribe(event -> {
//
//            //Event emit notice
//            //return event.number?
//            ctx.say("" + event.number);
//        });
//    }

     public void listentoallevents() {
        EthFilter filter = new EthFilter(DefaultBlockParameterName.LATEST, DefaultBlockParameterName.LATEST, numberContract2.getContractAddress().substring(2));



         web3j.ethLogFlowable(filter).subscribe(log -> System.out.println(log));


    }

    private void postmessage(Log log) {
        System.out.println(java.time.LocalTime.now());
        System.out.println(log);
            }


    //if the contract isn't deployed you need to deploy the contract (e.g. via bot command DeployContract) and specify the address here
    // NumberContract.load( address...
    private String loadContract(String address) throws Exception {
        
        if ((NumberContract2.load(address, web3j, creds, gasProvider) != null)) {

            numberContract2 = NumberContract2.load(address, web3j, creds, gasProvider);
            return "Contract loaded successfully";
        } else {
            System.out.println("Please deploy the contract first or check your given address");
            return "Please deploy the contract first or check your given address";
        }


    }


    public NumberContract2 getNumberContract() {
        return numberContract2;
    }


    public Web3j getWeb3j2() {
        return web3j;
    }


}



