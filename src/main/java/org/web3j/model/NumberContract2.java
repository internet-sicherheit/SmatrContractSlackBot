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
    public static final String BINARY = "608060405234801561001057600080fd5b506000600155610122806100256000396000f3fe6080604052348015600f57600080fd5b5060043610603c5760003560e01c8063acd482f3146041578063b6339418146059578063cfd1a7e2146075575b600080fd5b6047607b565b60408051918252519081900360200190f35b607360048036036020811015606d57600080fd5b50356081565b005b604760c4565b60015490565b60008190556040805182815290517fa7f1c945da9b165cf9d2d173e7bf823eb59569ecffef3d150fe6c74b6e78933f9181900360200190a1506001805481019055565b6040516000907f48cadc1eb88f0fcf074f538504e7613ce7c3eda3fa3b0768e21d21b5525de171908290a1506000549056fea165627a7a7230582018b84f485ef5f10e3be225d027ff1c98761f4fa27fbb02cb3e7ddad30034edf60029";

    public static final String FUNC_GETTIMESNUMBERSTORED = "getTimesNumberStored";

    public static final String FUNC_STORENUMBER = "storeNumber";

    public static final String FUNC_REQUESTNUMBER = "requestNumber";

    public static final Event NEWNUMBERSTORED_EVENT = new Event("newNumberStored", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
    ;

    public static final Event CALLEDREQUESTNUMBERFUNCTION_EVENT = new Event("calledRequestNumberFunction", 
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

    public RemoteFunctionCall<BigInteger> getTimesNumberStored() {
        final Function function = new Function(FUNC_GETTIMESNUMBERSTORED, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
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

    public List<NewNumberStoredEventResponse> getNewNumberStoredEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(NEWNUMBERSTORED_EVENT, transactionReceipt);
        ArrayList<NewNumberStoredEventResponse> responses = new ArrayList<NewNumberStoredEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            NewNumberStoredEventResponse typedResponse = new NewNumberStoredEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.number = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<NewNumberStoredEventResponse> newNumberStoredEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, NewNumberStoredEventResponse>() {
            @Override
            public NewNumberStoredEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(NEWNUMBERSTORED_EVENT, log);
                NewNumberStoredEventResponse typedResponse = new NewNumberStoredEventResponse();
                typedResponse.log = log;
                typedResponse.number = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<NewNumberStoredEventResponse> newNumberStoredEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(NEWNUMBERSTORED_EVENT));
        return newNumberStoredEventFlowable(filter);
    }

    public List<CalledRequestNumberFunctionEventResponse> getCalledRequestNumberFunctionEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(CALLEDREQUESTNUMBERFUNCTION_EVENT, transactionReceipt);
        ArrayList<CalledRequestNumberFunctionEventResponse> responses = new ArrayList<CalledRequestNumberFunctionEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            CalledRequestNumberFunctionEventResponse typedResponse = new CalledRequestNumberFunctionEventResponse();
            typedResponse.log = eventValues.getLog();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<CalledRequestNumberFunctionEventResponse> calledRequestNumberFunctionEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, CalledRequestNumberFunctionEventResponse>() {
            @Override
            public CalledRequestNumberFunctionEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(CALLEDREQUESTNUMBERFUNCTION_EVENT, log);
                CalledRequestNumberFunctionEventResponse typedResponse = new CalledRequestNumberFunctionEventResponse();
                typedResponse.log = log;
                return typedResponse;
            }
        });
    }

    public Flowable<CalledRequestNumberFunctionEventResponse> calledRequestNumberFunctionEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CALLEDREQUESTNUMBERFUNCTION_EVENT));
        return calledRequestNumberFunctionEventFlowable(filter);
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

    public static class NewNumberStoredEventResponse extends BaseEventResponse {
        public BigInteger number;
    }

    public static class CalledRequestNumberFunctionEventResponse extends BaseEventResponse {
    }
}
