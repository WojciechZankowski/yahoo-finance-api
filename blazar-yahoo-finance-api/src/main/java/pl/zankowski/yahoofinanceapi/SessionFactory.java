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

/**
 * <p>
 * Date: 23.09.2015
 * </p>
 *
 * <p>
 * This class is a factory for {@link Session} class.
 * </p>
 *
 * @author Wojciech Zankowski
 */
public class SessionFactory {

	/**
	 * <p>
	 * This method creates {@link Session} class instance with default {@link RequestManager} with parameter
	 * corePoolSize of value 1.
	 * </p>
	 * 
	 * @param receiver
	 *            User's {@link DataReceiver} implementation.
	 * @return {@link Session} class instance.
	 */
	public static Session createNewSession(DataReceiver receiver) {
		return createNewSession(receiver, new RequestManager(1));
	}

	/**
	 * <p>
	 * This method creates {@link Session} class instance with {@link RequestManager} and {@link DataReceiver} passed by
	 * user.
	 * </p>
	 * 
	 * @param receiver
	 *            User's {@link DataReceiver} implementation.
	 * @param requestManager
	 *            User's {@link RequestManager}.
	 * @return {@link Session} class instance.
	 */
	public static Session createNewSession(DataReceiver receiver, RequestManager requestManager) {
		return new Session(receiver, requestManager);
	}
}
