package com.example.kopring_board.integrated.db.entity

import javax.persistence.AttributeConverter

class PasswordConverter : AttributeConverter<String, String> {
    override fun convertToDatabaseColumn(raw: String?): String? {
        return encoded(raw)
    }

    private fun encoded(raw: String?): String {
        return raw ?: ""
    }

    override fun convertToEntityAttribute(encoded: String?): String? {
        return decode(encoded)
    }

    private fun decode(encoded: String?) : String?{
        return ""
    }
}