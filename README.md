# Two dataSources

This repo shows a way to configure manually more than one datasource. In this example, we have a MySql database, with Company tables, and Postgres database, with Employee tables

### Docker compose 
To run databases in docker-compose, you can run:
```shell
docker-compose up -d
```

### JTA
In this `main` branch code, we are add `atomikos` to manage the transactional context using `JtaTransactionManager`, instead of `PlatformTransactionManager`.

If you want to see a simpler solution with two transaction manager, can checkout the branch [simple-transaction-manager](tree/simple-transaction-manager)

### References
- https://medium.com/@inzuael/how-to-use-multiple-datasources-with-springboot-exposed-jpa-6bd705bfe994
