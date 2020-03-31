package org.example;

import org.example.domain.B;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTimeout;

public class ReactiveStreamsTest {
    private final Logger log = LoggerFactory.getLogger(ReactiveStreamsTest.class);

    @ParameterizedTest
    @MethodSource("org.example.ReactiveStreamsTestDataProvider#prepareData")
    public void testReactiveStreams(List<B> bs) {
        log.debug("Data size {}!", bs.size());

        Flux<B> rf = Flux.fromIterable(bs)
                .scanWith(Flux::<B>empty, (bf, b) -> bf
                        .filter(bi -> bi.getId().equals(b.getParentId()))
                        .switchMap(bi -> {
                            log.debug("M: {} <= {}/{}", bi.getId(), b.getId(), b.getParentId());
                            b.setParent(bi);
                            bi.getChildren().add(b);

                            return Flux.just(b);
                        })
                        .defaultIfEmpty(b))
                .flatMap(bf -> bf);

        assertTimeout(Duration.ofSeconds(5), () -> {
            StepVerifier.create(rf)
                    .thenConsumeWhile(b -> true, b -> {
                        Long parentId = b.getParent() == null ? null : b.getParent().getId();
                        var childrenCount = b.getChildren().size();
                        log.debug("B: {}/{} => PC: {}/{}", b.getId(), b.getParentId(), parentId, childrenCount);
                    })
                    .expectComplete()
                    .verify();
        });

        List<B> result = rf.collectList().block();
        log.debug("Result: {}", result.size());
    }
}
