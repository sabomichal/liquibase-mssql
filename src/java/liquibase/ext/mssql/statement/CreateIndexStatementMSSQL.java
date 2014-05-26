package liquibase.ext.mssql.statement;

import liquibase.statement.core.CreateIndexStatement;

/**
 * Created by twhitbeck on 5/23/2014.
 */
public class CreateIndexStatementMSSQL extends CreateIndexStatement {
  private String includedColumnNames;

  public String getIncludedColumnNames() {
    return includedColumnNames;
  }

  public void setIncludedColumnNames(String includedColumnNames) {
    this.includedColumnNames = includedColumnNames;
  }

  public CreateIndexStatementMSSQL(CreateIndexStatement createIndexStatement, String includedColumnNames) {
    super(createIndexStatement.getIndexName(), createIndexStatement.getTableCatalogName(),
        createIndexStatement.getTableSchemaName(), createIndexStatement.getTableName(),
        createIndexStatement.isUnique(), createIndexStatement.getAssociatedWith(),
        createIndexStatement.getColumns());

    this.includedColumnNames = includedColumnNames;
  }
}
