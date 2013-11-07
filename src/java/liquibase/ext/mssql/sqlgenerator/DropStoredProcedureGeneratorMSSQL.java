package liquibase.ext.mssql.sqlgenerator;

import liquibase.database.Database;
import liquibase.database.core.MSSQLDatabase;
import liquibase.exception.ValidationErrors;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.AbstractSqlGenerator;
import liquibase.structure.DatabaseObject;

public class DropStoredProcedureGeneratorMSSQL extends AbstractSqlGenerator<DropStoredProcedureStatement> {

    public ValidationErrors validate(DropStoredProcedureStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        return sqlGeneratorChain.validate(statement, database);
    }

    public Sql[] generateSql(DropStoredProcedureStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        StringBuilder sql = new StringBuilder();
        sql.append("declare @procName varchar(500)\n");
        sql.append("declare cur cursor\n");
        sql.append("for select [name] from sys.objects where type = 'p' AND is_ms_shipped = 0\n");
        sql.append("open cur\n");
        sql.append("fetch next from cur into @procName\n");
        sql.append("while @@fetch_status = 0\n");
        sql.append("begin\n");
        sql.append("exec('drop procedure ' + @procName)\n");
        sql.append("fetch next from cur into @procName\n");
        sql.append("end\n");
        sql.append("close cur\n");
        sql.append("deallocate cur\n");
        
        return (new Sql[] {
            new UnparsedSql(sql.toString(), new DatabaseObject[0])
        });
    }
    
    @Override
    public boolean supports(DropStoredProcedureStatement statement, Database database)
    {
        return database instanceof MSSQLDatabase;
    }
    
    @Override
    public int getPriority() {
        return PRIORITY_DATABASE;
    }
}
