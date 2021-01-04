package Web3j;

import org.web3j.model.old.NumberContract;

import java.math.BigInteger;

public class NumberContractTests {


    public NumberContractTests(NumberContract numberContract) throws Exception {


        System.out.println(numberContract.getContractAddress());


        numberContract.storeNumber(BigInteger.valueOf(15)).send();
        System.out.println(numberContract.giveStoredNumber().send());

        numberContract.storeNumber(BigInteger.valueOf(10)).send();
        System.out.println(numberContract.giveStoredNumber().send());

    }
}
