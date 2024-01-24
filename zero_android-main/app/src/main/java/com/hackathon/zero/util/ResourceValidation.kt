package com.hackathon.zero.util

import com.hackathon.zero.core.Resource
import com.hackathon.zero.util.Constants.ERROR_UNKNOWN

fun <T> isLoading(resource: Resource<T>) = resource == Resource.loading(null)

fun <T> isSuccess(resource: Resource<T>) = resource == Resource.success(resource.data)

fun <T> isError(resource: Resource<T>) = resource == Resource.error(resource.message ?: ERROR_UNKNOWN, null)