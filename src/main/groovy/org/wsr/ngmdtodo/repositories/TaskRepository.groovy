import org.springframework.data.repository.Repository
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository;

@Repository
interface TaskRepository extends CrudRepository<Task, Integer> {
    
}