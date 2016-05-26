package allbegray.slack.webapi.method.im;

import allbegray.slack.webapi.method.AbstractHistoryMethod;
import allbegray.slack.webapi.SlackWebApiConstants;

public class ImHistoryMethod extends AbstractHistoryMethod {

	public ImHistoryMethod(String channel) {
		super(channel);
	}

	@Override
	public String getMethodName() {
		return SlackWebApiConstants.IM_HISTORY;
	}

}
