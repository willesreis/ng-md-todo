import org.springframework.stereotype.Component

@Component
class Task {
    Integer id
    boolean checked
    String description
    List<Step> steps
}