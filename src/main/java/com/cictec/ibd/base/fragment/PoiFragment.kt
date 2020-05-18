package com.cictec.ibd.base.fragment

import android.graphics.Point
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.amap.api.maps.AMapOptions
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.MapView
import com.amap.api.maps.model.LatLng
import com.amap.api.services.poisearch.PoiResult
import com.cictec.ibd.base.R
import com.cictec.ibd.base.adtapter.*
import com.cictec.ibd.base.base.BaseFragment
import com.cictec.ibd.base.cache.UserLocationCache
import com.cictec.ibd.base.http.BaseCallback
import com.cictec.ibd.base.model.PoiInfo
import com.cictec.ibd.base.model.SearchPoiEvent
import com.cictec.ibd.base.service.GeoSearchPresenter
import com.cictec.ibd.base.service.PoiSearchPresenter
import com.cictec.ibd.base.utils.GpsUtils.getDistance
import com.cictec.ibd.base.utils.LogUtil
import com.cictec.ibd.base.weidget.SoftKeyBoardListener
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.hankun.libnavannotation.FragmentDestination
import kotlinx.android.synthetic.main.base_fragment_amap_poi.*
import org.greenrobot.eventbus.EventBus

/**
 * CopyRight (c)2019: <北京中航讯科技股份有限公司>
 *
 *
 *
 * @author    HanKun
 * @date      2020/3/25
 * @version   1.0
 */
@FragmentDestination(pageUrl = "PoiFragment")
class PoiFragment : BaseFragment(), BaseCallback {


    private lateinit var mapView: MapView


    private var poiInfo: PoiInfo? = null


    private var latLng: LatLng? = null

    /**
     * 是否触动
     */
    private var isTouch = false


    /**
     * 是否地图加载完成
     */
    private var isMapLoaded = false

    private val geoSearchPresenter: GeoSearchPresenter = GeoSearchPresenter()


    private val poiSearchPresenter = PoiSearchPresenter()

    private lateinit var adapter: BaseRecyclerViewAdapter<PoiInfo>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        geoSearchPresenter.bindView(lifecycle, this)
        geoSearchPresenter.observe(this,
            Observer<PoiInfo> { poiInfo ->
                //判断下热点距离和屏幕距离是否超过100米，如果超过，查询时经纬度按照屏幕经纬度来计算
                if (getDistance(
                        poiInfo.lat,
                        poiInfo.lng,
                        latLng!!.latitude,
                        latLng!!.longitude
                    ) > 100
                ) {
                    poiInfo.lat = latLng!!.latitude
                    poiInfo.lng = latLng!!.longitude
                }
                poi_map_poiInfo_name_tv.text = poiInfo.name
                poi_map_poiInfo_add_tv.text = poiInfo.adress
                this.poiInfo = poiInfo

            })
        poiSearchPresenter.bindView(lifecycle, this)
        poiSearchPresenter.observe(this, Observer<PoiResult> {
            val list = ArrayList<PoiInfo>()
            for (poi in it.pois) {
                var address = poi.snippet
                if (address.isEmpty()) {
                    address = poi.adName
                }
                list.add(
                    PoiInfo(
                        poi.title,
                        address,
                        poi.latLonPoint.latitude,
                        poi.latLonPoint.longitude
                    )
                )
            }
            adapter.setNewData(list)
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        geoSearchPresenter.unBindView()
        poiSearchPresenter.unBindView()
    }


    override fun initRootView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater!!.inflate(R.layout.base_fragment_amap_poi, container, false)
        mapView = view.findViewById(R.id.poiMap)
        mapView.onCreate(savedInstanceState)
        return view
    }

