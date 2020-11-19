package ContractDeployment;


import org.web3j.crypto.Credentials;
import org.web3j.model.NumberContract;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.StaticGasProvider;

import java.math.BigInteger;

//This class deploys your contract (usually located in resources) onto your local ganache blockchain. It can be called by the Slack Bot and returns the address of the deployed Contract.
public class DeployContract {

    public String deploy() throws Exception {

        //Provides a HttpService to local Ganache Blockchain and creates credentials from a private key from a wallet from that blockchain
        var web3 = Web3j.build(new HttpService("HTTP://127.0.0.1:7545"));
        var creds = Credentials.create("44e746bdf08a465df59f94566643376d9f7a89128c6bac16562bb8da839b82a9");

        //A Gasprovider for later use with ethereum network (local)
        var gasProvider = new StaticGasProvider(BigInteger.valueOf(1000), BigInteger.valueOf(1000000));



        //currently you have to specify the correct Filename here. In the future this should be done in the parameter of the function if possible
        NumberContract numberContract = NumberContract.deploy(web3, creds, gasProvider).send();


        //add this address in Web3jMain in the loadContractFunction
        return numberContract.getContractAddress();


    }



}
