package com.dagger.devtermquiz.view.main.setting

import android.os.Bundle
import android.widget.CompoundButton
import com.dagger.devtermquiz.Constants
import com.dagger.devtermquiz.R
import com.dagger.devtermquiz.base.BaseFragment
import com.dagger.devtermquiz.databinding.FragmentSettingBinding
import com.dagger.devtermquiz.view.main.bookmark.BookMarkFragment
import com.dagger.devtermquiz.view.main.setting.model.SettingFragmentViewModel
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import com.orhanobut.logger.Logger
import com.pixplicity.easyprefs.library.Prefs
import kotlinx.android.synthetic.main.fragment_setting.*
import org.koin.android.ext.android.inject

class SettingFragment : BaseFragment<FragmentSettingBinding, SettingFragmentViewModel>(), SettingFragmentNavigator.View {
    override val layoutResourceId: Int get() = R.layout.fragment_setting
    override val viewModel: SettingFragmentViewModel by inject()

    override fun initView() {
        viewModel.setNavigator(this@SettingFragment)


    }

    override fun onProcess() {
        switch_fcm_on_off.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked) {
                if(Prefs.getBoolean(Constants.PREFS_PUSH_SETTING_IS_ON, true)) {
                    Firebase.messaging.subscribeToTopic(Constants.FIREBASE_SUBSCRIBE_KEY)
                    Prefs.putBoolean(Constants.PREFS_PUSH_SETTING_IS_ON, true)
                }
            }else {
                Firebase.messaging.unsubscribeFromTopic(Constants.FIREBASE_SUBSCRIBE_KEY)
                Prefs.putBoolean(Constants.PREFS_PUSH_SETTING_IS_ON, false)
                Logger.d("unsubscribe")
            }
        }


    }

    companion object {
        fun newInstance(position: Int): SettingFragment {
            val instance = SettingFragment()
            val args = Bundle()
            args.putInt("position", position)
            instance.arguments = args
            return instance
        }
    }

    override fun onViewModelCleared() {
        viewModel.disposableClear()
    }
}