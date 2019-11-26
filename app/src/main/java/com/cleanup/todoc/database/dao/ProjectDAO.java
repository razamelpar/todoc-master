package com.cleanup.todoc.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.cleanup.todoc.model.Project;

import java.util.List;

/**
 * Created by Alexandre Vanneçon "Razamelpar"
 */
@Dao
public interface ProjectDAO {

    @Query("SELECT * FROM projects")
    LiveData<List<Project>> getProjects();

    @Insert
    void insertProjects(Project... projects);
}
