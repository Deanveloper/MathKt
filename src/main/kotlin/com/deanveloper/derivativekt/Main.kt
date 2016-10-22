package com.deanveloper.derivativekt

import java.math.BigDecimal

/**
 * @author Dean
 */
fun main(vararg args: String) {
    println(
            ExponentialExpression(
                    AdditionExpression(
                            Value(BigDecimal.valueOf(5)),
                            Variable('x')
                    ),
                    Variable('x')
            ).invoke(Value(BigDecimal.valueOf(5)))
    )
}