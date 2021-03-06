package tests.tableDemo;

import implementations.fixtures.AppletSetupFixture;
import implementations.fixtures.TableDemoPrepareFixture;
import interfaces.fixtures.PrepareFixture;
import interfaces.fixtures.SetupFixture;
import interfaces.pageObjects.TableDemo;
import org.junit.*;
import org.junit.rules.Timeout;
import utils.Platform;
import utils.ResourceManager;
import utils.Specification;

public class TestInitialState {
    private final SetupFixture setupFixture = new AppletSetupFixture();
    private final PrepareFixture<TableDemo> prepareDemo = new TableDemoPrepareFixture();
    private final Specification spec = new Specification();
    private TableDemo demo;

    @Rule
    public Timeout globalTimeout = Timeout.millis(Platform.getConfigProp("testTimeout").asInt());

    @Before
    public void Setup() {
        demo = prepareDemo.prepare(setupFixture.init());
    }

    @After
    public void Close() {
        setupFixture.dispose();
    }

    private class TestData {
        boolean reordering = spec.get("tableDemo.initialReordering").asBoolean();
        boolean horizLines = spec.get("tableDemo.initialHorizLines").asBoolean();
        boolean vertLines = spec.get("tableDemo.initialVertLines").asBoolean();
        boolean rowSelection = spec.get("tableDemo.initialRowSelection").asBoolean();
        boolean columnSelection = spec.get("tableDemo.initialColumnSelection").asBoolean();
        int intercellSpacing = spec.get("tableDemo.initialIntercellSpacing").asInt();
        int rowHeight = spec.get("tableDemo.initialRowHeight").asInt();
        String selectionMode = ResourceManager.getResString("TableDemo.multiple_ranges");
        String autoresizeMode = ResourceManager.getResString("TableDemo.subsequent_columns");
    }

    /**
     * Go to Table Demo
     * Check that all controls are set as described in specification
     */
    @Test
    public void TestInitialState(){
        TestData testData = new TestData();
        boolean reordering = demo.isReorderingAllowed();
        boolean horizLines = demo.isHorizontalLinesControlEnabled();
        boolean vertLines = demo.isVerticalLinesControlEnabled();
        boolean rowSelection = demo.isRowSelectionEnabled();
        boolean columnSelection = demo.isColumnSelectionEnabled();
        int intercellSpacing = demo.getIntercellSpacingValue();
        int rowHeight = demo.getRowHeightValue();
        String selectionMode = demo.getSelectionMode();
        String autoresizeMode = demo.getAutoresizeMode();

        Assert.assertEquals(reordering,testData.reordering);
        Assert.assertEquals(horizLines, testData.horizLines);
        Assert.assertEquals(vertLines, testData.vertLines);
        Assert.assertEquals(rowSelection, testData.rowSelection);
        Assert.assertEquals(columnSelection, testData.columnSelection);
        Assert.assertEquals(intercellSpacing, testData.intercellSpacing);
        Assert.assertEquals(rowHeight, testData.rowHeight);
        Assert.assertEquals(selectionMode, testData.selectionMode);
        Assert.assertEquals(autoresizeMode, testData.autoresizeMode);
    }
}
