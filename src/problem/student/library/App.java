package problem.student.library;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

interface Constants {
	int NUMBER_OF_STUDNETS = 5;
	int NUBER_OF_BOOKS = 7;
}

public class App {

	public static void main(String[] args) {
		Student[] students;
		Book[] books;
		ExecutorService service= null;
		try {
			books= new Book[Constants.NUBER_OF_BOOKS];
			students= new Student[Constants.NUMBER_OF_STUDNETS];
			for(int i=0;i<Constants.NUBER_OF_BOOKS;i++) {
				books[i]= new Book(i+1);
			}
			for(int i=0;i<Constants.NUMBER_OF_STUDNETS;i++) {
				students[i]= new Student(i+1, books);
			}
			
			service= Executors.newFixedThreadPool(Constants.NUMBER_OF_STUDNETS);
			for(int i=0;i<Constants.NUMBER_OF_STUDNETS;i++) {
				service.submit(students[i]);
			}
			
			
			Thread.sleep(10000);
		}catch (Exception e) {
			// TODO: handle exception
		}finally {
			service.shutdown();
		}
	}

}

class Book {
	Lock lock;
	int id;

	public Book(int id) {
		this.id = id;
		this.lock = new ReentrantLock();
	}

	@Override
	public String toString() {
		return "Book [id=" + id + "]";
	}

	public void read(Student student) throws InterruptedException {
		if (this.lock.tryLock(10, TimeUnit.SECONDS)) {
			System.out.println(student + "started reading " + this);
			Thread.sleep(2000);
			System.out.println(student + " just finished reading " + this);
			this.lock.unlock();
		}

	}

}

class Student implements Runnable {
	private static Random random = new Random();
	int id;
	Book[] books;

	public Student(int id, Book[] books) {
		this.id = id;
		this.books = books;
	}

	@Override
	public void run() {

		while (true) {
			int bookId = random.nextInt(Constants.NUBER_OF_BOOKS);
			Book bookToRead = books[bookId];
			try {
				bookToRead.read(this);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public String toString() {
		return "Student [id=" + id + "]";
	}

}
