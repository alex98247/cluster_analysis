import java.util.List;

//HELLO
public class Main {

    public static void main(String[] args) {
        List<Person> generate = Generator.generate(100000);
        DbExecutor executor = new DbExecutor();
        executor.insert(generate);

    }
}
