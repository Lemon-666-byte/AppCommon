package com.net;

import com.ui.main.bean.CarItem;
import com.ui.main.bean.User;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * 接口请求
 */
public interface ApiService {

    //登陆
    @POST("api/Account/UserLogin")
    Observable<User> logIn(@Body Map<String, Object> params);

    //
    @POST("api/CarSource/CarSourceList")
    Observable<List<CarItem>> getCarSourceList(@Body Map<String, Object> params);


}
