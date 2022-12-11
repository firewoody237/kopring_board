package com.example.kopring_board.integrated.common

import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec
import javax.persistence.AttributeConverter


class PasswordConverter : AttributeConverter<String, String> {

    private val ALGORITHM = "AES/ECB/PKCS5Padding"
    private val KEY = "pringtesterkopringkopringkopring".toByteArray()

    override fun convertToDatabaseColumn(raw: String?): String? {
        return encoded(raw)
    }

    private fun encoded(raw: String?): String {
        val key = SecretKeySpec(KEY, "AES")
        return try {
            val cipher = Cipher.getInstance(ALGORITHM)
            cipher.init(Cipher.ENCRYPT_MODE, key)
            println("raw = ${raw}")
            String(Base64.getEncoder().encode(cipher.doFinal(raw?.toByteArray())))
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    override fun convertToEntityAttribute(encoded: String?): String? {
        return decode(encoded)
    }

    private fun decode(encoded: String?) : String?{
        val key = SecretKeySpec(KEY, "AES")
        return try {
            val cipher = Cipher.getInstance(ALGORITHM)
            cipher.init(Cipher.DECRYPT_MODE, key)
            String(cipher.doFinal(Base64.getDecoder().decode(encoded)))
        } catch (e: java.lang.Exception) {
            throw RuntimeException(e)
        }
    }
}