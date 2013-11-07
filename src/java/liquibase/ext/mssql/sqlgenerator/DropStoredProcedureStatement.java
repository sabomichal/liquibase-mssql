package liquibase.ext.mssql.sqlgenerator;

import liquibase.statement.AbstractSqlStatement;

public class DropStoredProcedureStatement extends AbstractSqlStatement {
    private String catalogName;
    private String schemaName;

    public DropStoredProcedureStatement(String catalogName, String schemaName) {
        this.catalogName = catalogName;
        this.schemaName = schemaName;
    }

    public String getCatalogName() {
        return catalogName;
    }

    public String getSchemaName() {
        return schemaName;
    }
}
