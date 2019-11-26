package com.cleanup.todoc.repositories;

import android.arch.lifecycle.LiveData;

import com.cleanup.todoc.database.dao.ProjectDAO;
import com.cleanup.todoc.model.Project;

import java.util.List;

/**
 * Created by Alexandre Vanneçon "Razamelpar" on 26/11/2019.
 */
public class ProjectDataRepository {
    private final ProjectDAO mProjectDao;

    public ProjectDataRepository(ProjectDAO projectDao) {
        mProjectDao = projectDao;
    }

    public LiveData<List<Project>> getProjects() {
        return mProjectDao.getProjects();
    }
}
