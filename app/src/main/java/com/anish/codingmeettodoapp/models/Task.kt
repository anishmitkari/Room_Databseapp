package com.anish.codingmeettodoapp.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity()
data class Task(
    @PrimaryKey (autoGenerate = false)
    @ColumnInfo(name = "taskId")
    val id :String,
    @ColumnInfo(name ="tasktitle")
    val title:String,
    val description:String,
    val date: Date,
)
