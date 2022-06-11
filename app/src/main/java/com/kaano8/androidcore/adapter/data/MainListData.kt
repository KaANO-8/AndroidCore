package com.kaano8.androidcore.com.kaano8.androidcore.adapter.data

sealed class MainListData {
    class HeaderItem(val header: String): MainListData()
    class DataItem(val title: String, val detail: String, val action: () -> Unit): MainListData()
}

