package com.example.kilt.models.dataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.kilt.models.authentification.User
import com.example.kilt.models.authentification.UserWithMetadata
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


val Context.userDataStore: DataStore<Preferences> by preferencesDataStore(name = "user_data")

class UserDataStoreManager(private val context: Context) {

    companion object {
        val USER_NAME_KEY = stringPreferencesKey("user_name")
        val USER_IIN_KEY = stringPreferencesKey("user_iin")
        val USER_LAST_NAME_KEY = stringPreferencesKey("user_last_name")
        val USER_EMAIL_KEY = stringPreferencesKey("user_email")
        val USER_BIRTH_DATE = stringPreferencesKey("user_birth_day")
        val USER_PHONE_KEY = stringPreferencesKey("user_phone")
        val USER_ID_KEY = intPreferencesKey("user_id")

        val USER_BONUS = intPreferencesKey("user_bonus")
        val USER_CREATED = booleanPreferencesKey("user_created")
        val USES_EXPIRED = booleanPreferencesKey("user_expired")
        val USER_TOKEN = stringPreferencesKey("user_token")

        val AGENCY_VERIFICATION_STATUS = intPreferencesKey("agency_verification_status")
        val AGENT_ABOUT = stringPreferencesKey("agent_about")
        val AGENT_CITY = stringPreferencesKey("agent_city")
        val AGENT_FULL_ADDRESS = stringPreferencesKey("agent_full_address")
        val AGENT_WORKING_HOURS = stringPreferencesKey("agent_working_hours")
        val CREATED_AT = stringPreferencesKey("created_at")
        val DELETED = intPreferencesKey("deleted")
        val DISCOUNT = intPreferencesKey("discount")
        val GENDER = stringPreferencesKey("gender")
        val INSTAGRAM = stringPreferencesKey("instagram")
        val IS_BLOCKED = booleanPreferencesKey("is_blocked")
        val KR_AGENCY_ID = intPreferencesKey("kr_agency_id")
        val KR_AGENCY_NAME = stringPreferencesKey("kr_agency_name")
        val KR_LISTING_TYPE = stringPreferencesKey("kr_listing_type")
        val KR_NAME = stringPreferencesKey("kr_name")
        val KR_USER_ID = intPreferencesKey("kr_user_id")
        val PHOTO = stringPreferencesKey("photo")
        val REFERRED_BY = stringPreferencesKey("referred_by")
        val UPDATED_AT = stringPreferencesKey("updated_at")
        val USER_TYPE = stringPreferencesKey("user_type")
    }
    suspend fun saveUserData(
        user: User,
        bonus: Int,
        created: Boolean,
        expired: Boolean,
        token: String
    ) {
        context.userDataStore.edit { preferences ->
            preferences[USER_NAME_KEY] = user.firstname
            preferences[USER_EMAIL_KEY] = user.email
            preferences[USER_PHONE_KEY] = user.phone
            preferences[USER_ID_KEY] = user.id
            preferences[USER_IIN_KEY] = user.iin
            preferences[USER_LAST_NAME_KEY] = user.lastname
            preferences[USER_BIRTH_DATE] = user.birthdate

            preferences[USER_BONUS] = bonus
            preferences[USER_CREATED] = created
            preferences[USES_EXPIRED] = expired
            preferences[USER_TOKEN] = token

            preferences[AGENCY_VERIFICATION_STATUS] = user.agency_verification_status
            preferences[AGENT_ABOUT] = user.agent_about
            preferences[AGENT_CITY] = user.agent_city
            preferences[AGENT_FULL_ADDRESS] = user.agent_full_address
            preferences[AGENT_WORKING_HOURS] = user.agent_working_hours
            preferences[CREATED_AT] = user.createdAt
            preferences[DELETED] = user.deleted
            preferences[DISCOUNT] = user.discount
            preferences[GENDER] = user.gender
            preferences[INSTAGRAM] = user.instagram
            preferences[IS_BLOCKED] = user.is_blocked
            preferences[KR_AGENCY_ID] = user.kr_agency_id
            preferences[KR_AGENCY_NAME] = user.kr_agency_name
            preferences[KR_LISTING_TYPE] = user.kr_listing_type
            preferences[KR_NAME] = user.kr_name
            preferences[KR_USER_ID] = user.kr_user_id
            preferences[PHOTO] = user.photo
            preferences[REFERRED_BY] = user.referred_by
            preferences[UPDATED_AT] = user.updatedAt
            preferences[USER_TYPE] = user.user_type
        }
    }
    val userDataFlow: Flow<UserWithMetadata?> = context.userDataStore.data
        .map { preferences ->
            val id = preferences[USER_ID_KEY] ?: -1
            if (id != -1) {
                UserWithMetadata(
                    user = User(
                        id = id,
                        firstname = preferences[USER_NAME_KEY] ?: "",
                        lastname = preferences[USER_LAST_NAME_KEY] ?: "",
                        email = preferences[USER_EMAIL_KEY] ?: "",
                        phone = preferences[USER_PHONE_KEY] ?: "",
                        iin = preferences[USER_IIN_KEY] ?: "",
                        birthdate = preferences[USER_BIRTH_DATE] ?: "",
                        agency_verification_status = preferences[AGENCY_VERIFICATION_STATUS] ?: 0,
                        agent_about = preferences[AGENT_ABOUT] ?: "",
                        agent_city = preferences[AGENT_CITY] ?: "",
                        agent_full_address = preferences[AGENT_FULL_ADDRESS] ?: "",
                        agent_working_hours = preferences[AGENT_WORKING_HOURS] ?: "",
                        createdAt = preferences[CREATED_AT] ?: "",
                        deleted = preferences[DELETED] ?: 0,
                        discount = preferences[DISCOUNT] ?: 0,
                        gender = preferences[GENDER] ?: "",
                        instagram = preferences[INSTAGRAM] ?: "",
                        is_blocked = preferences[IS_BLOCKED] ?: false,
                        kr_agency_id = preferences[KR_AGENCY_ID] ?: 0,
                        kr_agency_name = preferences[KR_AGENCY_NAME] ?: "",
                        kr_listing_type = preferences[KR_LISTING_TYPE] ?: "",
                        kr_name = preferences[KR_NAME] ?: "",
                        kr_user_id = preferences[KR_USER_ID] ?: 0,
                        photo = preferences[PHOTO] ?: "",
                        referred_by = preferences[REFERRED_BY] ?: "",
                        updatedAt = preferences[UPDATED_AT] ?: "",
                        user_type = preferences[USER_TYPE] ?: ""
                    ),
                    bonus = preferences[USER_BONUS] ?: 0,
                    created = preferences[USER_CREATED] ?: false,
                    expired = preferences[USES_EXPIRED] ?: false,
                    token = preferences[USER_TOKEN] ?: ""
                )
            } else null
        }

    suspend fun clearUserData() {
        context.userDataStore.edit { it.clear() }
    }
}