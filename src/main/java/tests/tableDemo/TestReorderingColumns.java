package tests.tableDemo;

import implementations.fixtures.AppletSetupFixture;
import implementations.fixtures.TableDemoPrepareFixture;
import interfaces.fixtures.PrepareFixture;
import interfaces.fixtures.SetupFixture;
import interfaces.pageObjects.TableDemo;
import org.junit.*;
import org.junit.rules.Timeout;
import utils.Platform;
import utils.Specification;

import java.awt.*;
import java.util.Arrays;

public class TestReorderingColumns {

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
        final String columnToDrag = spec.get("tableDemo.columnToDrag").asString();
        final String columnWhereToDrag = spec.get("tableDemo.columnWhereToDrag").asString();
        final int rowNumber = spec.get("tableDemo.reorderingRowNumber").asInt();
    }

    private class TestDataCells {
        final String cellToDrag = spec.get("tableDemo.cellToDrag").asString();
        final String cellWhereToDrop = spec.get("tableDemo.cellWhereToDrop").asString();
    }

    /**
     * Go to Table Demo
     * Make sure, that reordering is allowed
     * Get index of the column we want to drag
     * Get index of column which place we'll occupy
     * Load number of the row we'll use as for a check
     * Get an original value of a cell in the column we are going to drag and specified row
     * Get points where to drag from and to
     * Drag and drop column to a new place
     * Get value from the cell we projected our original value to be in
     * Check if the values match
     */
    @Test
    public void TestDragColumnToNewPosition() {
        TestData testData = new TestData();

        demo.allowReordering();

        int startIndex = demo.getColumnIndex(testData.columnToDrag);
        int endIndex = demo.getColumnIndex(testData.columnWhereToDrag);


        String originalCellValue = demo.getStringFromTableCell(testData.rowNumber, startIndex);

        Point dragFrom = demo.getColumnHeaderPoint(testData.columnToDrag);
        Point dropTo = demo.getColumnHeaderPoint(testData.columnWhereToDrag);

        demo.drapAndDrop(dragFrom, dropTo);

        String newCellValue = demo.getStringFromTableCell(testData.rowNumber, endIndex);

        Assert.assertEquals(originalCellValue, newCellValue);
    }

    /**
     * Go to Table Demo
     * Make sure, that reordering is allowed
     * Create a new point based on the first one(subtract from y coordinate to make it higher)
     * Get original index of column to be dragged
     * Try to drag column up
     * Get index of the column again
     * Check that column is in the same place(same index)
     */
    @Test
    public void TestDragColumnUp() {
        TestData testData = new TestData();

        demo.allowReordering();

        Point dragFrom = demo.getColumnHeaderPoint(testData.columnToDrag);
        Point dropTo = new Point(dragFrom.x, dragFrom.y - 100);

        int startIndex = demo.getColumnIndex(testData.columnToDrag);

        demo.drapAndDrop(dragFrom, dropTo);

        int endIndex = demo.getColumnIndex(testData.columnToDrag);

        Assert.assertEquals(startIndex, endIndex);
    }

    /**
     * Go to Table Demo
     * Make sure, that reordering is allowed
     * Create a new point based on where we want to drag and subtract from y coordinate to make it higher
     * Get original index of column to be dragged
     * Try to drag column up and in desired direction
     * Get index of the column again
     * Check that column is in the desired place(by index)
     */
    @Test
    public void TestDragColumnUpAndToTheSide() {
        TestData testData = new TestData();

        demo.allowReordering();

        Point dragFrom = demo.getColumnHeaderPoint(testData.columnToDrag);
        Point dropTo = demo.getColumnHeaderPoint(testData.columnWhereToDrag);
        dropTo.y -= 100;

        int whereToDropIndex = demo.getColumnIndex(testData.columnWhereToDrag);

        demo.drapAndDrop(dragFrom, dropTo);

        int currentIndex = demo.getColumnIndex(testData.columnToDrag);

        Assert.assertEquals(currentIndex, whereToDropIndex);
    }

    /**
     * Go to Table Demo
     * Make sure, that reordering is allowed
     * Get the point from where to drag cell
     * Get the point where to drop the cell
     * Try to drag cell
     * Get position of the cell again
     * Check that cell's position didn't change
     */
    @Test
    public void TestTryDragCellUpAndToTheSide() {
        TestDataCells testDataCells = new TestDataCells();

        demo.allowReordering();

        Point dragFrom = demo.getCellPoint(testDataCells.cellToDrag);
        Point dropTo = demo.getCellPoint(testDataCells.cellWhereToDrop);

        demo.drapAndDrop(dragFrom, dropTo);

        Point newPosition = demo.getCellPoint(testDataCells.cellToDrag);

        Assert.assertEquals(dragFrom.x, newPosition.x);
        Assert.assertEquals(dragFrom.y, newPosition.y);
    }

    /**
     * Go to Table Demo
     * Make sure, that reordering is forbidden
     * Get points to drag from and to
     * Try to drag and drop one column onto another
     * Get new Point value for column we tried to drag
     * Check that the column is in the same position
     */
    @Test
    public void TestReorderingDisabled() {
        TestData testData = new TestData();

        demo.forbidReordering();

        Point dragFrom = demo.getColumnHeaderPoint(testData.columnToDrag);
        Point dropTo = demo.getColumnHeaderPoint(testData.columnWhereToDrag);

        demo.drapAndDrop(dragFrom, dropTo);

        Point actual = demo.getColumnHeaderPoint(testData.columnToDrag);
        Assert.assertEquals(dragFrom, actual);
    }

    /**
     * Go to Table Demo
     * Make sure, that reordering is allowed
     * Get index of the column we want to drag
     * Get index of column where we want to drag
     * Get points where to drag from and to
     * Drag column to a new place
     * Drag column to it's original place
     * Get index of column we dragged
     * Check that column is in the same position as originally
     */
    @Test
    public void TestDragColumnToNewPositionAndBack() {
        TestData testData = new TestData();

        demo.allowReordering();

        int startIndex = demo.getColumnIndex(testData.columnToDrag);

        Point dragFrom = demo.getColumnHeaderPoint(testData.columnToDrag);
        Point dragTo = demo.getColumnHeaderPoint(testData.columnWhereToDrag);

        demo.drapAndDrop(dragFrom, Arrays.asList(dragTo, dragFrom));

        int endIndex = demo.getColumnIndex(testData.columnToDrag);

        Assert.assertEquals(startIndex, endIndex);
    }
}
