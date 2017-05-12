package liquibase.ext.mssql.statement;

import liquibase.statement.core.CreateIndexStatement;

public class CreateIndexStatementMSSQL extends CreateIndexStatement {
  private String includedColumns;

  private Integer fillFactor;

  public String getIncludedColumns() {
    return includedColumns;
  }

  public void setIncludedColumns(String includedColumns) {
    this.includedColumns = includedColumns;
  }

  public Integer getFillFactor() { return fillFactor; }

  public void setFillFactor(Integer fillFactor) { this.fillFactor = fillFactor; }

  public CreateIndexStatementMSSQL(CreateIndexStatement createIndexStatement, String includedColumns, Integer fillFactor) {
    super(createIndexStatement.getIndexName(), createIndexStatement.getTableCatalogName(),
        createIndexStatement.getTableSchemaName(), createIndexStatement.getTableName(),
        createIndexStatement.isUnique(), createIndexStatement.getAssociatedWith(),
        createIndexStatement.getColumns());

    this.includedColumns = includedColumns;
    this.fillFactor = fillFactor;
  }
}
