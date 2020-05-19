package com.cictec.ibd.base.model

/**
 * CopyRight (c)2020: <北京中航讯科技股份有限公司>
 *
 *  发送回执时间
 *
 *  type  是发送的标记码
 *  flagSrc  发起者的标记
 *  poiInfo 返回搜索的数据
 *
 *
 * @author    HanKun
 * @since      2020/4/22
 * @version   1.0
 */
data class SearchPoiEvent(val type: Int, val flagSrc: String, val poiInfo: PoiInfo)