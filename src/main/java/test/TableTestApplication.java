package test;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;

public class TableTestApplication extends WebApplication {

    @Override
    public Class<? extends Page> getHomePage() {
        return TableTestPage.class;
    }

}
