package liquibase.ext.mssql.sqlgenerator;

import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.OfflineConnection;
import liquibase.exception.DatabaseException;
import org.junit.Test;

import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertThat;

public class DatabaseSelectionTest {

    @Test
    public void selectionTest() throws DatabaseException {
        Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new OfflineConnection("offline:mssql", null));

        assertThat("Not the expected database", database, instanceOf(liquibase.ext.mssql.database.MSSQLDatabase.class));
    }
}
