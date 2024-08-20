package com.example.kilt.data.config

import com.example.kilt.data.ActiveBusinessList
import com.example.kilt.data.BathroomInside
import com.example.kilt.data.BusinessConditionList
import com.example.kilt.data.BusinessEntranceList
import com.example.kilt.data.BusinessParkingList
import com.example.kilt.data.CommunicationsList
import com.example.kilt.data.Condition
import com.example.kilt.data.ConditionList
import com.example.kilt.data.ConstructionTypes
import com.example.kilt.data.Door
import com.example.kilt.data.DrinkingWaterList
import com.example.kilt.data.Electricity
import com.example.kilt.data.ElectricityList
import com.example.kilt.data.FormerDormitory
import com.example.kilt.data.FreePlanningList
import com.example.kilt.data.FurnitureList
import com.example.kilt.data.HasRentersList
import com.example.kilt.data.HeatingList
import com.example.kilt.data.Internet
import com.example.kilt.data.IrrigationWaterList
import com.example.kilt.data.IsBailed
import com.example.kilt.data.ListOfConveniences
import com.example.kilt.data.NumRooms
import com.example.kilt.data.Security
import com.example.kilt.data.SewerList
import com.example.kilt.data.TelephoneList

data class PropMapping(
    val designation: Designation,
    val where_located: Located,
    val line_of_houses: Houseline,
    val property_type: PropertyType,
    val furniture: Furniture,
    val num_rooms: NumRooms,
    val construction_type: ConstructionTypes,
    val floor:Floor,
    val furniture_list: FurnitureList,
    val new_conveniences: ListOfConveniences,
    val rent_period:RentPeriod,
    val bathroom:Bathroom,
    val new_balcony:NewBalcony,
    val bathroom_inside:BathroomInside,
    val security:Security,
    val balcony: Balcony,
    val toilet_separation:ToiletSeparation,
    val loggia: Loggia,
    val windows: Windows,
    val suits_for:SuitsFor,
    val is_bailed:IsBailed,
    val former_dormitory:FormerDormitory,
    val internet:Internet,
    val balcony_glass:BalconyGlass,
    val door:Door,
    val parking:Parking,
    val floor_material:FloorMaterial,
    val condition:ConditionList,
    val heating:HeatingList,
    val sewer:SewerList,
    val drinking_water:DrinkingWaterList,
    val irrigation_water:IrrigationWaterList,
    val electricity: ElectricityList,
    val telephone: TelephoneList,
    val active_business : ActiveBusinessList,
    val has_renters: HasRentersList,
    val free_planning: FreePlanningList,
    val business_condition : BusinessConditionList,
    val business_parking: BusinessParkingList,
    val business_entrance: BusinessEntranceList,
    val communications : CommunicationsList




    )