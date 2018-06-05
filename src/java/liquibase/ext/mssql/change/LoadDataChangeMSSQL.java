package liquibase.ext.mssql.change;

import java.util.ArrayList;
import java.util.List;

import liquibase.change.ChangeMetaData;
import liquibase.change.DatabaseChange;
import liquibase.database.Database;
import liquibase.ext.mssql.database.MSSQLDatabase;
import liquibase.ext.mssql.statement.InsertSetStatementMSSQL;
import liquibase.ext.mssql.statement.InsertStatementMSSQL;
import liquibase.statement.SqlStatement;
import liquibase.statement.core.InsertSetStatement;
import liquibase.statement.core.InsertStatement;

@DatabaseChange(name = "loadData", description = "Load Data", priority = ChangeMetaData.PRIORITY_DATABASE, appliesTo = "table")
public class LoadDataChangeMSSQL extends liquibase.change.core.LoadDataChange {
    private Boolean identityInsertEnabled;

    public Boolean getIdentityInsertEnabled() {
	return identityInsertEnabled;
    }

    public void setIdentityInsertEnabled(Boolean identityInsertEnabled) {
	this.identityInsertEnabled = identityInsertEnabled;
    }

    @Override
    public SqlStatement[] generateStatements(Database database) {
	SqlStatement[] statements = super.generateStatements(database);
    if (!MSSQLDatabase.PRODUCT_NAME.equals(database.getDatabaseProductName())) {
    	return statements;
    }
	List<SqlStatement> wrappedStatements = new ArrayList<SqlStatement>(statements.length);
	for (SqlStatement statement : statements) {
	    if (statement instanceof InsertStatement) {
		wrappedStatements.add(new InsertStatementMSSQL((InsertStatement) statement, identityInsertEnabled));
	    } else if(statement instanceof InsertSetStatement) {
	        wrappedStatements.add(new InsertSetStatementMSSQL((InsertSetStatement) statement, identityInsertEnabled));
	    } else {
		wrappedStatements.add(statement);
	    }
	}
	return wrappedStatements.toArray(new SqlStatement[0]);
    }

	@Override
	public boolean supports(Database database) {
		return database instanceof MSSQLDatabase;
	}
}