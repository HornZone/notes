import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExchangerTest {
    private static final Exchanger<String> exgr = new Exchanger<String>();

    private static ExecutorService threadPool = Executors.newFixedThreadPool(2);

    public static void main(String[] args) {
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                try{
                    String A = "A录入的银行流水";//A录入银行流水数据
                    exgr.exchange(A);
                }catch (InterruptedException e) {

                }
            }
        });

        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                String B = "B录入的银行流水";
                try {

                    String A = exgr.exchange(B);//获取A传递给exgr的数据

                    System.out.println("A和数据是否一致："+A.equals(B)+"A录入的是："+A+"B录入的是："+B);
                } catch (InterruptedException e) {

                }
            }
        });

        threadPool.shutdown();//线程用完释放到线程池中
    }
}
