package liquibase.ext.mssql.sqlgenerator;

import liquibase.change.AddColumnConfig;
import liquibase.database.core.MSSQLDatabase;
import liquibase.ext.mssql.statement.CreateIndexStatementMSSQL;
import liquibase.sql.Sql;
import liquibase.sqlgenerator.SqlGeneratorFactory;
import liquibase.statement.core.CreateIndexStatement;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class IndexGeneratorTest {
    @Test
    public void integrates() {
        final AddColumnConfig firstColumnConfig = new AddColumnConfig();
        firstColumnConfig.setName("id");
        final AddColumnConfig secondColumnConfig = new AddColumnConfig();
        secondColumnConfig.setName("name");
        CreateIndexStatement statement = new CreateIndexStatement(null, null, null, "TABLE_NAME", true, null, firstColumnConfig, secondColumnConfig);

        statement = new CreateIndexStatementMSSQL(statement, "included, includedtoo");

        Sql[] sql = SqlGeneratorFactory.getInstance().generateSql(statement, new MSSQLDatabase());
        assertEquals("CREATE UNIQUE INDEX ON [TABLE_NAME]([id], [name]) INCLUDE ([included], [includedtoo])", sql[0].toSql());
    }
}
