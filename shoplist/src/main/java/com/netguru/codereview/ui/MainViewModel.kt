package com.netguru.codereview.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.netguru.codereview.network.ShopListApiMock
import com.netguru.codereview.network.ShopListRepository
import com.netguru.codereview.network.model.ShopListItemResponse
import com.netguru.codereview.network.model.ShopListResponse
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject
//import com.netguru.codereview.di.component.DaggerAppComponent

class MainViewModel @Inject constructor() : ViewModel() {

//    var appComponent: AppComponent = DaggerAppComponent.create()

    private val shopListRepository = ShopListRepository(ShopListApiMock())

    private val _shopLists = MutableLiveData<List<Pair<ShopListResponse, List<ShopListItemResponse>>>>()
    val shopLists: LiveData<List<Pair<ShopListResponse, List<ShopListItemResponse>>>>
        get() = _shopLists

    private val _eventLiveData = MutableLiveData<String>()
    val events: LiveData<String>
        get() = _eventLiveData

    init {
        viewModelScope.launch {
            val lists = shopListRepository.getShopLists()
            val data = mutableListOf<Pair<ShopListResponse, List<ShopListItemResponse>>>()
            for (list in lists) {
                val items = shopListRepository.getShopListItems(list.list_id)
                data.add(list to items)
            }
            _shopLists.postValue(data)
        }
        getUpdateEvents()
    }


    private fun getUpdateEvents() {
        viewModelScope.launch {
            shopListRepository.updateEvents().collect {
                _eventLiveData.postValue(it)
            }
        }
    }
}
