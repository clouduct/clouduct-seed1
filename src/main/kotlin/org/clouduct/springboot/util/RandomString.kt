package org.clouduct.springboot.util

import java.security.SecureRandom
import java.util.*

class RandomString @JvmOverloads constructor(length: Int = 21, random: Random = SecureRandom(), symbols: String = alphanum) {

    /**
     * Generate a random string.
     */
    fun nextString(): String {
        for (idx in buf.indices)
            buf[idx] = symbols[random.nextInt(symbols.size)]
        return String(buf)
    }

    private val random: Random

    private val symbols: CharArray

    private val buf: CharArray

    init {
        if (length < 1) throw IllegalArgumentException()
        if (symbols.length < 2) throw IllegalArgumentException()
        this.random = Objects.requireNonNull(random)
        this.symbols = symbols.toCharArray()
        this.buf = CharArray(length)
    }

    companion object {
        val upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        val lower = upper.toLowerCase(Locale.ROOT)
        val digits = "0123456789"
        val alphanum = upper + lower + digits
    }

}