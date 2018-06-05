package liquibase.ext.mssql.sqlgenerator;

import liquibase.database.Database;
import liquibase.ext.mssql.database.MSSQLDatabase;
import liquibase.ext.mssql.statement.AddPrimaryKeyStatementMSSQL;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.AddPrimaryKeyGenerator;
import liquibase.statement.core.AddPrimaryKeyStatement;
import liquibase.structure.core.Index;
import liquibase.util.StringUtils;

public class AddPrimaryKeyGeneratorMSSQL extends AddPrimaryKeyGenerator {
  @Override
  public int getPriority() {
    return 15;
  }

  /**
   * Conditionally executes to extension's custom Primary Key SQL generation process if statement is the
   * AddPrimaryKeyStatementMSSQL implementation and the custom attribute (fillFactor) is set.
   *
   * Otherwise, defers to default liquibase implementation.
   *
   * @param statement
   * @param database
   * @param sqlGeneratorChain
   * @return
   */
  @Override
  public Sql[] generateSql(AddPrimaryKeyStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
    if (statement instanceof AddPrimaryKeyStatementMSSQL && ((AddPrimaryKeyStatementMSSQL) statement).getFillFactor() != null) {
      return generateMSSQLSql((AddPrimaryKeyStatementMSSQL)statement, database, sqlGeneratorChain);
    }

    return super.generateSql(statement, database, sqlGeneratorChain);
  }

  /**
   * The extension's implementation is essentially a copy/paste of the default implementation, with the following changes:
   *
   * 1) Removed other database platform specific logic other than MSSQL (purely to simplify)
   *
   * 2) Added support for setting fillFactor
   *
   * @param statement
   * @param database
   * @param sqlGeneratorChain
   * @return
   */
  private Sql[] generateMSSQLSql(AddPrimaryKeyStatementMSSQL statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
    String sql;
    if (statement.getConstraintName() == null) {
      sql = "ALTER TABLE " + database.escapeTableName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName()) + " ADD PRIMARY KEY (" + database.escapeColumnNameList(statement.getColumnNames()) + ")";
    } else {
      sql = "ALTER TABLE " + database.escapeTableName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName()) + " ADD CONSTRAINT " + database.escapeConstraintName(statement.getConstraintName())+" PRIMARY KEY";
      if (!statement.isClustered()) {
        sql += " NONCLUSTERED";
      }
      sql += " (" + database.escapeColumnNameList(statement.getColumnNames()) + ")";
    }

    // the only new feature being added is support for fillFactor
    sql += " WITH (FILLFACTOR = " + statement.getFillFactor() + ")";

    if (StringUtils.trimToNull(statement.getTablespace()) != null && database.supportsTablespaces()) {
      sql += " ON "+statement.getTablespace();
    }

    if (statement.getForIndexName() != null) {
      sql += " USING INDEX "+database.escapeObjectName(statement.getForIndexCatalogName(), statement.getForIndexSchemaName(), statement.getForIndexName(), Index.class);
    }

    return new Sql[] {
        new UnparsedSql(sql, getAffectedPrimaryKey(statement))
    };
  }

  @Override
  public boolean supports(AddPrimaryKeyStatement statement, Database database) {
    return database instanceof MSSQLDatabase;
  }

}
