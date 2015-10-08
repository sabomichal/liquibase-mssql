package liquibase.ext.mssql.statement;

import liquibase.statement.core.InsertSetStatement;

public class InsertSetStatementMSSQL extends InsertSetStatement {
    
    private Boolean identityInsertEnabled;
    
    public InsertSetStatementMSSQL(InsertSetStatement statement, Boolean identityInsertEnable) {
        super(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName());
        this.identityInsertEnabled = identityInsertEnable;
    }
    
    public InsertSetStatementMSSQL(InsertSetStatement statement, Boolean identityInsertEnable, int batchSize) {
        super(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName(), batchSize);
        this.identityInsertEnabled = identityInsertEnable;
    }
    
    public Boolean getIdentityInsertEnabled() {
        return identityInsertEnabled;
    }
    public void setIdentityInsertEnabled(Boolean identityInsertEnabled) {
        this.identityInsertEnabled = identityInsertEnabled;
    }
}
