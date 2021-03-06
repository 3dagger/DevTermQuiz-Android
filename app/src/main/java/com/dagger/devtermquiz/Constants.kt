package com.dagger.devtermquiz

class Constants {
    companion object {
        const val TAG_NAME                                              = "leeam"
        const val FIREBASE_SUBSCRIBE_KEY                                = "4dagger"

        const val CONNECT_TIMEOUT                                       = 60L
        const val WRITE_TIMEOUT                                         = 60L
        const val READ_TIMEOUT                                          = 60L
        const val MOVE_MAIN_DELAYED_MILLIS                              = 3000L

        const val PREFS_NOW_STRING                                      = "PREFS_NOW_DATE"
        const val PREFS_USER_FIRST_ENTRY                                = "PREFS_USER_FIRST_ENTRY"
        const val PREFS_QUESTION_COUNT                                  = "PREFS_QUESTION_COUNT"
        const val PREFS_TOTAL_QUESTION_COUNT                            = "PREFS_TOTAL_QUESTION_COUNT"
        const val PREFS_PUSH_SETTING_IS_ON                             = "PREFS_PUSH_SETTING_ON_OFF"

        const val INTENT_ARGUMENT_BOOK_MARK_QUESTION                    = "INTENT_ARGUMENT_BOOK_MARK_QUESTION"
        const val INTENT_ARGUMENT_BOOK_MARK_ANSWER                      = "INTENT_ARGUMENT_BOOK_MARK_ANSWER"
        const val INTENT_ARGUMENT_BOOK_MARK_FIRST_EXAMPLE               = "INTENT_ARGUMENT_BOOK_MARK_FIRST_EXAMPLE"
        const val INTENT_ARGUMENT_BOOK_MARK_SECOND_EXAMPLE              = "INTENT_ARGUMENT_BOOK_MARK_SECOND_EXAMPLE"
        const val INTENT_ARGUMENT_BOOK_MARK_THIRD_EXAMPLE               = "INTENT_ARGUMENT_BOOK_MARK_THIRD_EXAMPLE"
        const val INTENT_ARGUMENT_BOOK_MARK_FOURTH_EXAMPLE              = "INTENT_ARGUMENT_BOOK_MARK_FOURTH_EXAMPLE"
        const val INTENT_ARGUMENT_BOOK_MARK_FIRST_COMMENTARY            = "INTENT_ARGUMENT_BOOK_MARK_FIRST_COMMENTARY"
        const val INTENT_ARGUMENT_BOOK_MARK_SECOND_COMMENTARY           = "INTENT_ARGUMENT_BOOK_MARK_SECOND_COMMENTARY"
        const val INTENT_ARGUMENT_BOOK_MARK_THIRD_COMMENTARY            = "INTENT_ARGUMENT_BOOK_MARK_THIRD_COMMENTARY"
        const val INTENT_ARGUMENT_BOOK_MARK_FOURTH_COMMENTARY           = "INTENT_ARGUMENT_BOOK_MARK_FOURTH_COMMENTARY"

        const val REQUEST_FAIL_MESSAGE_TITLE                            = "죄송합니다"
        const val REQUEST_FAIL_MESSAGE_MSG                              = "서버와 통신에 실패했습니다. 다시 시도해 주세요!"

        const val FRAGMENT_POSITION_QUIZ                                = 0
        const val FRAGMENT_POSITION_CALENDAR                             = 1
        const val FRAGMENT_POSITION_BOOKMARK                            = 2
        const val FRAGMENT_POSITION_SETTING                             = 3

        const val VIEWPAGER_ITEM_COUNT                                  = 4


    }
}