package org.wsr.ngmdtodo.service.impl

import java.util.logging.Logger
import org.wsr.ngmdtodo.domain.Task
import org.wsr.ngmdtodo.service.TaskService
import org.wsr.ngmdtodo.sql.SqlStatements
import org.springframework.stereotype.Service

@Service
class TaskServiceImpl implements TaskService {

    SqlStatements statement

    TaskServiceImpl(SqlStatements statement) {
        this.statement = statement
    }

    Task findOne(Integer id) {
        try {
            def row = statement.selectReturns("SELECT * FROM Task WHERE id = $id")
            def t = row.get(0)
            new Task(id: t.id, checked: t.checked, toDay: t.toDay, toImportant: t.toImportant, description: t.description)
        } catch(e) {
            Logger.getLogger(this.class.getName()).severe(e.getMessage())
            throw new BusinessException(e.getMessage())
        }
    }

    ArrayList findAll() {
        try {
            def rows = statement.selectReturns("SELECT * FROM Task")
            rows.collect { new Task(id: it.id, checked: it.checked, toDay: it.toDay, toImportant: it.toImportant, description: it.description) }
        } catch(e) {
            Logger.getLogger(this.class.getName()).severe(e.getMessage())
            throw new BusinessException(e.getMessage())
        }
    }

    String saveAndReturnId(Task task) {
        try {
            return statement.insertReturnsNewId("""
                INSERT INTO Task (checked, toDay, toImportant, description) 
                VALUES (${task.checked}, ${task.toDay}, ${task.toImportant}, '${task.description}')
                """
            )
        } catch (e) {
            Logger.getLogger(this.class.getName()).severe(e.getMessage())
            throw new BusinessException(e.getMessage())
        }
    }

    void save(Task task) {
        try {
            statement.executeWithTransaction("""
                UPDATE Task SET 
                checked = ${task.checked} 
                ,toDay = ${task.toDay} 
                ,toImportant = ${task.toImportant} 
                WHERE id = ${task.id}
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
                DELETE FROM Task 
                WHERE id = ${id}
                """
            )
        } catch (e) {
            Logger.getLogger(this.class.getName()).severe(e.getMessage())
            throw new BusinessException(e.getMessage())
        }
    }

}