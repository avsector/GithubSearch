@file:JvmName("Injector")

package com.samsaz.githubsearch.dagger

import com.samsaz.githubsearch.search.SearchActivity

/**
 * Copyright 2018
 * Created and maintained by Hamid Moazzami
 */

fun inject(searchActivity: SearchActivity) {
    DaggerSearchComponent.builder()
        .coreComponent(MyApplication.coreComponent(searchActivity))
        .build()
        .inject(searchActivity)
}