package com.example.work

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.InputMerger
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.util.concurrent.TimeUnit


/**
 * При необходимости мы можем написать свой InputMerger.
 * Для этого надо просто наследовать класс InputMerger и реализовать его метод:
 * На вход мы получаем несколько выходных данных из предыдущих задач, и от
 * нас требуется смержить их в входные данные для следующей задачи.
 * Далее остается только передать свой Merger в setInputMerger.
 * Вот пример своего Merger:
 */

class MyMerger : InputMerger() {
    override fun merge(inputs: List<Data>): Data {
        val output = Data.Builder()
        val mergedValues: MutableMap<String, Any> = HashMap()
        for (input in inputs) {
            mergedValues.putAll(input.keyValueMap)
        }
        output.putAll(mergedValues)
        output.putInt("input_data_count", inputs.size - 1)
        return output.build()
    }
}


/**
 * В метод doWork нам предлагается поместить код, который будет выполнен.
 * Я здесь просто ставлю паузу в 5 секунд и возвращаю результат SUCCESS,
 * означающий, что все прошло успешно. Нам не надо заморачиваться с потоками,
 * т.к. код будет выполнен не в UI потоке.
 */
class MyWorker(context: Context, workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {

    override fun doWork(): Result {

        /**
         * Выходные данные
         * Чтобы задача вернула данные, необходимо передать их в метод setOutputData.
         * Код в MyWorker.java будет следующим:
         */

        val output: Data = Data.Builder()
            .putString("keyA", "value1")
            .putInt("keyB", 1)
            .build()
        setOutputData(output)

        Log.d(TAG, "doWork: start")
        try {
            TimeUnit.SECONDS.sleep(3)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        Log.d(TAG, "doWork: end")
        return Result.success()
    }

    private fun setOutputData(output: Data) {
        /**
         * Данные помещаем в объект Data с помощью его билдера.
         * Далее этот объект передаем в метод setInputData билдера WorkRequest.
         * Когда задача будет запущена, то внутри ее (в MyWorker.java) мы можем получить эти входные данные так:
         */

//        val valueA: String = output.getString("keyA")!!
//        val valueB: Int = output.getInt("keyB", 0)
//        Log.d("DATA", valueA + valueB)

        Log.d(TAG, "work1, data " + output.keyValueMap)
    }


    companion object {
        const val TAG = "working"
    }
}


/**
 * В метод doWork нам предлагается поместить код, который будет выполнен.
 * Я здесь просто ставлю паузу в 5 секунд и возвращаю результат SUCCESS,
 * означающий, что все прошло успешно. Нам не надо заморачиваться с потоками,
 * т.к. код будет выполнен не в UI потоке.
 */
class MyWorker1(context: Context, workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {

    override fun doWork(): Result {
        /**
         * Выходные данные
         * Чтобы задача вернула данные, необходимо передать их в метод setOutputData.
         * Код в MyWorker.java будет следующим:
         */

        val output: Data = Data.Builder()
            .putString("keyA", "value2")
            .putInt("keyB", 2)
            .putInt("keyC", 3)
            .build()
        setOutputData(output)

        Log.d(TAG, "doWork: start")
        try {
            TimeUnit.SECONDS.sleep(3)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        Log.d(TAG, "doWork: end")
        return Result.success()
    }

    private fun setOutputData(output: Data) {
        /**
         * Данные помещаем в объект Data с помощью его билдера.
         * Далее этот объект передаем в метод setInputData билдера WorkRequest.
         * Когда задача будет запущена, то внутри ее (в MyWorker.java) мы можем получить эти входные данные так:
         */

//        val valueZ: String? = output.getString("keyA2")
//        val valueY: Int = output.getInt("keyB2", 0)
//        Log.d("DATA keyA2", valueZ + valueY)

        Log.d(TAG, "work2, data " + output.keyValueMap)
    }


    companion object {
        const val TAG = "working"
    }
}

/**
 * В метод doWork нам предлагается поместить код, который будет выполнен.
 * Я здесь просто ставлю паузу в 5 секунд и возвращаю результат SUCCESS,
 * означающий, что все прошло успешно. Нам не надо заморачиваться с потоками,
 * т.к. код будет выполнен не в UI потоке.
 */
class MyWorker2(context: Context, workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {

    override fun doWork(): Result {

        /**
         * Выходные данные
         * Чтобы задача вернула данные, необходимо передать их в метод setOutputData.
         * Код в MyWorker.java будет следующим:
         */

        val output: Data = Data.Builder()
            .putString("keyA", "value3")
            .putInt("keyB", 3)
            .putInt("keyC", 100)
            .putInt("keyD", 1200)
            .build()
        setOutputData(output)

        Log.d(TAG, "doWork: start")
        try {
            TimeUnit.SECONDS.sleep(3)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        Log.d(TAG, "doWork: end")
        return Result.success()
    }

    private fun setOutputData(output: Data) {
        /**
         * Данные помещаем в объект Data с помощью его билдера.
         * Далее этот объект передаем в метод setInputData билдера WorkRequest.
         * Когда задача будет запущена, то внутри ее (в MyWorker.java) мы можем получить эти входные данные так:
         */

//        val valueQ: String? = output.getString("keyA3")
//        val valueR: Int = output.getInt("keyB3", 0)
//        val valueB3: Int = output.getInt("keyB4", 0)
//        Log.d("DATA keyA3", valueQ + valueR + valueB3)

        Log.d(TAG, "work3, data " + output.keyValueMap)
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

