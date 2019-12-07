package com.cleanup.todoc.database.dao;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.cleanup.todoc.database.LiveDataTest;
import com.cleanup.todoc.database.TodocDatabase;
import com.cleanup.todoc.model.Project;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

/**
 * Created by Alexandre Vanneçon "Razamelpar"
 */

@RunWith(AndroidJUnit4.class)
public class ProjectDaoTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
    private TodocDatabase database;
    private Project[] projects = Project.getAllProjects();

    @Before
    public void initDatabase() {
        this.database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                TodocDatabase.class)
                .allowMainThreadQueries()
                .build();
    }

    @After
    public void closeDatabase() {
        this.database.close();
    }

    @Test
    public void testProject() throws InterruptedException {
        List<Project> projects = LiveDataTest.getValue(this.database.projectDAO().getProjects());
        assertTrue(projects.isEmpty());
        this.database.projectDAO().insertProjects(this.projects);

        projects = LiveDataTest.getValue(this.database.projectDAO().getProjects());
        assertEquals(projects.get(0).getName(), this.projects[0].getName());
        assertEquals(projects.get(0).getId(), this.projects[0].getId());
        assertEquals(projects.get(0).getColor(), this.projects[0].getColor());
    }
}
