// Copyright 2015 Palantir Technologies
//
// Licensed under the BSD-3 License (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://opensource.org/licenses/BSD-3-Clause
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.palantir.util;

import java.util.concurrent.atomic.AtomicLong;

/**
 *
 * @y.exclude
 *
 */
public class CacheStats implements CacheStatsMBean {

    private final SoftCache<?, ?> cache;
    final AtomicLong misses = new AtomicLong();
    final AtomicLong hits = new AtomicLong();
    final AtomicLong inverseMisses = new AtomicLong();
    final AtomicLong inverseHits = new AtomicLong();
    final AtomicLong cleanups = new AtomicLong();
    final AtomicLong puts = new AtomicLong();
    final AtomicLong gcs = new AtomicLong();
    final AtomicLong loadTimeForMisses = new AtomicLong();
    final AtomicLong loadTimeForCacheKey = new AtomicLong();

    public CacheStats(SoftCache<?, ?> cache) {
        this.cache = cache;
    }

    private void clear() {
        misses.set(0);
        hits.set(0);
        inverseMisses.set(0);
        inverseHits.set(0);
        cleanups.set(0);
        puts.set(0);
        gcs.set(0);
        loadTimeForMisses.set(0);
        loadTimeForCacheKey.set(0);
    }

    @Override
    public float getCacheHitPercentage() {
        long hit = hits.get();
        long miss = misses.get();
        if(hit + miss == 0)
            return 100.f;
        return 100.0f*hit/(hit+miss);
    }

    @Override
    public long getMissCount() {
        return misses.get();
    }

    @Override
    public long getPutCount() {
        return puts.get();
    }

    @Override
    public long getHitCount() {
        return hits.get();
    }

    @Override
    public int getSize() {
        return cache.size();
    }

    @Override
    public long getForcedGcCount() {
        return gcs.get();
    }

    @Override
    public String getName() {
        return cache.getName();
    }

    @Override
    public long getInverseHits() {
        return inverseHits.get();
    }

    @Override
    public long getInverseMisses() {
        return inverseMisses.get();
    }

    @Override
    public void clearCacheAndStats() {
        cache.clear();
        clear();
    }

    @Override
    public long getLoadTimeFromMisses() {
        return loadTimeForMisses.get();
    }

    @Override
    public long getLoadTimeForCacheKey() {
        return loadTimeForCacheKey.get();
    }

    @Override
    public String getCacheClass() {
        return cache.getClass().getSimpleName();
    }

    @Override
    public int getMaxCacheSize() {
        return cache.getMaxCacheSize();
    }

    @Override
    public void setMaxCacheSize(int size) {
        cache.setMaxCacheSize(size);
    }
}