package com.cleanup.todoc.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.cleanup.todoc.model.Task;

import java.util.List;

/**
 * Created by Alexandre Vanneçon "Razamelpar"
 */
@Dao
public interface TaskDAO {

    @Query("SELECT * FROM tasks")
    LiveData<List<Task>> getTasks();

    @Insert
    void insertTask(Task task);

    @Delete
    void deleteTask(Task task);
}
