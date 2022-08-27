package com.example.work

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import java.util.*


class MainActivity2 : AppCompatActivity() {

    private lateinit var btn1: AppCompatButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        btn1 = findViewById(R.id.btn1)

        /**
         * Входные данные
         * Сначала рассмотрим как передать в задачу входные данные:
         */
        val myData1: Data = Data.Builder()
            .putString("keyA", "value1")
            .putInt("keyB", 1)
            .build()

        val myData2: Data = Data.Builder()
            .putString("keyA", "value2")
            .putInt("keyB", 2)
            .putInt("keyC", 3)
            .build()

        val myData3: Data = Data.Builder()
            .putString("keyA", "value3")
            .putInt("keyB", 3)
            .putInt("keyC", 100)
            .putInt("keyD", 1200)
            .build()

        val myWorkRequest1 = OneTimeWorkRequest.Builder(MyWorker::class.java)
            .setInputData(myData1)
            .build()

        val myWorkRequest2 = OneTimeWorkRequest.Builder(MyWorker1::class.java)
            .setInputData(myData2)
            .build()

        val myWorkRequest3 = OneTimeWorkRequest.Builder(MyWorker2::class.java)
            .setInputData(myData3)
            .build()


        /**
         * Готовим список параллельных задач:
         */

        val workRequests: MutableList<OneTimeWorkRequest> = LinkedList()
        workRequests.add(myWorkRequest1)
        workRequests.add(myWorkRequest2)
        workRequests.add(myWorkRequest3)

        /**
         * InputMerger
         * Рассмотрим еще один InputMerger - ArrayCreatingInputMerger.
         * Он при совпадении ключей создаст массив, в который поместит все значения этого ключа.
         * Давайте для третьей задачи укажем его методом setInputMerger:
         */
        val myWorkRequest4: OneTimeWorkRequest = OneTimeWorkRequest.Builder(MyWorkers3::class.java)
            .setInputMerger(MyMerger::class.java)
            .build()


        /**
         * Эти выходные данные мы сможем достать из WorkStatus
         */
        WorkManager.getInstance()
            .getWorkInfoByIdLiveData(myWorkRequest4.id)
            .observe(this) { works ->
                Log.d(MyWorker.TAG, "onChanged: ${works.state}")
            }


        /**
         * Данные между задачами
         * Если вы создаете последовательность задач, то выходные данные предыдущей задачи будут передаваться как входные в последующую задачу.
         * Например, запускаем последовательность из первой и второй задач
         */
        btn1.setOnClickListener {
//            WorkManager.getInstance()
//                .beginWith(myWorkRequest1)
//                .then(myWorkRequest2)
//                .then(myWorkRequest3)
//                .enqueue()

            WorkManager.getInstance()
                .beginWith(workRequests)
                .then(myWorkRequest4)
                .enqueue();
        }
    }
}