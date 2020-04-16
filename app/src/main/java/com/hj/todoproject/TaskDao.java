package com.hj.todoproject;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TaskDao {

    @Insert
    long add(Task task);

    @Update
    int update(Task task);

    @Delete
    int delete(Task task);

    @Query("SELECT * FROM TBL_TASK ")
    List<Task> getAll();

    @Query("SELECT * FROM TBL_TASK WHERE title LIKE '%' || :query || '%' ")
    List<Task> search(String query);

    @Query("DELETE FROM TBL_TASK")
    void deleteAll();
}
