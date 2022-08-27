package com.example.work

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.work.Constraints
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.example.work.MyWorker.Companion.TAG

class MainActivity : AppCompatActivity() {

    private lateinit var startWork: AppCompatButton
    private lateinit var cancelWork: AppCompatButton
    private lateinit var RunningMultipleTasks: AppCompatButton
    private lateinit var cancelWorkAll: AppCompatButton
    private lateinit var activity2: AppCompatButton
    private lateinit var activityDataBinding: AppCompatButton

    /**
     * Задача готова. Теперь нам нужно MyWorker обернуть в WorkRequest:
     */
    private val myWorkRequest = OneTimeWorkRequest.Builder(MyWorker::class.java).build()

    /**
     * В Constraints.Builder включаем критерий setRequiresCharging,
     * создаем объект Constraints и передаем его в OneTimeWorkRequest.Builder
     * в метод setConstraints.
     */
    private var constraints: Constraints = Constraints.Builder().setRequiresCharging(true).build()
    private val myWorkRequest1 = OneTimeWorkRequest.Builder(MyWorkers::class.java)
        .setConstraints(constraints)
        .build()

    private val myWorkRequest2 = OneTimeWorkRequest.Builder(MyWorkers2::class.java)
        .setConstraints(constraints)
        .build()

    private val myWorkRequest3 = OneTimeWorkRequest.Builder(MyWorkers3::class.java)
        .setConstraints(constraints)
        .build()

    //private val myWorkRequest = OneTimeWorkRequest.Builder(MyWorkers::class.java).build()

    /**
     * Задаче можно присвоить тег методом addTag:
     * Одной задаче можно добавлять несколько тегов.
     * У WorkStatus есть метод getTags, который вернет все теги, которые присвоены этой задаче.
     */
    //private val myWorkRequest = OneTimeWorkRequest.Builder(MyWorker::class.java).addTag("mytag").build()

    /**
     * Рассмотренный нами OneTimeWorkRequest - это разовая задача.
     * А если нужно многократное выполнение через определенный период времени,
     * то можно использовать PeriodicWorkRequest:
     * В билдере задаем интервал в 30 минут. Теперь задача будет выполняться с этим интервалом.
     * Минимально доступный интервал - 15 минут. Если поставите меньше, WorkManager сам повысит до 15 минут.
     * WorkManager гарантирует, что задача будет запущена один раз в течение указанного интервала. И это может случиться в любой момент интервала - через 1 минуту, через 10 или через 29.
     * С помощью параметра flex можно ограничить разрешенный диапазон времени запуска.
     */
    //var myWorkRequest = PeriodicWorkRequest.Builder(MyWorker::class.java, 30, TimeUnit.MINUTES).build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startWork = findViewById(R.id.startWork)
        cancelWork = findViewById(R.id.cancelWork)
        RunningMultipleTasks = findViewById(R.id.RunningMultipleTasks)
        cancelWorkAll = findViewById(R.id.cancelWorkAll)
        activity2 = findViewById(R.id.activity2)
        activityDataBinding = findViewById(R.id.clickDataBinding)


        activityDataBinding.setOnClickListener {
            startActivity(Intent(this, MainActivity3::class.java))
        }

        activity2.setOnClickListener {
            startActivity(Intent(this, MainActivity2::class.java))
        }


        /**
         * В метод beginWith передаем первую задачу и тем самым создаем начало последовательности задач.
         * Далее вызовами метода then добавляем к последовательности вторую и третью задачи.
         * И методом enqueue отправляем эту последовательность на запуск.
         */
        RunningMultipleTasks.setOnClickListener {
            WorkManager.getInstance()
                .beginWith(myWorkRequest1)
                .then(myWorkRequest2)
                .then(myWorkRequest3)
                .enqueue()
        }

        startWork.setOnClickListener {
            /**
             * Теперь можно запускать задачу:
             * Берем WorkManager и в его метод enqueue передаем WorkRequest.
             * После этого задача будет запущена.
             */
            WorkManager.getInstance().enqueue(myWorkRequest)
        }

        /**
         * В метод getStatusById необходимо передать ID задачи,
         * который может быть получен методом WorkRequest.getId.
         * В результате мы получаем LiveData, подписываемся на него и в метод
         * onChanged нам будут приходить все изменения статуса нашей задачи.
         * Методом WorkStatus.getState будем получать текущее состояние.
         */
//        WorkManager.getInstance().getWorkInfoByIdLiveData(myWorkRequest.id)
//            .observe(this) { workInfo ->
//                Log.d(TAG, "onChanged: " + workInfo.state)
//            }


        cancelWork.setOnClickListener {
            /**
             * Мы можем отменить задачу методом cancelWorkById, передав ID задачи
             */
            WorkManager.getInstance().cancelWorkById(myWorkRequest.id)

            /**
             * Присвоив один тег нескольким задачам, мы можем всех их отменить методом cancelAllWorkByTag:
             */
            //WorkManager.getInstance().cancelAllWorkByTag("mytag")
        }

        cancelWorkAll.setOnClickListener {
            /**
            Также есть метод cancelAllWork, который отменит все ваши задачи.
             * Но хелп предупреждает, что он крайне нежелателен к использованию,
             * т.к. может зацепить работу библиотек, которые вы используете.
             */
            WorkManager.getInstance().cancelAllWork()
        }

        /**
         * В Activity код для отслеживания статуса задачи:
         */
        WorkManager.getInstance()
            .getWorkInfoByIdLiveData(myWorkRequest.id)
            .observe(this) { works ->
                Log.d(TAG, "onChanged: " + works.state)
            }
    }
}