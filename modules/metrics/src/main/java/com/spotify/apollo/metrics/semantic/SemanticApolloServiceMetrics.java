/*
 * -\-\-
 * Spotify Apollo Metrics Module
 * --
 * Copyright (C) 2013 - 2016 Spotify AB
 * --
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * -/-/-
 */
package com.spotify.apollo.metrics.semantic;

import com.codahale.metrics.Meter;
import com.spotify.apollo.metrics.ApolloRequestMetrics;
import com.spotify.apollo.metrics.ApolloServiceMetrics;
import com.spotify.metrics.core.MetricId;
import com.spotify.metrics.core.SemanticMetricRegistry;

class SemanticApolloServiceMetrics implements ApolloServiceMetrics {
  private static final String COMPONENT = "scope-factory";
  private final SemanticMetricRegistry metricRegistry;
  private final MetricId metricId;
  private final Meter sentReplies;
  private final Meter sentErrors;

  SemanticApolloServiceMetrics(SemanticMetricRegistry metricRegistry, MetricId id) {
    this.metricRegistry = metricRegistry;
    // Already tagged with 'application' and 'service'
    this.metricId = id.tagged("component", COMPONENT);
    sentReplies = new Meter();
    sentErrors = new Meter();
  }

  @Override
  public ApolloRequestMetrics newServiceRequest(String name) {
    final MetricId id = metricId.tagged("endpoint", name);
    return new SemanticApolloRequestMetrics(metricRegistry, id, sentReplies, sentErrors);
  }
}
