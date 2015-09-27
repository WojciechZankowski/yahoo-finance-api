/*
 * Copyright 2015 Wojciech Zankowski
 *
 * http://www.zankowski.pl
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package pl.zankowski.yahoofinanceapi;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

/**
 * <p>
 * Date: 19.09.2015
 * </p>
 *
 * <p>
 * This class is used to managing user's request. Every Request Manager use one {@link ScheduledExecutorService} class
 * with possible setting number of threads used to execute scheduled tasks.
 * </p>
 *
 * @author Wojciech Zankowski
 */
public class RequestManager {

	private final Map<Integer, List<ScheduledFuture<?>>> schedulerMap = new ConcurrentHashMap<>();

	private final ScheduledExecutorService scheduler;

	/**
	 * Public constructor.
	 * 
	 * @param corePoolSize
	 *            the number of threads to keep in the pool, even if they are idle
	 */
	public RequestManager(int corePoolSize) {
		this.scheduler = Executors.newScheduledThreadPool(corePoolSize);
	}

	protected List<ScheduledFuture<?>> getScheduledTask(int requestID) {
		return schedulerMap.get(requestID);
	}

	protected ScheduledExecutorService getScheduler() {
		return scheduler;
	}

	protected void addScheduledTasks(int requestID, List<ScheduledFuture<?>> scheduledTasks) {
		schedulerMap.put(requestID, scheduledTasks);
	}

	protected void removeScheduledTasks(int requestID) {
		schedulerMap.remove(requestID);
	}

	protected boolean isEmpty() {
		return schedulerMap.isEmpty();
	}

	protected void shutdownScheduler() {
		scheduler.shutdown();
	}

}
