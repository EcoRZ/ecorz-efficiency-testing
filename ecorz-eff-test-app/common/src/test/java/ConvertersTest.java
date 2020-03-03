import static org.junit.Assert.assertEquals;

import com.ecorz.stressapp.common.converters.AgentToEngineConfig;
import com.ecorz.stressapp.common.run.RunConfigParams;
import java.util.Arrays;
import java.util.Collection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class ConvertersTest {
  private final static String usedStr = AgentToEngineConfig.tgParamCmdLineDefString + "10 " +
      AgentToEngineConfig.urtParamCmdLineDefString + "100 " +
      AgentToEngineConfig.dumpFileCmdLineDefString + "dumpFile " +
      AgentToEngineConfig.jMeterHomeCmdLineDefString + "/home/john/jmeter " +
      AgentToEngineConfig.lbIpCmdLineDefString + "192.168.178.23 " +
      AgentToEngineConfig.lbPortCmdLineDefString + "8080 " +
      AgentToEngineConfig.wsTestStrCmdLineDefString + "test " +
      AgentToEngineConfig.testDurationCmdLineDefString + "43 " +
      AgentToEngineConfig.testDelayCmdLineDefString + "23 " +
      AgentToEngineConfig.totalArgsCmdLineDefString + "8";
  private final static RunConfigParams usedParams = new RunConfigParams("10",
      "100", "dumpFile","/home/john/jmeter", "192.168.178.23",
      "8080", "test", "43", "23", "8");

  private String testStr;
  private RunConfigParams params;

  public ConvertersTest(String str, RunConfigParams pars) {
    testStr = str;
    params = pars;
  }

  // creates the test data
  @Parameters
  public static Collection<Object[]> data() {
    Object[][] data = new Object[][] { { usedStr, usedParams } };
    return Arrays.asList(data);
  }

  @Test
  public void toStringTest() {
    assertEquals("Result", testStr, AgentToEngineConfig.INSTANCE.convertToString(params));
  }

  @Test
  public void toParamsTest() {
    assertEquals("Result", params, AgentToEngineConfig.INSTANCE.convertToParams(testStr));
  }
}
