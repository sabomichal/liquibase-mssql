package liquibase.ext.mssql.statement;

import liquibase.statement.core.AddPrimaryKeyStatement;

/**
 * SQL Server extension that adds support for optionally specifying the index fillfactor for a primary key.
 */
public class AddPrimaryKeyStatementMSSQL extends AddPrimaryKeyStatement {
  private Integer fillFactor;

  public Integer getFillFactor() { return fillFactor; }

  public void setFillFactor(Integer fillFactor) { this.fillFactor = fillFactor; }

  public AddPrimaryKeyStatementMSSQL(AddPrimaryKeyStatement statement, Integer fillFactor ) {
    super(statement.getCatalogName(),
          statement.getSchemaName(),
          statement.getTableName(),
          statement.getColumnNames(),
          statement.getConstraintName());

    this.setClustered(statement.isClustered());
    this.setFillFactor(fillFactor);
  }
}
