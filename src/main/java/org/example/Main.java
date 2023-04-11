package org.example;

import org.example.task1.Bank;
import org.example.task1.Transferer;
import org.example.task2.Consumer;
import org.example.task2.Drop;
import org.example.task2.Producer;
import org.example.task3.EJournal;
import org.example.task3.Mark;
import org.example.task3.Student;
import org.example.task3.Teacher;

import java.util.*;

public class Main {


    public static void main(String[] args) {
        setUpTask3();
    }

    private static void setUpTask1() {
        int NACCOUNTS = 10;
        int INITIAL_BALANCE = 10000;
        Bank b = new Bank(NACCOUNTS, INITIAL_BALANCE);
        int i;
        for (i = 0; i < NACCOUNTS; i++) {
            Transferer t = new Transferer(b, i, INITIAL_BALANCE);
            t.setPriority(Thread.NORM_PRIORITY + i % 2);
            t.start();
        }
    }

    private static void setUpTask2() {
        Drop drop = new Drop();
        (new Thread(new Producer(drop, 1000))).start();
        (new Thread(new Consumer(drop))).start();
    }

    private static void setUpTask3() {
        String[] subjects = {
                "Jesseology"
        };
        Student[] st = {
                new Student("Jessica", "A1"),
                new Student("Jessic", "A1"),
                new Student("Jessi", "A1"),
                new Student("Jess", "A1"),
                new Student("Jes", "A1"),
                new Student("Je", "A1"),

                new Student("Jessica", "A2"),
                new Student("Jessic", "A2"),
                new Student("Jessi", "A2"),
                new Student("Jess", "A2"),
                new Student("Jes", "A2"),
                new Student("Je", "A2"),

                new Student("Jessica", "A3"),
                new Student("Jessic", "A3"),
                new Student("Jessi", "A3"),
                new Student("Jess", "A3"),
                new Student("Jes", "A3"),
                new Student("Je", "A3"),
        };

        Mark[] mk3 = {
                new Mark(st[2], subjects[0], 69),
                new Mark(st[7], subjects[0], 67),
                new Mark(st[6], subjects[0], 60),
                new Mark(st[9], subjects[0], 67),
                new Mark(st[4], subjects[0], 66),
                new Mark(st[0], subjects[0], 64),
        };
        Mark[] mk4 = {
                new Mark(st[2], subjects[0], 79),
                new Mark(st[4], subjects[0], 77),
                new Mark(st[6], subjects[0], 70),
                new Mark(st[7], subjects[0], 77),
                new Mark(st[9], subjects[0], 76),
                new Mark(st[0], subjects[0], 74),
        };
        Mark[] mk2 = {
                new Mark(st[12], subjects[0], 89),
                new Mark(st[13], subjects[0], 87),
                new Mark(st[14], subjects[0], 80),
                new Mark(st[15], subjects[0], 87),
                new Mark(st[16], subjects[0], 86),
                new Mark(st[17], subjects[0], 84),
        };
        Mark[] mk1 = {
                new Mark(st[1], subjects[0], 99),
                new Mark(st[3], subjects[0], 97),
                new Mark(st[5], subjects[0], 90),
                new Mark(st[10], subjects[0], 97),
                new Mark(st[8], subjects[0], 96),
                new Mark(st[11], subjects[0], 94),
        };

        Map<Student, Student> studentMap = new HashMap<>();

        Arrays.stream(st).forEach(student -> studentMap.put(student, new Student(student.getName(), student.getGroup(), subjects)));
        EJournal journal = new EJournal(studentMap);

        Runnable[] teachers = {
                new Teacher(journal, Arrays.stream(mk1).toList()),
                new Teacher(journal, Arrays.stream(mk2).toList()),
                new Teacher(journal, Arrays.stream(mk3).toList()),
                new Teacher(journal, Arrays.stream(mk4).toList())
        };
        List<Thread> started = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            Arrays.stream(teachers).map(Thread::new).forEach(thread -> {
                thread.start();
                started.add(thread);
            });
        }
        for (Thread teacher : started) {
            try {
                teacher.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        int numOfMarks = 0;
        for (Student student : journal.getStudents()) {
            numOfMarks += student.getMarks(subjects[0]).size();
            System.out.println(student);
        }
        System.out.println("----------------");
        System.out.println(numOfMarks);
    }

}