package com.farmer.open9527.module.test.network

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.LogUtils
import com.farmer.open9527.base.BaseActivity
import com.farmer.open9527.base.page.DataBindingConfig
import com.farmer.open9527.module.test.BR
import com.farmer.open9527.module.test.R


/**
 *@author   open_9527
 *Create at 2021/10/18
 **/
class NetWorkActivity : BaseActivity() {

    protected var mActivity: Activity? = null

    private var mViewModel: NetWorkViewModel? = null


//    private val mViewModel by viewModels<NetWorkViewModel>()

    override fun initViewModel() {
        mViewModel = getActivityScopeViewModel(NetWorkViewModel::class.java);

    }

    override fun getDataBindingConfig(): DataBindingConfig {
        return DataBindingConfig(R.layout.network_activity, BR.vm, mViewModel!!)
            .addBindingParam(BR.click, ClickProxy())
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        mActivity = this
        initStatusBar();
        super.onCreate(savedInstanceState)
        initView(intent.extras)

    }

    private fun initStatusBar() {
        BarUtils.transparentStatusBar(this)
        BarUtils.setStatusBarLightMode(this, true)
    }

    private fun initView(extras: Bundle?) {

        mViewModel?.userLiveData?.observeState(this) {
            onSuccess {
                LogUtils.i(TAG, "userLiveData: onSuccess")
//                LogUtils.i(TAG, "userLiveData: " + it.toString())
                mViewModel?.fetchWxArticleFromNet();
            }
        }
        mViewModel?.wxArticleLiveData?.observeState(this) {
            onSuccess {
                LogUtils.i(TAG, "wxArticleLiveData: onSuccess")
//                LogUtils.i(TAG, "wxArticleLiveData: " + it.toString())
            }

        }


    }


    inner class ClickProxy {

        var requestClick = View.OnClickListener {
            LogUtils.i(TAG, "requestClick")
            mViewModel?.fetchWxArticleFromNet();
        }

        var loginClick = View.OnClickListener {
            LogUtils.i(TAG, "loginClick")
//            mViewModel?.login("open_9527", "kuaige930817")
            val intent = Intent()
            intent.putExtra("data", "data from NetWorkActivity")
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}