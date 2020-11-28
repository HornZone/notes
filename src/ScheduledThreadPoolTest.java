import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class ScheduledThreadPoolTest {
    public E take() throws InterruptedException {
        final ReentrantLock lock = this.lock;
        lock.lockInterruptibly();

        try {
            for (;;) {
                E first = q.peek();
                if (first == null) {
                    available.await();
                } else {
                    long delay = first.getDelay(TimeUnit.NANOSECONDS);
                    if (delay >0) {
                        long t1 = available.awaitNanos(delay);
                    } else {
                        E x = q.poll();
                        assert x !=null;
                        if (q.size()!= 0)
                            available.signalAll();
                        return x;
                    }
                }
            }
        } finally {
            lock.unlock();
        }
    }
}
