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

        public Web3jMain(){


            var web3 = Web3j.build(new HttpService("HTTP://127.0.0.1:7545"));
            var creds = Credentials.create("44e746bdf08a465df59f94566643376d9f7a89128c6bac16562bb8da839b82a9");

            var gasProvider = new StaticGasProvider(BigInteger.valueOf(1000), BigInteger.valueOf(1000000));
        }






        //NumberContract numberContract = NumberContract.deploy(web3, creds, gasProvider).send();
        NumberContract numberContract = NumberContract.load("0xd0189c47d6fd02f8b6735a22d6f7f678bebdc029",web3, creds, gasProvider);

        System.out.println(numberContract.getContractAddress());


        numberContract.storeNumber(BigInteger.valueOf(15)).send();
        System.out.println(numberContract.giveStoredNumber().send());

        numberContract.storeNumber(BigInteger.valueOf(10)).send();
        System.out.println(numberContract.giveStoredNumber().send());



    }

