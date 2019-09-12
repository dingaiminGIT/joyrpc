package io.joyrpc.exception;

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

/**
 * 处理链异常，不重试
 */
public class HandlerException extends LafException {

    private static final long serialVersionUID = -8258755708955572216L;

    public HandlerException() {
    }

    public HandlerException(String message) {
        super(message);
    }

    public HandlerException(String message, String errorCode) {
        super(message, errorCode);
    }

    public HandlerException(String message, Throwable cause) {
        super(message, cause);
    }

    public HandlerException(String message, Throwable cause, String errorCode) {
        super(message, cause, errorCode);
    }

    public HandlerException(Throwable cause) {
        super(cause);
    }

    public HandlerException(Throwable cause, String errorCode) {
        super(cause, errorCode);
    }
}
