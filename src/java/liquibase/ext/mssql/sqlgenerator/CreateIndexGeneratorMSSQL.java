package liquibase.ext.mssql.sqlgenerator;

import liquibase.change.AddColumnConfig;
import liquibase.database.Database;
import liquibase.ext.mssql.database.MSSQLDatabase;
import liquibase.ext.mssql.statement.CreateIndexStatementMSSQL;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.CreateIndexGenerator;
import liquibase.statement.core.CreateIndexStatement;
import liquibase.util.StringUtils;

import java.util.Arrays;
import java.util.Iterator;

public class CreateIndexGeneratorMSSQL extends CreateIndexGenerator {
  @Override
  public int getPriority() {
    return 15;
  }

  @Override
  public Sql[] generateSql(CreateIndexStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
    if (statement instanceof CreateIndexStatementMSSQL) {
      return generateMSSQLSql((CreateIndexStatementMSSQL)statement, database, sqlGeneratorChain);
    }

    return super.generateSql(statement, database, sqlGeneratorChain);
  }

  private Sql[] generateMSSQLSql(CreateIndexStatementMSSQL statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
    StringBuilder builder = new StringBuilder();

    // Basically copied from liquibase.sqlgenerator.core.CreateIndexGenerator
    builder.append("CREATE ");
    if (statement.isUnique() != null && statement.isUnique()) {
      builder.append("UNIQUE ");
    }
    
    if (statement.isClustered() != null) {
        if (statement.isClustered()) {
            builder.append("CLUSTERED ");
        } else {
            builder.append("NONCLUSTERED ");
        }
    }
    
    builder.append("INDEX ");

    if (statement.getIndexName() != null) {
      String indexSchema = statement.getTableSchemaName();
      builder.append(database.escapeIndexName(statement.getTableCatalogName(), indexSchema, statement.getIndexName())).append(" ");
    }
    builder.append("ON ");
    builder.append(database.escapeTableName(statement.getTableCatalogName(), statement.getTableSchemaName(), statement.getTableName())).append("(");
    Iterator<AddColumnConfig> iterator = Arrays.asList(statement.getColumns()).iterator();
    while (iterator.hasNext()) {
      AddColumnConfig column = iterator.next();
      builder.append(database.escapeColumnName(statement.getTableCatalogName(), statement.getTableSchemaName(), statement.getTableName(), column.getName()));
      if (column.getDescending() != null && column.getDescending()) {
          builder.append(" DESC");
      }
      if (iterator.hasNext()) {
        builder.append(", ");
      }
    }
    if (statement.getIncludedColumns() != null && ! statement.getIncludedColumns().isEmpty()) {
      builder.append(") INCLUDE (");
      builder.append(database.escapeColumnNameList(statement.getIncludedColumns()));
    }
    builder.append(")");
    if (statement.getFillFactor() != null) {
      builder.append(" WITH (FILLFACTOR = ").append(statement.getFillFactor()).append(")");
    }
    // This block simplified, since we know we have MSSQLDatabase
    if (StringUtils.trimToNull(statement.getTablespace()) != null) {
      builder.append(" ON ").append(statement.getTablespace());
    }

    return new Sql[]{new UnparsedSql(builder.toString(), getAffectedIndex(statement))};
  }

  @Override
  public boolean supports(CreateIndexStatement statement, Database database) {
    return database instanceof MSSQLDatabase;
  }
}
