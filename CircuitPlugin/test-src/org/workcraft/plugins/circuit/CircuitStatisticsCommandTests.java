package org.workcraft.plugins.circuit;

import java.net.URL;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.workcraft.Framework;
import org.workcraft.exceptions.DeserialisationException;
import org.workcraft.gui.DesktopApi;
import org.workcraft.plugins.circuit.commands.CircuitStatisticsCommand;
import org.workcraft.plugins.statistics.BasicStatisticsCommand;
import org.workcraft.util.PackageUtils;
import org.workcraft.workspace.WorkspaceEntry;

public class CircuitStatisticsCommandTests {

    @BeforeClass
    public static void init() {
        final Framework framework = Framework.getInstance();
        framework.init();
        switch (DesktopApi.getOs()) {
        case LINUX:
            CircuitSettings.setGateLibrary("dist-template/linux/libraries/workcraft.lib");
            break;
        case MACOS:
            CircuitSettings.setGateLibrary("dist-template/osx/Contents/Resources/libraries/workcraft.lib");
            break;
        case WINDOWS:
            CircuitSettings.setGateLibrary("dist-template\\windows\\libraries\\workcraft.lib");
            break;
        default:
        }
    }

    @Test
    public void testBufferTmCircuitStatisticsCommand() throws DeserialisationException {
        String workName = PackageUtils.getPackagePath(getClass(), "buffer-tm.circuit.work");
        testCircuitStatisticsCommand(workName,
                "Component count:"
                        + "\n  Component -  1"
                        + "\n  Port -  2"
                        + "\n",
                "Circuit analysis:"
                        + "\n  Component count (mapped + unmapped) -  1 (1 + 0)"
                        + "\n  Area of mapped components -  0.0"
                        + "\n  Driver pin count (combinational + sequential + undefined) -  1 (1 + 0 + 0)"
                        + "\n  Literal count combinational / sequential (set + reset) -  1 / 0 (0 + 0)"
                        + "\n  Port count (input + output) -  2 (1 + 1)"
                        + "\n  Max fanin / fanout -  1 / 1"
                        + "\n  Fanin distribution [0 / 1 / 2 ...] -  0 / 1"
                        + "\n  Fanout distribution [0 / 1 / 2 ...] -  0 / 2"
                        + "\n  Isolated components / ports / pins -  0 / 0 / 0"
                        + "\n");
    }

    @Test
    public void testCelementDecomposedTmCircuitStatisticsCommand() throws DeserialisationException {
        String workName = PackageUtils.getPackagePath(getClass(), "celement-decomposed-tm.circuit.work");
        testCircuitStatisticsCommand(workName,
                "Component count:"
                        + "\n  Component -  5"
                        + "\n  Port -  3"
                        + "\n",
                "Circuit analysis:"
                        + "\n  Component count (mapped + unmapped) -  5 (5 + 0)"
                        + "\n  Area of mapped components -  72.0"
                        + "\n  Driver pin count (combinational + sequential + undefined) -  5 (5 + 0 + 0)"
                        + "\n  Literal count combinational / sequential (set + reset) -  11 / 0 (0 + 0)"
                        + "\n  Port count (input + output) -  3 (2 + 1)"
                        + "\n  Max fanin / fanout -  3 / 2"
                        + "\n  Fanin distribution [0 / 1 / 2 ...] -  0 / 0 / 4 / 1"
                        + "\n  Fanout distribution [0 / 1 / 2 ...] -  0 / 2 / 5"
                        + "\n  Isolated components / ports / pins -  0 / 0 / 0"
                        + "\n");
    }

    @Test
    public void testVmeTmCircuitStatisticsCommand() throws DeserialisationException {
        String workName = PackageUtils.getPackagePath(getClass(), "vme-tm.circuit.work");
        testCircuitStatisticsCommand(workName,
                "Component count:"
                        + "\n  Component -  21"
                        + "\n  Port -  6"
                        + "\n",
                "Circuit analysis:"
                        + "\n  Component count (mapped + unmapped) -  21 (21 + 0)"
                        + "\n  Area of mapped components -  292.0"
                        + "\n  Driver pin count (combinational + sequential + undefined) -  21 (20 + 1 + 0)"
                        + "\n  Literal count combinational / sequential (set + reset) -  47 / 4 (2 + 2)"
                        + "\n  Port count (input + output) -  6 (3 + 3)"
                        + "\n  Max fanin / fanout -  6 / 8"
                        + "\n  Fanin distribution [0 / 1 / 2 ...] -  0 / 12 / 2 / 1 / 1 / 4 / 1"
                        + "\n  Fanout distribution [0 / 1 / 2 ...] -  0 / 17 / 1 / 1 / 2 / 0 / 1 / 0 / 2"
                        + "\n  Isolated components / ports / pins -  0 / 0 / 0"
                        + "\n");
    }

    private void testCircuitStatisticsCommand(String workName, String expectedBasicStatistics, String expectedAdvancedStatistics)
            throws DeserialisationException {
        final Framework framework = Framework.getInstance();
        final ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        URL url = classLoader.getResource(workName);
        WorkspaceEntry we = framework.loadWork(url.getFile());

        BasicStatisticsCommand basicStatisticsCommand = new BasicStatisticsCommand();
        String basicStatistics = basicStatisticsCommand.execute(we);
        Assert.assertEquals(basicStatistics, expectedBasicStatistics);

        CircuitStatisticsCommand advancedStatisticsCommand = new CircuitStatisticsCommand();
        String advancedStatistics = advancedStatisticsCommand.execute(we);
        Assert.assertEquals(advancedStatistics, expectedAdvancedStatistics);

        framework.closeWork(we);
    }

}
