[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.sabomichal/liquibase-mssql/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.sabomichal/liquibase-mssql)

liquibase-mssql
===============

Fork of Liquibase MS SqlServer Extensions extension - https://liquibase.jira.com/wiki/display/CONTRIB/MS+SqlServer+Extensions.

This fork adds following functionality:
- it is Liquibase 3.x ready
- supports stored procedures drop
- wraps all calls to *loadData* with "set identity insert on" and "set identity insert off"
- wraps flagged calls to *insert* with "set identity insert on" and "set identity insert off" - see sample

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
    
	<createTable tableName="person">
		<column name="id" autoIncrement="true" type="BIGINT">
			<constraints nullable="false" primaryKey="true" primaryKeyName="pk_person_id"/>
		</column>
		<column name="address" type="varchar(255)"/>
	</createTable>
	
	<ext:insert tableName="person" identityInsertEnabled="true">
		<column name="id" valuenumeric="100" />
		<column name="address" value="thing" />
	</ext:insert>

</databaseChangeLog>
```

### Change: 'createIndex'

Extends create index change with includedColumns property

#### New attributes

*includedColumns* - string - columns to be included in index (comma separated if multiple)

#### Sample
```xml
...
<ext:createIndex indexName="IDX_first_name" tableName="person" includedColumns="id, last_name, dob">
  <column name="first_name" />
</ext:createIndex>
...
```
