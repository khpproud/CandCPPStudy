import io.reactivex.Flowable;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

/**
 * concatMap 메서드 내에서 서로 다른 스레드로 작동하는 Flowable을 생성하는 예
 * 순서 유지
 */
public class ConcatMapSample {
    public static void main(String[] args) throws InterruptedException {
        Flowable<String> flowable =
                // Flowable을 생성
                Flowable.just("A", "B", "C")
                // 받은 데이터로 Flowable을 생성하고 가진 데이터를 통지
                .concatMap(data -> {
                    // 1000ms 늦게 데이터를 통지하는 Flowable을 생성
                    return Flowable.just(data).delay(1000L, TimeUnit.MILLISECONDS);
                });

        // 구독
        flowable.subscribe(data -> {
            String threadName = Thread.currentThread().getName();
            String time = LocalTime.now().format(DateTimeFormatter.ofPattern("ss.SSS"));
            System.out.println(threadName + " : data = " + data + ", time = " + time);
        });

        // 잠시 기다림
        Thread.sleep(4000L);
    }
}
