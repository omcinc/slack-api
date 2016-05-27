package allbegray.slack;

import allbegray.slack.type.*;
import allbegray.slack.webapi.SlackWebApiClient;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

public class DndMethodTest {

	private String token = "xoxp-18899142932-18905601458-41812031460-b54fb48eec";
	private SlackWebApiClient webApiClient;

	@Before
	public void setup() {
		webApiClient = SlackClientFactory.createWebApiClient(token);
	}

	@After
	public void shutdown() {
		webApiClient.shutdown();
	}

	@Test
	public void basicTest() {
		Authentication authentication = webApiClient.auth();

		Map<String, DndSimpleInfo> dndTeamInfo = webApiClient.getDndTeamInfo();
		Assert.assertTrue(dndTeamInfo.size() > 0);

		DndInfo dndInfo = webApiClient.getDndInfo(authentication.getUser_id());
		Assert.assertTrue(dndInfo.getSnooze_enabled() != null);

		boolean isEndDnd = webApiClient.endDnd();
		Assert.assertTrue(isEndDnd == true);

		SetSnooze setSnooze = webApiClient.setSnooze(1);
		Assert.assertTrue(setSnooze.getSnooze_enabled() == true);

		EndSnooze endSnooze = webApiClient.endSnooze();
		Assert.assertTrue(endSnooze.getSnooze_enabled() == false);

		isEndDnd = webApiClient.endDnd();
		Assert.assertTrue(isEndDnd == true);
	}

}
