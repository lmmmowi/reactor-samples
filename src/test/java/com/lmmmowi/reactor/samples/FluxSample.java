package com.lmmmowi.reactor.samples;

import org.junit.Test;
import org.reactivestreams.Subscription;
import reactor.core.Disposable;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Arrays;

/**
 * @Author: 11102942
 * @Date: 2019/10/9
 * @Description:
 */
public class FluxSample {

    @Test
    public void just() {
        Flux.just(1, 2, 3)
                .subscribe(TestUtils::print);
    }

    @Test
    public void fromIterable() {
        Iterable<String> iterable = Arrays.asList("a", "b", "c");
        Flux.fromIterable(iterable)
                .subscribe(TestUtils::print);
    }

    @Test
    public void range() {
        Flux.range(10, 5)
                .subscribe(TestUtils::print);
    }

    @Test
    public void errorHandling() {
        Flux.just(1, 2, 3)
                .map(i -> 10 / (i - 2))
                .subscribe(TestUtils::print,
                        TestUtils::print);
    }

    @Test
    public void completeHandling() {
        Flux.just(1, 2, 3)
                .subscribe(TestUtils::print,
                        TestUtils::print,
                        () -> TestUtils.print("Done"));
    }

    @Test
    public void subscriptionHandling() {
        Flux.range(1, 10)
                .subscribe(TestUtils::print,
                        TestUtils::print,
                        () -> TestUtils.print("Done"),
                        subscription -> subscription.request(5));
    }

    @Test
    public void subscriber() {
        BaseSubscriber<Long> subscriber = new BaseSubscriber<Long>() {
            @Override
            protected void hookOnSubscribe(Subscription subscription) {
                request(5);
            }

            @Override
            protected void hookOnNext(Long value) {
                TestUtils.print(value);
                if (value <= 3) {
                    request(1);
                }
            }

            @Override
            protected void hookOnError(Throwable throwable) {
                TestUtils.print(throwable);
            }
        };

        Flux.interval(Duration.ofMillis(200))
                .doOnRequest(n -> TestUtils.print(String.format("request %d elements", n)))
                .subscribe(subscriber);

        while (!subscriber.isDisposed()) {
            TestUtils.sleep(1);
        }
    }

    @Test
    public void dispose() {
        Disposable disposable = Flux.interval(Duration.ofSeconds(1))
                .subscribe(TestUtils::print);

        TestUtils.sleep(5);
        disposable.dispose();
    }

}
