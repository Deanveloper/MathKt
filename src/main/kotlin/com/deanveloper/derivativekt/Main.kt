package com.deanveloper.derivativekt

import com.deanveloper.derivativekt.expression.*
import com.deanveloper.derivativekt.expression.twopartexpression.AdditionExpression
import com.deanveloper.derivativekt.expression.twopartexpression.ExponentialExpression
import com.deanveloper.derivativekt.expression.twopartexpression.LogExpression
import com.deanveloper.derivativekt.expression.twopartexpression.MultiplicationExpression
import java.math.BigDecimal

/**
 * @author Dean
 */
fun main(vararg args: String) {
    val exp = LogExpression('x',
            Value(E),
            AdditionExpression('x',
                    ExponentialExpression('x', Variable('x'), Value(BigDecimal.valueOf(3))),
                    MultiplicationExpression('x', Value(BigDecimal.ZERO), Variable('x'))
            )
    )

    println(exp)
    println(exp.simplify())
    println(exp.derive('x'))
    println(exp.derive('x').simplify())

    println(exp(Value(BigDecimal.valueOf(5))).simplify())
}