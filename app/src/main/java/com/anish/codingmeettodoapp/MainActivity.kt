package com.anish.codingmeettodoapp

import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.anish.codingmeettodoapp.Utils.Status
import com.anish.codingmeettodoapp.Utils.clearEditText
import com.anish.codingmeettodoapp.Utils.longToastShow
import com.anish.codingmeettodoapp.Utils.setupDialog
import com.anish.codingmeettodoapp.Utils.validateEditText
import com.anish.codingmeettodoapp.ViewModels.MainActivity_viewmodel
import com.anish.codingmeettodoapp.databinding.ActivityMainBinding
import com.anish.codingmeettodoapp.models.Task
import com.coding.meet.todo_app.adapters.TaskRecyclerViewAdapter
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date
import java.util.UUID

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    //    Step 1 after xml making make viewbinding
    private val mainBinding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    //    Step 2 setup Dialog
    private val addTaskDialog: Dialog by lazy {
        Dialog(this).apply { setupDialog(R.layout.add_task_dialog) }
    }

    //    Step 3 update Dialog
    private val updateTaskDialog: Dialog by lazy {
        Dialog(this).apply { setupDialog(R.layout.update_task_dialog) }
    }

    private val loadingDialog: Dialog by lazy {
        Dialog(this).apply { setupDialog(R.layout.loading_dialog) }
    }

    private val taskViewModel: MainActivity_viewmodel by lazy {
        ViewModelProvider(this)[MainActivity_viewmodel::class.java]
    }
    private val taskRecyclerViewAdapter: TaskRecyclerViewAdapter by lazy {
        TaskRecyclerViewAdapter{position, task ->
            taskViewModel.deleteTaskUsingId(task.id).observe(this){

                when (it.status) {
                    Status.LOADING -> {
                        loadingDialog.show()
                    }

                    Status.SUCCESS -> {
                           if(it.data!= -1)  {
                               Toast.makeText(this,"msg",Toast.LENGTH_LONG).show()
                           }
                    }

                    Status.ERROR -> {
                        loadingDialog.show()
                    }

                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mainBinding.root)

        mainBinding.taskRV.adapter = taskRecyclerViewAdapter


        // Add task start
        val addCloseImg = addTaskDialog.findViewById<ImageView>(R.id.closeImg)
        addCloseImg.setOnClickListener { addTaskDialog.dismiss() }

        val addETTitle = addTaskDialog.findViewById<TextInputEditText>(R.id.edTaskTitle)
        val addETTitleL = addTaskDialog.findViewById<TextInputLayout>(R.id.edTaskTitleL)

        addETTitle.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(s: Editable) {
                validateEditText(addETTitle, addETTitleL)
            }

        })

        val addETDesc = addTaskDialog.findViewById<TextInputEditText>(R.id.edTaskDesc)
        val addETDescL = addTaskDialog.findViewById<TextInputLayout>(R.id.edTaskDescL)

        addETDesc.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(s: Editable) {
                validateEditText(addETDesc, addETDescL)
            }
        })

        mainBinding.addbtn.setOnClickListener {
            clearEditText(addETTitle, addETTitleL)
            clearEditText(addETDesc, addETDescL)
            addTaskDialog.show()
        }
        val saveTaskBtn = addTaskDialog.findViewById<Button>(R.id.saveTaskBtn)
        saveTaskBtn.setOnClickListener {
            if (validateEditText(addETTitle, addETTitleL)
                && validateEditText(addETDesc, addETDescL)
            ) {
                addTaskDialog.dismiss()
                val newTask = Task(
                    UUID.randomUUID().toString(),
                    addETTitle.text.toString().trim(),
                    addETDesc.text.toString().trim(),
                    Date()
                )

                taskViewModel.insertTask(newTask)
                Log.e(TAG, "inserted>>>>>>  : " )
            }

         }
        // Add task end



        // Update Task Start
        val updateETTitle = updateTaskDialog.findViewById<TextInputEditText>(R.id.edTaskTitle)
        val updateETTitleL = updateTaskDialog.findViewById<TextInputLayout>(R.id.edTaskTitleL)

        updateETTitle.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(s: Editable) {
                validateEditText(updateETTitle, updateETTitleL)
            }

        })

        val updateETDesc = updateTaskDialog.findViewById<TextInputEditText>(R.id.edTaskDesc)
        val updateETDescL = updateTaskDialog.findViewById<TextInputLayout>(R.id.edTaskDescL)

        updateETDesc.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(s: Editable) {
                validateEditText(updateETDesc, updateETDescL)
            }
        })

        val updateCloseImg = updateTaskDialog.findViewById<ImageView>(R.id.closeimg)
        updateCloseImg.setOnClickListener { updateTaskDialog.dismiss() }

        val updateTaskBtn = updateTaskDialog.findViewById<Button>(R.id.updateTaskBtn)

        // Update Task End
        callGettaskList()
    }


    fun callGettaskList() {
        CoroutineScope(Dispatchers.Main).launch {
            taskViewModel.getTaskList().collect {
                when (it.status) {
                    Status.LOADING -> {
                        loadingDialog.show()
                    }

                    Status.SUCCESS -> {
                        it.data?.collect { taskList ->
                            loadingDialog.dismiss()

                            taskRecyclerViewAdapter.addAlltask(taskList)
                        }
                    }

                    Status.ERROR -> {
                        loadingDialog.show()
                    }

                }
            }
        }
    }
}