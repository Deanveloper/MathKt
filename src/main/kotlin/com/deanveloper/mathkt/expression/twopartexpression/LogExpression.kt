package com.deanveloper.mathkt.expression.twopartexpression

import com.deanveloper.mathkt.expression.Expression
import com.deanveloper.mathkt.expression.value.IntValue
import com.deanveloper.mathkt.expression.value.IrrationalValue
import com.deanveloper.mathkt.expression.value.RealValue

class LogExpression(
        variables: CharArray,
        val base: Expression,
        f: Expression,
        isNegative: Boolean = false
) : Expression.TwoPartExpression(variables, f, base, isNegative) {
    constructor(
            variable: Char,
            base: Expression,
            f: Expression,
            isNegative: Boolean = false
    ) : this(charArrayOf(variable), base, f, isNegative)

    override fun execute(args: Map<Char, Expression>): LogExpression {
        return LogExpression(vars, base.execute(args), f.execute(args))
    }

    override fun derive(variable: Char): Expression {
        if (base is RealValue) {
            if (base == IrrationalValue.E) {
                // when f(x) = ln(x), f'(x) is x'/x
                return f.derive(variable) / f
            } else {
                // when f(x) = logBASE(c, x) and c is constant, f'(x) = (x'/(x * ln(c))
                return (f.derive(variable) / (f * LogExpression(vars, IrrationalValue.E, base)))
            }
        } else {
            // when h(x) = logBASE(b(x), f(x)), h(x) = ln(f(x)) / ln(b(x)) and use quotient rule to get h'(x)
            return (LogExpression(vars, IrrationalValue.E, f)
                            / LogExpression(vars, IrrationalValue.E, base)
                    ).derive(variable)
        }
    }

    override fun simplify(): Expression {
        val simp = LogExpression(vars, base.simplify(), f.simplify())
        with(simp) {
            if (f is ExponentialExpression) {
                return MultiplicationExpression(vars, f.g, LogExpression(vars, base, f.f)).simplify()
            }
            if (f is RealValue) {
                if (f == IntValue[1]) {
                    return IntValue[0]
                }

                if (base is RealValue) {
                    if (base == f) {
                        return IntValue[1]
                    }
                }
            }

            return this
        }
    }

    override fun unaryMinus(): Expression {
        return LogExpression(vars, f, g, !isNegative)
    }

    override fun toString(): String {
        if (base is RealValue && base == IrrationalValue.E) {
            return "${if (isNegative) "-" else ""}ln($f)"
        } else {
            return "${if (isNegative) "-" else ""}logBASE($base,$f)"
        }
    }
}