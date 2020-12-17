import com.slack.api.bolt.App;
import com.slack.api.bolt.aws_lambda.SlackApiLambdaHandler;
import com.slack.api.bolt.aws_lambda.request.ApiGatewayRequest;

public class SampleHandler extends SlackApiLambdaHandler {


    public SampleHandler(App app) {
        super(app);
    }

    @Override
    protected boolean isWarmupRequest(ApiGatewayRequest apiGatewayRequest) {
        return false;
    }
}