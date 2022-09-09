package br.com.devcave.jpa.multidatasource.configuration

import com.atomikos.jdbc.AtomikosDataSourceBean
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import javax.sql.DataSource


@Configuration
@EnableJpaRepositories(
    basePackages = ["br.com.devcave.jpa.multidatasource.domain.mysql"],
    entityManagerFactoryRef = "mysqlEntityManager",
    transactionManagerRef = "jtaTransactionManager"
)
class MysqlDatasourceConfiguration(
    private val hibernateConfiguration: HibernateConfiguration
) {
    @Bean
    @ConfigurationProperties("spring.mysql-datasource")
    fun mysqlDataSource(): DataSource {
        return AtomikosDataSourceBean()
    }

    @Bean
    fun mysqlEntityManager(
        @Qualifier("mysqlDataSource") mysqlDataSource: DataSource,
        factoryBuilder: EntityManagerFactoryBuilder
    ): LocalContainerEntityManagerFactoryBean {
        return factoryBuilder.dataSource(mysqlDataSource)
            .packages("br.com.devcave.jpa.multidatasource.domain.mysql")
            .jta(true)
            .properties(hibernateConfiguration.vendorProperties("org.hibernate.dialect.MySQL8Dialect"))
            .build()
    }
}