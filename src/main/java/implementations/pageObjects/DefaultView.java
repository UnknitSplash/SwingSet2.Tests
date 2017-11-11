package implementations.pageObjects;

import interfaces.factories.ViewFactory;
import interfaces.pageObjects.DemoSwitcher;
import interfaces.pageObjects.Menu;
import interfaces.pageObjects.TableDemo;
import interfaces.pageObjects.View;

public class DefaultView implements View {

    ViewFactory factory;

    public DefaultView(ViewFactory factory){
        this.factory = factory;
    }

    @Override
    public Menu getMenu() {
        return factory.createMenu();
    }

    @Override
    public DemoSwitcher getDemoSwitcher() {
        return factory.createDemoSwitcher();
    }

    @Override
    public TableDemo getTableDemo() {
        return factory.createTableDemo();
    }
}