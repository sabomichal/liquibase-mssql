package liquibase.ext.mssql.sqlgenerator;

import liquibase.database.Database;
import liquibase.ext.mssql.statement.CreateIndexStatementMSSQL;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.CreateIndexGenerator;
import liquibase.statement.core.CreateIndexStatement;
import liquibase.util.StringUtils;

import java.util.Arrays;
import java.util.Iterator;

/**
 * Created by twhitbeck on 5/23/2014.
 */
public class CreateIndexGeneratorMSSQL extends CreateIndexGenerator {
  @Override
  public int getPriority() {
    return 15;
  }

  @Override
  public Sql[] generateSql(CreateIndexStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
    if (statement instanceof CreateIndexStatementMSSQL &&
        ((CreateIndexStatementMSSQL)statement).getIncludedColumnNames() != null &&
        !((CreateIndexStatementMSSQL)statement).getIncludedColumnNames().isEmpty()) {
      return generateMSSQLSql((CreateIndexStatementMSSQL)statement, database, sqlGeneratorChain);
    }

    return super.generateSql(statement, database, sqlGeneratorChain);
  }

  private Sql[] generateMSSQLSql(CreateIndexStatementMSSQL statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
    StringBuffer buffer = new StringBuffer();

    // Basically copied from liquibase.sqlgenerator.core.CreateIndexGenerator
    buffer.append("CREATE ");
    if (statement.isUnique() != null && statement.isUnique()) {
      buffer.append("UNIQUE ");
    }
    buffer.append("INDEX ");

    if (statement.getIndexName() != null) {
      String indexSchema = statement.getTableSchemaName();
      buffer.append(database.escapeIndexName(statement.getTableCatalogName(), indexSchema, statement.getIndexName())).append(" ");
    }
    buffer.append("ON ");
    buffer.append(database.escapeTableName(statement.getTableCatalogName(), statement.getTableSchemaName(), statement.getTableName())).append("(");
    Iterator<String> iterator = Arrays.asList(statement.getColumns()).iterator();
    while (iterator.hasNext()) {
      String column = iterator.next();
      buffer.append(database.escapeColumnName(statement.getTableCatalogName(), statement.getTableSchemaName(), statement.getTableName(), column));
      if (iterator.hasNext()) {
        buffer.append(", ");
      }
    }
    buffer.append(") INCLUDE (");
    buffer.append(database.escapeColumnNameList(statement.getIncludedColumnNames()));
    buffer.append(") ");

    // This block simplified, since we know we have MSSQLDatabase
    if (StringUtils.trimToNull(statement.getTablespace()) != null) {
      buffer.append(" ON ").append(statement.getTablespace());
    }

    return new Sql[]{new UnparsedSql(buffer.toString(), getAffectedIndex(statement))};
  }
}
