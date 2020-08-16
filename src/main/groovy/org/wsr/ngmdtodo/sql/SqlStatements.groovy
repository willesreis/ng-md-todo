package org.wsr.ngmdtodo.sql

import groovy.sql.GroovyResultSet
import groovy.sql.Sql
import java.util.logging.Logger
import org.springframework.stereotype.Component;

@Component
class SqlStatements {

    final Map dbConnParams = [url: 'jdbc:h2:mem:todo', user: 'sa', password: '', driver: 'org.h2.Driver']

    private Sql instance() {
        try {
            return Sql.newInstance(dbConnParams)
        } catch(e) {
            Logger.getLogger(this.class.getName()).severe("Error at newInstance: ${e.getMessage()}")
        }
    }

    void executeWithTransaction(String... query) {
        def sql = instance()
        sql.withTransaction {
            try {
                for (q in query) {
                    sql.execute(q)
                }
                sql.commit()
            } catch(e) {
                Logger.getLogger(this.class.getName()).severe("Rollback with error: ${e.getMessage()}")
                sql.rollback()
            }
        }
    }

    ArrayList selectReturns(String query) {
        def sql = instance()
        try {
            def rs = sql.rows(query)
            return rs
        } catch(e) {
            Logger.getLogger(this.class.getName()).severe("SQL error: ${e.getMessage()}")
        }
    }

    int insertReturnsNewId(String query) {
        def sql = instance()
        try {
            def ids = sql.executeInsert(query)
            return ids[0][0]
        } catch(e) {
            Logger.getLogger(this.class.getName()).severe("SQL error: ${e.getMessage()}")
        }
    }

    int updateReturnsCount(String query) {
        def sql = instance()
        try {
            def count = sql.executeUpdate(query)
            return count
        } catch(e) {
            Logger.getLogger(this.class.getName()).severe("SQL error: ${e.getMessage()}")
        }
    }

}