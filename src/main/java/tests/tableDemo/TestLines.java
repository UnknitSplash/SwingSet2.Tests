package tests.tableDemo;

import implementations.fixtures.AppletSetupFixture;
import implementations.fixtures.TableDemoPrepareLookFixture;
import interfaces.fixtures.PrepareLookFixture;
import interfaces.fixtures.SetupFixture;
import interfaces.pageObjects.TableDemo;
import org.junit.*;
import org.junit.rules.Timeout;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import utils.Platform;
import utils.Specification;

import java.awt.*;
import java.util.Collection;

@RunWith(value = Parameterized.class)
public class TestLines {
    private final SetupFixture setupFixture = new AppletSetupFixture();
    private final PrepareLookFixture<TableDemo> prepareDemo = new TableDemoPrepareLookFixture();
    private final Specification spec;
    private TableDemo demo;

    @Rule
    public Timeout globalTimeout = Timeout.millis(Platform.getConfigProp("testTimeout").asInt());

    @Before
    public void Setup() {
        demo = prepareDemo.prepare(setupFixture.init(), spec.get("menu.lookAndFeel").asString());
    }

    @After
    public void Close() {
        setupFixture.dispose();
    }

    @Parameterized.Parameters(name = "{0}")
    public static Collection<String> data() {
        return Specification.getSpecifications();
    }

    public TestLines(String specName) {
        spec = new Specification(specName);
    }

    private class TestData {
        public Color getSpecGridColor() {
            return spec.get("tableDemo.gridColor").asColor();
        }
    }

    /**
     * Change Look & Feel to the one in specification
     * Go to Table Demo
     * Enable horizontal lines
     * Disable vertical lines
     * Get grid color
     * Check that horizontal lines are showing
     * Check that grid color corresponds to specification
     */
    @Test
    public void TestHorizontalLines(){
        demo.disableHorizontalLines();
        demo.enableHorizontalLines();
        demo.disableVerticalLines();
        boolean horizLinesEnabled = demo.horizontalLinesEnabled();
        Color gridColor = demo.getGridColor();

        Assert.assertTrue(horizLinesEnabled);
        Assert.assertEquals(gridColor, new TestData().getSpecGridColor());
    }

    /**
     * Change Look & Feel to the one in specification
     * Go to Table Demo
     * Enable vertical lines
     * Disable horizontal lines
     * Get grid color
     * Check that vertical lines are showing
     * Check that grid color corresponds to specification
     */
    @Test
    public void TestVerticalLines(){
        demo.disableVerticalLines();
        demo.enableVerticalLines();
        demo.disableHorizontalLines();
        boolean vertLinesEnabled = demo.verticalLinesEnabled();
        Color gridColor = demo.getGridColor();

        Assert.assertTrue(vertLinesEnabled);
        Assert.assertEquals(gridColor, new TestData().getSpecGridColor());
    }

    /**
     * Change Look & Feel to the one in specification
     * Go to Table Demo
     * Enable horizontal lines
     * Enable vertical lines
     * Get grid color
     * Check that horizontal and vertical lines are showing
     * Check that grid color corresponds to specification
     */
    @Test
    public void TestHorizontalAndVerticalLines(){
        demo.disableHorizontalLines();
        demo.disableVerticalLines();
        demo.enableHorizontalLines();
        demo.enableVerticalLines();
        boolean horizLinesEnabled = demo.horizontalLinesEnabled();
        boolean vertLinesEnabled = demo.verticalLinesEnabled();
        Color gridColor = demo.getGridColor();

        Assert.assertTrue(horizLinesEnabled);
        Assert.assertTrue(vertLinesEnabled);
        Assert.assertEquals(gridColor, new TestData().getSpecGridColor());
    }

    /**
     * Change Look & Feel to the one in specification
     * Go to Table Demo
     * Disable horizontal lines
     * Disable vertical lines
     * Get grid color
     * Check that horizontal and vertical lines are not showing
     * Check that grid color corresponds to specification
     */
    @Test
    public void TestDisabledLines(){
        demo.disableHorizontalLines();
        demo.disableVerticalLines();
        boolean horizLinesEnabled = demo.horizontalLinesEnabled();
        boolean vertLinesEnabled = demo.verticalLinesEnabled();

        Assert.assertFalse(horizLinesEnabled);
        Assert.assertFalse(vertLinesEnabled);
    }
}
