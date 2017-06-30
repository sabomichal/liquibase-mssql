[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.sabomichal/liquibase-mssql/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.sabomichal/liquibase-mssql)

liquibase-mssql
=

Fork of Liquibase MS SqlServer Extensions extension - https://liquibase.jira.com/wiki/display/CONTRIB/MS+SqlServer+Extensions.

This fork adds following functionality:
- it is Liquibase 3.x ready (currently supported version: 3.4.2)
- supports stored procedures drop
- wraps flagged calls to *loadData* with "set identity insert on" and "set identity insert off" - see sample
- wraps flagged calls to *insert* with "set identity insert on" and "set identity insert off" - see sample
- adds support to createIndex for specifying included columns and fill-factor
- adds support to addPrimaryKey for specifying fill-factor

Download statistics
-
![alt text](http://chart.apis.google.com/chart?cht=lc&chs=800x200&chco=326A9E&chxt=x,y&chtt=Downloads+Over+the+Last+12+Months+For+com.github.sabomichal:liquibase-mssql&chxr=1,0,2007&chds=0,2007&chxs=1N*s*&chls=3&chm=o,0066FF,0,-1,10,0&chd=t:164,196,158,223,187,173,257,455,540,730,1163,2007&chxl=0:|Jun2016|Jul2016|Aug2016|Sep2016|Oct2016|Nov2016|Dec2016|Jan2017|Mar2017|Mar2017|Apr2017|May2017 "Downloads over the last 12 months")

Usage
-

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
-

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

	<changeSet id="00000000000001" author="author">

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

	</changeSet>

</databaseChangeLog>
```

### Change: ‘loadData’

Extends load data changeset with identityInsertEnabled property.

#### New Attributes

*identityInsertEnabled* - boolean - when set to true, allows explicit values to be inserted into the identity column of a table.

#### Sample
```xml
...		
<ext:loadData encoding="UTF-8"
	file="classpath:config/liquibase/persons.csv" separator=";"
	tableName="person" identityInsertEnabled="true" />
...
```

### Change: 'createIndex'

Extends create index change with includedColumns and fillFactor properties. Properties clustered and descending are supported.

#### New attributes

*includedColumns* - string - columns to be included in index (comma separated if multiple)

*fillFactor* - int (1 to 100) - specifies a percentage that indicates how full the Database Engine should make the leaf level of each index page during index creation or rebuild

#### Sample
```xml
...
<ext:createIndex indexName="IDX_first_name" tableName="person" includedColumns="id, last_name, dob" fillFactor="50">
  <column name="first_name" />
</ext:createIndex>
...
```

### Change: 'addPrimaryKey'

Extends add primary key change with fillFactor property

#### New attribute

*fillFactor* - int (1 to 100) - specifies a percentage that indicates how full the Database Engine should make the leaf level of each index page during index creation or rebuild

#### Sample
```xml
...
<ext:addPrimaryKey columnNames="id" constraintName="pk_mytable_id" tableName="mytable" fillFactor="70" />
...
```

### Refactoring: Changed check if table has an identity column

Changes identity column check not to use global tables to enable usage of multiple Liquibase instances simultaneously with the same database (but with different schemas)
