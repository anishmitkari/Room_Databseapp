package com.anish.codingmeettodoapp.ViewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.anish.codingmeettodoapp.Utils.Resource
import com.anish.codingmeettodoapp.Utils.Resource.*
import com.anish.codingmeettodoapp.models.Task
import com.anish.codingmeettodoapp.repository.TaskRepository
import kotlinx.coroutines.flow.flow

class MainActivity_viewmodel (application: Application) : AndroidViewModel(application) {

    private val taskRepository = TaskRepository(application)

    fun getTaskList() = taskRepository.getTaskList()

    fun insertTask(task: Task){
        taskRepository.insertTask(task)
    }
    fun deleteTask(task: Task): MutableLiveData<Resource<Int>> {
        return taskRepository.deleteTask(task)
    }
    fun deleteTaskUsingId(taskId: String): MutableLiveData<Resource<Int>> {
        return taskRepository.deleteTaskUsingId(taskId)
    }
}