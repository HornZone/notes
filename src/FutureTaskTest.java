import jdk.nashorn.internal.codegen.CompilerConstants;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;

public class FutureTaskTest {
    private final ConcurrentHashMap<Object, Future<String>> taskCache = new ConcurrentHashMap<Object, Future<String>>() ;
    private String executionTask(final String taskName) throws ExecutionException, InterruptedException {
        while(true) {
            Future<String> future = taskCache.get(taskName);

            if (future == null) {
                Callable<String> task = new Callable<String>() {
                    public String call() throws InterruptedException {
                        return taskName;
                    }
                };

                //1.2创建任务
                FutureTask<String> futureTask = new FutureTask<String>(task);
                future = taskCache.putIfAbsent(taskName, futureTask);

                if (future == null) {
                    future = futureTask;
                    futureTask.run();
                }
            }

            try {
                return future.get();
            } catch (CancellationException e) {
                taskCache.remove(taskName, future);
            }
        }
    }

}
