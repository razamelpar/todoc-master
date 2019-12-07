package com.cleanup.todoc.database.dao;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.cleanup.todoc.database.LiveDataTest;
import com.cleanup.todoc.database.TodocDatabase;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

/**
 * Created by Alexandre Vanneçon "Razamelpar"
 */

@RunWith(AndroidJUnit4.class)
public class TaskDaoTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
    private TodocDatabase database;
    private Project[] projects = Project.getAllProjects();
    private Task tacheTest1 = new Task(projects[0].getId(), "tache de test", new Date().getTime());
    private Task tacheTest2 = new Task(projects[0].getId(), "une autre tache de test", new Date().getTime());

    @Before
    public void initDatabase() throws InterruptedException {
        this.database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                TodocDatabase.class)
                .allowMainThreadQueries()
                .build();
        this.database.projectDAO().insertProjects(this.projects);

        List<Task> tasks = LiveDataTest.getValue(this.database.taskDAO().getTasks());
        assertTrue(tasks.isEmpty());
    }

    @After
    public void closeDatabase() {
        this.database.close();
    }

    @Test
    public void insertTestTask() throws InterruptedException {
        List<Project> projects = LiveDataTest.getValue(this.database.projectDAO().getProjects());

        this.database.taskDAO().insertTask(this.tacheTest1);
        this.database.taskDAO().insertTask(this.tacheTest2);

        List<Task> tasks = LiveDataTest.getValue(this.database.taskDAO().getTasks());
        assertEquals(2, tasks.size());

        tasks = LiveDataTest.getValue(this.database.taskDAO().getTasks());
        assertEquals(projects.get(0).getId(), tasks.get(0).getProjectId());
        assertEquals(projects.get(0).getId(), tasks.get(1).getProjectId());

        assertEquals(tacheTest1.getName(), tasks.get(0).getName());
        assertEquals(tacheTest2.getName(), tasks.get(1).getName());
        assertEquals(tacheTest1.getCreationTimestamp(), tasks.get(0).getCreationTimestamp());
        assertEquals(tacheTest2.getCreationTimestamp(), tasks.get(1).getCreationTimestamp());
    }

    @Test
    public void deleteTestTask() throws InterruptedException {
        List<Project> projects = LiveDataTest.getValue(this.database.projectDAO().getProjects());

        this.database.taskDAO().insertTask(this.tacheTest1);
        this.database.taskDAO().insertTask(this.tacheTest2);

        List<Task> tasks = LiveDataTest.getValue(this.database.taskDAO().getTasks());
        assertEquals(2, tasks.size());
        assertEquals(tacheTest2.getName(), tasks.get(1).getName());
        assertEquals(tacheTest2.getCreationTimestamp(), tasks.get(1).getCreationTimestamp());

        this.database.taskDAO().deleteTask(tasks.get(1));
        tasks = LiveDataTest.getValue(this.database.taskDAO().getTasks());
        assertEquals(1, tasks.size());
        assertEquals(tacheTest1.getName(), tasks.get(0).getName());
        assertEquals(tacheTest1.getCreationTimestamp(), tasks.get(0).getCreationTimestamp());
    }

}
