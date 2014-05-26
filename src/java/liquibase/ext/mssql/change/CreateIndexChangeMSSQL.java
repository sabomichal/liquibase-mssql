package liquibase.ext.mssql.change;

import liquibase.change.ChangeMetaData;
import liquibase.change.DatabaseChange;
import liquibase.change.core.CreateIndexChange;
import liquibase.database.Database;
import liquibase.ext.mssql.statement.CreateIndexStatementMSSQL;
import liquibase.statement.SqlStatement;
import liquibase.statement.core.CreateIndexStatement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by twhitbeck on 5/23/2014.
 */
@DatabaseChange(name="createIndex", description = "Creates an index on an existing column or set of columns.", priority = ChangeMetaData.PRIORITY_DATABASE, appliesTo = "index")
public class CreateIndexChangeMSSQL extends CreateIndexChange {
  private List<String> includedColumnNames;

  public List<String> getIncludedColumnNames() {
	  if (includedColumnNames == null) {
		  return new ArrayList<String>();
	  }
	  return includedColumnNames;
  }

  public void setIncludedColumnNames(List<String> includedColumnNames) {
    this.includedColumnNames = includedColumnNames;
  }

  @Override
  public SqlStatement[] generateStatements(Database database) {
    SqlStatement[] statements = super.generateStatements(database);

    List<SqlStatement> extendedStatements = new ArrayList<SqlStatement>(statements.length);
    for (SqlStatement statement : statements) {
      if (statement instanceof CreateIndexStatement) {
        extendedStatements.add(new CreateIndexStatementMSSQL((CreateIndexStatement)statement, includedColumnNames.toArray(new String[getIncludedColumnNames().size()])));
      } else {
        extendedStatements.add(statement);
      }
    }

    return extendedStatements.toArray(new SqlStatement[0]);
  }
}
