package com.gsr.gsr_yatm.utilities.contract.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Indicates a value should by contract never be negative.
 * Intended only for integral numerics.
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.TYPE_USE})
public @interface NotNegative 
{

} // end annotation