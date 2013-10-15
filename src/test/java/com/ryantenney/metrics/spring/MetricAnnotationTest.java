/**
 * Copyright (C) 2012 Ryan W Tenney (ryan@10e.us)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ryantenney.metrics.spring;

import static com.ryantenney.metrics.spring.TestUtil.forMetricField;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.codahale.metrics.Counter;
import com.codahale.metrics.Histogram;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import com.codahale.metrics.UniformReservoir;
import com.ryantenney.metrics.annotation.Metric;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:metric-annotation.xml")
public class MetricAnnotationTest {

	@Autowired
	MetricAnnotationTest.Target target;

	@Autowired
	MetricRegistry metricRegistry;

	@Test
	public void targetIsNotNull() throws Exception {
		assertNotNull(target);

		assertNotNull(target.theNameForTheMeter);
		Meter meter = (Meter) forMetricField(metricRegistry, MetricAnnotationTest.Target.class, "theNameForTheMeter");
		assertSame(target.theNameForTheMeter, meter);

		assertNotNull(target.timer);
		Timer timer = (Timer) forMetricField(metricRegistry, MetricAnnotationTest.Target.class, "timer");
		assertSame(target.timer, timer);

		assertNotNull(target.counter);
		Counter ctr = (Counter) forMetricField(metricRegistry, MetricAnnotationTest.Target.class, "counter");
		assertSame(target.counter, ctr);

		assertNotNull(target.histogram);
		Histogram hist = (Histogram) forMetricField(metricRegistry, MetricAnnotationTest.Target.class, "histogram");
		assertSame(target.histogram, hist);
	}

	@Test
	public void targetIsNotNull2() throws Exception {
		assertNotNull(target);

		assertNotNull(target.uniformHistogram);
		Histogram hist = (Histogram) forMetricField(metricRegistry, MetricAnnotationTest.Target.class, "uniformHistogram");
		assertSame(target.uniformHistogram, hist);
	}

	public static class Target {

		@Metric
		public Meter theNameForTheMeter;

		@Metric(name = "theNameForTheTimer")
		private Timer timer;

		@Metric(name = "group.type.counter", absolute = true)
		Counter counter;

		@Metric
		Histogram histogram;

		@Metric
		Histogram uniformHistogram = new Histogram(new UniformReservoir());

	}

}
