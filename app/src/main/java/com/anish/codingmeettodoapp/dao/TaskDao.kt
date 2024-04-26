package com.anish.codingmeettodoapp.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.anish.codingmeettodoapp.models.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {


    @Query("SELECT * FROM Task ORDER BY date DESC")
    fun getTaskList(): Flow<List<Task>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task): Long

    //Delete first way
    @Delete
    suspend fun deleteTask(task: Task): Int

    //Delete Task second way
    @Query("DELETE FROM Task WHERE taskId == :taskId")
    suspend fun deleteTaskUsingId(taskId: String): Int

}