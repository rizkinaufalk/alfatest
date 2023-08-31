package com.rizki.alfatest.ext.constant

object Const {
    // AppConstant
//    const val APP_DB_NAME      = BuildConfig.APPLICATION_ID + ".db"
    const val PREFS_NAME       = "wanna_prefs"
    const val NULL_INDEX       = -1L

    const val VIEW_TYPE_EMPTY  = 0
    const val VIEW_TYPE_NORMAL = 1

    // Time
    const val TIMESTAMP_FORMAT               = "yyyy-MM-dd HH:mm:ss"
    const val SERVER_TIME_FORMAT             = "yyyy-MM-dd"
    const val LOCAL_TIME_FORMAT              = "HH:mm"
    const val LOCAL_DATE_FORMAT              = "dd/MM/yyyy"
    const val LOCAL_MONTH_DATE_FORMAT        = "dd MMMM yyyy"
    const val LOCAL_DATE_SKOLLA_FORMAT       = "dd MMM, yyyy"
    const val LOCAL_DATE_HOUR_SKOLLA_FORMAT  = "dd MMM, yyyy HH:mm"
    const val LOCAL_HOUR_SKOLLA_FORMAT       = "HH:mm"
    const val LOCAL_SIMPLE_MONTH_DATE_FORMAT = "dd MMM yyyy"
    const val LOCAL_DATE_TIME_FORMAT         = "dd MMMM yyyy HH:mm:ss"
    const val LOCAL_MONTH_HOUR_DATE_FORMAT   = "dd MMMM yyyy"
    const val DUEL_DATE_FORMAT               = "dd MMMM yyyy | HH:mm"
    const val LOCAL_DATE_AND_TIME            = "yyyy.MM.dd G 'at' HH:mm:ss"

    //
    const val REGISTRATION_COMPLETE = "REGISTRATION_COMPLETE";

    // Firebase Cloud Massage
    const val NOTIFICATION_CHANNEL_GLOBAL         = "CHANNEL_GLOBAL"
    const val NOTIFICATION_CHANNEL_PROGRESS       = "CHANNEL_PROGRESS"
    const val NOTIFICATION_CHANNEL_GRADING        = "CHANNEL_GRADING"
    const val NOTIFICATION_CHANNEL_TRYOUT         = "CHANNEL_TRYOUT"
    const val NOTIFICATION_CHANNEL_DUEL           = "CHANNEL_DUEL"
    const val NOTIFICATION_CHANNEL_CONSULTATION   = "CHANNEL_CONSUL"
    const val NOTIFICATION_TYPE_CONSULTATION      = "TYPE_CONSUL"
    const val NOTIFICATION_TYPE_GLOBAL            = "TYPE_GLOBAL"
    const val NOTIFICATION_TYPE_TRYOUT_STARTED    = "TYPE_STARTED"
    const val NOTIFICATION_TYPE_TRYOUT_GRADED     = "TYPE_GRADED"
    const val NOTIFICATION_TYPE_ANNOUNCEMENT      = "TYPE_ANNOUNCEMENT"
    const val NOTIFICATION_TYPE_DUEL_CONFIRMATION = "TYPE_DUEL_CONFIRMATION"
    const val NOTIFICATION_TOPIC_GLOBAL           = "TOPIC_GLOBAL"

    // BUCKET PATH DO
    const val DO_CONFIG_ANDROID                   = "config/config.android"
    const val DO_CONFIG_REVIEW                    = "config/config_konsul.json"
    const val DO_BRACKET_HOME                     = "config/home"
    const val DO_BRACKET_MENU_MODULE              = "config/menu_module"
    const val DO_BRACKET_HELP                     = "config/help/help"
    const val DO_BRACKET_SOCMED                   = "config/socmed"
    const val DO_PREMIUM_PACKAGE                  = "premium/premium.package"
    const val DO_PREMIUM_PAYMENT_METHOD           = "premium/premium.payment"
    const val DO_BRACKET_SUBTESTS                 = "bundles/"
    const val DO_BRACKET_SUBMISSION               = "submissions/"
    const val DO_BRACKET_INSTANT_SUBMISSION       = "instant_submissions/"
    const val DO_BRACKET_INSTANT_RESULT           = "instant_submissions_result/"
    const val DO_BRACKET_RESULT                   = "submissions_result/"
    const val DO_BRACKET_SCORE                    = "submissions_result_final/"
    const val DO_BRACKET_PORTFOLIO                = "submissions_result_portofolio/"
    const val DO_BRACKET_INSTANT_SCORE            = "submissions_result_instant_nf/"
    const val DO_BRACKET_MODULES                  = "modules/"
    const val DO_PROFILE_FILE_NAME                = "user_"
    const val DO_BRACKET_AVATAR                   = "avatar/"


    const val FIRESTORE_COLLECTION_CONSUL_ROOM    = "chat_rooms"
    const val FIRESTORE_COLLECTION_CONSUL_REQ     = "consul_requests"
    const val FIRESTORE_CONSUL_REQ_ID             = "consul_requests_id"
    const val FIRESTORE_CONSUL_REQ_SUBJECT        = "consul_requests_subject"
    const val CONFIG_WAITING_TIME                 = "config_waiting_time";
    const val CONFIG_CHAT_TIME                    = "config_chat_time";
    const val CONSUL_RESPONSE_FAILED              = "failed"

    const val MAX_VIDEO_COUNT                     = 10
    const val LIMIT_PAGINATION_CHAT               = 50
    const val COUNTDOWN_INTERVAL                  = 1000L

    const val STYLE_CSS = "style/style.css"


    enum class LoggedInMode constructor(val type: Int) {
        LOGGED_IN_MODE_LOGGED_OUT(1),
        LOGGED_IN_MODE_SERVER(2),
    }
}