package liquibase.ext.mssql.change;

import liquibase.change.AbstractChange;
import liquibase.change.ChangeMetaData;
import liquibase.change.DatabaseChange;
import liquibase.database.Database;
import liquibase.ext.mssql.database.MSSQLDatabase;
import liquibase.ext.mssql.statement.DropStoredProcedureStatement;
import liquibase.statement.SqlStatement;

@DatabaseChange(name="dropProcedures", description = "Drop Stored Procedures", priority = ChangeMetaData.PRIORITY_DEFAULT)
public class DropStoredProcedureChange extends AbstractChange {
    
    private String catalogName;
    private String schemaName;
    
    public DropStoredProcedureChange()
    {
    }

    public String getConfirmationMessage() {
        return (new StringBuilder()).append("Stored procedures has been droped").toString();
    }

    public SqlStatement[] generateStatements(Database database) {
        DropStoredProcedureStatement statement = new DropStoredProcedureStatement(getCatalogName(), getSchemaName());
        return (new SqlStatement[] {
            statement
        });
    }

    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }
    
    public String getSchemaName()
    {
        return schemaName;
    }

    public void setSchemaName(String schemaName)
    {
        this.schemaName = schemaName;
    }

    @Override
    public boolean supports(Database database) {
        return database instanceof MSSQLDatabase;
    }
}