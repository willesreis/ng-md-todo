package org.wsr.ngmdtodo.sql

import groovy.sql.GroovyResultSet
import groovy.sql.Sql
import org.springframework.stereotype.Component;

@Component
class SqlStatements {

    final Map dbConnParams = [url: 'jdbc:h2:mem:todo', user: 'sa', password: '', driver: 'org.h2.Driver']

    private Sql instance() {
        return Sql.newInstance(dbConnParams)
    }

    void executeWithTransaction(String... query) {
        def sql = instance()
        sql.withTransaction {
            try {
                for (q in query) {
                    sql.execute(q)
                }
                sql.commit()
            } catch(Exception e) {
                System.err.println(e.getMessage())
                sql.rollback()
            }
        }
    }

    ArrayList selectReturns(String query) {
        def sql = instance()
        def rs = sql.rows(query)
        return rs
    }

    int insertReturnsNewId(String query) {
        def sql = instance()
        def ids = sql.executeInsert(query)
        return ids[0][0]
    }

    int updateReturnsCount(String query) {
        def sql = instance()
        def count = sql.executeUpdate(query)
        return count
    }

}