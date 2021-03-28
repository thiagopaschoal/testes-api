package br.com.wcaquino.controllers.tests.suites;

import br.com.wcaquino.controllers.tests.*;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
@SelectClasses({
        AccountControllerTests.class,
        TransactionControllerTests.class,
        UserControllerTest.class,
        UserV2ControllerTest.class,
        FileControllerTests.class,
        HelloControllerTest.class,
        AuthenticationTests.class,
        JsonSchemaTests.class,
        XmlSchemaTests.class
})
public class Suite {
}
