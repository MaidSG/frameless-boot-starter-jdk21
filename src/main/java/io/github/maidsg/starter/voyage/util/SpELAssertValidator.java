package io.github.maidsg.starter.voyage.util;

import io.github.maidsg.starter.voyage.annotation.SpELAssert;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;



/*******************************************************************
 * <pre></pre>
 * @文件名称： SpELAssertValidator.java
 * @包 路  径： io.github.maidsg.starter.start.util
 * @Copyright：wy (C) 2024 *
 * @Description:
 * @Version: V1.0
 * @Author： wy
 * @Date：2024/3/23 10:01
 * @Modify：
 */
public class SpELAssertValidator implements ConstraintValidator<SpELAssert, Object> {

    private ExpressionParser parser = new SpelExpressionParser();
    private String expression;

    @Override
    public void initialize(SpELAssert constraintAnnotation) {
        expression = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        StandardEvaluationContext standardEvaluationContext = new StandardEvaluationContext();
        standardEvaluationContext.setRootObject(value);
        return parser.parseExpression(expression).getValue(standardEvaluationContext, Boolean.class);
    }

}
