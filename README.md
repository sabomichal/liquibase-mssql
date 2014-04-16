liquibase-mssql
===============

Fork of Liquibase MS SqlServer Extensions extension - https://liquibase.jira.com/wiki/display/CONTRIB/MS+SqlServer+Extensions.

This fork is Liquibase 3.x ready and adds support for stored procedures drop.
Support for SQL Server unicode strings with a prefix N as described here http://support.microsoft.com/kb/239530/en-us is no longer included since it is fixed in liquibase version 3.1.1.

Usage
-----

To use, simply include the liquibase-mssql.jar file in your classpath. And add the ext namespace to your xml root node:
```xml
<databaseChangeLog
    xmlns="http://www.liquibase.org/xml/ns/dbchangelog"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:ext="http://www.liquibase.org/xml/ns/dbchangelog-ext"
    xsi:schemaLocation="http://www.liquibase.org/xml/ns/dbchangelog http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-3.0.xsd
    http://www.liquibase.org/xml/ns/dbchangelog-ext http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-ext.xsd">
```

Available changes/refactorings
-----------------------

### Change: ‘insert’

Extends insert data changeset with identityInsertEnabled property.

#### New Attributes

*identityInsertEnabled* - boolean - when set to true, allows explicit values to be inserted into the identity column of a table.

#### Sample
```xml
<databaseChangeLog
    xmlns="http://www.liquibase.org/xml/ns/dbchangelog"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:ext="http://www.liquibase.org/xml/ns/dbchangelog-ext"
    xsi:schemaLocation="http://www.liquibase.org/xml/ns/dbchangelog http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-3.0.xsd
    http://www.liquibase.org/xml/ns/dbchangelog-ext http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-ext.xsd">
    
    <ext:insert tableName="person" identityInsertEnabled="true">
        <column name="id" valuenumeric="100" />
        <column name="some" value="thing" />
    </ext:insert>
    
</databaseChangeLog>
```
