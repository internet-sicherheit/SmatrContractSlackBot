import com.slack.api.bolt.App;
import com.slack.api.bolt.jetty.SlackAppServer;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

public class MyApp {
    public static void main(String[] args) throws Exception {


        Web3j.build(new HttpService("HTTP://127.0.0.1:7545"));
        var app = new App();

        // All the room in the world for your code

        var server = new SlackAppServer(app);
        server.start();
    }
}