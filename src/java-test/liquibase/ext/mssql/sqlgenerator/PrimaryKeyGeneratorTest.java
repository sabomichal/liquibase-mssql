package liquibase.ext.mssql.sqlgenerator;

import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.OfflineConnection;
import liquibase.exception.DatabaseException;
import liquibase.ext.mssql.statement.AddPrimaryKeyStatementMSSQL;
import liquibase.sql.Sql;
import liquibase.sqlgenerator.SqlGeneratorFactory;
import liquibase.statement.core.AddPrimaryKeyStatement;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PrimaryKeyGeneratorTest {
  @Test
  public void integrates() throws DatabaseException {

    //Liquibase must find our mssql impl.
    Database database= DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new OfflineConnection("offline:mssql", null));

    AddPrimaryKeyStatement statement = new AddPrimaryKeyStatement("myCat", "mySchema", "myTable", "myCol", "myConstraint");
    statement.setClustered(true);

    Sql[] sql = SqlGeneratorFactory.getInstance().generateSql(statement, database);
    assertEquals("ALTER TABLE [mySchema].[myTable] ADD CONSTRAINT [myConstraint] PRIMARY KEY ([myCol])", sql[0].toSql());

    statement = new AddPrimaryKeyStatementMSSQL(statement, null);
    sql = SqlGeneratorFactory.getInstance().generateSql(statement, database);
    assertEquals("ALTER TABLE [mySchema].[myTable] ADD CONSTRAINT [myConstraint] PRIMARY KEY ([myCol])", sql[0].toSql());

    statement = new AddPrimaryKeyStatementMSSQL(statement, 50);
    sql = SqlGeneratorFactory.getInstance().generateSql(statement, database);
    assertEquals("ALTER TABLE [mySchema].[myTable] ADD CONSTRAINT [myConstraint] PRIMARY KEY ([myCol]) WITH (FILLFACTOR = 50)", sql[0].toSql());
  }
}
