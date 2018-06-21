/*
 * Copyright (C) 2015 Square, Inc.
 *
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
 */

package com.net.converter;

import com.google.gson.TypeAdapter;
import com.net.bean.Result;
import com.net.exception.NoDataExceptionException;
import com.net.exception.ServerErrorException;
import com.net.exception.SessionKeyInvalidException;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

final class GsonResponseBodyConverter<T> implements Converter<ResponseBody, Object> {

    private final TypeAdapter<T> adapter;

    GsonResponseBodyConverter(TypeAdapter<T> adapter) {
        this.adapter = adapter;
    }

    @Override
    public Object convert(ResponseBody value) throws IOException {
        try {
            Result response = (Result) adapter.fromJson(value.charStream());
            if (response.getSuccess() == 0) {
                if (response.getData() != null)
                    return response.getData();
                else throw new NoDataExceptionException();
            } else if (response.getSuccess() == 1) {
                // 登录 失效
                throw new SessionKeyInvalidException(response.getSuccess(), response.getMessage());
            } else if (response.getSuccess() == 2) {
                // 返回错误
                throw new ServerErrorException(response.getMessage());
            }
        } finally {
            value.close();
        }
        return null;
    }
}
