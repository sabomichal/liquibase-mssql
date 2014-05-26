package liquibase.ext.mssql.statement;

import liquibase.statement.core.CreateIndexStatement;

public class CreateIndexStatementMSSQL extends CreateIndexStatement {
  private String includedColumns;

  public String getIncludedColumns() {
    return includedColumns;
  }

  public void setIncludedColumns(String includedColumns) {
    this.includedColumns = includedColumns;
  }

  public CreateIndexStatementMSSQL(CreateIndexStatement createIndexStatement, String includedColumns) {
    super(createIndexStatement.getIndexName(), createIndexStatement.getTableCatalogName(),
        createIndexStatement.getTableSchemaName(), createIndexStatement.getTableName(),
        createIndexStatement.isUnique(), createIndexStatement.getAssociatedWith(),
        createIndexStatement.getColumns());

    this.includedColumns = includedColumns;
  }
}
