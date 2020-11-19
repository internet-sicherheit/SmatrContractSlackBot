package Web3j;

import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;

import java.io.File;
import java.math.BigDecimal;

public class WalletManager {

    public static void main(String[] args) throws Exception {



      var fileName =  WalletUtils.generateNewWalletFile("secret", new File("."));



//        System.out.println(creds.getAddress());
//        System.out.println(creds.getEcKeyPair().getPublicKey().toString(16));
//        System.out.println(creds.getEcKeyPair().getPrivateKey().toString(16));

//       var importedCreds =  Credentials.create("b33e51ae1db1aa4f35f704fc3e49cbdc7b91af0ffe7af490ed6c2f5d217ea030");
//        System.out.println(importedCreds.getAddress());

//       var receipt = Transfer.sendFunds(web3, importedCreds, creds.getAddress(), BigDecimal.TEN, Convert.Unit.ETHER).send();
//        System.out.println(receipt.getBlockNumber());

//        var balance = web3.ethGetBalance(creds.getAddress(), DefaultBlockParameterName.LATEST).send();
//        System.out.println(balance.getBalance());
    }
}
