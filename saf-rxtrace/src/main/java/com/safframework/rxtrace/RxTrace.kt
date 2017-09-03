package cn.magicwindow.toutiao.utils

import com.safframework.log.L
import io.reactivex.ObservableTransformer
import io.reactivex.functions.Function



/**
 * Created by Tony Shen on 2017/7/11.
 */

object RxTrace {

    @JvmField
    val LOG_NEXT_DATA = 1

    @JvmField
    val LOG_NEXT_EVENT = 2

    @JvmField
    val LOG_ERROR = 4

    @JvmField
    val LOG_COMPLETE = 8

    @JvmField
    val LOG_SUBSCRIBE = 16

    @JvmField
    val LOG_TERMINATE = 32

    @JvmField
    val LOG_DISPOSE = 64

    @JvmStatic
    fun <T> logObservable(tag: String, bitMask: Int): ObservableTransformer<T, T> {

        return ObservableTransformer {upstream->
            var upstream = upstream

            if (bitMask and LOG_SUBSCRIBE > 0) {
                upstream = upstream.compose(oLogSubscribe(tag))
            }
            if (bitMask and LOG_TERMINATE > 0) {
                upstream = upstream.compose(oLogTerminate(tag))
            }
            if (bitMask and LOG_ERROR > 0) {
                upstream = upstream.compose(oLogError(tag))
            }
            if (bitMask and LOG_COMPLETE > 0) {
                upstream = upstream.compose(oLogComplete(tag))
            }
            if (bitMask and LOG_NEXT_DATA > 0) {
                upstream = upstream.compose(oLogNext(tag))
            } else if (bitMask and LOG_NEXT_EVENT > 0) {
                upstream = upstream.compose(oLogNextEvent(tag))
            }
            if (bitMask and LOG_DISPOSE > 0) {
                upstream = upstream.compose(oLogDispose(tag))
            }
            upstream
        }
    }

    @JvmStatic
    fun <T> log(tag: String): ObservableTransformer<T, T> {

        return ObservableTransformer { upstream ->
            upstream.compose(oLogAll(tag))
                    .compose(oLogNext(tag))
        }
    }

    private fun <T> oLogAll(tag: String): ObservableTransformer<T, T> {

        return ObservableTransformer { upstream ->
            upstream.compose(oLogError(tag))
                    .compose(oLogComplete(tag))
                    .compose(oLogSubscribe(tag))
                    .compose(oLogTerminate(tag))
                    .compose(oLogDispose(tag))
        }
    }

    private fun <T> oLogNext(tag: String): ObservableTransformer<T, T> {

        return ObservableTransformer {
            upstream -> upstream.doOnNext{
                data ->
                    L.i(tag, String.format("[onNext] -> %s", data))
            }
        }
    }

    private fun <T> oLogNextEvent(tag: String): ObservableTransformer<T, T> {

        return ObservableTransformer {
            upstream -> upstream.doOnNext {
                L.i(tag, String.format("[onNext]"))
            }
        }
    }

    private fun <T> oLogError(tag: String): ObservableTransformer<T, T> {

        val message = Function<Throwable, String> { throwable -> if (throwable.message != null) throwable.message!! else throwable.javaClass.simpleName }

        return ObservableTransformer {
            upstream -> upstream.doOnError {
                throwable ->
                     L.i(tag, String.format("[onError] -> %s", message.apply(throwable)))
            }
        }
    }

    private fun <T> oLogComplete(tag: String): ObservableTransformer<T, T> {

        return ObservableTransformer {
            upstream -> upstream.doOnComplete {
                L.i(tag, String.format("[onComplete]"))
            }
        }
    }

    private fun <T> oLogSubscribe(tag: String): ObservableTransformer<T, T> {

        return ObservableTransformer {
            upstream -> upstream.doOnSubscribe {
                L.i(tag, String.format("[subscribe]"))
            }
        }
    }

    private fun <T> oLogTerminate(tag: String): ObservableTransformer<T, T> {

        return ObservableTransformer {
            upstream -> upstream.doOnTerminate {
                L.i(tag, String.format("[terminate]"))
            }
        }
    }

    private fun <T> oLogDispose(tag: String): ObservableTransformer<T, T> {

        return ObservableTransformer {
            upstream -> upstream.doOnDispose {
                L.i(tag, String.format("[dispose]"))
            }
        }
    }

//    @JvmStatic
//    fun <T> logFlowable(msg: String): FlowableTransformer<T, T> {
//
//        return FlowableTransformer { upstream ->
//            upstream.compose(fLogSubscribe(msg))
//                    .compose(fLogTerminate(msg))
//                    .compose(fLogError(msg))
//                    .compose(fLogComplete(msg))
//                    .compose(fLogNext(msg))
//                    .compose(fLogNextEvent(msg))
//        }
//    }
//
//    private fun <T> fLogNext(msg: String): FlowableTransformer<T, T> {
//        return FlowableTransformer {
//            upstream -> upstream.doOnNext{
//                data -> L.i(String.format("[onNext] %s %s", msg, data))
//            }
//        }
//    }
//
//    private fun <T> fLogNextEvent(msg: String): FlowableTransformer<T, T> {
//        return FlowableTransformer {
//            upstream -> upstream.doOnNext{
//                L.i(String.format("[onNext] %s", msg))
//            }
//        }
//    }
//
//    private fun <T> fLogError(msg: String): FlowableTransformer<T, T> {
//        val message =  Function<Throwable, String> { throwable -> if (throwable.message != null) throwable.message else throwable.javaClass.getSimpleName() }
//        return FlowableTransformer {
//            upstream -> upstream.doOnError{
//                throwable ->
//                     L.i(String.format("[onError] %s - %s", msg, message.apply(throwable)))
//            }
//        }
//    }
//
//    private fun <T> fLogComplete(msg: String): FlowableTransformer<T, T> {
//
//        return FlowableTransformer {
//            upstream -> upstream.doOnComplete{
//                L.i(String.format("[onComplete] %s", msg))
//            }
//        }
//    }
//
//    private fun <T> fLogSubscribe(msg: String): FlowableTransformer<T, T> {
//
//        return FlowableTransformer {
//            upstream -> upstream.doOnSubscribe{
//                L.i(String.format("[subscribe] %s", msg))
//            }
//        }
//    }
//
//    private fun <T> fLogTerminate(msg: String): FlowableTransformer<T, T> {
//
//        return FlowableTransformer {
//            upstream -> upstream.doOnTerminate{
//                L.i(String.format("[terminate] %s", msg))
//            }
//        }
//    }
}
