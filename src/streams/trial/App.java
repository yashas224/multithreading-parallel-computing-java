package streams.trial;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class App {

	public static void main(String[] args) throws IOException, URISyntaxException {

		int[] arr = new int[] { 1, 4, 2, 6, 7, 8, 3, 1, 35, 6, 7, 99, 7, 6, 4 };

		// Arrays.stream(arr).forEach((a) -> System.out.println(a));
		// Arrays.stream(arr).forEach(System.out::println);
		// System.out.println();
		// System.out.println(Arrays.stream(arr).sum());
		Supplier<IntStream> supplier = () -> IntStream.range(0, 20);
		supplier.get().forEach(a -> System.out.print(a + " ,"));
		System.out.println();
		supplier.get().filter(a -> a > 4).forEach(b -> System.out.print(b + " ,"));
		System.err.println();
		String[] strArr = new String[] { "yashas", "malathi", "samaga", "lisa", "tanvi", "noob", "kevin", "zack" };

		Supplier<Stream<String>> supplier1 = () -> Arrays.stream(strArr);
		supplier1.get().forEach(s -> System.out.print(s + " "));
		System.out.println();
		supplier1.get().sorted(Comparator.reverseOrder()).forEach(s -> System.out.print(s + " "));
		System.out.println();
		String path = "E:\\multithreading-parallel-computing-java-udemy\\src\\streams\\trial\\test";
		Files.lines(Paths.get(path)).forEach(s -> System.out.print(s + " "));

		System.out.println();
		Consumer<String> consumer = s -> System.out.print(s + " ");
		List<Student> list = Arrays.asList(new Student("yashas", true), new Student("malathi", false),
				new Student("krishnamprthy", true), new Student("Lisa", true), new Student("adam", false),
				new Student("danial", true));

		list.stream().filter(obj -> obj.local).map(obj -> obj.getName()).forEach(consumer);
		System.out.println();
		System.out.println(list.stream().filter(obj -> obj.local).count());
	}

}

class Student {
	String name;
	boolean local;

	public boolean isLocal() {
		return local;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setLocal(boolean local) {
		this.local = local;
	}

	public Student(String string, boolean local) {
		super();
		this.name = string;
		this.local = local;
	}

	@Override
	public String toString() {
		return "Student [name=" + name + ", local=" + local + "]";
	}

}