package com.parg3v.domain.use_cases

class ValidatePhoneUseCase {
    operator fun invoke(input: String): Boolean = Regex("^\$|^[0-68-9][0-9]{0,9}\$").matches(input)
}