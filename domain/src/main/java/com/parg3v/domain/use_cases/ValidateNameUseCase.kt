package com.parg3v.domain.use_cases

class ValidateNameUseCase{
    operator fun invoke(input: String): Boolean = Regex("[а-яА-ЯёЁ]+").matches(input)
}