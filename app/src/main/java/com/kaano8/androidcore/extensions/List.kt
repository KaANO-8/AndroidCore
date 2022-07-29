package com.kaano8.androidcore.com.kaano8.androidcore.extensions

import com.kaano8.androidcore.com.kaano8.androidcore.coroutine.network.models.User

fun List<User>.aggregate(): List<User> =
    groupBy { it.login }
        .map { (login, group) -> User(login, group.sumBy { it.contributions }) }
        .sortedByDescending { it.contributions }