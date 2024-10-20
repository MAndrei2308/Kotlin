package com.internship.dwd.util

import com.internship.dwd.model.Command

object CLI {

    fun readCommand(): Command? {
        val readCommand = readlnOrNull()?.trim()?.lowercase()?.replaceFirstChar { it.uppercase() }

        return runCatching {
            Command.valueOf(readCommand!!)
        }.getOrNull()
    }
}