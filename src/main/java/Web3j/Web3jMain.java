package Web3j;

import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.model.NumberContract;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.StaticGasProvider;

import java.io.File;
import java.math.BigInteger;

public class Web3jMain {

    Web3j web3;
    Credentials creds;
    StaticGasProvider gasProvider;
    NumberContract numberContract;


    public Web3jMain() throws Exception {

        //Provides a HttpService to local Ganache Blockchain and creates credentials from a private key from that blockchain
        web3 = Web3j.build(new HttpService("HTTP://127.0.0.1:7545"));
        creds = Credentials.create("44e746bdf08a465df59f94566643376d9f7a89128c6bac16562bb8da839b82a9");


        //A Gasprovider for later use with ethereum network (local)
        gasProvider = new StaticGasProvider(BigInteger.valueOf(1000), BigInteger.valueOf(1000000));


        loadContract();
        NumberContractTests tests = new NumberContractTests(numberContract);

    }

    //if the contract isn't deployed you need to deploy the contract (e.g. via bot command DeployContract) and specify the address here
    // NumberContract.load( address...
    private String loadContract() throws Exception {


        if ((NumberContract.load("0xd0189c47d6fd02f8b6735a22d6f7f678bebdc029", web3, creds, gasProvider) != null)) {
            System.out.println("hello");
            numberContract = NumberContract.load("0xd0189c47d6fd02f8b6735a22d6f7f678bebdc029", web3, creds, gasProvider);
            return "Contract loaded successfully";
        } else {
            System.out.println("Please deploy the contract first or check your given address");
            return "Please deploy the contract first or check your given address";
        }


    }


}

