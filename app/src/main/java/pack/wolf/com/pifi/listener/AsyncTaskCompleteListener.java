package pack.wolf.com.pifi.listener;

/**
 * Created by Whitney Champion on 8/29/15.
 */
public interface AsyncTaskCompleteListener<T> {

    void onTaskComplete(T result);
}