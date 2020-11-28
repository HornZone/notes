package Yuna;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 大家在网上看到好的技术文章，执行复制-黏贴-发送就完成了一次分享，但是我发线技术文章不能沉淀下来，新来的同事不能看到以前分享的技术
 * 大家也很难看到以前分享的技术文章
 * 1.申请了一个专门用来收集分享邮件的邮箱，大家将分享的文章发送到这个邮箱，
 * 但是让大家每次都抄送到这个邮箱肯定很麻烦，所以将这个邮箱地址放到部门邮件列表中
 * 分享的同事只要抄送整个部门就可以了
 *
 * yuna通过读取邮件服务器中的邮件，把所有的分享邮件下载下来，根据标题中包含的关键子：xx分享来过滤
 * 下载完成邮件后，通过conf luence的webservice接口，把文章插入到conf luence中，这样新同事就可以在conf luence中看以前分享的文章了
 * 并且yuna可以进行自动分类和归档
 *
 * 首先生产者线程按照一定的规则去邮件中抽取邮件，然后存放在阻塞队列里，消费者从阻塞队列中取出文章，插入到confluence中
 */
public class Yuna {

    //单生产者，多消费者模式
    private ThreadPoolExecutor threadPool;

    private ArticleBlockingQueue<ExchangeEmailShallowDTO> emailQueue;

}
