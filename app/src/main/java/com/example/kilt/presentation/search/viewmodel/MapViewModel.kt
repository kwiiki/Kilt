package com.example.kilt.presentation.search.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.kilt.models.PropertyCoordinate
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.dgis.sdk.coordinates.GeoPoint
import ru.dgis.sdk.geometry.Elevation
import ru.dgis.sdk.geometry.GeoPointWithElevation
import ru.dgis.sdk.map.Camera
import ru.dgis.sdk.map.CameraPosition
import ru.dgis.sdk.map.CameraState
import ru.dgis.sdk.map.Image
import ru.dgis.sdk.map.Map
import ru.dgis.sdk.map.MapObjectManager
import ru.dgis.sdk.map.Marker
import ru.dgis.sdk.map.MarkerOptions
import ru.dgis.sdk.map.Zoom
import javax.inject.Inject


@HiltViewModel
class MapViewModel @Inject constructor() : ViewModel() {
    var mapObjectManager: MapObjectManager? = null
    var lastCameraPosition: CameraPosition? = null
    var lastCameraState: CameraState? = null
        private set

    fun initializeMap(map: Map) {
        if (lastCameraPosition == null) {
            map.camera.move(
                CameraPosition(
                    point = GeoPoint(43.238949, 76.889709),
                    zoom = Zoom(value = 12.0f)
                )
            )
        } else {
            map.camera.move(lastCameraPosition!!)
        }
    }

    fun updateMarkers(mapPoints: List<PropertyCoordinate>, icon: Image) {
        mapObjectManager?.let { manager ->
            manager.removeAll() // Удаляем предыдущие маркеры
            mapPoints.forEach { point ->
                val marker = Marker(
                    MarkerOptions(
                        position = GeoPointWithElevation(
                            latitude = point.lat,
                            longitude = point.lng,
                            elevation = Elevation(0f)
                        ),
                        text = "ID: ${point.id}",
                        icon = icon,
                        draggable = false
                    )
                )
                manager.addObject(marker) // Добавляем новый маркер
            }
            Log.d("MapViewModel", "Updated markers: ${mapPoints.size}")
        }
    }
    fun restoreCameraPosition(camera: Camera) {
        lastCameraPosition?.let { savedPosition ->
            camera.move(
                point = savedPosition.point,
                zoom = savedPosition.zoom,
                tilt = savedPosition.tilt,
                bearing = savedPosition.bearing
            )
            Log.d("MapViewModel", "Restored camera position: $savedPosition")
        } ?: Log.d("MapViewModel", "No saved camera position to restore")
    }



    fun saveCameraState(camera: Camera) {
        lastCameraState = camera.state
    }
}