package com.anish.codingmeettodoapp.repository

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.anish.codingmeettodoapp.Utils.Resource
import com.anish.codingmeettodoapp.Utils.Resource.Error
import com.anish.codingmeettodoapp.Utils.Resource.Loading
import com.anish.codingmeettodoapp.Utils.Resource.Success
import com.anish.codingmeettodoapp.dataBase.TaskDatabase
import com.anish.codingmeettodoapp.models.Task
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class TaskRepository(application: Application) {

    private val taskDao = TaskDatabase.getInstance(application).taskDao


    fun insertTask(task: Task) = MutableLiveData<Resource<Long>>().apply {
        postValue(Loading())
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val result = taskDao.insertTask(task)
                postValue(Success(result))
            }
        } catch (e: Exception) {
            postValue(Error(e.message.toString()))
        }
    }

    fun deleteTask(task: Task) = MutableLiveData<Resource<Int>>().apply {
        postValue(Loading())
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val result = taskDao.deleteTask(task)
                postValue(Success(result))
            }
        } catch (e: Exception) {
            postValue(Error(e.message.toString()))
        }
    }
  fun deleteTaskUsingId(taskId: String) = MutableLiveData<Resource<Int>>().apply {
        postValue(Loading())
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val result = taskDao.deleteTaskUsingId(taskId)
                postValue(Success(result))
            }
        } catch (e: Exception) {
            postValue(Error(e.message.toString()))
        }
    }

    fun getTaskList() = flow {
        emit(Loading())
        try {
            val result = taskDao.getTaskList()
            emit(Success(result))
        } catch (e: Exception) {
            emit(Error(e.message.toString()))
        }
    }

}