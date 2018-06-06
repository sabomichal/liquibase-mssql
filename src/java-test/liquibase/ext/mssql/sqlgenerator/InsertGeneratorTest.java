package liquibase.ext.mssql.sqlgenerator;

import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.OfflineConnection;
import liquibase.exception.DatabaseException;
import liquibase.ext.mssql.statement.InsertStatementMSSQL;
import liquibase.sql.Sql;
import liquibase.sqlgenerator.SqlGeneratorFactory;
import liquibase.statement.core.InsertStatement;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class InsertGeneratorTest {
    @Test
    public void integrates() throws DatabaseException {

        //Liquibase must find our mssql impl.
        Database database= DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new OfflineConnection("offline:mssql", null));

        InsertStatement statement = new InsertStatement(null, null, "TABLE_NAME");
        statement.addColumnValue("id", 1);
        statement.addColumnValue("name", "asdf");
        
        statement = new InsertStatementMSSQL(statement, true);

        Sql[] sql = SqlGeneratorFactory.getInstance().generateSql(statement, database);
        assertEquals(3, sql.length);

        for (Sql currentSql : sql) {
            assertSqlHasNoDanglingTokens(currentSql.toSql());
        }
    }

    private void assertSqlHasNoDanglingTokens(String s) {
        //System.out.println(s);
        assertFalse(s.contains("${"));
        assertFalse(s.contains("}"));
    }
}
