package liquibase.ext.mssql.statement;

import liquibase.statement.core.InsertSetStatement;
import liquibase.statement.core.InsertStatement;

public class InsertSetStatementMSSQL extends InsertSetStatement {

    private Boolean identityInsertEnabled;

    public InsertSetStatementMSSQL(InsertSetStatement statement, Boolean identityInsertEnable) {
        this(statement, identityInsertEnable, 50);
    }

    public InsertSetStatementMSSQL(InsertSetStatement statement, Boolean identityInsertEnable, int batchSize) {
        super(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName(), batchSize);
        for (InsertStatement insertStatement : statement.getStatements()) {
            addInsertStatement(insertStatement);
        }
        this.identityInsertEnabled = identityInsertEnable;
    }

    public Boolean getIdentityInsertEnabled() {
        return identityInsertEnabled;
    }

    public void setIdentityInsertEnabled(Boolean identityInsertEnabled) {
        this.identityInsertEnabled = identityInsertEnabled;
    }
}
