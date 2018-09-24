/*
 * Copyright (C) Keanu Poeschko - All Rights Reserved
 * Unauthorized copying of this file is strictly prohibited
 *
 * Created by Keanu Poeschko <nur1popcorn@gmail.com>, August 2017
 * This file is part of {BASM}.
 *
 * Do not copy or distribute files of {BASM} without permission of {Keanu Poeschko}
 *
 * Permission to use, copy, modify, and distribute my software for
 * educational, and research purposes, without a signed licensing agreement
 * and for free, is hereby granted, provided that the above copyright notice
 * and this paragraph appear in all copies, modifications, and distributions.
 *
 * {BASM} is based on this document: https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html
 *
 */

package buttonbase

import child
import react.RBuilder
import react.RProps
import react.ReactElement

interface ButtonBaseActions {
    fun focusVisible()
}

interface ButtonBaseProps : RProps {
    var action : ((ButtonBaseActions) -> Unit)
    var centerRipple : Boolean
    var component : dynamic
    var disabled : Boolean
    var disableRipple : Boolean
    var disableTouchRipple : Boolean
    var focusRipple : Boolean
    var focusVisibleClassName : String
    var onFocusVisible : (() -> Unit)
    @JsName("TouchRippleProps")
    var touchRippleProps : RProps
    var type : String
}

/**
 * https://material-ui.com/api/button-base/
 */
fun RBuilder.ButtonBase(
    action : ((ButtonBaseActions) -> Unit)? = null,
    centerRipple : Boolean = false,
    component : dynamic = "button",
    disabled : Boolean? = null,
    disableRipple : Boolean = false,
    disableTouchRipple : Boolean = false,
    focusRipple : Boolean = false,
    focusVisibleClassName : String? = null,
    onFocusVisible : (() -> Unit)? = null,
    touchRippleProps : RProps? = null,
    type : String = "button",
    block : RBuilder.() -> Unit
) : ReactElement {
    return child<ButtonBaseProps>(default) {
        attrs {
            action?.let { this.action = action }
            this.centerRipple = centerRipple
            this.component = component
            disabled?.let { this.disabled = disabled }
            this.disableRipple = disableRipple
            this.disableTouchRipple = disableTouchRipple
            this.focusRipple = focusRipple
            focusVisibleClassName?.let { this.focusVisibleClassName = focusVisibleClassName }
            onFocusVisible?.let { this.onFocusVisible = onFocusVisible }
            touchRippleProps?.let { this.touchRippleProps = touchRippleProps }
            this.type = type
        }
        block()
    }
}
