package liquibase.ext.mssql.statement;

import java.util.Map;

import liquibase.statement.core.InsertStatement;

public class InsertStatementMSSQL extends InsertStatement {
    
    private Boolean identityInsertEnabled;
    
    public InsertStatementMSSQL(InsertStatement statement, Boolean identityInsertEnable) {
        super(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName());
        Map<String, Object> values = statement.getColumnValues();
        for (String key : values.keySet()) {
            addColumnValue(key, values.get(key));
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
