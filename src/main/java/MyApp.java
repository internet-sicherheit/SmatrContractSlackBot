

import Web3j.Web3jMain;
import com.slack.api.bolt.App;
import com.slack.api.bolt.context.builtin.SlashCommandContext;
import com.slack.api.bolt.handler.builtin.SlashCommandHandler;
import com.slack.api.bolt.jetty.SlackAppServer;
import com.slack.api.bolt.request.builtin.SlashCommandRequest;
import com.slack.api.bolt.response.Response;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import com.slack.api.webhook.WebhookResponse;

import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.response.chat.ChatGetPermalinkResponse;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import com.slack.api.methods.response.reactions.ReactionsAddResponse;
import com.slack.api.model.event.MessageEvent;

import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import com.slack.api.model.event.ReactionAddedEvent;

import java.util.Arrays;
import java.util.regex.Pattern;

import java.io.IOException;

public class MyApp {
    public static void main(String[] args) throws Exception {
        // App expects env variables (SLACK_BOT_TOKEN, SLACK_SIGNING_SECRET)



        var app = new App();


        Web3jMain web3j = new Web3jMain();

        app.command("/info", (req, ctx) -> {
            return ctx.ack(res -> res.responseType("in_channel").text("I'm a bot to interact with Smart Contracts and listen to events"));
        });

        app.command("/hello1", (req, ctx) -> {
            // Post a message via response_url
            System.out.println("he");
            WebhookResponse result = ctx.respond(res -> res
                    .responseType("in_channel") // or "in_channnel"
                    .text("Hi there!") // blocks, attachments are also available
            );
            return ctx.ack(); // ack() here doesn't post a message
        });


        app.command("/hello2", (req, ctx) -> {
            // ctx.client() holds a valid bot token
            ChatPostMessageResponse response = ctx.client().chatPostMessage(r -> r
                    .channel(ctx.getChannelId())
                    .text(":wave: How are you?")
            );
            return ctx.ack();
        });


        app.command("/hello3", (req, ctx) -> {
            ChatPostMessageResponse response = ctx.say(":wave: How are you?");
            return ctx.ack();
        });




        Pattern sdk = Pattern.compile("check contract.*|listenToEvents", Pattern.CASE_INSENSITIVE);

        String notificationChannelId = "C017K8W3PTP";
// check if the message contains some monitoring keyword
        app.message(sdk, (payload, ctx) -> {


            MessageEvent event = payload.getEvent();
            String text = event.getText();
            System.out.println(text);
            MethodsClient client = ctx.client();

            // Add reacji to the message
            String channelId = event.getChannel();
            String ts = event.getTs();
            ReactionsAddResponse reaction = client.reactionsAdd(r -> r.channel(channelId).timestamp(ts).name("hourglass_flowing_sand"));
            if (!reaction.isOk()) {
                ctx.logger.error("reactions.add failed: {}", reaction.getError());
            }

            // Send the message to the SDK author
            ChatGetPermalinkResponse permalink = client.chatGetPermalink(r -> r.channel(channelId).messageTs(ts));
            if (permalink.isOk()) {
                ChatPostMessageResponse message = client.chatPostMessage(r -> r
                        .channel(notificationChannelId)
                        .text("Testing reaction emoji" + permalink.getPermalink())
                        .unfurlLinks(true));
                if (!message.isOk()) {
                    ctx.logger.error("chat.postMessage failed: {}", message.getError());
                }
            } else {
                ctx.logger.error("chat.getPermalink failed: {}", permalink.getError());
            }

            return ctx.ack();
        });


        app.event(ReactionAddedEvent.class, (payload, ctx) -> {
            ReactionAddedEvent event = payload.getEvent();
            if (event.getReaction().equals("white_check_mark")) {
                ChatPostMessageResponse message = ctx.client().chatPostMessage(r -> r
                        .channel(event.getItem().getChannel())
                        .threadTs(event.getItem().getTs())
                        .text("<@" + event.getUser() + "> Thank you! We greatly appreciate your efforts :two_hearts:"));
                if (!message.isOk()) {
                    ctx.logger.error("chat.postMessage failed: {}", message.getError());
                }
            }
            return ctx.ack();
        });




        var server = new SlackAppServer(app);
        server.start();
    }



}