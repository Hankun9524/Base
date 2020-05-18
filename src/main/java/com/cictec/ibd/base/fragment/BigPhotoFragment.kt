package com.cictec.ibd.base.fragment

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.cictec.ibd.base.R
import com.cictec.ibd.base.base.BaseFragment
import com.davemorrissey.labs.subscaleview.ImageSource
import com.hankun.libnavannotation.FragmentDestination
import kotlinx.android.synthetic.main.base_fragment_big_photo.*
import java.io.File

/**
 * CopyRight (c)2019: <北京中航讯科技股份有限公司>
 *
 * 大图加载的页面
 *
 * @author    HanKun
 * @date      2019/12/4
 * @version   1.0
 */
@FragmentDestination(pageUrl = "BigPhotoFragment")
class BigPhotoFragment : BaseFragment() {

    private lateinit var target: Target<File>

    override fun initRootView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater!!.inflate(R.layout.base_fragment_big_photo, container, false)

    override fun onStop() {
        if (null != target) {
            Glide.with(this).clear(target)
        }
        super.onStop()
    }

    override fun initChildView() {

    }

    override fun initListener() {
        download.setOnClickListener {
            load()
        }
        imageView.setOnClickListener {
            finishThis()
        }
        load()
    }

    private fun load() {
        imageView.visibility = View.GONE
        download.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
        target = Glide.with(this).downloadOnly()
            .load(arguments?.getString("url") ?: "")
            .listener(object : RequestListener<File> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<File>?,
                    isFirstResource: Boolean
                ): Boolean {
                    imageView.visibility = View.GONE
                    progressBar.visibility = View.GONE
                    download.visibility = View.VISIBLE
                    e?.printStackTrace()
                    return false
                }

                override fun onResourceReady(
                    resource: File?,
                    model: Any?,
                    target: Target<File>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    download.visibility = View.GONE
                    progressBar.visibility = View.GONE
                    imageView.visibility = View.VISIBLE
                    imageView.setImage(ImageSource.uri(Uri.fromFile(resource)))
                    return false
                }
            }).preload()
    }
}