    override fun initChildView() {
        super.initChildView()
        //设置适配器
        adapter = BaseRecyclerViewAdapter(object : RecyclerViewBindDataInterface<PoiInfo> {
            override fun getItemLayoutId(viewType: Int): Int = R.layout.base_item_poi_search_result

            override fun onBindData(
                bean: PoiInfo?,
                holder: BaseRecyclerViewHolder?,
                position: Int,
                type: Int,
                size: Int
            ) {
                if (bean != null && holder != null) {
                    holder.getSubView<TextView>(R.id.poi_name_txt).text = bean.name
                    holder.getSubView<TextView>(R.id.poi_address_txt).text = bean.adress
                }
            }
        })
        //设置列表
        base_search_poi_recycler.layoutManager = LinearLayoutManager(context!!)
        base_search_poi_recycler.adapter = adapter


        mapView.map.apply {
            minZoomLevel = 14f
            maxZoomLevel = 19f
            uiSettings.apply {
                //设置去除放大缩小按钮
                isZoomControlsEnabled = false
                //不允许倾斜手势
                isTiltGesturesEnabled = false
                //显示指南针
                isCompassEnabled = true
                //比例尺可见
                isScaleControlsEnabled = true
                //设置Log和标尺的位置在右下角
                logoPosition = AMapOptions.ZOOM_POSITION_RIGHT_BUTTOM
                //以地图中心缩放
                isGestureScaleByMapCenter = true
            }
        }
        mapView.map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(
                UserLocationCache.getLocationLatLng(),
                12f
            )
        )
    }

    override fun initListener() {
        mapView.map.setOnMapLoadedListener {
            isMapLoaded = true
            //移动地图到当前点
            latLng = UserLocationCache.getLocationLatLng()
            mapView.map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16f))
            searchPoi(latLng!!)
        }

        mapView.map.setOnMapTouchListener {
            when (it.action) {
                MotionEvent.ACTION_DOWN -> isTouch = false
                MotionEvent.ACTION_MOVE -> isTouch = true
                MotionEvent.ACTION_UP -> if (isTouch) {
                    formScreenLatLng()
                }
                else -> {
                }
            }
        }

        poi_map_poiInfo_commit_tv.setOnClickListener {
            val temp = poiInfo
            if (temp != null) {
                onPostResult(temp)
            } else {
                showLongToast("未找到地点信息，请稍后再试！")
            }
        }

        edt_base_map_poi_search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                s?.let {
                    if (s.isNotEmpty()) {
                        poiSearchPresenter.searchPoi(s.toString())
                    } else {
                        adapter.setNewData(null)
                    }
                }
            }
        })

        //获取弹出层的状态
        val params = bottom_sheet_rv.layoutParams as CoordinatorLayout.LayoutParams
        val bottomBehavior = params.behavior as BottomSheetBehavior
        bottomBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                LogUtil.i("bottomSheet", "slideOffset = $slideOffset")
                //对view进行隐藏
                if (slideOffset == 1.0f) {
                    poi_map_poiInfo_commit_tv.visibility = View.GONE
                    poi_map_poiInfo_name_tv.visibility = View.GONE
                    poi_map_poiInfo_add_tv.visibility = View.GONE

                } else {
                    setViewAnimator(poi_map_poiInfo_commit_tv, slideOffset)
                    setViewAnimator(poi_map_poiInfo_name_tv, slideOffset)
                    setViewAnimator(poi_map_poiInfo_add_tv, slideOffset)
                    iv_base_arrow.rotation = 180 * slideOffset
                }
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                LogUtil.i("bottomSheet", "newState = $newState")
            }
        })

        //键盘弹出监听
        val listener = SoftKeyBoardListener(activity)
        listener.setOnSoftKeyBoardChangeListener(object :
            SoftKeyBoardListener.OnSoftKeyBoardChangeListener {
            override fun keyBoardShow(height: Int) {
                //键盘打开，在这里打开底部菜单
                bottomBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            }

            override fun keyBoardHide(height: Int) {

            }

        })
        //点击item的触发
        base_search_poi_recycler.addOnItemTouchListener(object :
            RecyclerViewClickListenerImpl(base_search_poi_recycler, object :
                RecyclerViewOnItemClickListener {
                override fun onItemClick(view: View?, position: Int) {
                    //点击条目，准备进行跳转的处理
                    onPostResult(adapter.data[position])
                }

                override fun onItemLongClick(view: View?, position: Int) {

                }
            }) {})
        //定位回乘客当前位置
        base_fa_user_location.setOnClickListener {
            //需要判断线路的信息是否已经获取，获取以后才可以进行跳转
            if (isMapLoaded) {
                //移动地图到当前点
                latLng = UserLocationCache.getLocationLatLng()
                mapView.map.animateCamera(CameraUpdateFactory.newLatLngZoom(UserLocationCache.getLocationLatLng(), 16f))
                searchPoi(latLng!!)
            }
        }


    }


    /**
     * 发送数据，后期可以在这里扩展为 广播或者原生回调
     *
     */
    private fun onPostResult(result: PoiInfo) {
        val posMode = arguments?.getInt("posMode", 0) ?: 0
        val flagSrc = arguments?.getString("flagSrc") ?: ""
        EventBus.getDefault().post(SearchPoiEvent(posMode, flagSrc, result))
        finishThis()
    }


    /**
     * 给view添加 变透明和模糊的动画
     *
     */
    private fun setViewAnimator(view: View, slideOffset: Float) {
        view.visibility = View.VISIBLE
        view.alpha = 1f - slideOffset
        view.scaleY = 1f - slideOffset
    }


    private fun formScreenLatLng() {
        if (isMapLoaded) {
            val x = marker.left + marker.width / 2
            val y = marker.top + marker.height
            val latLng = mapView.map.projection.fromScreenLocation(Point(x, y))
            searchPoi(latLng)
        }
    }

    private fun searchPoi(latLng: LatLng) {
        poi_map_poiInfo_name_tv.text = "正在搜索"
        poi_map_poiInfo_add_tv.text = null
        poiInfo = null
        this.latLng = latLng
        geoSearchPresenter.search(latLng)
    }


    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }


    override fun onDestroyView() {
        mapView.onDestroy()
        super.onDestroyView()
    }

    override fun onRequestBegin(presenter: Any?) {

    }

    override fun onRequestFinish(presenter: Any?) {

    }

    override fun onFail(presenter: Any?, msg: String?) {

    }
}