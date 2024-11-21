package com.example.kilt.presentation.search.viewmodel

import androidx.lifecycle.ViewModel
import ru.dgis.sdk.map.MapObjectManager
import ru.dgis.sdk.map.Marker
import ru.dgis.sdk.map.Map


class MapViewModel : ViewModel() {
    var mapObjectManager: MapObjectManager? = null

    fun addMarker(map: Map, marker: Marker) {
        if (mapObjectManager == null) {
            mapObjectManager = MapObjectManager(map)
        }
        mapObjectManager?.addObject(marker)
    }
}