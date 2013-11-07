package liquibase.ext.mssql.sqlgenerator;

import liquibase.change.AbstractChange;
import liquibase.change.ChangeMetaData;
import liquibase.change.DatabaseChange;
import liquibase.database.Database;
import liquibase.statement.SqlStatement;

@DatabaseChange(name="dropProcedures", description = "Drop Stored Procedures", priority = ChangeMetaData.PRIORITY_DEFAULT)
public class DropStoredProcedureChange extends AbstractChange {
    
    private String catalogName;
    private String schemaName;
    
    public DropStoredProcedureChange()
    {
    }

    public String getConfirmationMessage() {
        return (new StringBuilder()).append("Materialized view ").append("stored procedure name").append(" has been droped").toString();
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
}