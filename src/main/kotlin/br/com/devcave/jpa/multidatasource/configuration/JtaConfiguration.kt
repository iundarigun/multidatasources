package br.com.devcave.jpa.multidatasource.configuration

import com.atomikos.icatch.jta.UserTransactionImp
import com.atomikos.icatch.jta.UserTransactionManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.annotation.EnableTransactionManagement
import org.springframework.transaction.jta.JtaTransactionManager
import javax.transaction.UserTransaction

@Configuration
@EnableTransactionManagement
class JtaConfiguration {

    @Bean(initMethod = "init", destroyMethod = "close")
    fun userTransactionManager(): UserTransactionManager {
        val userTransactionManager = UserTransactionManager()
        userTransactionManager.setTransactionTimeout(300)
        userTransactionManager.forceShutdown = true
        return userTransactionManager
    }

    @Bean("jtaTransactionManager")
    fun transactionManager(): JtaTransactionManager {
        val jtaTransactionManager = JtaTransactionManager()
        jtaTransactionManager.transactionManager = userTransactionManager()
        jtaTransactionManager.userTransaction = userTransactionManager()
        return jtaTransactionManager
    }

    @Bean
    fun userTransaction(): UserTransaction {
        val userTransaction = UserTransactionImp()
        userTransaction.setTransactionTimeout(60000)
        return userTransaction
    }
}