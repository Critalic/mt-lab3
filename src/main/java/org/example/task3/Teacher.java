package org.example.task3;

import java.util.List;

public class Teacher implements Runnable {
    private EJournal journal;
    private List<Mark> marks;

    public Teacher(EJournal journal, List<Mark> marks) {
        this.journal = journal;
        this.marks = marks;
    }

    @Override
    public void run() {
        marks.forEach(mark -> journal.setMarkForStudent(mark));
    }
}
