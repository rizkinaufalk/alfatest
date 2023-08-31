package com.rizki.alfatest.ext.constant

import com.skollaedutech.skolla.ext.other.hexToByte

object ConstKeys {
    // Used to load the 'native-lib' library on application startup.
    init  {
        System.loadLibrary("native-lib");
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */

    private external fun getEndPoint(environment: Int): String
    private external fun getFirebaseProjectId(environment: Int): String
    private external fun getFirebaseAppId(environment: Int): String
    private external fun getFirebaseApiKey(environment: Int): String
    private external fun getRealmName(environment: Int): String
    private external fun getRealmKey(environment: Int): String
    private external fun getGoogleServerAuth(environment: Int): String
    private external fun getSaltKey(environment: Int): String
    private external fun getPreferenceName(environment: Int): String
    private external fun getDigitalOceanBucket(environment: Int): String
    private external fun getDigitalOceanEndPoint(environment: Int): String
    private external fun getDigitalOceanCompleteEndPoint(environment: Int): String
    private external fun getDigitalOceanAccessKey(environment: Int): String
    private external fun getDigitalOceanSecretKey(environment: Int): String
    private external fun getZoomKey(environment: Int): String
    private external fun getZoomSecret(environment: Int): String
    private external fun getMidtransMerchantID(environment: Int): String
    private external fun getMidtransBaseUrl(environment: Int): String
    private external fun getMidtransApiKey(environment: Int): String
    private external fun getMidtransEndPoint(environment: Int): String

    // CONST
    private const  val ENVIRONMENT = 0//BuildConfig.SERVER_STAGING
    val END_POINT = getEndPoint(ENVIRONMENT)
    val FIREBASE_PROJECT_ID = getFirebaseProjectId(ENVIRONMENT)
    val FIREBASE_APP_ID = getFirebaseAppId(ENVIRONMENT)
    val FIREBASE_API_KEY = getFirebaseApiKey(ENVIRONMENT)
    val REALM_NAME = getRealmName(ENVIRONMENT)
    val REALM_KEY = getRealmKey(ENVIRONMENT).hexToByte()
    val GOOGLE_SERVER_AUTH = getGoogleServerAuth(ENVIRONMENT)
    val SALT_KEY = getSaltKey(ENVIRONMENT)
    val PREFERENCE_NAME = getPreferenceName(ENVIRONMENT)
    val DO_BUCKET = getDigitalOceanBucket(ENVIRONMENT)
    val DO_END_POINT = getDigitalOceanEndPoint(ENVIRONMENT)
    val DO_COMPLETE_END_POINT = getDigitalOceanCompleteEndPoint(ENVIRONMENT)
    val DO_ACCESS_KEY = getDigitalOceanAccessKey(ENVIRONMENT)
    val DO_SECRET_KEY = getDigitalOceanSecretKey(ENVIRONMENT)
    val ZOOM_KEY = getZoomKey(ENVIRONMENT)
    val ZOOM_SECRET = getZoomSecret(ENVIRONMENT)
    val MIDTRANS_ID = getMidtransMerchantID(ENVIRONMENT)
    val MIDTRANS_URL = getMidtransBaseUrl(ENVIRONMENT)
    val MIDTRANS_SECRET = getMidtransApiKey(ENVIRONMENT)
    val MIDTRANS_ENDPOINT = getMidtransEndPoint(ENVIRONMENT)
}