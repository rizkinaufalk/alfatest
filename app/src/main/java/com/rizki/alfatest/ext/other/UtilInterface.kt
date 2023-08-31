package com.skollaedutech.skolla.ext.other

//import com.chuckerteam.chucker.api.ChuckerInterceptor
import android.content.Context

fun getRefreshTokenInterface(
    context: Context, endpoint: String,
//    sharePref: SharedPreferenceManager,
//    chuck: ChuckerInterceptor
){
//): RefreshTokenApi {
//    val logging = HttpLoggingInterceptor()
//    logging.setLevel(HttpLoggingInterceptor.Level.BODY)
//    val client: OkHttpClient = OkHttpClient.Builder()
//        .readTimeout((sharePref.userRequestTime).toLong(), TimeUnit.SECONDS)
//        .connectTimeout((sharePref.userRequestTime).toLong(), TimeUnit.SECONDS)
//        .writeTimeout((sharePref.userRequestTime).toLong(), TimeUnit.SECONDS)
//        .addInterceptor(RefreshTokenFailedInterceptor(context))
////        .addInterceptor(chuck)
//        .addInterceptor(logging)
//        .addInterceptor(Interceptor { chain: Interceptor.Chain ->
//            if (context.isNetworkAvailable) {
//                val request: Request = when (val token: String = sharePref.userAccessToken) {
//                    "" -> {
//                        chain.request().newBuilder()
//                            .addHeader("Content-Type", "application/json")
//                            .build()
//                    }
//                    else -> {
//                        chain.request().newBuilder()
//                            .addHeader("Content-Type", "application/json")
//                            .addHeader("Authorization", token)
//                            .build()
//                    }
//                }
//                return@Interceptor chain.proceed(request)
//            } else {
//                throw NoNetworkException()
//            }
//        }).build()
//
//    val gson: Gson = GsonBuilder()
//        .setLenient()
//        .setDateFormat(Const.SERVER_TIME_FORMAT)
//        .create()
//
//    val retrofit = Retrofit.Builder().baseUrl(endpoint)
//        .client(client)
//        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//        .addConverterFactory(GsonConverterFactory.create(gson))
//        .build()
//
//    return retrofit.create(RefreshTokenApi::class.java)
}