//package com.example.kilt.viewmodels
//
//import androidx.paging.PagingSource
//import androidx.paging.PagingState
//import com.example.kilt.data.Filters
//import com.example.kilt.data.PropertyItem
//import com.example.kilt.data.TConfig
//import com.example.kilt.data.THomeSale
//import com.example.kilt.repository.SearchRepository
//
//class PropertyPagingSource(
//    private val searchRepository: SearchRepository,
//    private val filters: Filters
//) : PagingSource<Int, PropertyItem>() {
//
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PropertyItem> {
//        try {
//            val page = params.key ?: 1
//            val pageSize = params.loadSize
//
//            val response = searchRepository.performSearch(
//                THomeSale(
//                    filters = filters,
//                    config = TConfig(
//                        residential_complex = "residential-complex",
//                        num_rooms = "list",
//                        price = "range",
//                        built_year = "range",
//                        construction_type = "list",
//                        floor = "list",
//                        description = "",
//                        num_floors = "range",
//                        furniture = "list",
//                        area = "range",
//                        kitchen_area = "range",
//                        is_bailed = "list",
//                        former_dormitory = "list",
//                        bathroom = "list",
//                        internet = "list",
//                        balcony = "list",
//                        balcony_glass = "list",
//                        door = "list",
//                        parking = "list",
//                        floor_material = "list",
//                        security = "list",
//                        status = "",
//                        user_type = "list",
//                        kato_path = "like",
//                        lat = "range",
//                        lng = "range"
//                    ),
//                    page = page,
//                    sorting = "new"
//                )
//            )
//
//            return LoadResult.Page(
//                data = response.list,
//                prevKey = if (page == 1) null else page - 1,
//                nextKey = if (response.list.isEmpty()) null else page + 1
//            )
//        } catch (e: Exception) {
//            return LoadResult.Error(e)
//        }
//    }
//
//    override fun getRefreshKey(state: PagingState<Int, PropertyItem>): Int? {
//        return state.anchorPosition?.let { anchorPosition ->
//            val anchorPage = state.closestPageToPosition(anchorPosition)
//            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
//        }
//    }
//}