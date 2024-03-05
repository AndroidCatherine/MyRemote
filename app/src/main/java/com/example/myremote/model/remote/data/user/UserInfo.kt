package com.example.myremote.model.remote.data.user

import com.example.myremote.model.remote.data.DataObj

/**
 * Created by Catherine on 01/03/2024
 */
data class UserInfo(
    val objectId: String,
    val name: String,
    val timezone: String,
    val phone: String,
    val createdAt: String,
    val updatedAt: String,
    val sessionToken: String
) : DataObj()