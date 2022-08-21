package com.example.work

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.util.concurrent.TimeUnit


/**
 * В метод doWork нам предлагается поместить код, который будет выполнен.
 * Я здесь просто ставлю паузу в 5 секунд и возвращаю результат SUCCESS,
 * означающий, что все прошло успешно. Нам не надо заморачиваться с потоками,
 * т.к. код будет выполнен не в UI потоке.
 */
class MyWorker(context: Context, workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {

    override fun doWork(): Result {
        Log.d(TAG, "doWork: start")
        try {
            TimeUnit.SECONDS.sleep(5)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        Log.d(TAG, "doWork: end")
        return Result.success()
    }

    companion object {
        const val TAG = "working"
    }
}


/**
 * WorkManager позволяет нам задать критерии запуска задачи, например - включенный интернет на девайсе.
 * Когда вы передадите такую задачу в WorkManager.enqueue, то будет выполнена проверка, что есть интернет.
 * Если он есть, то задача запустится. А если его нет, то задача будет висеть в статусе ENQUEUED, пока инет не появится.
 * Если задача запущена и интернет по каким-то причинам пропал, то задача будет остановлена и снова запланирована (ENQUEUED).
 */
class MyWorkers(context: Context, workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {

    override fun doWork(): Result {
        Log.d(TAG, "doWork1: start")
        try {
            for (i in 0..9) {
                TimeUnit.SECONDS.sleep(1)
                Log.d(TAG, "$i, isStopped1 $isStopped")
                if (isStopped) return Result.failure()
            }
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        Log.d(TAG, "doWork1: end")
        return Result.success()
    }

    override fun onStopped() {
        super.onStopped()
        Log.d(TAG, "onStopped")
    }

    companion object {
        const val TAG = "workmng"
    }
}

class MyWorkers2(context: Context, workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {

    override fun doWork(): Result {
        Log.d(TAG, "doWork2: start")
        try {
            for (i in 0..7) {
                TimeUnit.SECONDS.sleep(1)
                Log.d(TAG, "$i, isStopped2 $isStopped")
                if (isStopped) return Result.failure()
            }
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        Log.d(TAG, "doWork2: end")
        return Result.success()
    }

    override fun onStopped() {
        super.onStopped()
        Log.d(TAG, "onStopped2")
    }

    companion object {
        const val TAG = "workmng"
    }
}


class MyWorkers3(context: Context, workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {

    override fun doWork(): Result {
        Log.d(TAG, "doWork3: start")
        try {
            for (i in 0..5) {
                TimeUnit.SECONDS.sleep(1)
                Log.d(TAG, "$i, isStopped3 $isStopped")
                if (isStopped) return Result.failure()
            }
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        Log.d(TAG, "doWork3: end")
        return Result.success()
    }

    override fun onStopped() {
        super.onStopped()
        Log.d(TAG, "onStopped3")
    }

    companion object {
        const val TAG = "workmng"
    }
}

