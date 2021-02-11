package com.nezamipour.mehdi.moviebaz.network.exception

import java.io.IOException

class NotFoundException : IOException() {

    override val message: String?
        get() = "Not Found"
}