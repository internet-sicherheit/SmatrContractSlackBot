import Web3j.Event;
import Web3j.StoredContract;
import Web3j.Web3jMain;
import com.slack.api.bolt.App;

import com.slack.api.bolt.jetty.SlackAppServer;

import com.slack.api.methods.SlackApiException;

import org.web3j.protocol.core.methods.response.Log;
import org.web3j.tx.Contract;

import java.io.IOException;
import java.util.ArrayList;


public class MyApp {





    //private static SampleHandler sampleHandler;


    public static void main(String[] args) throws Exception {


        App app = new App();
        Web3jMain web3j = new Web3jMain();


//        app.command("/info", (req, ctx) -> {
//            return ctx.ack(res -> res.responseType("in_channel").text("I'm a bot to interact with Smart Contracts and listen to events"));        });




        /*sets the eventnames for the contract
            User has to give all event names of the contract seperated by spaces
        */
        app.command("/storecontract", (req, ctx) -> {

            try {
                web3j.storeNewContractFromSlack(req.getPayload().getText());
            } catch (Exception e) {
                e.printStackTrace();
            }

            return ctx.ack("Stored the contractaddress and it's events and is now the active Contract ");


        });
        app.command("/switchActiveContract", (req, ctx) -> {

            String successMessage = null;
            try {
                successMessage = web3j.switchActiveContract(req.getPayload().getText());
            } catch (Exception e) {
                e.printStackTrace();
            }

            return ctx.ack(successMessage);


        });


//        app.command("/listentoallevents", (req, ctx) -> {
//
//
//            EthFilter filter = new EthFilter(DefaultBlockParameterName.LATEST, DefaultBlockParameterName.LATEST, numberContract.getContractAddress().substring(2));
//
//
//            web3j.getWeb3j().ethLogFlowable(filter).subscribe(log -> botPostListenerMessage(log));
//
//            return ctx.ack("Added listener to contract");
//
//
//        });

        app.command("/listentoeventx", (req, ctx) -> {


            String successMessage = "";
            successMessage = web3j.listenToEventX(req.getPayload().getText());


            return ctx.ack(successMessage);
        });




        //listen to specific event
        app.command("/info", (req, ctx) -> {



            ArrayList<StoredContract> contracts = web3j.getContractManager().getStoredContracts();
            String contractsString = "";
            for (int i = 0; i < contracts.size(); i++) {
                StoredContract currentContract = contracts.get(i);
                contractsString += currentContract.getContractAddress() + "\n Events: ";

                for (int j = 0; j < currentContract.getEvents().size(); j++) {

                    contractsString += currentContract.getEvents().get(j).getName() + "\n";
                }
            }



            return ctx.ack(contractsString);
        });


//currently not in use
//        // Pattern sdk = Pattern.compile("check contract.*|listenToEvents", Pattern.CASE_INSENSITIVE);
//        Pattern sdk = Pattern.compile("deploy contract.*", Pattern.CASE_INSENSITIVE);
//
//        Pattern store = Pattern.compile("store number.*", Pattern.CASE_INSENSITIVE);
//
//        String notificationChannelId = "C017K8W3PTP";
//// check if the message contains some monitoring keyword
//        app.message(sdk, (payload, ctx) -> {
//
//            String address = "";
//
//            DeployContract d = new DeployContract();
//            try {
//                address = d.deploy();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            ChatPostMessageResponse response = ctx.say("Your contract has been deployed. The address on the ethereum blockchain is: \n" + address);
//            MessageEvent event = payload.getEvent();
//            String text = event.getText();
//            System.out.println(text);
//            MethodsClient client = ctx.client();
//
//            // Add reacji to the message
//
//            String channelId = event.getChannel();
//            String ts = event.getTs();
//            ReactionsAddResponse reaction = client.reactionsAdd(r -> r.channel(channelId).timestamp(ts).name("white_check_mark"));
//            if (!reaction.isOk()) {
//                ctx.logger.error("reactions.add failed: {}", reaction.getError());
//            }
//
//            // Send the message to the SDK author
//            ChatGetPermalinkResponse permalink = client.chatGetPermalink(r -> r.channel(channelId).messageTs(ts));
//            if (permalink.isOk()) {
//                ChatPostMessageResponse message = client.chatPostMessage(r -> r
//                        .channel(notificationChannelId)
//                        .text("Testing reaction emoji" + permalink.getPermalink())
//                        .unfurlLinks(true));
//                if (!message.isOk()) {
//                    ctx.logger.error("chat.postMessage failed: {}", message.getError());
//                }
//            } else {
//                ctx.logger.error("chat.getPermalink failed: {}", permalink.getError());
//            }
//
//            return ctx.ack();
//        });


       // app.command("/storenumber", (req, ctx) -> {
//            String botRespondText;
//            String commandArgText = req.getPayload().getText();
//            int number;
//
//            try {
//                number = Integer.parseInt(commandArgText);
//
//            } catch (NumberFormatException e) {
//
//                botRespondText = "Could not handle: " + commandArgText + ". Please enter a correct number";
//                return ctx.ack(botRespondText);
//            }
//
//            try {
//                numberContract.storeNumber(BigInteger.valueOf(number)).send();
//            } catch (Exception e) {
//                e.printStackTrace();
//                return ctx.ack("Something went wrong");
//            }
//            botRespondText = "Your number has been stored into the Contract";
//
//            ctx.say("hed");
//
//            return ctx.ack(botRespondText); // respond with 200 OK
//        });

        SlackAppServer server = new SlackAppServer(app);
        server.start();

    }

    private static void botPostListenerMessage(Log log) throws IOException, SlackApiException {






        System.out.println(log.getData() + " /n" + log.getTopics());
        System.out.println(log);
        System.out.println();



    }

}