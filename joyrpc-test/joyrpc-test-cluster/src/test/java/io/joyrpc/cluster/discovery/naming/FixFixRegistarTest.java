package io.joyrpc.cluster.discovery.naming;

/*-
 * #%L
 * joyrpc
 * %%
 * Copyright (C) 2019 joyrpc.io
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import io.joyrpc.cluster.Shard;
import io.joyrpc.cluster.discovery.naming.fix.FixRegistar;
import io.joyrpc.cluster.event.ClusterEvent;
import io.joyrpc.extension.URL;
import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class FixFixRegistarTest {

    protected static Registar registar = new FixRegistar(URL.valueOf("jmq://test?url=192.168.1.1,192.168.1.2,192.168.1.3;192.168.1.4,192.168.1.5,192.168.1.6?dataCenter=test&region=test"));

    @Test
    public void test() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(2);
        MyClusterHandler handler1 = new MyClusterHandler(latch);
        MyClusterHandler handler2 = new MyClusterHandler(latch);
        URL url = URL.valueOf("jmq://topic1");
        Assert.assertTrue(registar.subscribe(url, handler1));
        Assert.assertFalse(registar.subscribe(url, handler1));
        Assert.assertTrue(registar.subscribe(url, handler2));
        latch.await();
        Assert.assertEquals(handler1.count, 1);
        Assert.assertEquals(handler1.shards.size(), 2);
        Assert.assertEquals(handler2.count, 1);
        Assert.assertEquals(handler2.shards.size(), 2);
    }

    protected static class MyClusterHandler implements ClusterHandler {

        protected int count;
        protected List<Shard> shards = new LinkedList<>();
        protected CountDownLatch latch;

        public MyClusterHandler(CountDownLatch latch) {
            this.latch = latch;
        }

        @Override
        public void handle(final ClusterEvent event) {
            List<ClusterEvent.ShardEvent> events = event.getDatum();
            if (events != null) {
                for (ClusterEvent.ShardEvent e : events) {
                    switch (e.getType()) {
                        case ADD:
                            shards.add(e.getShard());
                    }
                }
            }
            if (++count == 1) {
                latch.countDown();
            }

        }
    }
}
