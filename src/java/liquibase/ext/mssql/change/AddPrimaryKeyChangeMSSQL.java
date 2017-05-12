package liquibase.ext.mssql.change;

import liquibase.change.ChangeMetaData;
import liquibase.change.DatabaseChange;
import liquibase.change.core.AddPrimaryKeyChange;
import liquibase.database.Database;
import liquibase.ext.mssql.statement.AddPrimaryKeyStatementMSSQL;
import liquibase.statement.SqlStatement;
import liquibase.statement.core.AddPrimaryKeyStatement;

import java.util.ArrayList;
import java.util.List;

@DatabaseChange(name="addPrimaryKey", description = "Adds creates a primary key out of an existing column or set of columns.", priority = ChangeMetaData.PRIORITY_DEFAULT, appliesTo = "column")
public class AddPrimaryKeyChangeMSSQL extends AddPrimaryKeyChange {

  private Integer fillFactor;

  public Integer getFillFactor() {
    return fillFactor;
  }

  public void setFillFactor(Integer fillFactor) {
    this.fillFactor = fillFactor;
  }

  @Override
  public SqlStatement[] generateStatements(Database database) {
    SqlStatement[] statements = super.generateStatements(database);

    List<SqlStatement> extendedStatements = new ArrayList<SqlStatement>(statements.length);

    for (SqlStatement statement : statements) {
      if (statement instanceof AddPrimaryKeyStatement) {
        extendedStatements.add(new AddPrimaryKeyStatementMSSQL((AddPrimaryKeyStatement)statement, fillFactor));
      } else {
        extendedStatements.add(statement);
      }
    }

    return extendedStatements.toArray(new SqlStatement[0]);
  }
}