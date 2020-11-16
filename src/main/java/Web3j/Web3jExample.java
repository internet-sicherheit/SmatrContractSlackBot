package Web3j;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterNumber;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;

public class Web3jExample {

    public static void main(String[] args) throws IOException {


        var web3 = Web3j.build(new HttpService("HTTP://127.0.0.1:7545"));

        var blocknummer = web3.ethBlockNumber().send().getBlockNumber();
        System.out.println(blocknummer);


        int anzahlTransaktionen = 0;
        for (int i = 0; i <= blocknummer.intValue(); i++) {


          anzahlTransaktionen +=  web3.ethGetBlockByNumber(new DefaultBlockParameterNumber(i), false).send().getBlock().getTransactions().size();
        }
        System.out.println(anzahlTransaktionen);

//       var sum =  IntStream.range(0, blocknummer.intValue() + 1)
//                .mapToObj(i -> {
//                    try {
//                        var block = web3.ethGetBlockByNumber(new DefaultBlockParameterNumber(i), false)
//                                .send();
//                        return Optional.of(block);
//                    } catch (IOException e) {
//                        return Optional.<EthBlock>empty();
//                    }
//                })
//                .flatMap(Optional::stream)
//                .mapToInt(block -> block.getBlock().getTransactions().size())
//                .sum();

      //  System.out.println(sum);

    }

    }



