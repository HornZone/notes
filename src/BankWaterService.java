import java.util.Map;
import java.util.concurrent.*;

public class BankWaterService implements Runnable {
    //创建4个屏障，处理完成之后，执行当前类的run方法
    private CyclicBarrier c = new CyclicBarrier(4, this);

    //假设有4个sheet，所以执行4个线程执行
    private Executor executor = Executors.newFixedThreadPool(4);

    //保存每个sheet计算出的银行流水结果
    private ConcurrentHashMap<String, Integer> sheetBankWaterCount = new ConcurrentHashMap<String, Integer>();

    private void count() {
        for(int i =0; i<4;i++) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    //计算当前sheet的银行流水数据
                    sheetBankWaterCount.put(Thread.currentThread().getName(), 1);
                    //计算完成后，插入一个屏障
                    try{
                        c.await();
                    } catch (InterruptedException | BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    @Override
    public void run() {
        int result = 0;

        //汇总每一个sheet计算出结果
        for (Map.Entry<String, Integer> sheet: sheetBankWaterCount.entrySet()) {
            result+=sheet.getValue();
        }

        //将结果输出
        sheetBankWaterCount.put("result", result);
        System.out.println(result);
    }

    public static void main(String[] args) {
        BankWaterService bankWaterService = new BankWaterService();
        bankWaterService.count();
    }
}
