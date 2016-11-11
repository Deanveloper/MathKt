package com.deanveloper.mathkt.expression.trigexpression

import com.deanveloper.mathkt.expression.Expression
import com.deanveloper.mathkt.expression.value.RealValue
import com.deanveloper.mathkt.expression.twopartexpression.MultiplicationExpression
import com.deanveloper.mathkt.sin

/**
 * @author Dean
 */
class SineExpression(
        variables: CharArray,
        f: Expression,
        isNegative: Boolean = false
) : Expression.TrigExpression(variables, f, isNegative) {
    constructor(variable: Char,
                f: Expression,
                isNegative: Boolean = false
    ) : this(charArrayOf(variable), f, isNegative)

    override fun execute(args: Map<Char, Expression>): SineExpression {
        return SineExpression(vars, f.execute(args), isNegative)
    }

    override fun derive(variable: Char): Expression {
        return MultiplicationExpression(vars,
                CosineExpression(vars, f, isNegative),
                f.derive(variable)
        )
    }

    override fun simplify(): Expression {
        val simp = SineExpression(vars, f.simplify(), isNegative)
        with(simp) {
            if (f.isNegative) {
                return SineExpression(vars, -f, !isNegative).simplify()
            }

            if (f is RealValue) {
                return RealValue(f.value.sin()).simplify()
            }

            return this
        }
    }

    override fun unaryMinus(): Expression {
        return SineExpression(vars, f, !isNegative)
    }

    override fun toString(): String {
        return "sin($f)"
    }
}