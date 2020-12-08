package org.web3j.model;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.5.11.
 */
@SuppressWarnings("rawtypes")
public class NumberContract2 extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b50600060015561014f806100256000396000f3fe608060405234801561001057600080fd5b50600436106100415760003560e01c80630647b13114610046578063b633941814610060578063cfd1a7e21461007f575b600080fd5b61004e610087565b60408051918252519081900360200190f35b61007d6004803603602081101561007657600080fd5b50356100b9565b005b61004e6100f1565b6040516000907f8cae21117fb5ba06af55571e4baf3e302003af2ca3382176029bea3365e18b9e908290a15060015490565b6040805182815290517f3780149a2e969cb1dde078709d0f96a56086a2a76b46fa5082741167d3262a859181900360200190a1600055565b6040516000907f87ef966865a757463d1aed438e8c0a4e5532b511d3a947fde1c4b46ecf0189e9908290a1506000549056fea165627a7a72305820907991a38e5c7be9a8aa8e49d4d9595649d8016cdc83bf80fe38df5b8411013f0029";

    public static final String FUNC_GETTIMESFUNCTIONCALLED = "getTimesFunctionCalled";

    public static final String FUNC_STORENUMBER = "storeNumber";

    public static final String FUNC_REQUESTNUMBER = "requestNumber";

    public static final Event NEWNUMBER_EVENT = new Event("newNumber", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
    ;

    public static final Event CALLEDREQUESTNUMBER_EVENT = new Event("calledRequestNumber", 
            Arrays.<TypeReference<?>>asList());
    ;

    public static final Event CALLEDGETTIMESFUNCTIONCALLED_EVENT = new Event("calledGetTimesFunctionCalled", 
            Arrays.<TypeReference<?>>asList());
    ;

    @Deprecated
    protected NumberContract2(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected NumberContract2(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected NumberContract2(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected NumberContract2(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<TransactionReceipt> getTimesFunctionCalled() {
        final Function function = new Function(
                FUNC_GETTIMESFUNCTIONCALLED, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> storeNumber(BigInteger num) {
        final Function function = new Function(
                FUNC_STORENUMBER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(num)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> requestNumber() {
        final Function function = new Function(
                FUNC_REQUESTNUMBER, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public List<NewNumberEventResponse> getNewNumberEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(NEWNUMBER_EVENT, transactionReceipt);
        ArrayList<NewNumberEventResponse> responses = new ArrayList<NewNumberEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            NewNumberEventResponse typedResponse = new NewNumberEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.number = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<NewNumberEventResponse> newNumberEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, NewNumberEventResponse>() {
            @Override
            public NewNumberEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(NEWNUMBER_EVENT, log);
                NewNumberEventResponse typedResponse = new NewNumberEventResponse();
                typedResponse.log = log;
                typedResponse.number = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<NewNumberEventResponse> newNumberEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(NEWNUMBER_EVENT));
        return newNumberEventFlowable(filter);
    }

    public List<CalledRequestNumberEventResponse> getCalledRequestNumberEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(CALLEDREQUESTNUMBER_EVENT, transactionReceipt);
        ArrayList<CalledRequestNumberEventResponse> responses = new ArrayList<CalledRequestNumberEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            CalledRequestNumberEventResponse typedResponse = new CalledRequestNumberEventResponse();
            typedResponse.log = eventValues.getLog();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<CalledRequestNumberEventResponse> calledRequestNumberEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, CalledRequestNumberEventResponse>() {
            @Override
            public CalledRequestNumberEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(CALLEDREQUESTNUMBER_EVENT, log);
                CalledRequestNumberEventResponse typedResponse = new CalledRequestNumberEventResponse();
                typedResponse.log = log;
                return typedResponse;
            }
        });
    }

    public Flowable<CalledRequestNumberEventResponse> calledRequestNumberEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CALLEDREQUESTNUMBER_EVENT));
        return calledRequestNumberEventFlowable(filter);
    }

    public List<CalledGetTimesFunctionCalledEventResponse> getCalledGetTimesFunctionCalledEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(CALLEDGETTIMESFUNCTIONCALLED_EVENT, transactionReceipt);
        ArrayList<CalledGetTimesFunctionCalledEventResponse> responses = new ArrayList<CalledGetTimesFunctionCalledEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            CalledGetTimesFunctionCalledEventResponse typedResponse = new CalledGetTimesFunctionCalledEventResponse();
            typedResponse.log = eventValues.getLog();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<CalledGetTimesFunctionCalledEventResponse> calledGetTimesFunctionCalledEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, CalledGetTimesFunctionCalledEventResponse>() {
            @Override
            public CalledGetTimesFunctionCalledEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(CALLEDGETTIMESFUNCTIONCALLED_EVENT, log);
                CalledGetTimesFunctionCalledEventResponse typedResponse = new CalledGetTimesFunctionCalledEventResponse();
                typedResponse.log = log;
                return typedResponse;
            }
        });
    }

    public Flowable<CalledGetTimesFunctionCalledEventResponse> calledGetTimesFunctionCalledEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CALLEDGETTIMESFUNCTIONCALLED_EVENT));
        return calledGetTimesFunctionCalledEventFlowable(filter);
    }

    @Deprecated
    public static NumberContract2 load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new NumberContract2(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static NumberContract2 load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new NumberContract2(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static NumberContract2 load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new NumberContract2(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static NumberContract2 load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new NumberContract2(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<NumberContract2> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(NumberContract2.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    public static RemoteCall<NumberContract2> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(NumberContract2.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<NumberContract2> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(NumberContract2.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<NumberContract2> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(NumberContract2.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class NewNumberEventResponse extends BaseEventResponse {
        public BigInteger number;
    }

    public static class CalledRequestNumberEventResponse extends BaseEventResponse {
    }

    public static class CalledGetTimesFunctionCalledEventResponse extends BaseEventResponse {
    }
}
