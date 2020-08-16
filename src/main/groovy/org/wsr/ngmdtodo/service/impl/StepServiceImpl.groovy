package org.wsr.ngmdtodo.service.impl

import java.util.logging.Logger
import org.wsr.ngmdtodo.domain.Step
import org.wsr.ngmdtodo.service.StepService
import org.wsr.ngmdtodo.sql.SqlStatements
import org.springframework.stereotype.Service

@Service
class StepServiceImpl implements StepService {

    SqlStatements statement

    StepServiceImpl(SqlStatements statement) {
        this.statement = statement
    }

    Step findOne(Integer id) {
        try {
            def row = statement.selectReturns("SELECT * FROM Step WHERE id = $id")
            def s = row.get(0)
            new Step(id: s.id, checked: s.checked, description: s.description)
        } catch(e) {
            Logger.getLogger(this.class.getName()).severe(e.getMessage())
            throw new BusinessException(e.getMessage())
        }
    }

    ArrayList findAll() {
        try {
            def rows = statement.selectReturns("SELECT * FROM Step")
            rows.collect { new Step(id: it.id, checked: it.checked, description: it.description) }
        } catch(e) {
            Logger.getLogger(this.class.getName()).severe(e.getMessage())
            throw new BusinessException(e.getMessage())
        }
    }

    String saveAndReturnId(Step step) {
        try {
            return statement.insertReturnsNewId("""
                INSERT INTO Step (checked, description) 
                VALUES (${step.checked}, '${step.description}')
                """
            )
        } catch (e) {
            Logger.getLogger(this.class.getName()).severe(e.getMessage())
            throw new BusinessException(e.getMessage())
        }
    }

    void save(Step step) {
        try {
            statement.executeWithTransaction("""
                UPDATE Step SET 
                checked = ${step.checked} 
                WHERE id = ${step.id}
                """
            )
        } catch (e) {
            Logger.getLogger(this.class.getName()).severe(e.getMessage())
            throw new BusinessException(e.getMessage())
        }
    }

    void remove(Integer id) {
        try {
            statement.executeWithTransaction("""
                DELETE FROM Step 
                WHERE id = ${id}
                """
            )
        } catch (e) {
            Logger.getLogger(this.class.getName()).severe(e.getMessage())
            throw new BusinessException(e.getMessage())
        }
    }

}