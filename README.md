# Two dataSources

This repo shows a way to configure manually more than one datasource. In this example, we have a MySql database, with Company tables, and Postgres database, with Employee tables

### Docker compose 
To run databases in docker-compose, you can run:
```shell
docker-compose up -d
```

### Considerations
To improve this solution, we can wrapper dataSources with Hikari to more detailed datasource configuration. 

```kotlin
    private fun hikariConfig(dataSource: DataSource): HikariConfig {
        val hikariConfig = HikariConfig()
        hikariConfig.dataSource = dataSource
        hikariConfig.isAutoCommit = false
        return hikariConfig
    }

    fun hikariDataSource(dataSource: DataSource): HikariDataSource {
        return HikariDataSource(hikariConfig(dataSource))
    }
```