package org.example


import org.example.domain.B
import org.junit.jupiter.params.provider.Arguments

import java.util.stream.Stream

class ReactiveStreamsTestDataProvider {
    static Stream<Arguments> prepareData() {
        List<B> bs = new ArrayList<>();

        bs
                << new B(id: 1L, descr: 'Test Item #1')
                << new B(id: 2L, descr: 'Test Item #2')

                << new B(id: 11L, descr: 'Test Item #11', parentId: 1L)
                << new B(id: 12L, descr: 'Test Item #12', parentId: 1L)
                << new B(id: 13L, descr: 'Test Item #13', parentId: 1L)

                << new B(id: 21L, descr: 'Test Item #21', parentId: 2L)
                << new B(id: 22L, descr: 'Test Item #22', parentId: 2L)

                << new B(id: 111L, descr: 'Test Item #111', parentId: 11L)
                << new B(id: 112L, descr: 'Test Item #112', parentId: 11L)

                << new B(id: 121L, descr: 'Test Item #121', parentId: 12L)

                << new B(id: 211L, descr: 'Test Item #211', parentId: 21L)
                << new B(id: 212L, descr: 'Test Item #212', parentId: 21L)

                << new B(id: 221L, descr: 'Test Item #221', parentId: 22L)
                << new B(id: 222L, descr: 'Test Item #222', parentId: 22L)

                << new B(id: 1211L, descr: 'Test Item #1211', parentId: 121L)
                << new B(id: 1212L, descr: 'Test Item #1211', parentId: 121L)

                << new B(id: 2221L, descr: 'Test Item #2221', parentId: 222L)
                << new B(id: 2222L, descr: 'Test Item #2222', parentId: 222L)

        return Stream.of(
                Arguments.of(bs)
        )
    }
}
