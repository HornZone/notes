import java.util.concurrent.*;


/**
 * 因为是有结果的任务，所以必须继承RecursiveTask
 */
public class CountTask extends RecursiveTask<Integer> {
    private static final int THRESHOLD = 2;//阈值
    private int start;
    private int end;

    public CountTask(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        int sum = 0;

        //如果任务足够小就计算任务
        boolean canCompute = (end-start) <= THRESHOLD;

        //即只有两个任务，或者一个任务，就直接加和
        if(canCompute) {
            for (int i=start; i<end; i++) {
                sum += i;
            }
        } else {
            //如果任务大于阈值，分裂成两个子任务计算
            int middle = (start+end) /2;
            CountTask leftTask = new CountTask(start, middle);
            CountTask rightTask = new CountTask(middle+1, end);
            //执行子任务
            leftTask.fork();
            rightTask.fork();

            //等待子任务完成，并得到结果
            int leftResult = leftTask.join();
            int rightResult = rightTask.join();

            //合并子任务
            sum = leftResult+rightResult;
        }

        return sum;
    }

    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        //生成一个计算任务，负责计算1+2+3+4
        CountTask task = new CountTask(1,4);
        //执行任务
        Future<Integer> result = forkJoinPool.submit(task);
        try{
            System.out.println(result.get());
        }catch (InterruptedException e) {

        }catch (ExecutionException e) {

        }
    }
}
